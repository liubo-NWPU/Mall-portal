package com.ai.website.member.dao;

import com.ai.website.member.dto.MemberInfoDto;
import com.ai.website.member.entity.MemberLoginLog;
import com.ai.website.member.entity.UmsMember;


public interface MemberMapper {

    UmsMember findByUserName(String username);

    int insert(UmsMember member);

    int updateHeadImage(String username, String imageUrl);

    int updatePersonInfo(MemberInfoDto memberInfoDto);

    int insertLoginLog(MemberLoginLog memberLoginLog);
}
