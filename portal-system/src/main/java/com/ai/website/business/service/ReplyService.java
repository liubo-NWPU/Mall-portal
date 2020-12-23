package com.ai.website.business.service;

import com.ai.website.business.entity.Reply;

public interface ReplyService {
    //发布回复
    Reply create(String content, String replyToUsername);

    int addLikeNum(Long replyId);

    int addUnLikeNum(Long replyId);

}
