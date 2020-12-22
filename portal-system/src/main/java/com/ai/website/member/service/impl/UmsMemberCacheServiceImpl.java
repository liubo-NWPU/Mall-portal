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
//    @Autowired
    // private UmsMemberMapper memberMapper;


    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.expire.authCode}")
    private Long REDIS_EXPIRE_AUTH_CODE;
    @Value("${redis.key.member}")
    private String REDIS_KEY_MEMBER;
    @Value("${redis.key.authCode}")
    private String REDIS_KEY_AUTH_CODE;

    @Override
    public void delCacheMember(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + username;
        redisService.del(key);
    }

    @Override
    public void delAttachFile(String username) {
        String key = username + ":" + "attachFile";
        redisService.del(key);
    }

    @Override
    public void delMember(Long memberId) {
//        //UmsMember umsMember = memberDao.findById(String.valueOf(memberId)).orElse(null);
//        if (umsMember != null) {
//            String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + umsMember.getUsername();
//            redisService.del(key);
//        }
    }

    @Override
    public UmsMember getMember(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + username;
        return (UmsMember) redisService.get(key);
    }

    @Override
    public void setMember(UmsMember member) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + member.getUsername();
        redisService.set(key, member, REDIS_EXPIRE);
    }

    @CacheException
    @Override
    public void setAuthCode(String telephone, String authCode) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + telephone;
        redisService.set(key, authCode, REDIS_EXPIRE_AUTH_CODE);
    }


    @CacheException
    @Override
    public String getAuthCode(String telephone) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + telephone;
        return (String) redisService.get(key);
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
        String key = username + ":" + "attachFile";
        redisService.sAdd(key, fileUrl);
    }

    @Override
    public Set getAttachFile(String username) {
        String key = username + ":" + "attachFile";
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
