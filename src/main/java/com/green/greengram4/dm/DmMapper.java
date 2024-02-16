package com.green.greengram4.dm;


import com.green.greengram4.dm.model.*;
import com.green.greengram4.user.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DmMapper {
    int insDm(DmInsDto dto);

    int insDmUser(DmInsDto dto);

    List<DmMsgSelVo> getMsgAll(DmMsgSelDto dmMsgSelDto);
    List<DmSelVo> getDmAll(@Param("dto") DmSelDto dmSelDto, @Param("idm") int idm);

    int checkEx(DmInsDto dto);


    int postDmMsg(DmMsgInsDto dmMsgInsDto);

    int delDmMsg(DmMsgDelDto dmMsgDelDto);

    int updDmLastMsg(DmMsgInsDto dto);

    UserModel selOtherPersonByLoginUser(DmMsgInsDto dto);
}
