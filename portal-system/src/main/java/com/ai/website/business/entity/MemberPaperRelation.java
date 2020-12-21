package com.ai.website.business.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberPaperRelation {

    private Long id;

    @ApiModelProperty(value = "会员姓名")
    private Long memberName;

    @ApiModelProperty(value = "文章ID")
    private Long paperId;
}
