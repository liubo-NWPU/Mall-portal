package com.ai.website.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PaperDto {

    @ApiModelProperty(value = "类型：article,question,thought")
    private String type;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "正文")
    private String content;

    @ApiModelProperty(value = "多个标签，逗号分隔")
    private String tags;
}
