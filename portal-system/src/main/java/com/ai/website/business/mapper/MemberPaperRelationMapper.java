package com.ai.website.business.mapper;

import com.ai.website.business.entity.Paper;

import java.util.List;

public interface MemberPaperRelationMapper {

    int insert(String username, Long paperId);

    List<Paper> selectArticleList(String type, String username);

    int selectArticleNum(String username, String type);

    int deleteByPaperId(Long paperId);
}
