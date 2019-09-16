package com.bdqn.controller;

import com.bdqn.pojo.Provider;
import com.bdqn.service.ProviderService;
import com.bdqn.service.UserService;
import com.bdqn.utils.page.PageResultBean;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/provider")
public class ProviderContoroller {

    @Autowired
    private ProviderService providerService;

    @GetMapping("/providerList")
   public  String providerlist(@RequestParam(value = "pageNum",required = false) String pageNum,
                               @RequestParam(value = "queryProName",required = false) String queryProName,
                               @RequestParam(value = "queryProCode",required = false) String queryProCode,
                               Model model){
        if (pageNum==null){
            pageNum = "1";
        }
        PageHelper.startPage(Integer.valueOf(pageNum),5,"creationDate desc");
        PageResultBean<Provider> providerPageResultBean=new PageResultBean<>(providerService.findprovide(queryProName,queryProCode));
        List<Provider> providerList= providerPageResultBean.getRows();
        model.addAttribute("providerList",providerList);
        model.addAttribute("page",providerPageResultBean);//分页信息
        model.addAttribute("pageNum",pageNum);//当前页
        return "provider/providerlist";
    }
}
