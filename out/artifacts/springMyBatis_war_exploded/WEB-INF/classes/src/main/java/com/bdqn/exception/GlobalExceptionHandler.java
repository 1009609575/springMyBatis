package com.bdqn.exception;

import com.bdqn.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName:GlobalExceptionHandler
 *@Description:全局异常处理
 *@Author:lzq
 *@Date: 2019/9/9 9:49
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public  Object handleException(HttpServletRequest request,Exception e) throws  Exception{
        Map<String,Object> resposeData=new HashMap<>();
        if (e instanceof  BusinessExcpetion){//业务异常（包含业务异常的代码和信息）
            BusinessExcpetion businessExcpetion=(BusinessExcpetion) e;
            resposeData.put("errCode", businessExcpetion.getErrorCode());
            resposeData.put("errMsg", businessExcpetion.getErrMsg());
        }else {//未知异常（包含文字和信息）
            resposeData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrorCode());
            resposeData.put("errMsg", EmBusinessError.UNKNOWN_ERROR.getErrMsg());

        }

        //最后，无论是业务异常还是未知异常都将其存储到格式化后的异常类中（并且都是异常，即处理
        return CommonReturnType.create(resposeData,"fail");
    }
}
