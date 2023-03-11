package com.atguigu.eduservice;

public class strsubTest {
    public static void main(String[] args) {
        String str="https://guli-file-190513.oss-cn-beijing.aliyuncs.com/cover/default.gif";
        String str1=str.replace("https://guli-file-190513.oss-cn-beijing.aliyuncs.com/","");
        System.out.println(str1);
    }
}
