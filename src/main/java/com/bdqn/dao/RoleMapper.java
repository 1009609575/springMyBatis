package com.bdqn.dao;

import com.bdqn.pojo.Role;

import java.util.List;

/**
 * ClassName: {@link RoleMapper}
 * Description: 角色数据访问接口
 * Author: xyf
 * Date 2019/8/28 12:03
 */
public interface RoleMapper {

    /**
     * Description: 查询角色的数量
     * param: []
     * return: int
     * Date: 2019/8/28 12:04
     */
    int selectCount();

    List<Role> selectroles();
}
