package com.bdqn.dao;

import com.bdqn.pojo.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderMapper {
    List<Provider> selectfindprovide(@Param("queryProCode") String queryProCode, @Param("queryProName") String queryProName);
}
