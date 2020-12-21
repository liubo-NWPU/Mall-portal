package com.ai.website.member.service.impl;


import com.ai.website.member.dao.MemberMapper;
import com.ai.website.member.domain.MemberDetails;
import com.ai.website.member.dto.MemberInfoDto;
import com.ai.website.member.entity.MemberLoginLog;
import com.ai.website.member.entity.UmsMember;
import com.ai.website.member.service.UmsMemberCacheService;
import com.ai.website.member.service.UmsMemberService;
import com.ai.website.security.util.JwtTokenUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * 会员管理Service实现类
 * 2018/8/3.
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsMemberServiceImpl.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UmsMemberCacheService memberCacheService;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public UmsMember getByUsername(String username) {
        //1.缓存里查
//        UmsMember member = memberCacheService.getMember(username);
//        if (member != null) return member;
        //2.数据库里查
        UmsMember umsMember = memberMapper.findByUserName(username);
        if (umsMember != null) {
            //memberCacheService.setMember(umsMember);
            return umsMember;
        }
        return null;
    }

    @Override
    public UmsMember getById(Long id) {
        //return memberMapper.selectByPrimaryKey(id);
        return null;
    }

    public UmsMember register(String username,
                              String password) {
        //验证验证码
//        if (!verifyAuthCode(authCode, "ValidCode")) {
//            Asserts.fail("验证码错误");
//        }
        //查询是否已有该用户
        UmsMember member = new UmsMember();
        member.setUsername(username);
        member.setCreateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        member.setStatus(1);
        String encodePassword = passwordEncoder.encode(password);
        member.setPassword(encodePassword);
        member.setIntegration(0);
        UmsMember name = memberMapper.findByUserName(member.getUsername());
        if (name != null) {
            return null;
        }
        memberMapper.insert(member);
        return member;
    }

    //需要手机号生成验证码
    @Override
    public String generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        memberCacheService.setAuthCode(telephone, sb.toString());
        return sb.toString();
    }

    //不需要手机号生成验证码
    @Override
    public String generatedAuthCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        memberCacheService.setAuthCode("ValidCode", sb.toString());
        return sb.toString();
    }


    @Override
    public UmsMember getCurrentMember() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        MemberDetails memberDetails = (MemberDetails) auth.getPrincipal();
        return memberDetails.getUmsMember();
    }

    @Override
    public void updateIntegration(Long id, Integer integration) {
        UmsMember record = new UmsMember();
        record.setId(id);
        record.setIntegration(integration);
        memberCacheService.delMember(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsMember member = getByUsername(username);
        if (member != null) {
            return new MemberDetails(member);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            //记录用户登录
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    //添加登陆记录
    @Override
    public void insertLoginLog(String username) {
        MemberLoginLog memberLoginLog = new MemberLoginLog();
        memberLoginLog.setUsername(username);
        memberLoginLog.setLoginTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        memberMapper.insertLoginLog(memberLoginLog);
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshHeadToken(token);
    }

    @Override
    public List<UmsMember> list(Integer pageSize, Integer pageNum) {
//        PageHelper.startPage(pageNum, pageSize);
//        return memberMapper.findAll();
        return null;
    }

    //对输入的验证码进行校验
    public boolean verifyAuthCode(String authCode, String telephone) {
        if (StringUtils.isEmpty(authCode)) {
            return false;
        }
        String realAuthCode = memberCacheService.getAuthCode(telephone);
        return authCode.equals(realAuthCode);
    }

    @Override
    public int updateHeadImage(String username, String imageUrl) {
        //一旦更新会员信息，删除该会员缓存
        //memberCacheService.delCacheMember(username);
        return memberMapper.updateHeadImage(username, imageUrl);
    }

    @Override
    public int updatePersonInfo(MemberInfoDto memberInfoDto) {
        //memberCacheService.delCacheMember(username);
        return memberMapper.updatePersonInfo(memberInfoDto);
    }

}
