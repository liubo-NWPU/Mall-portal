package com.ai.website.business.service.impl;

import com.ai.website.business.dto.CommentDto;
import com.ai.website.business.entity.Comment;
import com.ai.website.business.mapper.CommentMapper;
import com.ai.website.business.mapper.PaperCommentRelationMapper;
import com.ai.website.business.service.CommentService;
import com.ai.website.common.util.DateUtil;
import com.ai.website.member.entity.UmsMember;
import com.ai.website.member.service.UmsMemberService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UmsMemberService umsMemberService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PaperCommentRelationMapper paperCommentRelationMapper;

    @Override
    public Comment create(CommentDto commentDto) {
        UmsMember currentMember = umsMemberService.getCurrentMember();
        String username = currentMember.getUsername();
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto, comment);
        comment.setCriticName(username);
        comment.setCreateTime(DateUtil.DateToString(new Date()));
        comment.setLikeNum(0);
        comment.setUnlikeNum(0);
        int count = commentMapper.insert(comment);
        if(count<=0){
            return null;
        }
        return comment;
    }

    @Override
    public int addLikeNum(Long commentId) {
        return commentMapper.addLikeNum(commentId);
    }

    @Override
    public int addUnLikeNum(Long commentId) {
        return commentMapper.addUnLikeNum(commentId);
    }

    @Override
    public List<Comment> getCommentsList(Long paperId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = paperCommentRelationMapper.selectCommentsByPaperId(paperId);
        return comments;
    }

}
