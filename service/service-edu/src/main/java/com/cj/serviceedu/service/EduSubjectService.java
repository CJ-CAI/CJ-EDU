package com.cj.serviceedu.service;

import com.cj.serviceedu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cj.serviceedu.entity.subject.EduSubject_one;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author cj
 * @since 2023-01-17
 */
public interface EduSubjectService extends IService<EduSubject> {
    void importSubjectData(MultipartFile file,EduSubjectService eduSubjectService);

    List<EduSubject_one> getTree();
}
