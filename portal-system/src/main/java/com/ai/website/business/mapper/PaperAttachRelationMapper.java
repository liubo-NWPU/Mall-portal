package com.ai.website.business.mapper;

import com.ai.website.business.entity.PaperAttachRelation;

import java.util.List;

public interface PaperAttachRelationMapper {

    int insert(PaperAttachRelation paperAttachRelation);

    List<PaperAttachRelation> selectFileUrlByPaperId(Long paperId);

    int deleteByPaperId(Long paperId);
}
