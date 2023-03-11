package com.cj.serviceedu.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private Integer sort;
    private List<VideoVo> children = new ArrayList<>();
}
