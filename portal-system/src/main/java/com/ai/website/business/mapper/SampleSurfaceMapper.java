package com.ai.website.business.mapper;

import java.util.List;

public interface SampleSurfaceMapper {

//    List selectImageUrl(String name);

    String selectImageById(Long id);

//    String selectLabelById(Long id);

    List<Long> selectIdsByName(String name);
}
