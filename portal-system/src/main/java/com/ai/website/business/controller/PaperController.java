package com.ai.website.business.controller;


import com.ai.website.business.dto.PaperDto;
import com.ai.website.business.entity.MemberCollectRelation;
import com.ai.website.business.entity.Paper;
import com.ai.website.business.mapper.MemberPaperRelationMapper;
import com.ai.website.business.mapper.PaperAttachRelationMapper;
import com.ai.website.business.service.PaperService;
import com.ai.website.common.api.CommonPage;
import com.ai.website.common.api.CommonResult;
import com.ai.website.common.util.FileUtil;
import com.ai.website.member.service.UmsMemberCacheService;
import com.ai.website.member.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@Api(tags = "PaperController", description = "文章管理")
@RequestMapping("/api/paper")
@CrossOrigin
public class PaperController {

    @Value("${web.root-path}")
    private String rootPath;
    @Value("${web.file-path}")
    private String filePath;
    @Value("${web.ip-address}")
    private String ipAddress;
    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private PaperService paperService;

    @Autowired
    private UmsMemberCacheService umsMemberCacheService;

    @Autowired
    private UmsMemberService umsMemberService;


    @ApiOperation(value = "文章发布")
    @RequestMapping(value = "/release", method = RequestMethod.POST)
    public CommonResult release(@RequestBody PaperDto paperDto) {
        Paper paper = paperService.create(paperDto);
        if (paper == null) {
            return CommonResult.failed("发布失败");
        }
        return CommonResult.success(paper);
    }

    //文章列表 分页
//    @ApiOperation(value = "技术圈首页文章分页显示列表")
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public CommonResult list(@RequestParam(value = "keyword", required = false) String keyword,
//                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
//                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
//        if (keyword == null) keyword = "";
//        List<Paper> paperList = paperService.list(keyword, pageSize, pageNum);
//        return CommonResult.success(CommonPage.restPage(paperList));
//    }

    //文章列表 不分页
    @ApiOperation(value = "技术圈首页文章不分页显示列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list() {
        List<Paper> paperList = paperService.listAll();
        return CommonResult.success(paperList);
    }

    @ApiOperation(value = "根据文章id获取文章详情")
    @RequestMapping(value = "/getPaperDetail", method = RequestMethod.GET)
    public CommonResult getPaperDetail(@RequestParam Long paperId,
                                       @RequestParam(value = "readFlag", defaultValue = "true") boolean readFlag) {
        Paper paper = paperService.selectAllById(paperId, readFlag);
        if (paper == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(paper);
    }

    @ApiOperation(value = "点赞")
    @RequestMapping(value = "/addLikeNum", method = RequestMethod.POST)
    public CommonResult addLikeNum(@RequestParam Long paperId) {
        int count = paperService.addLikeNum(paperId);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation(value = "文章上传附件")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public CommonResult upload(@RequestParam(value = "file", required = false) MultipartFile file) {
        String username = umsMemberService.getCurrentMember().getUsername();
        String fileUrl = null;
        String originalFilename = file.getOriginalFilename();
        if (file != null) {
            String fileName = FileUtil.upload(file, rootPath + filePath, originalFilename);
            fileUrl = ipAddress + ":" + serverPort + filePath + fileName;
            umsMemberCacheService.setAttachFile(username, originalFilename + "=!@#%" +fileUrl);
        }
        return CommonResult.success(originalFilename + "=" + fileUrl);
    }

    @ApiOperation(value = "进入发布文章或发布提问页面")
    @RequestMapping(value = "/enterArticle", method = RequestMethod.GET)
    public CommonResult enterArticle() {
        String username = umsMemberService.getCurrentMember().getUsername();
        //若缓存中有附件地址，先删除
        umsMemberCacheService.delAttachFile(username);
        return CommonResult.success("成功进入");
    }

    @ApiOperation(value = "收藏文章")
    @RequestMapping(value = "/collectArticle", method = RequestMethod.POST)
    public CommonResult collectArticle(@RequestParam Long paperId) {
        String username = umsMemberService.getCurrentMember().getUsername();
        //1.先查是否收藏过该文章
        MemberCollectRelation relation = paperService.query(username, paperId);
        if (relation != null) {
            return CommonResult.failed("对不起，你已收藏");
        }
        //2. 添加收藏记录
        paperService.createCollect(username, paperId);
        return CommonResult.success("收藏成功");
    }

}
