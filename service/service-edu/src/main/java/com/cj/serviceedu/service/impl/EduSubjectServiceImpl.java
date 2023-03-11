package com.cj.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.cj.servicebase.exception.GuliException;
import com.cj.serviceedu.config.SubjectExcelListener;
import com.cj.serviceedu.entity.EduSubject;
import com.cj.serviceedu.entity.subject.EduSubject_one;
import com.cj.serviceedu.entity.excel.EduSubject_save;
import com.cj.serviceedu.entity.subject.EduSubject_two;
import com.cj.serviceedu.mapper.EduSubjectMapper;
import com.cj.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author cj
 * @since 2023-01-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void importSubjectData(MultipartFile file,EduSubjectService subjectService) {
        try {
            InputStream inputStream=file.getInputStream();
            EasyExcel.read(inputStream, EduSubject_save.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
            throw new GuliException(20002,"添加课程失败");
        }
    }

    @Override
    public List<EduSubject_one> getTree() {
        List<EduSubject> eduSubjects=baseMapper.selectList(null);
//        System.out.println(eduSubjects);
        List<EduSubject_one> eduSubject_ones=new ArrayList<>();
        List<EduSubject_two> eduSubject_twos=new ArrayList<>();
        EduSubject_one eduSubject_one=new EduSubject_one();
        EduSubject_two eduSubject_two=new EduSubject_two();

        for (EduSubject eduSubject : eduSubjects) {
            if (eduSubject.getParentId().equals("0")) {
                BeanUtils.copyProperties(eduSubject, eduSubject_one);
//                System.out.println("input-two"+eduSubject_one);
                for (EduSubject subject : eduSubjects) {
                    if (subject.getParentId().equals(eduSubject.getId())) {
                        BeanUtils.copyProperties(subject, eduSubject_two);
                        eduSubject_twos.add(eduSubject_two);
//                        System.out.print("two-"+eduSubject_twos);
                        eduSubject_two=new EduSubject_two();
                    }
                }
//                System.out.println();
//                System.out.println(eduSubject_twos);
                eduSubject_one.setEduSubject_twos(eduSubject_twos);
//                eduSubject_twos.clear();
//                System.out.println(eduSubject_one);
                eduSubject_twos=new ArrayList<>();
                eduSubject_ones.add(eduSubject_one);
                eduSubject_one=new EduSubject_one();
            }
        }
//        System.out.println(eduSubject_ones);
        return eduSubject_ones;
    }
}
