package team1.allo.group.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import team1.allo.auth.annotation.Auth;
import team1.allo.group.service.GroupService;
import team1.allo.group.service.dto.EnterRequest;
import team1.allo.group.service.dto.EnterResponse;
import team1.allo.group.service.dto.GroupRequest;
import team1.allo.group.service.dto.GroupResponse;
import team1.allo.group.service.dto.GroupState;
import team1.allo.group.service.dto.MemberResponse;
import team1.allo.group.service.dto.MyGroupResponse;
import team1.allo.group.service.dto.PlaceResponse;
import team1.allo.group.service.dto.PlaceSaveRequest;
import team1.allo.group.service.dto.TagResponse;
import team1.allo.group.service.dto.TagSaveRequest;
import team1.allo.housework.service.HouseWorkService;
import team1.allo.housework.service.dto.HouseWorkActivitySummaryResponse;
import team1.allo.housework.service.dto.HouseWorkListByDateResponse;
import team1.allo.housework.service.dto.HouseWorkListByPlaceResponse;
import team1.allo.housework.service.dto.HouseWorkListRecentResponse;
import team1.allo.housework.service.dto.HouseWorkMyCompleteStateResponse;
import team1.allo.housework.service.dto.HouseWorkMyContributionResponse;
import team1.allo.housework.service.dto.HouseWorkResponse;
import team1.allo.housework.service.dto.HouseWorkSaveRequest;
import team1.allo.housework.service.dto.HouseWorkStatusByPeriodResponse;
import team1.allo.housework.service.dto.HouseWorkWeeklyComparisonResponse;
import team1.allo.housework.service.dto.HouseWorkWeeklyResponse;
import team1.allo.member.entity.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
public class GroupController {

	private final GroupService groupService;
	private final HouseWorkService houseWorkService;

	//TODO: 액세스토큰 파싱해 Member 바인딩
	@Operation(summary = "그룹 생성")
	@PostMapping
	public GroupResponse save(
		@Parameter(hidden = true) @Auth Member member,
		@RequestBody GroupRequest groupRequest
	) {
		return groupService.save(member, groupRequest);
	}

	@Operation(summary = "그룹 입장")
	@PostMapping("/enter")
	public EnterResponse enter(
		@Parameter(hidden = true) @Auth Member member,
		@RequestBody EnterRequest enterRequest
	) {
		return groupService.enter(member, enterRequest);
	}

	@Operation(summary = "그룹 장소 목록 조회")
	@GetMapping("/{groupId}/places")
	public List<PlaceResponse> getPlaces(@PathVariable Long groupId) {
		return groupService.getPlaces(groupId);
	}

	@Operation(summary = "그룹 태그 목록 조회")
	@GetMapping("/{groupId}/tags")
	public List<TagResponse> getTags(@PathVariable Long groupId) {
		return groupService.getTags(groupId);
	}

	@Operation(summary = "그룹 멤버 목록 조회")
	@GetMapping("/{groupId}/members")
	public List<MemberResponse> getMembers(@Auth Member member, @PathVariable Long groupId) {
		return groupService.getMembers(member, groupId);
	}

	@Operation(summary = "내 그룹 조회")
	@GetMapping("/my-group")
	public MyGroupResponse getMyGroup(@Parameter(hidden = true) @Auth Member member) {
		return groupService.getMyGroup(member);
	}

	@Operation(summary = "집안일 일정 추가")
	@PostMapping("/{groupId}/house-work")
	public void saveHouseWork(@PathVariable Long groupId, @RequestBody HouseWorkSaveRequest request) {
		houseWorkService.saveHouseWork(groupId, request);
	}

	@Operation(summary = "집안일 단건 조회")
	@GetMapping("/house-work/{houseWorkId}")
	public HouseWorkResponse getHouseWork(@PathVariable Long houseWorkId) {
		return houseWorkService.getHouseWork(houseWorkId);
	}

	@Operation(summary = "특정 기간 내 집안일 존재 여부 조회")
	@GetMapping("/{groupId}/my-house-work/period")
	public List<HouseWorkStatusByPeriodResponse> getHouseWorkStatusByPeriod(
		@PathVariable Long groupId,
		@RequestParam LocalDate from,
		@RequestParam LocalDate to
	) {
		return houseWorkService.getHouseWorkStatusByPeriod(groupId, from, to);
	}

	@Operation(summary = "특정 날짜의 집안일 목록 조회")
	@GetMapping("/{groupId}/my-house-work/date")
	public HouseWorkListByDateResponse getHouseWorksByDate(
		@Parameter(hidden = true) @Auth Member member,
		@PathVariable Long groupId,
		@RequestParam LocalDate date
	) {
		return houseWorkService.getHouseWorksByDate(member.getId(), groupId, date);
	}

	@Operation(summary = "집안일 완료")
	@PutMapping("/house-work/{houseWorkId}/complete")
	public void completeHouseWork(
		@Parameter(hidden = true) @Auth Member member,
		@PathVariable Long houseWorkId
	) {
		houseWorkService.completeHouseWork(member.getId(), houseWorkId);
	}

	@Operation(summary = "집안일 삭제")
	@DeleteMapping("/house-work/{houseWorkId}")
	public void deleteHouseWork(
		@Parameter(hidden = true) @Auth Member member,
		@PathVariable Long houseWorkId
	) {
		houseWorkService.deleteHouseWork(member.getId(), houseWorkId);
	}

	@Operation(summary = "특정 사용자의 오늘 기점 최근 7일간 완수한 집안일 조회")
	@GetMapping("/{groupId}/house-work/recent")
	public List<HouseWorkListRecentResponse> getHouseWorksRecent(
		@PathVariable Long groupId,
		@RequestParam Long receiverId
	) {
		return houseWorkService.getHouseWorksRecent(groupId, receiverId, LocalDate.now());
	}

	@Operation(summary = "나의 기여도 조회")
	@GetMapping("/{groupId}/house-work/me/contribution")
	public HouseWorkMyContributionResponse getMyContribution(
		@Parameter(hidden = true) @Auth Member member,
		@PathVariable Long groupId
	) {
		return houseWorkService.getContribution(groupId, member.getId(), LocalDate.now());
	}

	@Operation(summary = "나의 오늘 집안일 완수 상태 조회")
	@GetMapping("/house-work/me/today")
	public HouseWorkMyCompleteStateResponse getHouseWorkCompleteState(
		@Parameter(hidden = true) @Auth Member member
	) {
		return houseWorkService.getHouseWorkCompleteState(member.getId(), LocalDate.now());
	}

	@Operation(summary = "나의 지난 일주일 간 집안일 완수 상태 조회")
	@GetMapping("/house-work/me/week")
	public HouseWorkWeeklyResponse getLastHouseWorkCompletedState(
		@Parameter(hidden = true) @Auth Member member
	) {
		return houseWorkService.getLastHouseWorkCompletedState(member.getId(), LocalDate.now());
	}

	@Operation(summary = "나의 2주 전, 1주 전 집안일 완수 상태 비교 조회")
	@GetMapping("/house-work/me/comparison")
	public HouseWorkWeeklyComparisonResponse getWeeklyHouseWorkCompletedComparison(
		@Parameter(hidden = true) @Auth Member member
	) {
		return houseWorkService.getWeeklyHouseWorkCompletedComparison(member.getId(), LocalDate.now());
	}

	@Operation(summary = "나의 받은 칭찬 수, 보낸 칭찬 수, 완수 집안일 수 조회")
	@GetMapping("/house-work/me/activity-summary")
	public HouseWorkActivitySummaryResponse getHouseWorkActivitySummary(
		@Parameter(hidden = true) @Auth Member member
	) {
		return houseWorkService.getHouseWorkActivitySummary(member.getId());
	}

	@Operation(summary = "그룹 태그 추가")
	@PostMapping("/{groupId}/tags")
	public TagResponse saveTag(@PathVariable Long groupId, @RequestBody TagSaveRequest request) {
		return groupService.saveTag(groupId, request);
	}

	@Operation(summary = "장소 추가")
	@PostMapping("/{groupId}/places")
	public void savePlace(@PathVariable Long groupId, @RequestBody PlaceSaveRequest request) {
		groupService.savePlace(groupId, request);
	}

	@Operation(summary = "우리집 청결도, 캐릭터 상태, 캐릭터 대사 조회")
	@GetMapping("/{groupId}/state")
	public GroupState getGroupState(@PathVariable Long groupId) {
		return houseWorkService.getGroupState(groupId, LocalDate.now());
	}

	@Operation(summary = "특정 장소의 오늘할 집안일 조회")
	@GetMapping("/places/{placeId}/house-work")
	public HouseWorkListByPlaceResponse getHouseWorksByPlace(
		@Parameter(hidden = true) @Auth Member member,
		@PathVariable Long placeId
	) {
		return houseWorkService.getHouseWorksByPlace(member.getId(), placeId, LocalDate.now());
	}
}
