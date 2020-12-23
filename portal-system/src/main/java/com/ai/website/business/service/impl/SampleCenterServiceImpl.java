package com.ai.website.business.service.impl;

import com.ai.website.business.entity.SampleDetailData;
import com.ai.website.business.entity.SampleTarget;
import com.ai.website.business.mapper.SampleSurfaceMapper;
import com.ai.website.business.mapper.SampleTargetMapper;
import com.ai.website.business.service.SampleCenterService;
import com.ai.website.common.api.CommonPage;
import com.ai.website.common.log.WebLogAspect;
import com.ai.website.common.service.RedisService;
import com.ai.website.common.util.FileUtil;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SampleCenterServiceImpl implements SampleCenterService {
    private static final Logger logger = LoggerFactory.getLogger(SampleCenterServiceImpl.class);

    @Autowired
    private SampleSurfaceMapper sampleSurfaceMapper;

    @Autowired
    private SampleTargetMapper sampleTargetMapper;

    @Value("${web.root-path}")
    private String rootPath;

    @Override
    public Map getSurfaceIds() {
        Map<String, Map> surfaceMap = new HashMap();
        Map<String, List> categoryMap = new HashMap<>();
        categoryMap.put("road", sampleSurfaceMapper.selectIdsByName("road"));
        categoryMap.put("building", sampleSurfaceMapper.selectIdsByName("building"));
        categoryMap.put("water", sampleSurfaceMapper.selectIdsByName("water"));
        categoryMap.put("plant", sampleSurfaceMapper.selectIdsByName("plant"));
        categoryMap.put("element", sampleSurfaceMapper.selectIdsByName("element"));
        surfaceMap.put("surfaceId", categoryMap);
        return surfaceMap;
    }

    @Override
    public void getSurfaceImageById(Long id, HttpServletResponse servletResponse) {
        String image_url = sampleSurfaceMapper.selectImageById(id);
        String url = rootPath + image_url;
        byte[] arr = FileUtil.readFile(url);
        FileUtil.writeImage(arr, "jpg", servletResponse);
    }

    @Override
    public Map getTargetIds() {

        Map<String, Map> targetMap = new HashMap();
        Map<String, List> categoryMap = new HashMap<>();
        categoryMap.put("car", sampleTargetMapper.selectIdsByName("car"));
        categoryMap.put("plane", sampleTargetMapper.selectIdsByName("plane"));
        categoryMap.put("airport", sampleTargetMapper.selectIdsByName("airport"));
        categoryMap.put("boat", sampleTargetMapper.selectIdsByName("boat"));
        categoryMap.put("oil", sampleTargetMapper.selectIdsByName("oil"));
        targetMap.put("targetId", categoryMap);

        return targetMap;
    }

    @Override
    public void getTargetImageById(Long id, HttpServletResponse servletResponse) {
        String image_url = sampleTargetMapper.selectImageById(id);
        String url = rootPath + image_url;
        byte[] arr = FileUtil.readFile(url);
        FileUtil.writeImage(arr, "jpg", servletResponse);
    }

    @Override
    public Map getData() {
        Map mapTotal = new HashMap();
        Map mapTarget = new HashMap();
        mapTarget.put("plane", 100);
        mapTarget.put("car", 118);
        mapTarget.put("airport", 85);
        mapTarget.put("boat", 81);
        mapTarget.put("oil", 88);

        Map mapSurface = new HashMap();
        mapSurface.put("road", 100);
        mapSurface.put("builing", 100);
        mapSurface.put("water", 100);
        mapSurface.put("plant", 100);
        mapSurface.put("element", 20);

        mapTotal.put("surface", mapSurface);
        mapTotal.put("target", mapTarget);
        return mapTotal;
    }


    @Override
    public SampleDetailData getSurfaceData(String name) {

        SampleDetailData sampleDetailData = new SampleDetailData();
        switch (name) {
            case "element":
                sampleDetailData.setNum("20");
                break;
        }
        return sampleDetailData;
    }

    @Override
    public SampleDetailData getTargetData(String name) {
        SampleDetailData sampleDetailData = new SampleDetailData();
        sampleDetailData.setSize("800x800");
        sampleDetailData.setChannel("3");
        sampleDetailData.setFormat(".jpg");
        sampleDetailData.setVideo("多种载荷");
        switch (name) {
            case "plane":
                sampleDetailData.setVolume("100M");
                sampleDetailData.setNum("100");
                sampleDetailData.setRatio("0.2-1m");
                break;
            case "car":
                sampleDetailData.setVolume("118M");
                sampleDetailData.setNum("118");
                sampleDetailData.setRatio("0.2-1m");
                break;
            case "airport":
                sampleDetailData.setVolume("85M");
                sampleDetailData.setNum("85");
                sampleDetailData.setRatio("1-5m");
                break;
            case "boat":
                sampleDetailData.setVolume("81M");
                sampleDetailData.setNum("81");
                sampleDetailData.setRatio("0.2-2m");
                break;
            case "oil":
                sampleDetailData.setVolume("88M");
                sampleDetailData.setNum("88");
                sampleDetailData.setRatio("0.2-2m");
                break;
        }
        return sampleDetailData;
    }

//    @Override
//    public CommonPage TargetList(String name, Integer pageSize, Integer pageNum) {
//        CommonPage cp = (CommonPage) redisService.get("target:" + name);
//        if (cp != null) return cp;
//        PageHelper.startPage(pageNum, pageSize);
//        List<SampleTarget> SampleTargets = sampleTargetMapper.selectAllList(name);
//        if (SampleTargets.size() != 0) {
//            redisService.set("target:" + name, CommonPage.restPage(SampleTargets), REDIS_EXPIRE);
//            return CommonPage.restPage(SampleTargets);
//        }
//        return null;
//    }

//    @Override
//    public Object surfaceStartPage() {
//        //先查缓存
//        Object surfaceStartPage = redisService.get("surfaceStartPage");
//        if (surfaceStartPage != null) return surfaceStartPage;
//        Map<String, Map> surfaceMap = new HashMap();
//        Map<String, List> categoryMap = new HashMap<>();
//        categoryMap.put("road", sampleSurfaceMapper.selectImageUrl("road"));
//        categoryMap.put("building", sampleSurfaceMapper.selectImageUrl("building"));
//        categoryMap.put("water", sampleSurfaceMapper.selectImageUrl("water"));
//        categoryMap.put("plant", sampleSurfaceMapper.selectImageUrl("plant"));
//        categoryMap.put("element", sampleSurfaceMapper.selectImageUrl("element"));
//        surfaceMap.put("surface", categoryMap);
//        redisService.set("surfaceStartPage", surfaceMap, REDIS_EXPIRE);
//        return surfaceMap;
//    }

//    @Override
//    public Object targetStartPage() {
//        //先查缓存
//        Object targetStartPage = redisService.get("targetStartPage");
//        if (targetStartPage != null) return targetStartPage;
//        Map<String, Map> targetMap = new HashMap();
//        Map<String, List> categoryMap = new HashMap<>();
//        categoryMap.put("car", sampleTargetMapper.selectImageUrl("car"));
//        categoryMap.put("plane", sampleTargetMapper.selectImageUrl("plane"));
//        categoryMap.put("airport", sampleTargetMapper.selectImageUrl("airport"));
//        categoryMap.put("boat", sampleTargetMapper.selectImageUrl("boat"));
//        categoryMap.put("oil", sampleTargetMapper.selectImageUrl("oil"));
//        targetMap.put("target", categoryMap);
//        redisService.set("targetStartPage", targetMap, REDIS_EXPIRE);
//        return targetMap;
//    }


}
