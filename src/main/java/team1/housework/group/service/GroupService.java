package team1.housework.group.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team1.housework.character.service.CharacterService;
import team1.housework.group.entity.Group;
import team1.housework.group.entity.GroupMember;
import team1.housework.group.entity.HouseWork;
import team1.housework.group.entity.HouseWorkMember;
import team1.housework.group.entity.HouseWorkTag;
import team1.housework.group.entity.Place;
import team1.housework.group.entity.Tag;
import team1.housework.group.repository.GroupMemberRepository;
import team1.housework.group.repository.GroupRepository;
import team1.housework.group.repository.PlaceRepository;
import team1.housework.group.repository.housework.HouseWorkRepository;
import team1.housework.group.repository.houseworkmember.HouseWorkMemberRepository;
import team1.housework.group.repository.houseworktag.HouseWorkTagRepository;
import team1.housework.group.repository.tag.TagRepository;
import team1.housework.group.service.dto.EnterRequest;
import team1.housework.group.service.dto.EnterResponse;
import team1.housework.group.service.dto.GroupRequest;
import team1.housework.group.service.dto.GroupResponse;
import team1.housework.group.service.dto.HouseWorkListByDateResponse;
import team1.housework.group.service.dto.HouseWorkResponse;
import team1.housework.group.service.dto.HouseWorkSaveRequest;
import team1.housework.group.service.dto.HouseWorkStatusByPeriodResponse;
import team1.housework.group.service.dto.MemberResponse;
import team1.housework.group.service.dto.MyGroupResponse;
import team1.housework.group.service.dto.PlaceResponse;
import team1.housework.group.service.dto.TagForHouseWorkListResponse;
import team1.housework.group.service.dto.TagResponse;
import team1.housework.group.service.generator.InviteCodeGenerator;
import team1.housework.group.service.policy.DayOfWeekPolicy;
import team1.housework.group.service.policy.RoutinePolicy;
import team1.housework.member.entity.Member;
import team1.housework.member.repository.MemberRepository;
import team1.housework.member.service.MemberService;
import team1.housework.preset.service.PresetService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupService {

	private final GroupRepository groupRepository;
	private final PlaceRepository placeRepository;
	private final TagRepository tagRepository;
	private final GroupMemberRepository groupMemberRepository;
	private final HouseWorkRepository houseWorkRepository;
	private final HouseWorkTagRepository houseWorkTagRepository;
	private final HouseWorkMemberRepository houseWorkMemberRepository;

	private final InviteCodeGenerator inviteCodeGenerator;

	private final CharacterService characterService;
	private final PresetService presetService;
	private final MemberService memberService;
	private final MemberRepository memberRepository;

	@Transactional
	public GroupResponse save(Member member, GroupRequest groupRequest) {
		Boolean existsByCharacterId = characterService.existsById(groupRequest.characterId());
		if (!existsByCharacterId) {
			throw new NoSuchElementException("Character does not exist");
		}
		//초대코드 생성
		String inviteCode = inviteCodeGenerator.generateInviteCode();
		//그룹생성
		Group group = new Group(groupRequest.characterId(), inviteCode);
		groupRepository.save(group);
		//요청자 그룹 멤버에 추가
		GroupMember groupMember = new GroupMember(group, member.getId());
		groupMemberRepository.save(groupMember);
		//장소, 태그 프리셋 복사해 생성
		List<Place> places = presetService.getAllPlaces()
			.stream()
			.map(it -> new Place(it.getName(), group))
			.toList();
		placeRepository.saveAll(places);

		List<Tag> tags = presetService.getAllTags()
			.stream()
			.map(it -> new Tag(it.getName(), group))
			.toList();
		tagRepository.saveAll(tags);

		return new GroupResponse(group.getId(), group.getInviteCode());
	}

	@Transactional
	public EnterResponse enter(Member member, EnterRequest enterRequest) {
		Group group = groupRepository.findByInviteCode(enterRequest.inviteCode())
			.orElseThrow(() -> new NoSuchElementException("Invite code does not exist"));
		//멤버추가
		GroupMember groupMember = new GroupMember(group, member.getId());
		groupMemberRepository.save(groupMember);
		return new EnterResponse(group.getId());
	}

	public List<PlaceResponse> getPlaces(Long groupId) {
		validateGroupExists(groupId);
		return placeRepository.findByGroupId(groupId)
			.stream()
			.map(it -> new PlaceResponse(it.getId(), it.getName()))
			.toList();
	}

	public List<TagResponse> getTags(Long groupId) {
		validateGroupExists(groupId);
		return tagRepository.findByGroupId(groupId)
			.stream()
			.map(it -> new TagResponse(it.getId(), it.getName()))
			.toList();
	}

	public List<MemberResponse> getMembers(Long groupId) {
		validateGroupExists(groupId);
		return groupMemberRepository.findByGroupId(groupId)
			.stream()
			.map(it -> {
				Member member = memberService.findById(it.getId());
				return new MemberResponse(
					member.getId(),
					member.getName() == null ? null : member.getName(),
					member.getProfileImageUrl() == null ? null : member.getProfileImageUrl()
				);
			})
			.toList();

	}

	private void validateGroupExists(Long groupId) {
		boolean exists = groupRepository.existsById(groupId);
		if (!exists) {
			throw new NoSuchElementException("Group does not exist");
		}
	}

	public MyGroupResponse getMyGroup(Member member) {
		GroupMember groupMember = groupMemberRepository.findFirstByMemberId(member.getId())
			.orElseThrow(() -> new NoSuchElementException("Group Member does not exist"));
		return new MyGroupResponse(groupMember.getGroup().getId());
	}

	@Transactional
	public void saveHouseWork(Long groupId, HouseWorkSaveRequest request) {
		Group group = groupRepository.findById(groupId)
			.orElseThrow(() -> new NoSuchElementException("Group does not exist"));

		Place place = placeRepository.findById(request.placeId())
			.orElseThrow(() -> new NoSuchElementException("Place does not exist"));

		RoutinePolicy policy = RoutinePolicy.valueOf(request.routinePolicy());
		List<DayOfWeek> targetDays = request.dayOfWeek().stream()
			.map(d -> DayOfWeekPolicy.valueOf(d).toJavaDayOfWeek())
			.toList();

		LocalDate current = request.startDate();

		while (!current.isAfter(request.dueDate())) {
			if (policy.shouldAdd(request.startDate(), current, targetDays)) {
				HouseWork houseWork = new HouseWork(
					request.houseWorkName(),
					place,
					group,
					current,
					request.isNotified()
				);
				houseWorkRepository.save(houseWork);

				//멤버들에게 houseWork 할당
				List<HouseWorkMember> houseWorkMembers = request.members().stream()
					.map(it -> new HouseWorkMember(houseWork, it))
					.toList();
				houseWorkMemberRepository.saveAll(houseWorkMembers);
				//집안일에 태그 저장
				List<Tag> tags = request.tags()
					.stream()
					.map(it -> tagRepository.findById(it)
						.orElseThrow(() -> new NoSuchElementException("Tag does not exist")))
					.toList();
				for (Tag tag : tags) {
					HouseWorkTag houseWorkTag = new HouseWorkTag(houseWork, tag);
					houseWorkTagRepository.save(houseWorkTag);
				}

			}
			current = current.plusDays(1);
		}
	}

	public List<HouseWorkStatusByPeriodResponse> getHouseWorkStatusByPeriod(
		Long groupId,
		LocalDate from,
		LocalDate to
	) {
		List<HouseWorkStatusByPeriodResponse> responses = new ArrayList<>();
		List<LocalDate> result = houseWorkRepository.findTaskDatesBetween(groupId, from, to);
		for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {
			responses.add(new HouseWorkStatusByPeriodResponse(date, result.contains(date)));
		}
		return responses;
	}

	public HouseWorkListByDateResponse getHouseWorksByDate(Long memberId, Long groupId, LocalDate date) {
		// HouseWork 목록 조회 및 HouseWork id 목록 추출
		List<HouseWork> houseWorks = houseWorkRepository.findByGroupIdAndTaskDate(groupId, date);
		List<Long> houseWorkIds = houseWorks.stream()
			.map(HouseWork::getId)
			.toList();

		// HouseWorkMember 목록 조회
		List<HouseWorkMember> houseWorkMembers = houseWorkMemberRepository.findByHouseWorkIds(houseWorkIds);

		// Member id 목록 추출 및 구체 정보 조회
		List<Long> memberIds = houseWorkMembers.stream()
			.map(HouseWorkMember::getMemberId)
			.distinct()
			.toList();
		Map<Long, Member> memberMap = memberRepository.findByIds(memberIds).stream()
			.collect(Collectors.toMap(Member::getId, m -> m));

		// HouseWorkTag 목록 조회
		List<HouseWorkTag> houseWorkTags = houseWorkTagRepository.findByHouseWorkIds(houseWorkIds);

		// Tag id 목록 추출 및 구체 정보 조회
		List<Long> tagIds = houseWorkTags.stream()
			.map(ht -> ht.getTag().getId())
			.distinct()
			.toList();
		Map<Long, Tag> tagMap = tagRepository.findByIds(tagIds).stream()
			.collect(Collectors.toMap(Tag::getId, t -> t));

		// 집안일 id로 그룹화
		Map<Long, List<HouseWorkMember>> houseWorkMemberMap = houseWorkMembers.stream()
			.collect(Collectors.groupingBy(hm -> hm.getHouseWork().getId()));
		Map<Long, List<HouseWorkTag>> houseWorkTagMap = houseWorkTags.stream()
			.collect(Collectors.groupingBy(ht -> ht.getHouseWork().getId()));

		List<HouseWorkResponse> myHouseWorkLeft = new ArrayList<>();
		List<HouseWorkResponse> ourHouseWorkLeft = new ArrayList<>();
		List<HouseWorkResponse> myHouseWorkCompleted = new ArrayList<>();
		List<HouseWorkResponse> ourHouseWorkCompleted = new ArrayList<>();
		for (HouseWork houseWork : houseWorks) {
			Long houseWorkId = houseWork.getId();

			List<MemberResponse> memberResponses = houseWorkMemberMap.getOrDefault(houseWorkId, List.of()).stream()
				.map(hm -> {
					Member member = memberMap.get(hm.getMemberId());
					return new MemberResponse(
						member.getId(),
						member.getName(),
						member.getProfileImageUrl()
					);
				})
				.toList();

			List<TagForHouseWorkListResponse> tagResponses = houseWorkTagMap.getOrDefault(houseWorkId, List.of())
				.stream()
				.map(ht -> {
					Tag tag = tagMap.get(ht.getTag().getId());
					return new TagForHouseWorkListResponse(tag.getName());
				})
				.toList();

			HouseWorkResponse houseWorkResponse = new HouseWorkResponse(
				houseWorkId,
				houseWork.getName(),
				tagResponses,
				houseWork.getTaskDate(),
				memberResponses
			);

			boolean isMyTask = houseWorkMemberMap.get(houseWorkId).stream()
				.anyMatch(hm -> hm.getMemberId().equals(memberId));
			boolean completed = houseWork.isCompleted();
			if (isMyTask) {

				// myHouseWorkLeft
				if (!completed) {
					myHouseWorkLeft.add(houseWorkResponse);
					continue;
				}

				// myHouseWorkCompleted
				myHouseWorkCompleted.add(houseWorkResponse);
				continue;
			}

			// ourHouseWorkLeft
			if (!completed) {
				ourHouseWorkLeft.add(houseWorkResponse);
				continue;
			}

			// ourHouseWorkCompleted
			ourHouseWorkCompleted.add(houseWorkResponse);
		}

		return new HouseWorkListByDateResponse(
			myHouseWorkLeft,
			ourHouseWorkLeft,
			myHouseWorkCompleted,
			ourHouseWorkCompleted
		);
	}

	@Transactional
	public void completeHouseWork(Long memberId, Long houseWorkId) {
		HouseWork houseWork = houseWorkRepository.findById(houseWorkId)
			.orElseThrow(() -> new NoSuchElementException("HouseWork does not exist"));

		if (!houseWorkMemberRepository.existsByHouseWorkIdAndMemberId(houseWorkId, memberId)) {
			throw new IllegalStateException("Not authorized");
		}

		houseWork.markCompleted();
	}
}
