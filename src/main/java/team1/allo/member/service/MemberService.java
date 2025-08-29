package team1.allo.member.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team1.allo.group.service.dto.MemberResponse;
import team1.allo.member.entity.Member;
import team1.allo.member.repository.MemberRepository;
import team1.allo.member.service.dto.NickNameUpdateRequest;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Member findById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new NoSuchElementException("Member not found"));
	}

	public List<Member> findAllById(List<Long> memberIds) {
		return memberRepository.findAllById(memberIds);
	}

	@Transactional
	public MemberResponse updateNickName(Long memberId, NickNameUpdateRequest request) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new NoSuchElementException("Member not found"));

		member.updateNickname(request.nickName());
		return new MemberResponse(
			member.getId(),
			member.getName() == null ? null : member.getName(),
			member.getProfileImageUrl() == null ? null : member.getProfileImageUrl()
		);
	}
}
