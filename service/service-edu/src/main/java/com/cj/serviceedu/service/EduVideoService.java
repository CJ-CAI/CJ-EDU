package com.cj.serviceedu.service;

import com.cj.serviceedu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author cj
 * @since 2023-01-18
 */
public interface EduVideoService extends IService<EduVideo> {
    boolean deleteByCourseId(String CourseId);
    boolean removeVideoById(String id);
}
