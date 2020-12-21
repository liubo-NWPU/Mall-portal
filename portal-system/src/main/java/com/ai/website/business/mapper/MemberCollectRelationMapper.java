package com.ai.website.business.mapper;

import com.ai.website.business.entity.MemberCollectRelation;
import com.ai.website.business.entity.Paper;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface MemberCollectRelationMapper {
    MemberCollectRelation select(String username, Long paperId);

    int insert(String username, Long paperId);

    int selectNum(String username);

    List<Paper> selectList(String username);
}
