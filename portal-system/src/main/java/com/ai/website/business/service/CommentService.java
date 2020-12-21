package com.ai.website.business.service;

import com.ai.website.business.dto.CommentDto;
import com.ai.website.business.entity.Comment;

import java.util.List;

public interface CommentService {
    //发布文章
    Comment create(CommentDto commentDto);

    int addLikeNum(Long commentId);

    int addUnLikeNum(Long commentId);

    List<Comment> getCommentsList(Long paperId, Integer pageSize, Integer pageNum);
}
