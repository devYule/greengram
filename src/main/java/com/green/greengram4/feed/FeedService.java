package com.green.greengram4.feed;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.feedcomment.FeedCommentMapper;
import com.green.greengram4.feed.feedcomment.model.FeedCommentSelDto;
import com.green.greengram4.feed.feedcomment.model.FeedCommentSelVo;
import com.green.greengram4.feed.model.*;
import com.green.greengram4.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedService {

    private final FeedMapper mapper;
    private final FeedPicsMapper picsMapper;
    private final FeedFavMapper favMapper;
    private final FeedCommentMapper commentMapper;

    private final AuthenticationFacade authenticationFacade;

    public ResVo insertFeed(FeedInsDto feedInsDto) {
        // security 이용 iuser 값 세팅
        feedInsDto.setIuser(authenticationFacade.getLoginUserPk());

        InsertFeedDto insertFeedDto = new InsertFeedDto(feedInsDto);
        ResVo resVo = new ResVo();

        if (mapper.insertFeed(insertFeedDto) == 0) return resVo;
        InsertPicDto insertPicDto = new InsertPicDto(insertFeedDto.getIfeed(), feedInsDto.getPics());

        if (picsMapper.insertPic(insertPicDto) == 0) return resVo;

        resVo.setResult(insertFeedDto.getIfeed());

        return resVo;

    }

    public List<FeedSelResultVo> getFeeds(FeedSelectDto feedSelectDto) {

        List<FeedSelResultVo> buildResult = mapper.getFeeds(feedSelectDto);

        FeedCommentSelDto feedCommentSelDto = new FeedCommentSelDto();
        feedCommentSelDto.setStartIdx(0);
        feedCommentSelDto.setRowCount(4);
        for (FeedSelResultVo aObj : buildResult) {
            aObj.setPics(picsMapper.getEachPics(aObj.getIfeed()));
            feedCommentSelDto.setIfeed(aObj.getIfeed());
            List<FeedCommentSelVo> comments = commentMapper.selFeedCommentAll(feedCommentSelDto);
            if (comments.size() == 4) {
                comments.remove(comments.size() - 1);
                aObj.setIsMoreComment(1);
            }
            aObj.setComments(comments);
        }
        return buildResult;
    }

    public ResVo delFeed(FeedDelDto feedDelDto) {
        ResVo result = new ResVo();
        if (mapper.isExists(feedDelDto) == 0) {
            return result;
        }
        mapper.delExtra(feedDelDto);

        if (mapper.delFeed(feedDelDto) == 0) {
            return result;
        }
        result.setResult(1);
        return result;
    }

    // ---------------- t_feed_fav ----------------
    // insert: 1 delete: 0
    public ResVo toggleFeedFav(FeedFavDto feedFavDto) {
        ResVo result = new ResVo();
        if (favMapper.deleteFeedFav(feedFavDto) == 1) return result;
        if (favMapper.insertFeedFav(feedFavDto) != 0) result.setResult(1);
        return result;
    }


}
