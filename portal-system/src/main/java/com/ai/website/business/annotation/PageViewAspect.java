package com.ai.website.business.annotation;

import com.ai.website.common.service.RedisService;
import com.ai.website.common.util.RequestUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Configuration
@Slf4j
public class PageViewAspect {

    @Autowired
    private RedisService redisService;

    @Value("${redis.key.pageView}")
    private String REDIS_KEY_PAGE_VIEW;

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.ai.website.business.annotation.PageView)")
    public void PageViewAspect() {

    }

    /**
     * 切入处理
     *
     * @param joinPoint
     * @return
     */
    @Around("PageViewAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        //获取被代理对象的参数
        Object[] object = joinPoint.getArgs();
        Object httpServletRequest = object[0];
        Object obj = null;
        try {
            String requestIp = RequestUtil.getRequestIp((HttpServletRequest) httpServletRequest);
            log.info("requestIp===" + requestIp);
            String key = REDIS_KEY_PAGE_VIEW;
            // 浏览量存入redis中  SET数据类型
            Long num = redisService.sAdd(key, requestIp);
            if (num == 0) {
                log.info("该ip:{},访问的浏览量已经新增过了", requestIp);
            }
            obj = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return obj;
    }
}
