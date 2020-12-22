package com.ai.website.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
public class ReplyDto {

    @NotEmpty
    private String content;
    @NotEmpty
    private Long commentid;
    @NotEmpty
    private String replytousername;
}
