package com.ai.website.business.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentReplyRelation {
    private Long id;

    @ApiModelProperty(value = "评论id")
    private Long commentId;

    @ApiModelProperty(value = "回复id")
    private Long replyId;
}
