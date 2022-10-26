package work.jimmmy.allinsearch.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;
import work.jimmmy.allinsearch.api.common.CommonRes;
import work.jimmmy.allinsearch.api.common.ErrorCodeEnum;
import work.jimmmy.allinsearch.api.exception.BusinessException;
import work.jimmmy.allinsearch.common.AdminPermission;
import work.jimmmy.allinsearch.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller("/admin/admin")
@RequestMapping("/admin/admin")
public class AdminController {
    @Value("${admin.email}")
    private String email;

    @Value("${admin.encryptPassword}")
    private String encryptPassword;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserService userService;

    public static final String CURRENT_ADMIN_SESSION = "currentAdminSession";

    @RequestMapping("/index")
    @AdminPermission//(produceType = "application/json")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/admin/admin/index");
        modelAndView.addObject("userCount", userService.countAllUser());
        modelAndView.addObject("CONTROLLER_NAME", "admin");
        modelAndView.addObject("ACTION_NAME", "admin");
        return modelAndView;
        //return CommonRes.create(null);
    }

    /**
     * 跳转到登录页面
     *
     * @return modelAndView
     */
    @RequestMapping("/loginpage")
    public ModelAndView loginPage() {
        return new ModelAndView("/admin/admin/login");
    }

    /**
     * 跳转到登录页面
     *
     * @return modelAndView
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPage(@RequestParam(name = "email") String email,
                                  @RequestParam(name = "password") String password) throws BusinessException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new BusinessException(ErrorCodeEnum.PARAMETER_VALIDATION_ERROR, "用户名密码不能为空");
        }
        if (email.equals(this.email) && encodeByMd5(password).equals(this.encryptPassword)) {
            // 登录成功，设置session
            httpServletRequest.getSession().setAttribute(CURRENT_ADMIN_SESSION, email);
            // 重定向到运营后台首页
            return "redirect:/admin/admin/index";
        } else {
            throw new BusinessException(ErrorCodeEnum.PARAMETER_VALIDATION_ERROR, "用户名密码错误");
        }
    }

    private String encodeByMd5(String str) throws NoSuchAlgorithmException {
        // 确认计算方法MD5
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(messageDigest.digest(str.getBytes(StandardCharsets.UTF_8)));
    }
}
