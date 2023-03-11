package com.cj.servicemsm.service;

import java.util.Map;

public interface MsmService {
    public boolean send(String PhoneNumber, Map<String,Object> param) throws Exception;
}
