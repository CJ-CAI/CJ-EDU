package com.cj.serviceedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cj.commonutils.R;
import com.cj.serviceedu.entity.EduComment;
import com.cj.serviceedu.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author cj
 * @since 2023-02-14
 */
@RestController
@RequestMapping("/front/EduService/edu-comment")
public class EduCommentController {
    @Autowired
    private EduCommentService eduCommentService;
    @GetMapping("getList/{courseID}")
    public R getList(@PathVariable String courseID){
        QueryWrapper<EduComment> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseID);
        List<EduComment> eduCommentList=eduCommentService.list(queryWrapper);
        return R.ok().data("items",eduCommentList);
    }
    @PostMapping("add")
    public R add(@RequestBody EduComment eduComment){
        return eduCommentService.save(eduComment)?R.ok():R.error().message("评论失败");
    }
}

