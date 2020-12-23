package com.ai.website.business.mapper;

import com.ai.website.business.entity.SampleTarget;

import java.util.List;

public interface SampleTargetMapper {

//    List selectImageUrl(String name);
//
//    List<SampleTarget> selectAllList(String name);

    String selectImageById(Long id);

    List<Long> selectIdsByName(String name);
}
