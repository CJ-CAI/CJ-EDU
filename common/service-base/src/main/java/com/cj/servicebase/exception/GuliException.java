package com.cj.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException{
    private Integer code;
    private String msg;
    @Override
    public String toString() {
        return "GuliException{" +
                "message=" + this.getMsg() +
                ", code=" + code +
                '}';
    }
}
