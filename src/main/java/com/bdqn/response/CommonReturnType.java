package com.bdqn.response;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: CommonReturnType
 * @Description: 通用的返回类型
 * @Author: xyf
 * @Date 2019/7/13 12:11
 */
public class CommonReturnType {

    //表名对应请求的处理结果为success或fail
    private String status;
    //若status=success,则data内返回前端需要的JSON数据
    //若status=fail,则data内返回通用的错误码格式（已在其他类或接口中定义）
    private  Object data;
    public  CommonReturnType(){

    }

    public CommonReturnType(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    /**
     * Description:构造一个通用的返回的数据类型
     * param: [data, status]
     * return: com.bdqn.response.CommonReturnType
     * Date: 2019/9/6 16:32
     */
    public  static  CommonReturnType create(Object data,String status){
        CommonReturnType type=new CommonReturnType();
        type.setData(data);
        type.setStatus(status);
        return type;
}
    /**
     * Description:数据获取成功返回的数据类型
     * param:
     * return:
     * Date: 2019/9/6 16:35
     */
public  static  CommonReturnType create(Object data){
    return CommonReturnType.create(data,"success");
}
}
