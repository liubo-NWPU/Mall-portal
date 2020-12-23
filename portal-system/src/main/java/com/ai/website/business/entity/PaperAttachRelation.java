package com.ai.website.business.entity;

import lombok.Data;

@Data
public class PaperAttachRelation {

    private Long id;

    private Long paperId;

    private String fileUrl;

    private String fileName;

}
