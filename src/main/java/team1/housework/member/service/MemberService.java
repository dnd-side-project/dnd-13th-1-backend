package team1.housework.member.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team1.housework.member.entity.Member;
import team1.housework.member.repository.MemberRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Member findById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new NoSuchElementException("Member not found"));
	}

}
