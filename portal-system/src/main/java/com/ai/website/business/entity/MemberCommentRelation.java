package com.ai.website.business.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberCommentRelation {
    private Long id;

    @ApiModelProperty(value = "会员ID")
    private Long memberId;

    @ApiModelProperty(value = "评论ID")
    private Long commentId;
}
