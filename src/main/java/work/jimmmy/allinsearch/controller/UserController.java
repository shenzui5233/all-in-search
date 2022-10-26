package work.jimmmy.allinsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import work.jimmmy.allinsearch.api.common.CommonRes;
import work.jimmmy.allinsearch.api.common.ErrorCodeEnum;
import work.jimmmy.allinsearch.api.exception.BusinessException;
import work.jimmmy.allinsearch.common.CommonUtil;
import work.jimmmy.allinsearch.model.UserModel;
import work.jimmmy.allinsearch.request.LoginReq;
import work.jimmmy.allinsearch.request.RegisterReq;
import work.jimmmy.allinsearch.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/user")
public class UserController {
    public static final String CURRENT_USER_SESSION = "currentUserSession";

    /**
     * spring bean, 使用threadlocal技术，使得每个用户请求拿到request都是threadlocal中的request
     */
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CommonRes getUserById(@PathVariable String id) throws BusinessException {
        UserModel userModel = userService.getUser(Integer.parseInt(id));
        if (userModel == null) {
            // return CommonRes.create(new CommonError(ErrorCodeEnum.NOT_OBJECT_FOUND), "failed");
            throw new BusinessException(ErrorCodeEnum.NOT_OBJECT_FOUND);
        }
        return CommonRes.create(userModel);
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        String userName = "shen";
        // 根目录在templates
        ModelAndView modelAndView = new ModelAndView("/index.html");
        modelAndView.addObject("name", userName);
        return modelAndView;
    }

    @RequestMapping("/register")
    @ResponseBody
    public CommonRes register(@Valid @RequestBody RegisterReq registerReq, BindingResult bindingResult) throws BusinessException, NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(ErrorCodeEnum.PARAMETER_VALIDATION_ERROR, CommonUtil.processErrorString(bindingResult));
        }
        UserModel registerUser = new UserModel();
        registerUser.setTelphone(registerReq.getTelphone());
        registerUser.setPassword(registerReq.getPassword());
        registerUser.setNickname(registerReq.getNickName());
        registerUser.setGender(registerReq.getGender());
        UserModel resUserModel = userService.register(registerUser);
        return CommonRes.create(resUserModel);
    }

    @RequestMapping("/login")
    @ResponseBody
    public CommonRes login(@RequestBody @Valid LoginReq loginReq, BindingResult bindingResult) throws BusinessException, NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(ErrorCodeEnum.PARAMETER_VALIDATION_ERROR, CommonUtil.processErrorString(bindingResult));
        }
        UserModel userModel = userService.login(loginReq.getTelphone(), loginReq.getPassword());
        httpServletRequest.getSession().setAttribute(CURRENT_USER_SESSION, userModel);
        return CommonRes.create(userModel);
    }

    @RequestMapping("/logout")
    @ResponseBody
    public CommonRes logout() {
        httpServletRequest.getSession().invalidate();
        return CommonRes.create(null);
    }

    /**
     * 获取当前用户信息
     *
     * @return CommonRes
     */
    @RequestMapping("/getcurrentuser")
    @ResponseBody
    public CommonRes getCurrentUser() {
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute(CURRENT_USER_SESSION);
        return CommonRes.create(userModel);
    }
}
