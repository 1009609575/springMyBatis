package com.bdqn.service;

import com.bdqn.pojo.Role;

import java.util.List;

/**
 * ClassName: RoleService
 * Description: 角色业务接口
 * Author: xyf
 * Date 2019/8/28 12:01
 */
public interface RoleService {

    /**
     * Description: 查找角色数量
     * param: []
     * return: int
     * Date: 2019/8/28 12:02
     */
    int findRoleCount();

    List<Role> selecrole();

}
