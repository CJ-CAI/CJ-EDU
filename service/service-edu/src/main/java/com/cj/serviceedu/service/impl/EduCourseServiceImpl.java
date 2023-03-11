package com.cj.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cj.servicebase.exception.GuliException;
import com.cj.serviceedu.client.OssClient;
import com.cj.serviceedu.entity.front_vo.CourseQueryVo;
import com.cj.serviceedu.entity.front_vo.CourseWebVo;
import com.cj.serviceedu.entity.query.CourseQuery;
import com.cj.serviceedu.entity.vo.CourseInfoForm;
import com.cj.serviceedu.entity.EduCourse;
import com.cj.serviceedu.entity.EduCourseDescription;
import com.cj.serviceedu.entity.vo.CoursePublishVo;
import com.cj.serviceedu.mapper.EduCourseMapper;
import com.cj.serviceedu.service.EduChapterService;
import com.cj.serviceedu.service.EduCourseDescriptionService;
import com.cj.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cj.serviceedu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author cj
 * @since 2023-01-18
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private OssClient ossClient;
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        EduCourse eduCourse=new EduCourse();
        eduCourse.setStatus(EduCourse.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        Boolean result_C= this.save(eduCourse);
        if(!result_C){
            throw new GuliException(20001,"课程信息保存失败");
        }
        EduCourseDescription eduCourseDescription=new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescription.setId(eduCourse.getId());
        Boolean result_D=eduCourseDescriptionService.save(eduCourseDescription);
        if(!result_D){
            throw new GuliException(20001,"课程详情信息保存失败");
        }
        return eduCourse.getId();
    }

    @Override
    public Boolean updateCourseInfo(CourseInfoForm courseInfoForm) {
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        Boolean result_C= this.updateById(eduCourse);
        if(!result_C){
            throw new GuliException(20001,"课程信息修改失败");
        }
        EduCourseDescription eduCourseDescription=new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescription.setId(eduCourse.getId());
        Boolean result_D=eduCourseDescriptionService.updateById(eduCourseDescription);
        if(!result_D){
            throw new GuliException(20001,"课程详情信息修改失败");
        }
        return true;
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.getPublishCourseInfo(id);
    }

    @Override
    public Boolean setPublishCourse(String id) {
        EduCourse eduCourse=new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus(eduCourse.COURSE_NORMAL);
        Integer count=baseMapper.updateById(eduCourse);
        return null!=count&&count>0;
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
//        System.out.println(courseQuery);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return ;
        }
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("subject_id", subjectId);
        }
            baseMapper.selectPage(pageParam, queryWrapper);
        }

    @Override
    public boolean delete_courseById(String id) {
        eduVideoService.deleteByCourseId(id);
        eduChapterService.deleteByCourseId(id);
        eduCourseDescriptionService.removeById(id);
        //远程调用删除阿里云课程封面
        String delete_name=baseMapper.selectById(id).getCover();
//        System.out.println("delete_name:"+delete_name);
        ossClient.delete_file(delete_name);
        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }
//    ============================front======================================
    @Override
    public List<EduCourse> selectByTeacherId(String teacherId) {
        QueryWrapper<EduCourse> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("teacher_id", teacherId);
        queryWrapper.orderByDesc("gmt_modified");
        List<EduCourse> courses=baseMapper.selectList(queryWrapper);
        return  courses;
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseQueryVo courseQueryVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id",
                    courseQueryVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectId())) {
            queryWrapper.eq("subject_id", courseQueryVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseQueryVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseQueryVo.getPriceSort()))
            queryWrapper.orderByDesc("price");
        baseMapper.selectPage(pageParam, queryWrapper);
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public void updatePageViewCount(String id) {
        EduCourse eduCourse=baseMapper.selectById(id);
        eduCourse.setViewCount(eduCourse.getViewCount()+1);
        baseMapper.updateById(eduCourse);
    }

    @Override
    public CourseWebVo selectInfoWebById(String id) {
        this.updatePageViewCount(id);
        return baseMapper.selectInfoWebById(id);
    }
}
