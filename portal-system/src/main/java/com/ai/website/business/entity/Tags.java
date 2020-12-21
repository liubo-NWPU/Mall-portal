package com.ai.website.business.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Tags {

    private Long id;

    @ApiModelProperty(value = "标签名")
    private String name;
}
