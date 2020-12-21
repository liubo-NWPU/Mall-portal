package com.ai.website.business.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class Paper {

    private Long id;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "类型：article,question,thought")
    private String type;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "正文")
    private String content;

    @ApiModelProperty(value = "多个标签，逗号分隔")
    private String tags;

    @ApiModelProperty(value = "点赞数")
    private Integer likeNum;

    @ApiModelProperty(value = "浏览数")
    private Integer readNum;

    @ApiModelProperty(value = "收藏数")
    private Integer storeNum;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "文章评论数")
    private Integer commentNum;

    @ApiModelProperty(value = "附件地址")
    private List<PaperAttachRelation> attachFiles;

    @ApiModelProperty(value = "置顶")
    private String top;

}
