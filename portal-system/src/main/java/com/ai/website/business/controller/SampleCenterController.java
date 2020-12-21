package com.ai.website.business.controller;


import com.ai.website.business.entity.SampleDetailData;
import com.ai.website.business.service.SampleCenterService;
import com.ai.website.common.api.CommonPage;
import com.ai.website.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Api(tags = "SampleCenterController", description = "样本中心")
@RequestMapping("/api/sample")
@CrossOrigin
public class SampleCenterController {

    @Autowired
    private SampleCenterService sampleCenterService;

    @ApiOperation(value = "获取地物图片ID")
    @RequestMapping(value = "/getSurfaceIds", method = RequestMethod.GET)
    public CommonResult getSurfaceIds() {
        Map surfaceIds = sampleCenterService.getSurfaceIds();
        return CommonResult.success(surfaceIds);
    }

    @ApiOperation(value = "根据地物id获取image")
    @RequestMapping(value = "/getSurfaceImage", method = RequestMethod.GET)
    public void getSurfaceImage(@RequestParam Long id, HttpServletResponse servletResponse) {
        sampleCenterService.getSurfaceImageById(id, servletResponse);
    }

    @ApiOperation(value = "获取目标图片ID")
    @RequestMapping(value = "/getTargetIds", method = RequestMethod.GET)
    public CommonResult getTargetIds() {
        Map targetIds = sampleCenterService.getTargetIds();
        return CommonResult.success(targetIds);
    }

    @ApiOperation(value = "根据目标id获取image")
    @RequestMapping(value = "/getTargetImage", method = RequestMethod.GET)
    public void getTargetImage(@RequestParam Long id, HttpServletResponse servletResponse) {
        sampleCenterService.getTargetImageById(id, servletResponse);
    }

    @ApiOperation(value = "首页数据")
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public CommonResult getData() {
        Map data = sampleCenterService.getData();
        return CommonResult.success(data);
    }

//    @ApiOperation(value = "地物首页4张图片")
//    @RequestMapping(value = "/getSurface", method = RequestMethod.GET)
//    public CommonResult getSurface() {
//        Object surfaceStartPage = sampleCenterService.surfaceStartPage();
//        return CommonResult.success(surfaceStartPage);
//    }

//    @ApiOperation(value = "目标首页4张图片")
//    @RequestMapping(value = "/getTarget", method = RequestMethod.GET)
//    public CommonResult getTarget() {
//        Object targetStartPage = sampleCenterService.targetStartPage();
//        return CommonResult.success(targetStartPage);
//    }

    @ApiOperation(value = "更多地物数据展示")
    @RequestMapping(value = "/getSurfaceDetailsData", method = RequestMethod.GET)
    public CommonResult getSurfaceDetailsData(@RequestParam String name) {
        SampleDetailData surfaceData = sampleCenterService.getSurfaceData(name);
        return CommonResult.success(surfaceData);
    }

    @ApiOperation(value = "更多目标数据展示")
    @RequestMapping(value = "/getTargetDetailsData", method = RequestMethod.GET)
    public CommonResult getTargetDetailsData(@RequestParam String name) {
        SampleDetailData targetData = sampleCenterService.getTargetData(name);
        return CommonResult.success(targetData);
    }


}
