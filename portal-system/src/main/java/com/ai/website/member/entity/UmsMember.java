package com.ai.website.member.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UmsMember {

    private Long id;

    @ApiModelProperty(value = "用户登陆名")
    private String username;

    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "简介")
    private String profile;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "学校")
    private String school;

    @ApiModelProperty(value = "行业")
    private String industry;

    @ApiModelProperty(value = "公司")
    private String company;

    @ApiModelProperty(value = "岗位")
    private String position;

    private String phoneNumber;

    @ApiModelProperty(value = "帐号启用状态：0->禁用；1->启用")
    private Integer status;

    private String createTime;

    @ApiModelProperty(value = "积分")
    private Integer integration;

    @ApiModelProperty(value = "头像地址")
    private String imageUrl;

}
