package team1.housework.emotioncard.repository;

import static team1.housework.emotioncard.entity.QEmotionCard.*;

import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team1.housework.emotioncard.entity.EmotionCard;

@RequiredArgsConstructor
public class EmotionCardCustomRepositoryImpl implements EmotionCardCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<EmotionCard> findByIdWithFetchJoin(Long id) {
		return Optional.ofNullable(queryFactory.selectFrom(emotionCard)
			.join(emotionCard.compliments).fetchJoin()
			.where(emotionCard.id.eq(id))
			.fetchOne());
	}
}
