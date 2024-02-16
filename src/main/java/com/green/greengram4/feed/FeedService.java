package com.green.greengram4.feed;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.common.fileupload.MyFileUtils;
import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.FeedPicsEntity;
import com.green.greengram4.entity.UserEntity;
import com.green.greengram4.exception.AuthErrorCode;
import com.green.greengram4.exception.RestApiException;
import com.green.greengram4.feed.feedcomment.FeedCommentMapper;
import com.green.greengram4.feed.feedcomment.model.FeedCommentSelDto;
import com.green.greengram4.feed.feedcomment.model.FeedCommentSelVo;
import com.green.greengram4.feed.model.*;
import com.green.greengram4.security.AuthenticationFacade;
import com.green.greengram4.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class FeedService {

    private final FeedMapper mapper;
    private final FeedPicsMapper picsMapper;
    private final FeedFavMapper favMapper;
    private final FeedCommentMapper commentMapper;

    private final AuthenticationFacade authenticationFacade;

    private final MyFileUtils myFileUtils;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @Transactional
    public InsertPicDto insertFeed(FeedInsDto feedInsDto) {
        // security 이용 iuser 값 세팅
        UserEntity userEntity = userRepository.findById((long) authenticationFacade.getLoginUserPk())
                .orElseThrow(() -> new RestApiException(AuthErrorCode.NEED_SIGNIN));
        FeedEntity feedEntity = FeedEntity.builder()
                .user(userEntity)
                .contents(feedInsDto.getContents())
                .location(feedInsDto.getLocation())
                .build();
        feedRepository.save(feedEntity);

        log.info("feedEntity.ifeed() = {}", feedEntity.getIfeed());


        // save pics
        String target = "feed/" + feedEntity.getIfeed();
        List<String> storedFileNames = new ArrayList<>();
        for (MultipartFile pic : feedInsDto.getPics()) {
            storedFileNames.add(myFileUtils.transferTo(pic, target));
        }

        storedFileNames.forEach(s -> {
            FeedPicsEntity feedPicsEntity = new FeedPicsEntity();
            feedPicsEntity.setPic(s);
            feedPicsEntity.setFeedEntity(feedEntity);
            feedEntity.getFeedPicsEntities().add(feedPicsEntity);
        });


        return new InsertPicDto(feedEntity.getIfeed().intValue(), storedFileNames);

    }

//    public InsertPicDto insertFeed(FeedInsDto feedInsDto) {
//        // security 이용 iuser 값 세팅
//        int loginUserPk = authenticationFacade.getLoginUserPk();
//        feedInsDto.setIuser(loginUserPk);
//
//        InsertFeedDto insertFeedDto = new InsertFeedDto(feedInsDto);
//        ResVo resVo = new ResVo();
//        mapper.insertFeed(insertFeedDto);
//
//
//        // save pics
//        String target = "feed/" + insertFeedDto.getIfeed();
//        List<String> storedFileNames = new ArrayList<>();
//        for (MultipartFile pic : feedInsDto.getPics()) {
//            storedFileNames.add(myFileUtils.transferTo(pic, target));
//        }
//
//        InsertPicDto insertPicDto = new InsertPicDto(insertFeedDto.getIfeed(), storedFileNames);
//        picsMapper.insertPic(insertPicDto);
//
//
//        resVo.setResult(insertFeedDto.getIfeed());
//
//        return insertPicDto;
//
//    }

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
