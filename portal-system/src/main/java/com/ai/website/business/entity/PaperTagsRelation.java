package com.ai.website.business.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PaperTagsRelation {
    private Long id;

    @ApiModelProperty(value = "文章ID")
    private Long paperId;

    @ApiModelProperty(value = "标签ID")
    private Long tagsId;
}
