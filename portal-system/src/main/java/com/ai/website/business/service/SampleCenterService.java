package com.ai.website.business.service;

import com.ai.website.business.entity.SampleDetailData;
import com.ai.website.common.api.CommonPage;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface SampleCenterService {
    SampleDetailData getSurfaceData(String name);

    SampleDetailData getTargetData(String name);

    Map getData();

    Map getSurfaceIds();

    Map getTargetIds();

    void getSurfaceImageById(Long id, HttpServletResponse servletResponse);

    void getTargetImageById(Long id, HttpServletResponse servletResponse);

}
