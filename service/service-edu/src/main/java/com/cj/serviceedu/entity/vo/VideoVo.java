package com.cj.serviceedu.entity.vo;

import lombok.Data;

@Data
public class VideoVo {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private Integer isFree;
    private Integer sort;
    private String videoSourceId;
    private String videoOriginalName;
}
