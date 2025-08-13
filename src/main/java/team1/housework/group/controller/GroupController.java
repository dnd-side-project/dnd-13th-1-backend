package team1.housework.group.controller;

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
import team1.housework.auth.aop.Auth;
import team1.housework.group.service.GroupService;
import team1.housework.group.service.dto.EnterRequest;
import team1.housework.group.service.dto.EnterResponse;
import team1.housework.group.service.dto.GroupRequest;
import team1.housework.group.service.dto.GroupResponse;
import team1.housework.group.service.dto.HouseWorkListByDateResponse;
import team1.housework.group.service.dto.HouseWorkListRecentResponse;
import team1.housework.group.service.dto.HouseWorkSaveRequest;
import team1.housework.group.service.dto.HouseWorkStatusByPeriodResponse;
import team1.housework.group.service.dto.MemberResponse;
import team1.housework.group.service.dto.MyGroupResponse;
import team1.housework.group.service.dto.PlaceResponse;
import team1.housework.group.service.dto.TagResponse;
import team1.housework.member.entity.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
public class GroupController {

	private final GroupService groupService;

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
		groupService.saveHouseWork(groupId, request);
	}

	@GetMapping("/{groupId}/my-house-work/period")
	public List<HouseWorkStatusByPeriodResponse> getHouseWorkStatusByPeriod(
		@PathVariable Long groupId,
		@RequestParam LocalDate from,
		@RequestParam LocalDate to
	) {
		return groupService.getHouseWorkStatusByPeriod(groupId, from, to);
	}

	@GetMapping("/{groupId}/my-house-work/date")
	public HouseWorkListByDateResponse getHouseWorksByDate(
		@Auth Member member,
		@PathVariable Long groupId,
		@RequestParam LocalDate date
	) {
		return groupService.getHouseWorksByDate(member.getId(), groupId, date);
	}

	@PutMapping("/house-work/{houseWorkId}/complete")
	public void completeHouseWork(@Auth Member member, @PathVariable Long houseWorkId) {
		groupService.completeHouseWork(member.getId(), houseWorkId);
	}

	@DeleteMapping("/house-work/{houseWorkId}")
	public void deleteHouseWork(@Auth Member member, @PathVariable Long houseWorkId) {
		groupService.deleteHouseWork(member.getId(), houseWorkId);
	}

	@GetMapping("/{groupId}/house-work/recent")
	public List<HouseWorkListRecentResponse> getHouseWorksRecent(
		@PathVariable Long groupId,
		@RequestParam Long receiverId
	) {
		return groupService.getHouseWorksRecent(groupId, receiverId, LocalDate.of(2025, 8, 15));
	}
}
