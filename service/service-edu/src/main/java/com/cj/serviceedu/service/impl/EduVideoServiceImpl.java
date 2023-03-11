package com.cj.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cj.serviceedu.client.VodClient;
import com.cj.serviceedu.entity.EduVideo;
import com.cj.serviceedu.mapper.EduVideoMapper;
import com.cj.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author cj
 * @since 2023-01-18
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;
    @Override
    public boolean deleteByCourseId(String CourseId) {
        QueryWrapper<EduVideo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id", CourseId);
        queryWrapper.select("video_source_id");
        List<EduVideo> eduVideoList=baseMapper.selectList(queryWrapper);
//        System.out.println("deleteByCourseId:eduVideoList  :"+eduVideoList);
        List<String> videoSourceIdList=new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList) {
            String videoSourceId = eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId))
                videoSourceIdList.add(videoSourceId);
        }
//        System.out.println("deleteByCourseId:videoSourceIdList  ："+videoSourceIdList);
        vodClient.removeVideoList(videoSourceIdList);

        QueryWrapper<EduVideo> eduVideoQueryWrapper=new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id", CourseId);
        Integer count=baseMapper.delete(eduVideoQueryWrapper);
        return null != count && count > 0;
    }

    @Override
    public boolean removeVideoById(String id) {
       EduVideo eduVideo=baseMapper.selectById(id);
       String videoSourceId =eduVideo.getVideoSourceId();
//        System.out.println( " videoSourceId"+ videoSourceId);
       if(!StringUtils.isEmpty(videoSourceId))
       {
           vodClient.removeVideo(videoSourceId);
       }
        Integer result=baseMapper.deleteById(id);
       return result!=null && result>0;
    }
}
