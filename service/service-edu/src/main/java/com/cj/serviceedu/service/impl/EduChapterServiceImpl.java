package com.cj.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cj.servicebase.exception.GuliException;
import com.cj.serviceedu.entity.EduChapter;
import com.cj.serviceedu.entity.EduVideo;
import com.cj.serviceedu.entity.vo.ChapterVo;
import com.cj.serviceedu.entity.vo.VideoVo;
import com.cj.serviceedu.mapper.EduChapterMapper;
import com.cj.serviceedu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cj.serviceedu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author cj
 * @since 2023-01-18
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;
    @Override
    public List<ChapterVo> nestedList(String courseId) {
//        System.out.println("查询数据");
        List<ChapterVo> chapterVoList=new ArrayList<>();

        List<EduChapter> eduChapterList=new ArrayList<>();
        QueryWrapper<EduChapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.orderByAsc("sort");
        eduChapterList=baseMapper.selectList(queryWrapper);
//        System.out.println(eduChapterList);

        List<EduVideo> eduVideos=new ArrayList<>();
        QueryWrapper<EduVideo> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("course_id",courseId);
        eduVideos=eduVideoService.list(queryWrapper1);
//        System.out.println(eduVideos);

        for (EduChapter eduChapter : eduChapterList) {
            ChapterVo chapterVo=new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);

            List<VideoVo> videoVos=new ArrayList<>();
            for (EduVideo eduVideo : eduVideos) {
                if(eduVideo.getChapterId().equals(eduChapter.getId()))
                {
                    VideoVo videoVo=new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
            chapterVoList.add(chapterVo);
        }
//        System.out.println(chapterVoList);
        return chapterVoList;
    }

    @Override
    public void deleteChapter(String chapterID) {
        QueryWrapper<EduVideo> eduVideoQueryWrapper=new QueryWrapper<>();
        eduVideoQueryWrapper.eq("chapter_id",chapterID);
        int result=eduVideoService.count(eduVideoQueryWrapper);
        if (result>0)
            throw new GuliException(20001,"请先删除小节");
        else
            baseMapper.deleteById(chapterID);
    }

    @Override
    public Boolean deleteByCourseId(String CourseId) {
        QueryWrapper<EduChapter> eduChapterQueryWrapper=new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id", CourseId);
        Integer count=baseMapper.delete(eduChapterQueryWrapper);
        return null != count && count > 0;
    }
}
