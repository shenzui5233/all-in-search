package work.jimmmy.allinsearch.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import work.jimmmy.allinsearch.api.common.CommonError;
import work.jimmmy.allinsearch.api.common.CommonRes;
import work.jimmmy.allinsearch.api.common.ErrorCodeEnum;
import work.jimmmy.allinsearch.controller.admin.AdminController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Aspect
@Configuration
public class ControllerAspect {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Around("execution(* work.jimmmy.allinsearch.controller.admin..*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object adminControllerBeforeValidation(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        AdminPermission adminPermission = method.getAnnotation(AdminPermission.class);
        if (adminPermission == null) {
            // 公共方法
            return joinPoint.proceed();
        }
        // 判断管理员是否登录
        String email = (String) request.getSession().getAttribute(AdminController.CURRENT_ADMIN_SESSION);
        if (email == null) {
            if (adminPermission.produceType().equals("text/html")) {
                response.sendRedirect("/admin/admin/loginpage");
                return null;
            } else {
                CommonError commonError = new CommonError(ErrorCodeEnum.ADMIN_SHOULD_LOGIN);
                return CommonRes.create(commonError, "fail");
            }

        } else {
            return joinPoint.proceed();
        }
    }
}
