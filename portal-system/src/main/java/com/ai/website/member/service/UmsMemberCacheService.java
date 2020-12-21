package com.ai.website.member.service;


import com.ai.website.member.entity.UmsMember;

import java.util.Set;

/**
 * 会员信息缓存业务类
 * 2020/3/14.
 */
public interface UmsMemberCacheService {
    /**
     * 删除会员用户缓存
     */
    void delMember(Long memberId);

    /**
     * 获取会员用户缓存
     */
    UmsMember getMember(String username);

    /**
     * 设置会员用户缓存
     */
    void setMember(UmsMember member);

    /**
     * 设置验证码
     */
    void setAuthCode(String telephone, String authCode);

    /**
     * 获取验证码
     */
    String getAuthCode(String telephone);

    /**
     * 删除缓存中会员
     */
    void delCacheMember(String username);

    void setVerificationCode(String sessionId, String authCode);

    public Object getVerificationCode(String sessionId);

    void setAttachFile(String username,String fileUrl);

    Set getAttachFile(String username);

    void delAttachFile(String username);
}
