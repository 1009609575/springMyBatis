package com.bdqn.service.impl;

import com.bdqn.dao.ProviderMapper;
import com.bdqn.pojo.Provider;
import com.bdqn.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional //开启事务注解
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public List<Provider> findprovide(String queryProName, String queryProCode) {
        return providerMapper.selectfindprovide(queryProCode,queryProName);
    }
}
