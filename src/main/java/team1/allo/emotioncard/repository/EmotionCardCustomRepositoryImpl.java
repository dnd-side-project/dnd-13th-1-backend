package team1.allo.emotioncard.repository;

import static team1.allo.emotioncard.entity.QEmotionCard.*;
import static team1.allo.housework.entity.QHouseWork.*;

import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team1.allo.emotioncard.service.dto.EmotionCardListResponse;
import team1.allo.member.entity.QMember;

@RequiredArgsConstructor
public class EmotionCardCustomRepositoryImpl implements EmotionCardCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<EmotionCardListResponse> getAllWithCondition(Long memberId, String filter, String sorted) {
		QMember senderMember = new QMember("senderMember");
		QMember receiverMember = new QMember("receiverMember");

		return queryFactory.select(
				Projections.constructor(
					EmotionCardListResponse.class,
					emotionCard.id,
					houseWork.name,
					emotionCard.disappointment,
					senderMember.name,
					receiverMember.name,
					emotionCard.createdDate
				)
			)
			.from(emotionCard)
			.join(houseWork).on(emotionCard.houseWorkId.eq(houseWork.id))
			.join(senderMember).on(emotionCard.senderId.eq(senderMember.id))
			.join(receiverMember).on(emotionCard.receiverId.eq(receiverMember.id))
			.where(buildMemberFilter(memberId, filter))
			.orderBy(createOrderSpecifier(sorted))
			.fetch();
	}

	@Override
	public Long countEmotionCardSentByMember(Long memberId) {
		return queryFactory.select(emotionCard.count())
			.from(emotionCard)
			.where(emotionCard.senderId.eq(memberId))
			.fetchOne();
	}

	@Override
	public Long countEmotionCardReceivedByMember(Long memberId) {
		return queryFactory.select(emotionCard.count())
			.from(emotionCard)
			.where(emotionCard.receiverId.eq(memberId))
			.fetchOne();
	}

	private BooleanExpression buildMemberFilter(Long memberId, String filter) {
		return switch (filter) {
			case "from" -> emotionCard.receiverId.eq(memberId);
			case "to" -> emotionCard.senderId.eq(memberId);
			default -> null;
		};
	}

	private OrderSpecifier<LocalDateTime> createOrderSpecifier(String sorted) {
		return sorted.equalsIgnoreCase("asc") ? emotionCard.createdDate.asc() : emotionCard.createdDate.desc();
	}
}
