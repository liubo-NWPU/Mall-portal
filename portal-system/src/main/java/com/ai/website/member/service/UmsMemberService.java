package com.ai.website.member.service;

import com.ai.website.member.dto.MemberInfoDto;
import com.ai.website.member.entity.UmsMember;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 会员管理Service
 */
public interface UmsMemberService {
    /**
     * 根据用户名获取会员
     */
    UmsMember getByUsername(String username);

    /**
     * 根据会员编号获取会员
     */
    UmsMember getById(Long id);

    /**
     * 用户注册
     */
    @Transactional
    UmsMember register(String username, String password);
    // UmsMember register(MemberRegister memberRegister);

    /**
     * 生成验证码
     */
    String generateAuthCode(String telephone);

    String generatedAuthCode();

    /**
     * 修改密码
     */
//    @Transactional
//    void updatePassword(String telephone, String password, String authCode);

    /**
     * 获取当前登录会员
     */
    UmsMember getCurrentMember();

    /**
     * 根据会员id修改会员积分
     */
    void updateIntegration(Long id, Integer integration);


    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 登录后获取token
     */
    String login(String username, String password);

    /**
     * 刷新token
     */
    String refreshToken(String token);

    List<UmsMember> list(Integer pageSize, Integer pageNum);

    int updateHeadImage(String username, String imageUrl);

    int updatePersonInfo(MemberInfoDto memberInfoDto);

    void insertLoginLog(String username);
}
