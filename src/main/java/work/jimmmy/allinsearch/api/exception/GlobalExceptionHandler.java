package work.jimmmy.allinsearch.api.exception;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import work.jimmmy.allinsearch.api.common.CommonError;
import work.jimmmy.allinsearch.api.common.CommonRes;
import work.jimmmy.allinsearch.api.common.ErrorCodeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class) // controller没处理的exception异常，都会进入这个方法处理
    @ResponseBody // 访问结果按responsebody处理
    public CommonRes doError(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        if (ex instanceof BusinessException) {
            return CommonRes.create(((BusinessException) ex).getCommonError(), "failed");
        } else if (ex instanceof NoHandlerFoundException) { // URL不存在，没有对应的controller
            return CommonRes.create(new CommonError(ErrorCodeEnum.NO_HANDLER_FOUND), "failed");
        } else if (ex instanceof ServletRequestBindingException) {
            return CommonRes.create(new CommonError(ErrorCodeEnum.BIND_EXCEPTION_EEOR));
        } else {
            return CommonRes.create(new CommonError(ErrorCodeEnum.UNKNOWN_ERROR),"failed");
        }
    }
}
