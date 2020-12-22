package com.ai.website.business.controller;

import com.ai.website.business.annotation.PageView;
import com.ai.website.common.api.CommonResult;
import com.ai.website.common.service.RedisService;
import com.ai.website.common.util.RequestUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "PageViewController", description = "进入首页获取访问量")
@RequestMapping("/api/pageView")
@CrossOrigin
public class PageViewController {

    @Autowired
    private RedisService redisService;

    @PageView
    @RequestMapping(value = "/addPageViewNum", method = RequestMethod.GET)
    public CommonResult addPageViewNum(HttpServletRequest httpServletRequest) {
        String requestIp = RequestUtil.getRequestIp(httpServletRequest);
        String key = "pageView";
        Long viewNum = redisService.sSize(key);
        return CommonResult.success("当前ip：" + requestIp + "进入首页，" + "当前总浏览量：" + viewNum);
    }
}
