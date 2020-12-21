package com.ai.website.business.mapper;

import com.ai.website.business.entity.Reply;

import java.util.List;

public interface CommentReplyRelationMapper {

    int insert(Long commentId, Long replyId);

    List<Reply> selectReplysByCommentId(Long commentId);

}
