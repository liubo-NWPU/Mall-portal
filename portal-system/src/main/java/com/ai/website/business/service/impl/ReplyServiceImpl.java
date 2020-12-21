package com.ai.website.business.service.impl;

import com.ai.website.business.entity.Reply;
import com.ai.website.business.mapper.ReplyMapper;
import com.ai.website.business.service.ReplyService;
import com.ai.website.common.util.DateUtil;
import com.ai.website.member.entity.UmsMember;
import com.ai.website.member.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private UmsMemberService umsMemberService;

    @Override
    public Reply create(String content, String replyToUsername) {
        UmsMember currentMember = umsMemberService.getCurrentMember();
        String username = currentMember.getUsername();
        Reply reply = new Reply();
        reply.setCriticName(username);
        reply.setContent(content);
        reply.setReplyToUserName(replyToUsername);
        reply.setCreateTime(DateUtil.DateToString(new Date()));
        reply.setLikeNum(0);
        reply.setUnlikeNum(0);
        int count = replyMapper.insert(reply);
        if (count <= 0) {
            return null;
        }
        return reply;
    }

    @Override
    public int addLikeNum(Long replyId) {
        return replyMapper.addLikeNum(replyId);
    }

    @Override
    public int addUnLikeNum(Long replyId) {
        return replyMapper.addUnLikeNum(replyId);
    }
}
