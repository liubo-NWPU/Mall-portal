package com.ai.website.business.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Reply {
    private Long id;

    @ApiModelProperty(value = "回复人")
    private String criticName;

    @ApiModelProperty(value = "回复给谁")
    private String replyToUserName;

    @ApiModelProperty(value = "回复内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "点赞数")
    private Integer likeNum;

    @ApiModelProperty(value = "踩数")
    private Integer unlikeNum;
}
