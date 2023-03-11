package com.cj.serviceedu.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class EduSubject_one {
    private String id;
    private String title;
    private List<EduSubject_two> eduSubject_twos=new ArrayList<>();

}
