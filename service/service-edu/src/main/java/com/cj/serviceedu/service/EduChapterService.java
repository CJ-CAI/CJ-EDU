package com.cj.serviceedu.service;

import com.cj.serviceedu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cj.serviceedu.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author cj
 * @since 2023-01-18
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> nestedList(String courseId);

    void deleteChapter(String chapterID);
    Boolean deleteByCourseId(String CourseId);
}
