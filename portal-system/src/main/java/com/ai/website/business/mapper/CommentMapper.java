package com.ai.website.business.mapper;

import com.ai.website.business.entity.Comment;

public interface CommentMapper {

    int insert(Comment comment);

    int addLikeNum(Long commentId);

    int addUnLikeNum(Long commentId);
}
