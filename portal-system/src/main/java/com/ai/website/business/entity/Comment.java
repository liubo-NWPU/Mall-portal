package com.ai.website.business.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class Comment {

    private Long id;

    @ApiModelProperty(value = "正文")
    private String content;

    @ApiModelProperty(value = "当前时间")
    private String createTime;

    @ApiModelProperty(value = "评论人")
    private String criticName;

    @ApiModelProperty(value = "点赞数")
    private Integer likeNum;

    @ApiModelProperty(value = "踩数")
    private Integer unlikeNum;

    @ApiModelProperty(value = "评论下的回复")
    private List<Reply> replies;

}
