package com.green.greengram4.feed.feedcomment;

import com.green.greengram4.entity.FeedComment;
import com.green.greengram4.entity.FeedEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {
    @EntityGraph(attributePaths = {"feedEntity", "userEntity"})
    List<FeedComment> findTop4ByFeedEntity(FeedEntity feedEntity);

    @Query("select fc from FeedComment fc where fc.feedEntity.ifeed in :ifeeds")
    List<FeedComment> findEachComments(List<Long> ifeeds);

}
