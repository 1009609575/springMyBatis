package com.bdqn.service;

import com.bdqn.pojo.Provider;

import java.util.List;

public interface ProviderService {

     List<Provider> findprovide(String queryProName, String queryProCode);

}
