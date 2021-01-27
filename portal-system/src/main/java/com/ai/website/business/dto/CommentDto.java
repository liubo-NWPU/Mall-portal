package com.ai.website.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CommentDto {

    @ApiModelProperty(value = "正文")
    private String content;

}
