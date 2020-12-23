package com.ai.website.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PaperDto {

    @NotEmpty(message = "类型不能为空")
    @ApiModelProperty(value = "类型：article,question,thought")
    private String type;

    @NotEmpty(message = "标题不能为空")
    @ApiModelProperty(value = "标题")
    private String title;

    @NotEmpty(message = "正文不能为空")
    @ApiModelProperty(value = "正文")
    private String content;

    @NotEmpty(message = "标签不能为空")
    @ApiModelProperty(value = "多个标签，逗号分隔")
    private String tags;
}
