package team1.housework.emotioncard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team1.housework.auth.aop.Auth;
import team1.housework.emotioncard.service.EmotionCardService;
import team1.housework.emotioncard.service.dto.EmotionCardResponse;
import team1.housework.emotioncard.service.dto.EmotionCardSaveRequest;
import team1.housework.emotioncard.service.dto.EmotionCardSaveResponse;
import team1.housework.member.entity.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/emotion-cards")
public class EmotionCardController {

	private final EmotionCardService emotionCardService;

	@PostMapping
	public EmotionCardSaveResponse createEmotionCard(
		@Auth Member member,
		@RequestBody EmotionCardSaveRequest request
	) {
		return emotionCardService.save(member.getId(), request);
	}

	@GetMapping("/{emotionCardId}")
	public EmotionCardResponse getEmotionCard(@PathVariable Long emotionCardId) {
		return emotionCardService.getEmotionCard(emotionCardId);
	}
}
