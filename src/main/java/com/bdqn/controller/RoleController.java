package com.bdqn.controller;

import com.bdqn.exception.BusinessExcpetion;
import com.bdqn.exception.EmBusinessError;
import com.bdqn.pojo.Role;
import com.bdqn.response.CommonReturnType;
import com.bdqn.service.RoleService;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *@ClassName:RoleController
 *@Description:角色控制器
 *@Author:lzq
 *@Date: 2019/9/9 11:03
 **/
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @RequestMapping("/roleList")
    @ResponseBody
    public CommonReturnType getRoleList() throws BusinessExcpetion {
        List<Role> roleList=null;
        roleList= roleService.selecrole();
        if (roleList.size()==0){
            throw  new BusinessExcpetion(EmBusinessError.PROVIDER_NOT_EXIST);
        }
        return CommonReturnType.create(roleList);
    }
}
