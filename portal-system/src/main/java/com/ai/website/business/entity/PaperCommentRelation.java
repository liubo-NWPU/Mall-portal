package com.ai.website.business.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PaperCommentRelation {
    private Long id;

    @ApiModelProperty(value = "文章ID")
    private Long paperId;

    @ApiModelProperty(value = "评论ID")
    private Long commentId;

}
