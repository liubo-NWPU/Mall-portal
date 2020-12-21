package com.ai.website.member.controller;

import com.ai.website.business.entity.Paper;
import com.ai.website.business.mapper.MemberCollectRelationMapper;
import com.ai.website.business.mapper.MemberPaperRelationMapper;
import com.ai.website.business.service.PaperService;
import com.ai.website.common.api.CommonPage;
import com.ai.website.common.api.CommonResult;
import com.ai.website.common.util.FileUtil;
import com.ai.website.member.dto.MemberDto;
import com.ai.website.member.dto.MemberInfoDto;
import com.ai.website.member.entity.UmsMember;
import com.ai.website.member.entity.UmsMemberParam;
import com.ai.website.member.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员登录注册管理Controller
 */
@RestController
@Api(tags = "MemberController", description = "个人中心")
@RequestMapping("/api/member")
@CrossOrigin
public class MemberController {

    @Value("${web.root-path}")
    private String rootPath;
    @Value("${web.image-path}")
    private String imagePath;
    @Value("${web.ip-address}")
    private String ipAddress;
    @Value("${server.port}")
    private String serverPort;


    @Autowired
    private UmsMemberService memberService;

    @Autowired
    private MemberPaperRelationMapper memberPaperRelationMapper;

    @Autowired
    private MemberCollectRelationMapper memberCollectRelationMapper;

    @Autowired
    private PaperService paperService;

    @ApiOperation(value = "分页返回会员列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult getAllMembers(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<UmsMember> memberList = memberService.list(pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(memberList));
    }


    @ApiOperation(value = "根据姓名查询会员参与内容的数量")
    @RequestMapping(value = "/getCategoryNum", method = RequestMethod.GET)
    public CommonResult getCategoryNum(@RequestParam String username) {
        MemberDto memberDto = new MemberDto();
        //获取artilce文章类型数量
        int articleNum = memberPaperRelationMapper.selectArticleNum(username, "article");
        //获取question文章类型数量
        int questionNum = memberPaperRelationMapper.selectArticleNum(username, "question");
        //获取收藏数量
        int collectNum = memberCollectRelationMapper.selectNum(username);
        //获取match数量
        //...........
        memberDto.setArticleNum(articleNum);
        memberDto.setQuetionNum(questionNum);
        memberDto.setCollectNum(collectNum);
        memberDto.setMatchNum(0);
        return CommonResult.success(memberDto);
    }

    @ApiOperation(value = "展示会员发表的相应文章列表")
    @RequestMapping(value = "/findArticleByName", method = RequestMethod.GET)
    public CommonResult findArticleByName(@RequestParam String type,
                                          @RequestParam String username,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<Paper> papers = paperService.selectArticleList(type, username, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(papers));
    }

//    @ApiOperation(value = "展示会员收藏的文章列表")
////    @RequestMapping(value = "/findArticleByName", method = RequestMethod.GET)
////    public CommonResult findArticleByName(@RequestParam String username,
////                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
////                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
////        List<Paper> papers = paperService.selectArticleList(type, username, pageSize, pageNum);
////        return CommonResult.success(CommonPage.restPage(papers));
////    }

    @ApiOperation(value = "会员删除自己的帖子")
    @RequestMapping(value = "/deleteArticle", method = RequestMethod.POST)
    public CommonResult deleteArticle(@RequestParam Long paperId) {
        //删除文章
        int count = paperService.deleteArticle(paperId);
        if (count < 0) {
            return CommonResult.failed();
        }
        return CommonResult.success("删除成功");
    }

    @ApiOperation(value = "会员上传头像")
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public CommonResult uploadImage(@RequestParam String username,
                                    @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null) {
            String fileName = FileUtil.upload(file, rootPath + imagePath, file.getOriginalFilename());
            int count = 0;
            if (fileName != null) {
                count = memberService.updateHeadImage(username, ipAddress + ":" + serverPort + imagePath + fileName);
            }
            if (count >= 0) {
                return CommonResult.success(ipAddress + ":" + serverPort + imagePath + fileName);
            }
        }
        return CommonResult.failed();
    }

    @ApiOperation(value = "保存个人信息")
    @RequestMapping(value = "/savePersonInfo", method = RequestMethod.POST)
    public CommonResult savePersonInfo(@RequestBody MemberInfoDto memberInfoDto) {
        int count = memberService.updatePersonInfo(memberInfoDto);
        if (count < 0) {
            return CommonResult.failed();
        }
        return CommonResult.success("保存成功");
    }

}
