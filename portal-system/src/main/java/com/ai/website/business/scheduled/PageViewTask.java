package com.ai.website.business.scheduled;

import com.ai.website.business.entity.PageViewEntity;
import com.ai.website.business.mapper.PageViewMapper;
import com.ai.website.common.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Slf4j
public class PageViewTask {

    @Autowired
    private RedisService redisService;

    @Autowired
    private PageViewMapper pageViewMapper;

    // 每天凌晨三点执行
    @Scheduled(cron = "0 0 3 * * ? ")
    @Transactional(rollbackFor = Exception.class)
    public void createHyperLog() {
        log.info("浏览量入库开始");
        String key = "pageView";
        Long viewNum = redisService.sSize(key);
        if (viewNum > 0) {
            PageViewEntity pageViewEntity = new PageViewEntity();
            pageViewEntity.setViewNum(viewNum);
            pageViewEntity.setCreateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            pageViewMapper.insert(pageViewEntity);
            redisService.del(key);
        }
        log.info("浏览量入库结束");
    }
}
