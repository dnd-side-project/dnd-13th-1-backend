package team1.housework.group.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team1.housework.auth.aop.Auth;
import team1.housework.group.service.GroupService;
import team1.housework.group.service.dto.EnterRequest;
import team1.housework.group.service.dto.EnterResponse;
import team1.housework.group.service.dto.GroupRequest;
import team1.housework.group.service.dto.GroupResponse;
import team1.housework.group.service.dto.MemberResponse;
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
}
