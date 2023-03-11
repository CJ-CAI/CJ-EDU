package com.cj.servicebase.exception;

import com.cj.commonutils.R;
import com.cj.commonutils.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandle {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("全局异常处理");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error_detail(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("ArithmeticException异常处理");
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error_custom(GuliException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return R.error().message(e.getMsg()).code(e.getCode());
    }

}
