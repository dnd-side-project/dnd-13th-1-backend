package team1.housework.group.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
import team1.housework.group.repository.HouseWorkMemberRepository;
import team1.housework.group.repository.HouseWorkRepository;
import team1.housework.group.repository.HouseWorkTagRepository;
import team1.housework.group.repository.PlaceRepository;
import team1.housework.group.repository.TagRepository;
import team1.housework.group.service.dto.EnterRequest;
import team1.housework.group.service.dto.EnterResponse;
import team1.housework.group.service.dto.GroupRequest;
import team1.housework.group.service.dto.GroupResponse;
import team1.housework.group.service.dto.HouseWorkSaveRequest;
import team1.housework.group.service.dto.HouseWorkStatusByDateRangeResponse;
import team1.housework.group.service.dto.MemberResponse;
import team1.housework.group.service.dto.MyGroupResponse;
import team1.housework.group.service.dto.PlaceResponse;
import team1.housework.group.service.dto.TagResponse;
import team1.housework.group.service.generator.InviteCodeGenerator;
import team1.housework.group.service.policy.DayOfWeekPolicy;
import team1.housework.group.service.policy.RoutinePolicy;
import team1.housework.member.entity.Member;
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

	public List<HouseWorkStatusByDateRangeResponse> getHouseWorkStatusByDatePeriod(
		LocalDate from,
		LocalDate to,
		Long groupId
	) {
		List<HouseWorkStatusByDateRangeResponse> responses = new ArrayList<>();
		List<LocalDate> result = houseWorkRepository.findTaskDatesBetween(from, to, groupId);
		for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {
			responses.add(new HouseWorkStatusByDateRangeResponse(date, result.contains(date)));
		}
		return responses;
	}
}
