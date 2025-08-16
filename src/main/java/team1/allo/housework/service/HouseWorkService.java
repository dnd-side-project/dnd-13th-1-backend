package team1.allo.housework.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team1.allo.group.entity.Group;
import team1.allo.group.entity.Place;
import team1.allo.group.entity.Tag;
import team1.allo.group.service.GroupService;
import team1.allo.group.service.dto.MemberResponse;
import team1.allo.group.service.policy.DayOfWeekPolicy;
import team1.allo.group.service.policy.RoutinePolicy;
import team1.allo.housework.entity.HouseWork;
import team1.allo.housework.entity.HouseWorkMember;
import team1.allo.housework.entity.HouseWorkTag;
import team1.allo.housework.repository.housework.HouseWorkRepository;
import team1.allo.housework.repository.houseworkmember.HouseWorkMemberRepository;
import team1.allo.housework.repository.houseworktag.HouseWorkTagRepository;
import team1.allo.housework.service.dto.HouseWorkListByDateResponse;
import team1.allo.housework.service.dto.HouseWorkListRecentResponse;
import team1.allo.housework.service.dto.HouseWorkRecentResponse;
import team1.allo.housework.service.dto.HouseWorkResponse;
import team1.allo.housework.service.dto.HouseWorkSaveRequest;
import team1.allo.housework.service.dto.HouseWorkStatusByPeriodResponse;
import team1.allo.housework.service.dto.TagForHouseWorkListResponse;
import team1.allo.member.entity.Member;
import team1.allo.member.service.MemberService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HouseWorkService {

	private final HouseWorkRepository houseWorkRepository;
	private final HouseWorkTagRepository houseWorkTagRepository;
	private final HouseWorkMemberRepository houseWorkMemberRepository;

	private final MemberService memberService;
	private final GroupService groupService;

	@Transactional
	public void saveHouseWork(Long groupId, HouseWorkSaveRequest request) {
		Group group = groupService.findGroupById(groupId);

		Place place = groupService.findPlaceById(request.placeId());

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
					.map(groupService::findTagById)
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
		Map<Long, Member> memberMap = memberService.findAllById(memberIds).stream()
			.collect(Collectors.toMap(Member::getId, m -> m));

		// HouseWorkTag 목록 조회
		List<HouseWorkTag> houseWorkTags = houseWorkTagRepository.findByHouseWorkIds(houseWorkIds);

		// Tag id 목록 추출 및 구체 정보 조회
		List<Long> tagIds = houseWorkTags.stream()
			.map(ht -> ht.getTag().getId())
			.distinct()
			.toList();
		Map<Long, Tag> tagMap = groupService.findAllByTagId(tagIds).stream()
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

			boolean isMyTask = houseWorkMemberMap.getOrDefault(houseWorkId, List.of()).stream()
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
		if (!houseWorkMemberRepository.existsByHouseWorkIdAndMemberId(houseWorkId, memberId)) {
			throw new IllegalStateException("Not authorized");
		}

		HouseWork houseWork = findById(houseWorkId);
		houseWork.markCompleted();
	}

	@Transactional
	public void deleteHouseWork(Long memberId, Long houseWorkId) {
		if (!houseWorkMemberRepository.existsByHouseWorkIdAndMemberId(houseWorkId, memberId)) {
			throw new IllegalStateException("Not authorized");
		}

		HouseWork houseWork = findById(houseWorkId);

		houseWorkMemberRepository.deleteByHouseWorkId(houseWorkId);
		houseWorkTagRepository.deleteByHouseWorkId(houseWorkId);
		houseWorkRepository.delete(houseWork);
	}

	public List<HouseWorkListRecentResponse> getHouseWorksRecent(Long groupId, Long receiverId, LocalDate currentDate) {
		List<HouseWork> houseWorks = houseWorkRepository.findHouseWorkRecent(groupId, receiverId, currentDate);

		Map<LocalDate, List<HouseWork>> houseWorkMap = houseWorks.stream()
			.collect(Collectors.groupingBy(
				HouseWork::getCompletedDate,
				LinkedHashMap::new,
				Collectors.toList()
			));

		List<HouseWorkListRecentResponse> response = new ArrayList<>();
		houseWorkMap.forEach((k, v) -> {
			List<HouseWorkRecentResponse> houseWorkRecentResponses = v.stream()
				.map(HouseWorkRecentResponse::from)
				.toList();
			response.add(new HouseWorkListRecentResponse(k, houseWorkRecentResponses));
		});
		return response;
	}

	public HouseWork findById(Long houseWorkId) {
		return houseWorkRepository.findById(houseWorkId)
			.orElseThrow(() -> new NoSuchElementException("HouseWork does not exist"));
	}
}
