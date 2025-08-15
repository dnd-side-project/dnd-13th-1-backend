package team1.allo.member.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team1.allo.group.service.dto.MyGroupResponse;
import team1.allo.member.entity.Member;
import team1.allo.member.repository.MemberRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Member findById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new NoSuchElementException("Member not found"));
	}

	public MyGroupResponse getMyGroup(Member member) {
		return null;
	}
}
