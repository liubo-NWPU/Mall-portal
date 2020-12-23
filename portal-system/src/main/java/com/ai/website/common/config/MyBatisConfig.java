package com.ai.website.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"com.ai.website.business.mapper", "com.ai.website.member.dao"})
public class MyBatisConfig {
}