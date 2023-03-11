package com.cj.serviceedu.config;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cj.servicebase.exception.GuliException;
import com.cj.serviceedu.entity.EduSubject;
import com.cj.serviceedu.entity.excel.EduSubject_save;
import com.cj.serviceedu.service.EduSubjectService;

import java.util.Map;

public class SubjectExcelListener extends AnalysisEventListener<EduSubject_save> {
    public EduSubjectService eduSubjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
    }

    @Override
    public void invoke(EduSubject_save excelSubjectData, AnalysisContext analysisContext) {
            if (excelSubjectData==null)
                throw new GuliException(20001,"添加失败");
        EduSubject existOneObject=this.existOneSubject(eduSubjectService,excelSubjectData.getOneSubjectName());
//        System.out.println("查询one结果:"+existOneObject);
        if (existOneObject==null)
        {
            System.out.println("执行添加one");
            existOneObject=new EduSubject();
            existOneObject.setTitle(excelSubjectData.getOneSubjectName());
            existOneObject.setParentId("0");
            eduSubjectService.save(existOneObject);
            existOneObject=this.existOneSubject(eduSubjectService,excelSubjectData.getOneSubjectName());
        }
        String father_id=existOneObject.getId();
        EduSubject existTwoObject=this.existTwoSubject(eduSubjectService,excelSubjectData.getTwoSubjectName());
//        System.out.println("执行existTwoObject查询"+existTwoObject);
        if (existTwoObject==null)
        {
            existTwoObject=new EduSubject();
            existTwoObject.setTitle(excelSubjectData.getTwoSubjectName());
            existTwoObject.setParentId(father_id);
            eduSubjectService.save(existTwoObject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> eduSubjectQueryWrapper=new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("title",name);
        EduSubject eduSubject=eduSubjectService.getOne(eduSubjectQueryWrapper);
        return eduSubject;
    }
    private  EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> eduSubjectQueryWrapper=new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("title",name);
//        eduSubjectQueryWrapper.eq("parent_id","0");
        EduSubject eduSubject=eduSubjectService.getOne(eduSubjectQueryWrapper);
        return  eduSubject;
    }
}
