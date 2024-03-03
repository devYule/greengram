package com.green.greengram4.feed.feedcomment;

import com.green.greengram4.entity.FeedComment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedCommentDSLRepository {

    private final EntityManager em;

    public List<FeedComment> findFeedCommentByIfeeds(List<Integer> ifeeds) {


        return null;
    }

}
