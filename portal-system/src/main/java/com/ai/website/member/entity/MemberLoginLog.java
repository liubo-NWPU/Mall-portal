package com.ai.website.member.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberLoginLog {

    private String username;

    private String loginTime;
}
