package com.ai.website.member.service.impl;

import com.ai.website.common.service.RedisService;
import com.ai.website.member.dao.MemberMapper;
import com.ai.website.member.entity.UmsMember;
import com.ai.website.member.service.UmsMemberCacheService;

import com.ai.website.security.annotation.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * UmsMemberCacheService实现类
 * 2020/3/14.
 */
@Service
public class UmsMemberCacheServiceImpl implements UmsMemberCacheService {
    @Autowired
    private RedisService redisService;

    @Value("${redis.expire.authCode}")
    private Long REDIS_EXPIRE_AUTH_CODE;
    @Value("${redis.key.attachFile}")
    private String REDIS_KEY_ATTACH_FILE;
    @Value("${redis.key.authCode}")
    private String REDIS_KEY_AUTH_CODE;



    @Override
    public void delAttachFile(String username) {
        String key = username + ":" + REDIS_KEY_ATTACH_FILE;
        redisService.del(key);
    }


    @Override
    public void setVerificationCode(String requestIp, String authCode) {
        String key = requestIp;
        redisService.set(key, authCode, REDIS_EXPIRE_AUTH_CODE);
    }

    @Override
    public Object getVerificationCode(String requestIp) {
        String key = requestIp;
        Object o = redisService.get(key);
        return redisService.get(key);
    }

    @Override
    public void setAttachFile(String username, String fileUrl) {
        String key = username + ":" + REDIS_KEY_ATTACH_FILE;
        redisService.sAdd(key, fileUrl);
    }

    @Override
    public Set getAttachFile(String username) {
        String key = username + ":" + REDIS_KEY_ATTACH_FILE;
        Set<Object> attachFileSet = redisService.sMembers(key);
        return attachFileSet;
    }

    @Override
    public void setPageViewNum(String key, String requestIp) {
        redisService.sAdd(key, requestIp);
    }

    @Override
    public Long getPageViewNum(String key) {
        return redisService.sSize(key);
    }
}
