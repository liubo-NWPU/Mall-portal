package com.ai.website.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class ReplyDto {

    private String content;

    private Long commentid;

    private String replytousername;
}
