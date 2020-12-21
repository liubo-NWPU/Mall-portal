package com.ai.website.business.controller;


import com.ai.website.business.dto.ReplyDto;
import com.ai.website.business.entity.Reply;
import com.ai.website.business.mapper.CommentReplyRelationMapper;
import com.ai.website.business.service.ReplyService;
import com.ai.website.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "ReplyController", description = "回复管理")
@RequestMapping("/api/reply")
@CrossOrigin
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private CommentReplyRelationMapper commentReplyRelationMapper;


    @ApiOperation(value = "发布回复")
    @RequestMapping(value = "/release", method = RequestMethod.POST)
    public CommonResult release(@RequestBody ReplyDto replyDto) {
        System.out.println(replyDto);
        Reply reply = replyService.create(replyDto.getContent(), replyDto.getReplytousername());
        if (reply == null) {
            CommonResult.failed("回复成功");
        }
//        关联评论id与回复id
        int count = commentReplyRelationMapper.insert(replyDto.getCommentid(), reply.getId());
        if (count <= 0) {
            return CommonResult.failed("评论与回复关联失败");
        }
        return CommonResult.success(reply, "回复成功");
    }

    @ApiOperation(value = "赞")
    @RequestMapping(value = "/addLikeNum", method = RequestMethod.POST)
    public CommonResult addLikeNum(@RequestParam Long replyId) {
        int count = replyService.addLikeNum(replyId);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation(value = "踩")
    @RequestMapping(value = "/addUnlikeNum", method = RequestMethod.POST)
    public CommonResult addUnlikeNum(@RequestParam Long replyId) {
        int count = replyService.addUnLikeNum(replyId);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

}
