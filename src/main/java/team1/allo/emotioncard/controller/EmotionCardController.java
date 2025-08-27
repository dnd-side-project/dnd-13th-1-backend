package team1.allo.emotioncard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import team1.allo.auth.annotation.Auth;
import team1.allo.emotioncard.service.EmotionCardService;
import team1.allo.emotioncard.service.dto.EmotionCardListRequest;
import team1.allo.emotioncard.service.dto.EmotionCardListResponse;
import team1.allo.emotioncard.service.dto.EmotionCardResponse;
import team1.allo.emotioncard.service.dto.EmotionCardSaveRequest;
import team1.allo.emotioncard.service.dto.EmotionCardSaveResponse;
import team1.allo.member.entity.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/emotion-cards")
public class EmotionCardController {

	private final EmotionCardService emotionCardService;

	@Operation(summary = "마음 카드 생성")
	@PostMapping
	public EmotionCardSaveResponse createEmotionCard(
		@Parameter(hidden = true) @Auth Member member,
		@RequestBody EmotionCardSaveRequest request
	) {
		return emotionCardService.save(member.getId(), request);
	}

	@Operation(summary = "마음 카드 단건 조회")
	@GetMapping("/{emotionCardId}")
	public EmotionCardResponse getEmotionCard(@PathVariable Long emotionCardId) {
		return emotionCardService.getEmotionCard(emotionCardId);
	}

	@Operation(summary = "내가 보낸 or 받은 마음 카드 목록 조회")
	@GetMapping("/my-emotion-card")
	public List<EmotionCardListResponse> getMyEmotionCards(
		@Parameter(hidden = true) @Auth Member member,
		EmotionCardListRequest request
	) {
		return emotionCardService.getMyEmotionCards(member.getId(), request);
	}

	@Operation(summary = "마음 카드 삭제")
	@DeleteMapping("/{emotionCardId}")
	public void deleteEmotionCard(@PathVariable Long emotionCardId) {
		emotionCardService.deleteEmotionCard(emotionCardId);
	}
}
