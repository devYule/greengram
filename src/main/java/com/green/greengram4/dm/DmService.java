package com.green.greengram4.dm;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.dm.model.*;
import com.green.greengram4.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DmService {
    private final DmMapper mapper;
    private final ObjectMapper objMapper;

    public List<DmMsgSelVo> getMsgAll(DmMsgSelDto dmMsgSelDto) {
        return mapper.getMsgAll(dmMsgSelDto);
    }

    public List<DmSelVo> getDmAll(DmSelDto dmSelDto) {
        return mapper.getDmAll(dmSelDto, 0);
    }

    public DmSelVo postDm(DmInsDto dto) {
        if (mapper.checkEx(dto) != 0) return null;
        if (mapper.insDm(dto) == 1) {
            if (mapper.insDmUser(dto) != 0) {
                DmSelDto dmSelDto = new DmSelDto();
                dmSelDto.setLoginedIuser(dto.getLoginedIuser());
                List<DmSelVo> dmAll = mapper.getDmAll(dmSelDto, dto.getIdm());
                log.info("dmAll.get(0) = {}", dmAll.get(0));
                return dmAll.get(0);
            }
        }
        return null;
    }


    public ResVo postDmMsg(DmMsgInsDto dto) {
        int insAffectedRows = mapper.postDmMsg(dto);
        //last msg update
        if (insAffectedRows == 1) {
            int updAffectedRows = mapper.updDmLastMsg(dto);
        }
        LocalDateTime now = LocalDateTime.now(); // 현재 날짜 구하기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 포맷 정의
        String createdAt = now.format(formatter); // 포맷 적용

        //상대방의 firebaseToken값 필요. 나의 pic, iuser값 필요.
        UserEntity otherPerson = mapper.selOtherPersonByLoginUser(dto);

        try {

            if (otherPerson.getFirebaseToken() != null) {
                DmMsgPushVo pushVo = new DmMsgPushVo();
                pushVo.setIdm(dto.getIdm());
                pushVo.setSeq(dto.getSeq());
                pushVo.setWriterIuser(dto.getLoginedIuser());
                pushVo.setWriterPic(dto.getLoginedPic());
                pushVo.setMsg(dto.getMsg());
                pushVo.setCreatedAt(createdAt);

                //object to json
                String body = objMapper.writeValueAsString(pushVo);
                log.info("body: {}", body);
                Notification noti = Notification.builder()
                        .setTitle("dm")
                        .setBody(body)
                        .build();

                Message message = Message.builder()
                        .setToken(otherPerson.getFirebaseToken())
                        .setNotification(noti)
                        .build();

                FirebaseMessaging.getInstance().sendAsync(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResVo(dto.getSeq());
    }

    public ResVo delDmMsg(DmMsgDelDto msgDelDto) {
        return new ResVo(mapper.delDmMsg(msgDelDto));
    }
}
