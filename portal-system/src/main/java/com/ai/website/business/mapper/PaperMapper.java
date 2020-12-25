package com.ai.website.business.mapper;

import com.ai.website.business.entity.Paper;

import java.util.List;

public interface PaperMapper {
    int insert(Paper paper);

    //显示列表
    List<Paper> selectByList(String keyword);

    //进入详情页
    Paper selectAllById(Long paperId);

    //每进入一次详情页，阅读数+1
    int updateReadNum(Long paperId);

    int addLikeNum(Long commentId);

    int deleteArticle(Long paperId);
}
