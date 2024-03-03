package com.green.greengram4.feed;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.common.fileupload.MyFileUtils;
import com.green.greengram4.entity.*;
import com.green.greengram4.feed.feedcomment.FeedCommentMapper;
import com.green.greengram4.feed.feedcomment.FeedCommentRepository;
import com.green.greengram4.feed.feedcomment.model.FeedCommentSelVo;
import com.green.greengram4.feed.model.*;
import com.green.greengram4.security.AuthenticationFacade;
import com.green.greengram4.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private final FeedCommentRepository feedCommentRepository;
    private final FeedFavRepository feedFavRepository;


    @Transactional
    public InsertPicDto insertFeed(FeedInsDto feedInsDto) {
        // security 이용 iuser 값 세팅
        UserEntity userEntity = userRepository.getReferenceById((long) authenticationFacade.getLoginUserPk());
//                .orElseThrow(() -> new RestApiException(AuthErrorCode.NEED_SIGNIN));
        FeedEntity feedEntity = FeedEntity.builder()
                .userEntity(userEntity)
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

        feedEntity.getFeedPicsEntities().addAll(storedFileNames
                .stream()
                .map(n -> FeedPicsEntity.builder()
                        .pic(n)
                        .feedEntity(feedEntity)
                        .build()
                )
                .toList()
        );

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

    @Transactional
    public List<FeedSelResultVo> getFeeds(FeedSelectDto feedSelectDto, Pageable pageable) {

        long loginUserPk = authenticationFacade.getLoginUserPk();
        feedSelectDto.setLoginIuser(loginUserPk);

        List<FeedEntity> feedEntities = feedRepository.selFeedAll(feedSelectDto, pageable);

        List<FeedPicsEntity> picList = feedRepository.selFeedPicsAll(feedEntities);

//            List<Long> findIfeeds = feedFavRepository.findIfeedByIuser(loginUserPk); // my

        List<FeedFavEntity> findFeedFav = feedSelectDto.getIsFavList() == 0 ?
                feedRepository.findIfeedByIuser(feedEntities, loginUserPk) : null;


        List<Long> ifeeds = feedEntities.stream().map(FeedEntity::getIfeed).toList();

//        List<FeedComment> eachComments = feedCommentRepository.findEachComments(ifeeds); // my

        List<FeedCommentSelVo> eachComments = commentMapper.findByIfeeds(ifeeds);


        return feedEntities.stream().map(feed -> {
            List<FeedCommentSelVo> resultComments = eachComments.stream().filter(ec -> ec.getIfeed() == feed.getIfeed()).toList();
            return FeedSelResultVo.builder()
                    .ifeed(feed.getIfeed().intValue())
                    .contents(feed.getContents())
                    .location(feed.getLocation())
                    .createdAt(String.valueOf(feed.getCreatedAt()))
                    .writerIuser(feed.getUserEntity().getIuser().intValue())
                    .writerNm(feed.getUserEntity().getNm())
                    .writerPic(feed.getUserEntity().getPic())
                    .pics(picList.stream()
                            .filter(pf -> Objects.equals(pf.getFeedEntity().getIfeed(), feed.getIfeed()))
                            .map(FeedPicsEntity::getPic)
                            .toList()
                    )
//                            .isFav(findIfeeds.remove(feed.getIfeed()) ? 1 : 0) // my
                    .isFav(findFeedFav == null ? 1 : findFeedFav.stream()
                            .anyMatch(ff -> Objects.equals(ff.getFeedEntity().getIfeed(), feed.getIfeed())) ?
                            1 : 0)
                    .isMoreComment(resultComments.size() > 3 ? 1 : 0)
                    .comments(!resultComments.isEmpty() ? resultComments.subList(0, resultComments.size() - 1) : new ArrayList<>())
//                        .comments(eachComments.stream().filter(c -> Objects.equals(c.getFeedEntity().getIfeed(), feed.getIfeed()))
//                                .map(c -> FeedCommentSelVo.builder()
//                                        .ifeedComment(c.getIfeedComment().intValue())
//                                        .comment(c.getComment())
//                                        .writerNm(c.getUserEntity().getNm())
//                                        .writerPic(c.getUserEntity().getPic())
//                                        .createdAt(c.getCreatedAt().toString())
//                                        .build()
//                                ).toList()) // my
                    .build();


        }).toList();
//        return feedList.isEmpty() ?
//                new ArrayList<>() :
//                feedList.stream().map(feed -> {
//
//
//                            List<FeedCommentSelVo> comments = feedCommentRepository.findTop4ByFeedEntity(feed)
//                                    .stream().map(comment -> FeedCommentSelVo.builder()
//                                            .ifeedComment(comment.getIfeedComment().intValue())
//                                            .comment(comment.getComment())
//                                            .writerNm(comment.getUserEntity().getNm())
//                                            .writerIuser(comment.getUserEntity().getIuser().intValue())
//                                            .writerPic(comment.getUserEntity().getPic())
//                                            .createdAt(comment.getCreatedAt()
//                                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
//                                            .build()).toList();
//
//                            return FeedSelResultVo.builder()
//                                    .ifeed(feed.getIfeed().intValue())
//                                    .contents(feed.getContents())
//                                    .location(feed.getLocation())
//                                    .createdAt(feed.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
//                                    .writerIuser(feed.getUserEntity().getIuser().intValue())
//                                    .writerPic(feed.getUserEntity().getPic())
//                                    .writerNm(feed.getUserEntity().getNm())
//                                    .pics(feed.getFeedPicsEntities().stream().map(FeedPicsEntity::getPic).toList())
//                                    .isMoreComment(comments.size() == 4 ? 1 : 0)
//                                    .comments(comments.size() == 4 ? comments.subList(0, comments.size() - 1) : comments)
//                                    .pics(feed.getFeedPicsEntities().stream().map(FeedPicsEntity::getPic).toList())
//                                    .isFav(feedFavRepository.findById(FeedFavIdentity.builder()
//                                            .ifeed(feed.getIfeed())
//                                            .iuser((long) loginUserPk)
//                                            .build()).isPresent() ? 1 : 0)
//                                    .build();
//                        }
//                ).toList();
    }


//    public List<FeedSelResultVo> getFeeds(FeedSelectDto feedSelectDto) {
//
//        List<FeedSelResultVo> buildResult = mapper.getFeeds(feedSelectDto);
//
//        FeedCommentSelDto feedCommentSelDto = new FeedCommentSelDto();
//        feedCommentSelDto.setStartIdx(0);
//        feedCommentSelDto.setRowCount(4);
//        for (FeedSelResultVo aObj : buildResult) {
//            aObj.setPics(picsMapper.getEachPics(aObj.getIfeed()));
//            feedCommentSelDto.setIfeed(aObj.getIfeed());
//            List<FeedCommentSelVo> comments = commentMapper.selFeedCommentAll(feedCommentSelDto);
//            if (comments.size() == 4) {
//                comments.remove(comments.size() - 1);
//                aObj.setIsMoreComment(1);
//            }
//            aObj.setComments(comments);
//        }
//        return buildResult;
//    }

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
