package com.ai.website.business.controller;


import com.ai.website.business.dto.CommentDto;
import com.ai.website.business.entity.Comment;
import com.ai.website.business.entity.Reply;
import com.ai.website.business.mapper.CommentReplyRelationMapper;
import com.ai.website.business.mapper.PaperCommentRelationMapper;
import com.ai.website.business.service.CommentService;
import com.ai.website.common.api.CommonPage;
import com.ai.website.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "CommentController", description = "评论管理")
@RequestMapping("/api/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PaperCommentRelationMapper paperCommentRelationMapper;

    @Autowired
    private CommentReplyRelationMapper commentReplyRelationMapper;


    @ApiOperation(value = "发布评论")
    @RequestMapping(value = "/release", method = RequestMethod.POST)
    public CommonResult release(@RequestBody CommentDto commentDto,
                                @RequestParam Long paperId) {
        Comment comment = commentService.create(commentDto);
        if (comment == null) {
            return CommonResult.failed("评论失败");
        }
        //关联文章id与评论id
        int count = paperCommentRelationMapper.insert(comment.getId(), paperId);
        if (count <= 0) {
            return CommonResult.failed("评论与文章关联失败");
        }
        return CommonResult.success(comment);
    }

    @ApiOperation(value = "根据文章id获取评论及回复列表")
    @RequestMapping(value = "/getCommentsList", method = RequestMethod.GET)
    public CommonResult getCommentsList(@RequestParam Long paperId,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        //1.获取评论
        List<Comment> commentsList = commentService.getCommentsList(paperId, pageSize, pageNum);
        //2.获取评论下的回复
        for (Comment comment : commentsList) {
            List<Reply> replies = commentReplyRelationMapper.selectReplysByCommentId(comment.getId());
            comment.setReplies(replies);
        }
        return CommonResult.success(CommonPage.restPage(commentsList));
    }


    @ApiOperation(value = "赞")
    @RequestMapping(value = "/addLikeNum", method = RequestMethod.POST)
    public CommonResult addLikeNum(@RequestParam Long CommentId) {
        int count = commentService.addLikeNum(CommentId);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation(value = "踩")
    @RequestMapping(value = "/addUnlikeNum", method = RequestMethod.POST)
    public CommonResult addUnlikeNum(@RequestParam Long CommentId) {
        int count = commentService.addUnLikeNum(CommentId);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }


}
