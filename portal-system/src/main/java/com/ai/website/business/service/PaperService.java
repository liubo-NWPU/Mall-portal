package com.ai.website.business.service;

import com.ai.website.business.dto.PaperDto;
import com.ai.website.business.entity.MemberCollectRelation;
import com.ai.website.business.entity.Paper;

import java.util.List;

public interface PaperService {
    //发布文章
    Paper create(PaperDto paperDto);

    //分页文章列表
    List<Paper> list(String keyword, Integer pageSize, Integer pageNum);

    Paper selectAllById(Long paperId, boolean readFlag);

    int addLikeNum(Long commentId);

    List<Paper> selectArticleList(String type, String username, Integer pageSize, Integer pageNum);

    int deleteArticle(Long paperId);

    //文章的级联表删除
    void deleteArticleCascade(Long id);

    MemberCollectRelation query(String username, Long paperId);

    int createCollect(String username, Long paperId);

}
