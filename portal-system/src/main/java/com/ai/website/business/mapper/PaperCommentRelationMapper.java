package com.ai.website.business.mapper;

import com.ai.website.business.entity.Comment;

import java.util.List;

public interface PaperCommentRelationMapper {

    int selectCommentsNumById(Long paperId);

    int insert(Long commentId, Long paperId);

    List<Comment> selectCommentsByPaperId(Long paperId);

    int deleteByPaperId(Long paperId);
}
