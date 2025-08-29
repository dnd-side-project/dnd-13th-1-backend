package team1.allo.member.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team1.allo.group.service.dto.MemberResponse;
import team1.allo.member.service.MemberService;
import team1.allo.member.service.dto.NickNameUpdateRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

	private final MemberService memberService;

	@PatchMapping("/{memberId}/nick-name")
	public MemberResponse updateNickName(
		@PathVariable Long memberId,
		@RequestBody NickNameUpdateRequest request
	){
		return memberService.updateNickName(memberId,request);
	}

}
