package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.FeedFavEntity;
import com.green.greengram4.entity.FeedPicsEntity;
import com.green.greengram4.feed.model.FeedSelectDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.green.greengram4.entity.QFeedEntity.feedEntity;
import static com.green.greengram4.entity.QFeedFavEntity.feedFavEntity;
import static com.green.greengram4.entity.QFeedPicsEntity.feedPicsEntity;

@Slf4j
@RequiredArgsConstructor
public class FeedQueryDslRepositoryImpl implements FeedQueryDslRepository {

    private final JPAQueryFactory query;
    private final EntityManager em;

    @Override
    public List<FeedEntity> selFeedAll(FeedSelectDto feedSelectDto, Pageable pageable) {


        // ManyToOne 의 경우, 함께 사용할때에는 fetchJoin 으로 곧바로 가져와 주는게 좋다.
        JPAQuery<FeedEntity> jpaQuery = query.select(feedEntity)
                .from(feedEntity)
                .join(feedEntity.userEntity).fetchJoin()

                .orderBy(feedEntity.ifeed.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());


        if (feedSelectDto.getIsFavList() == 1) {
            jpaQuery.join(feedFavEntity).on(feedEntity.ifeed.eq(feedFavEntity.feedEntity.ifeed)
                    , feedFavEntity.userEntity.iuser.eq(feedSelectDto.getLoginIuser()));
        } else {
            jpaQuery.where(whereTargetIuser(feedSelectDto.getTargetIuser()));
        }

        return jpaQuery.fetch();


//

    }

    @Override
    public List<FeedPicsEntity> selFeedPicsAll(List<FeedEntity> feedEntities) {

        return query.select(Projections.fields(FeedPicsEntity.class, feedPicsEntity.feedEntity, feedPicsEntity.pic))
                .from(feedPicsEntity)
                .where(feedPicsEntity.feedEntity.in(feedEntities))
                .fetch();

    }

    @Override
    public List<FeedFavEntity> findIfeedByIuser(List<FeedEntity> feedEntities, Long loginIuser) {

        return query.select(Projections.fields(FeedFavEntity.class, feedFavEntity.feedEntity))
                .from(feedFavEntity)
                .where(feedFavEntity.feedEntity.in(feedEntities),
                        feedFavEntity.userEntity.iuser.eq(loginIuser)
                )
                .fetch();

    }

    private BooleanExpression whereTargetIuser(long iuser) {

        return iuser == 0 ? null : feedEntity.userEntity.iuser.eq(iuser);
    }


}
