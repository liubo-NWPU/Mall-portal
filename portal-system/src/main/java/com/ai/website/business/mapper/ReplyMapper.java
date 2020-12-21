package com.ai.website.business.mapper;

import com.ai.website.business.entity.Reply;

public interface ReplyMapper {

    int insert(Reply reply);

    int addLikeNum(Long replyId);

    int addUnLikeNum(Long replyId);

}
