package com.ai.website.member.service;


import com.ai.website.member.entity.UmsMember;

import java.util.Set;

/**
 * 会员信息缓存业务类
 * 2020/3/14.
 */
public interface UmsMemberCacheService {


    void setVerificationCode(String sessionId, String authCode);

    public Object getVerificationCode(String sessionId);

    void setAttachFile(String username,String fileUrl);

    Set getAttachFile(String username);

    void delAttachFile(String username);

    void setPageViewNum(String key, String requestIp);

    Long getPageViewNum(String key);

    Long cacelUpload(String username, String fileUrl);
}
