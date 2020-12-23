package com.ai.website.member.controller;

import com.ai.website.common.api.CommonResult;
import com.ai.website.common.util.RequestUtil;
import com.ai.website.member.entity.UmsMember;
import com.ai.website.member.entity.UmsMemberParam;
import com.ai.website.member.service.UmsMemberCacheService;
import com.ai.website.member.service.UmsMemberService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员登录注册管理Controller
 */
@RestController
@Api(tags = "LoginController", description = "登录注册管理")
@RequestMapping("/api/login")
@CrossOrigin
public class LoginController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UmsMemberService memberService;

    @Autowired
    private UmsMemberCacheService memberCacheService;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @ApiOperation("获取当前登录的会员信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult info(Principal principal) {
        if (principal == null) {
            return CommonResult.unauthorized(null);
        }
        UmsMember member = memberService.getCurrentMember();
        member.setPassword(null);

        return CommonResult.success(member);
    }

    @ApiOperation("会员注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult register(@RequestBody UmsMemberParam umsMemberParam) {
        UmsMember member = memberService.register(umsMemberParam.getUsername(), umsMemberParam.getPassword());
        if (member == null) {
            return CommonResult.failed("用户重复");
        }
        return CommonResult.success("注册成功");
    }

    @ApiOperation("会员登录")
    @RequestMapping(value = "/loginOn", method = RequestMethod.POST)
    public CommonResult login(@RequestBody UmsMemberParam umsMemberParam,
                              HttpServletRequest httpServletRequest) {
       //从缓存中取验证码
        String requestIp = RequestUtil.getRequestIp(httpServletRequest);
        String authCode = (String) memberCacheService.getVerificationCode(requestIp);
        //验证码校验
        if (StringUtils.isEmpty(authCode) || !authCode.equals(umsMemberParam.getVerificationCode())) {
            return CommonResult.failed("验证码错误");
        }
        //登录
        String token = memberService.login(umsMemberParam.getUsername(), umsMemberParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "生成验证码")
    @GetMapping("/getAuthCode")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        try {
            httpServletResponse.setHeader("Cache-Control", "no-store");
            httpServletResponse.setHeader("Expires", "-1");
            httpServletResponse.setContentType("image/jpeg");
            String createText = defaultKaptcha.createText();
            //登录ip为key，验证码为value
            String requestIp = RequestUtil.getRequestIp(httpServletRequest);
            memberCacheService.setVerificationCode(requestIp, createText);
            ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", responseOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

}
