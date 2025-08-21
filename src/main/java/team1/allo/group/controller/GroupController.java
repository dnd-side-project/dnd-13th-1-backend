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

import lombok.RequiredArgsConstructor;
import team1.allo.auth.annotation.Auth;
import team1.allo.group.service.GroupService;
import team1.allo.group.service.dto.EnterRequest;
import team1.allo.group.service.dto.EnterResponse;
import team1.allo.group.service.dto.GroupRequest;
import team1.allo.group.service.dto.GroupResponse;
import team1.allo.group.service.dto.MemberResponse;
import team1.allo.group.service.dto.MyGroupResponse;
import team1.allo.group.service.dto.PlaceResponse;
import team1.allo.group.service.dto.TagResponse;
import team1.allo.housework.service.HouseWorkService;
import team1.allo.housework.service.dto.HouseWorkListByDateResponse;
import team1.allo.housework.service.dto.HouseWorkListRecentResponse;
import team1.allo.housework.service.dto.HouseWorkMyCompleteStateResponse;
import team1.allo.housework.service.dto.HouseWorkMyContributionResponse;
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
	@PostMapping
	public GroupResponse save(@Auth Member member, @RequestBody GroupRequest groupRequest) {
		return groupService.save(member, groupRequest);
	}

	@PostMapping("/enter")
	public EnterResponse enter(@Auth Member member, @RequestBody EnterRequest enterRequest) {
		return groupService.enter(member, enterRequest);
	}

	@GetMapping("/{groupId}/places")
	public List<PlaceResponse> getPlaces(@PathVariable Long groupId) {
		return groupService.getPlaces(groupId);
	}

	@GetMapping("/{groupId}/tags")
	public List<TagResponse> getTags(@PathVariable Long groupId) {
		return groupService.getTags(groupId);
	}

	@GetMapping("/{groupId}/members")
	public List<MemberResponse> getMembers(@PathVariable Long groupId) {
		return groupService.getMembers(groupId);
	}

	@GetMapping("/my-group")
	public MyGroupResponse getMyGroup(@Auth Member member) {
		return groupService.getMyGroup(member);
	}

	@PostMapping("/{groupId}/house-work")
	public void saveHouseWork(@PathVariable Long groupId, @RequestBody HouseWorkSaveRequest request) {
		houseWorkService.saveHouseWork(groupId, request);
	}

	@GetMapping("/{groupId}/my-house-work/period")
	public List<HouseWorkStatusByPeriodResponse> getHouseWorkStatusByPeriod(
		@PathVariable Long groupId,
		@RequestParam LocalDate from,
		@RequestParam LocalDate to
	) {
		return houseWorkService.getHouseWorkStatusByPeriod(groupId, from, to);
	}

	@GetMapping("/{groupId}/my-house-work/date")
	public HouseWorkListByDateResponse getHouseWorksByDate(
		@Auth Member member,
		@PathVariable Long groupId,
		@RequestParam LocalDate date
	) {
		return houseWorkService.getHouseWorksByDate(member.getId(), groupId, date);
	}

	@PutMapping("/house-work/{houseWorkId}/complete")
	public void completeHouseWork(@Auth Member member, @PathVariable Long houseWorkId) {
		houseWorkService.completeHouseWork(member.getId(), houseWorkId);
	}

	@DeleteMapping("/house-work/{houseWorkId}")
	public void deleteHouseWork(@Auth Member member, @PathVariable Long houseWorkId) {
		houseWorkService.deleteHouseWork(member.getId(), houseWorkId);
	}

	@GetMapping("/{groupId}/house-work/recent")
	public List<HouseWorkListRecentResponse> getHouseWorksRecent(
		@PathVariable Long groupId,
		@RequestParam Long receiverId
	) {
		return houseWorkService.getHouseWorksRecent(groupId, receiverId, LocalDate.now());
	}

	@GetMapping("/{groupId}/house-work/me/contribution")
	public HouseWorkMyContributionResponse getMyContribution(
		@Auth Member member,
		@PathVariable Long groupId
	) {
		return houseWorkService.getContribution(groupId, member.getId(), LocalDate.now());
	}

	@GetMapping("/house-work/me/today")
	public HouseWorkMyCompleteStateResponse getHouseWorkCompleteState(@Auth Member member) {
		return houseWorkService.getHouseWorkCompleteState(member.getId(), LocalDate.now());
	}

	@GetMapping("/house-work/me/week")
	public HouseWorkWeeklyResponse getLastHouseWorkCompletedState(@Auth Member member) {
		return houseWorkService.getLastHouseWorkCompletedState(member.getId(), LocalDate.now());
	}

	@GetMapping("/house-work/me/comparison")
	public HouseWorkWeeklyComparisonResponse getWeeklyHouseWorkCompletedComparison(@Auth Member member) {
		return houseWorkService.getWeeklyHouseWorkCompletedComparison(member.getId(), LocalDate.now());
	}
}
