package com.bdqn.controller;

import com.bdqn.exception.BusinessExcpetion;
import com.bdqn.exception.EmBusinessError;
import com.bdqn.pojo.Role;
import com.bdqn.pojo.User;
import com.bdqn.pojo.ViewObject.UserVO;
import com.bdqn.response.CommonReturnType;
import com.bdqn.service.RoleService;
import com.bdqn.service.UserService;
import com.bdqn.utils.constant.Constants;
import com.bdqn.utils.page.PageResultBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: {@link UserController}
 * Description: 用户控制器
 * Author: xyf
 * Date 2019/9/3 15:01
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/userListJson")
    @ResponseBody
    public Object getUserList() throws Exception {

        List<User> userList;
        userList = userService.findUsers();
        if (userList.size() == 0) {
//            throw new RuntimeException("数据获取失败");
//            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
            throw new BusinessExcpetion(EmBusinessError.UNKNOWN_ERROR);
        }
        //        将业务层的用户数据User转换成转化成控制器层的UserVO(需要通过java8lambda表达式)
        List<UserVO> userVOList = userList.stream().map(user -> {
            UserVO userVO = this.convertFromModel(user);
            return userVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(userVOList);
    }

    @GetMapping("/userList")
    public String userList(@RequestParam(value = "pageNum",required = false) String pageNum,
                           @RequestParam(value = "queryUserRole",required = false) Integer queryUserRole,
                                  @RequestParam(value = "queryname",required = false) String queryname,
                                  Model model){
      {//查询全部
            if (pageNum==null){
                pageNum = "1";
            }
            PageHelper.startPage(Integer.valueOf(pageNum),5,"`u`.creationDate desc");
//            PageResultBean<User> userPageResultBean = new PageResultBean<>(userService.findUsers());
            PageResultBean<User> userPageResultBean = new PageResultBean<>(userService.findRode(queryname,queryUserRole));
            List<User> userList = userPageResultBean.getRows();
            List<Role> roleList= roleService.selecrole();
            model.addAttribute("userList",userList);//  分页实体信息
            model.addAttribute("roleList",roleList);
            model.addAttribute("page",userPageResultBean);//分页信息
            model.addAttribute("pageNum",pageNum);//当前页
            return "user/userlist";
        }

    }
    @GetMapping("/useradd")
    public String useraddView() {
        return "user/useradd";
    }
        /**
         * Description:讲业务领域中的模型转化为视图领域的中的模型
         * param:
         * return:
         * Date: 2019/9/9 12:14
         */
    /**
     * description: TODO 通过userCode获取用户信息
     * create time: 2019/9/7 10:42
     * [userCode]
     *
     * @return java.lang.Object
     */
    @PostMapping("/getUserByCode")
    @ResponseBody
    public Object getUserByCode(@RequestParam(value = "userCode") String userCode) throws Exception {
        User user = userService.findUsersByCode(userCode);

        if (user == null) {
//            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
            return CommonReturnType.create(20001, "fail");
        }
        //        需要将UserModel转换成UserVO（供用户来查看的信息）
        UserVO userVO = convertFromModel(user);
        return CommonReturnType.create(userVO);
    }
    /**
     * description: TODO  模型数据转成视图数据
     * create time: 2019/9/7 12:02
     * [user]
     *
     * @return com.bdqn.pojo.vo.UserVO
     */
    private UserVO convertFromModel(User user) {
//        处理空值的情况
            if (user == null) {
                return null;
            }
            UserVO userVO = new UserVO();
            //        copyProperties(Object source, Object target)；
            // 通过反射将一个对象的值赋值个另外一个对象（前提是对象中属性的名字相同）。
            BeanUtils.copyProperties(user, userVO);
            return userVO;
    }
@PostMapping(value = "doUseraddMulti")
    public String doUseraddMulti(HttpSession session,
                                 HttpServletRequest request,
                                 User user,
                                 @RequestParam(value = "attachs",required = false) MultipartFile[] attachs)throws IOException,BusinessExcpetion {
String idPicPath=null;
String workPicPath=null;
String errorInfo=null;
boolean flag1=true;//是否上传
user.setCreatedBy(((User)session.getAttribute(Constants.USERSESSION)).getId());
user.setCreationDate(new Date());
//    获取上传文件到指定目录的路径
String path="C:\\temp\\test";
for (int i=0;i<attachs.length;i++){
    MultipartFile attach = attachs[i];
    if (!attach.isEmpty()){
        if (i==0){
            errorInfo="uploadFileError";
        }else if (i==1){
            errorInfo="uploadWpError";
        }
        String oldFileName=attach.getOriginalFilename();//原文件名
        String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
        int filesize=521000;
if (attach.getSize()>filesize){
    request.setAttribute(errorInfo, " * 上传大小不得超过 500k");
    flag1 = false;
}else if (prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png")
        || prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")) {//上传图片格式不正确
    //重新命名
    String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_Personal.jpg";
    //目标目录
    File targetFile = new File(path, fileName);
    if (!targetFile.exists()) {
        targetFile.mkdirs();
    }
    try {
        attach.transferTo(targetFile);
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute(errorInfo," * 上传失败！");
        flag1=false;
    }
    if (i==0){
    idPicPath=path+File.separator+fileName;
    }else if (i==1){
        workPicPath = path + File.separator + fileName;
    }
}else{
    request.setAttribute(errorInfo, " * 上传图片格式不正确");
    flag1 = false;
}
}
}
if (flag1){
user.setIdPicPath(idPicPath);
user.setWorkPicPath(workPicPath);
    //调用保存用户的业务
    if (userService.addUser1(user)){
      return Constants.REDIRECT+"user/userList";//列表页
    }else {
        return   Constants.REDIRECT+"user/useradd";//重新添加页面
    }
}
return  Constants.REDIRECT+ "user/useradd";//重新添加页面
}
    /**
     * description: TODO
     * create time: 2019/9/8 20:32
     * [userId, model]
     *
     * @return java.lang.String
     */
    @GetMapping(value = "/viewUser/{userid}")
    public String viewUser(@PathVariable(value = "userid") Integer userId, Model model) throws BusinessExcpetion {
        //调取相应Model 业务逻辑数据
        User user = userService.findUserById(userId);
        //        需要将UserModel转换成UserVO（供用户来查看的信息）
        UserVO userVO = convertFromModel(user);
        model.addAttribute("user", user);
        return "user/userView";
    }
    @GetMapping(value = "/modifyUser/{userid}")
    public String modifyUser(@PathVariable(value = "userid") Integer userId, Model model) throws BusinessExcpetion {
        //调取相应Model 业务逻辑数据
        User user = userService.findUserById(userId);
        //        需要将UserModel转换成UserVO（供用户来查看的信息）
        UserVO userVO = convertFromModel(user);
        model.addAttribute("user", user);
        return "user/usermodify";
    }

    @RequestMapping(value = "/usermodifysave", method = RequestMethod.POST)
    public String usermodifySave(User user, HttpSession session) throws BusinessExcpetion {

        //设置谁修改了数据
        user.setModifyBy(((User) session.getAttribute(Constants.USERSESSION)).getId());
        user.setModifyDate(new Date());
//        调用修改用户业务
        Integer result = userService.modifyUser(user);
        if (result > 0) {
            return Constants.REDIRECT + "user/userList";
        } else {
            return "user/usermodify";
        }
    }

@GetMapping("/delUser/{userid}")
@ResponseBody
public Object delUser(@PathVariable(value = "userid") Integer userId)throws  BusinessExcpetion{
int result=0;
User user=userService.findUserById(userId);
if (user!=null){
    result= userService.delUserById(userId);
    return CommonReturnType.create(result);
}else {
    return CommonReturnType.create("notexist","fail");
}
      }
}


