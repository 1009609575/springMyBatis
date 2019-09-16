package com.bdqn.controller;

import com.bdqn.response.CommonReturnType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName: {@link TestController}
 * Description: 测试控制器（是Servlet分发的前端控制器）
 * Author: xyf
 * Date 2019/8/30 12:09
 */
//@Component
@Controller
@RequestMapping(value = "/test")
public class TestController {

    private Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping(value = "/test.html")
    public ModelAndView test(){
        logger.info("hello test");
        return new ModelAndView("test");
    }

    @RequestMapping(value = "/test1.html")
    public String test2(){
        logger.info("hello test2");
        return "test";
    }
    /**
     * @Description: 基本数据类型的绑定 http://localhost:8080/ssm/test/baseType?userAge=23
     * @param: [age]
     * @return: java.lang.Object
     * @Date: 2019/07/16 8:13
     */
    @RequestMapping("/baseType")
    @ResponseBody
 public  Object baseType(@RequestParam(value = "userAge") int age){
        logger.info(age);
     return CommonReturnType.create("age:" + age);
 }
    /**
     * @Description: 包装类的处理(可以传入空值) http://localhost:8080/ssm   /test/baseType2?age=23
     * @param: [age]
     * @return: java.lang.Object
     * @Date: 2019/07/16 8:15
     */
    @RequestMapping("/baseType2")
    @ResponseBody
    public Object baseType(@RequestParam("age") Integer age) {
//        return "age:" + age;
        return CommonReturnType.create("age:" + age);
    }

}
