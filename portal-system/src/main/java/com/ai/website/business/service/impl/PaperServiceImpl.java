package com.ai.website.business.service.impl;

import com.ai.website.business.dto.PaperDto;
import com.ai.website.business.entity.MemberCollectRelation;
import com.ai.website.business.entity.MemberPaperRelation;
import com.ai.website.business.entity.Paper;
import com.ai.website.business.entity.PaperAttachRelation;
import com.ai.website.business.mapper.*;
import com.ai.website.business.service.PaperService;
import com.ai.website.common.api.CommonResult;
import com.ai.website.common.util.DateUtil;
import com.ai.website.member.entity.UmsMember;
import com.ai.website.member.service.UmsMemberCacheService;
import com.ai.website.member.service.UmsMemberService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private PaperCommentRelationMapper paperCommentRelationMapper;

    @Autowired
    private MemberPaperRelationMapper memberPaperRelationMapper;

    @Autowired
    private PaperAttachRelationMapper paperAttachRelationMapper;

    @Autowired
    private MemberCollectRelationMapper memberCollectRelationMapper;

    @Autowired
    private UmsMemberService umsMemberService;

    @Autowired
    private UmsMemberCacheService umsMemberCacheService;

    @Override
    public Paper create(PaperDto paperDto) {
        UmsMember currentMember = umsMemberService.getCurrentMember();
        String username = currentMember.getUsername();
        Paper paper = new Paper();
        BeanUtils.copyProperties(paperDto, paper);
        paper.setCreateTime(DateUtil.DateToString(new Date()));
        paper.setAuthor(username);
        paper.setLikeNum(0);
        paper.setReadNum(0);
        paper.setStoreNum(0);
        int count = paperMapper.insert(paper);
        if (count <= 0) {
            return null;
        }
        //关联文章id与附件地址
        Set attachFiles = umsMemberCacheService.getAttachFile(username);
        for (Object attachFile : attachFiles) {
            String path = String.valueOf(attachFile);
            String[] split = path.split("=!@#%");
            PaperAttachRelation paperAttachRelation=new PaperAttachRelation();
            paperAttachRelation.setPaperId(paper.getId());
            paperAttachRelation.setFileName(split[0]);
            paperAttachRelation.setFileUrl(split[1]);
            paperAttachRelationMapper.insert(paperAttachRelation);
        }
        //删除缓存附件地址
        umsMemberCacheService.delAttachFile(username);
        //关联会员username与文章id
        memberPaperRelationMapper.insert(paper.getAuthor(), paper.getId());
        return paper;
    }

    //分页显示所有文章列表
    @Override
    public List<Paper> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Paper> papers = paperMapper.selectByList("%" + keyword.trim() + "%");
        for (Paper paper : papers) {
            int commentsNum = getCommentsNum(paper.getId());
            paper.setCommentNum(commentsNum);
        }
        return papers;
    }

    @Override
    public List<Paper> listAll() {
        List<Paper> papers = paperMapper.selectAll();
        for (Paper paper : papers) {
            int commentsNum = getCommentsNum(paper.getId());
            paper.setCommentNum(commentsNum);
        }
        return papers;
    }

    //获取文章的评论数
    public int getCommentsNum(Long paperId) {
        return paperCommentRelationMapper.selectCommentsNumById(paperId);
    }

    @Override
    public Paper selectAllById(Long paperId, boolean readFlag) {
        //这里需要判断阅读数是否 +1
        if (readFlag) {
            int count = paperMapper.updateReadNum(paperId);
            if (count <= 0) {
                return null;
            }
        }
        //查询详情
        Paper paper = paperMapper.selectAllById(paperId);
        if (paper == null) return null;
        //查询评论数
        paper.setCommentNum(paperCommentRelationMapper.selectCommentsNumById(paperId));
        //查询附件
        List<PaperAttachRelation> attachFiles = paperAttachRelationMapper.selectFileUrlByPaperId(paperId);
        paper.setAttachFiles(attachFiles);
        return paper;
    }

    @Override
    public int addLikeNum(Long paperId) {
        return paperMapper.addLikeNum(paperId);
    }

    //根据会员姓名查找会员发表的相应文章列表
    @Override
    public List<Paper> selectArticleList(String type, String username, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Paper> papers = memberPaperRelationMapper.selectArticleList(type, username);
        return papers;
    }

    @Override
    public int deleteArticle(Long paperId) {
        deleteArticleCascade(paperId);
        return paperMapper.deleteArticle(paperId);
    }

    //文章级联表记录的删除
    @Override
    public void deleteArticleCascade(Long paperId) {
        //1. 删除文章-作者表记录
        memberPaperRelationMapper.deleteByPaperId(paperId);
        //2. 删除文章-评论表记录
        paperCommentRelationMapper.deleteByPaperId(paperId);
        //3. 删除文章-附件表记录
        paperAttachRelationMapper.deleteByPaperId(paperId);
    }

    @Override
    public MemberCollectRelation query(String username, Long paperId) {
        return memberCollectRelationMapper.select(username, paperId);
    }

    @Override
    public int createCollect(String username, Long paperId) {
        return memberCollectRelationMapper.insert(username, paperId);
    }
}
