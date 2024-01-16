package com.green.greengram4.feed;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.feedcomment.FeedCommentMapper;
import com.green.greengram4.feed.feedcomment.model.FeedCommentSelDto;
import com.green.greengram4.feed.feedcomment.model.FeedCommentSelVo;
import com.green.greengram4.feed.model.*;
import com.green.greengram4.security.AuthenticationFacade;
import com.green.greengram4.common.fileupload.MyFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedService {

    private final FeedMapper mapper;
    private final FeedPicsMapper picsMapper;
    private final FeedFavMapper favMapper;
    private final FeedCommentMapper commentMapper;

    private final AuthenticationFacade authenticationFacade;

    private final MyFileUtils myFileUtils;

    public InsertPicDto insertFeed(FeedInsDto feedInsDto) {
        // security 이용 iuser 값 세팅
        int loginUserPk = authenticationFacade.getLoginUserPk();
        feedInsDto.setIuser(loginUserPk);

        InsertFeedDto insertFeedDto = new InsertFeedDto(feedInsDto);
        ResVo resVo = new ResVo();
        mapper.insertFeed(insertFeedDto);

//        InsertPicDto insertPicDto = new InsertPicDto(insertFeedDto.getIfeed(), feedInsDto.getPics());

//        if (picsMapper.insertPic(insertPicDto) == 0) return resVo;

        // save pics
        String target = "feed/" + insertFeedDto.getIfeed();
        List<String> storedFileNames = new ArrayList<>();
        for (MultipartFile pic : feedInsDto.getPics()) {
            storedFileNames.add(myFileUtils.transferTo(pic, target));
        }

        InsertPicDto insertPicDto = new InsertPicDto(insertFeedDto.getIfeed(), storedFileNames);
        picsMapper.insertPic(insertPicDto);


        resVo.setResult(insertFeedDto.getIfeed());

        return insertPicDto;

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
