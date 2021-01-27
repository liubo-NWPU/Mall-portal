package com.ai.website.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ReplyDto {

    @NotBlank
    private String content;

    private Long commentid;

    private String replytousername;
}
