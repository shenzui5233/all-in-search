package work.jimmmy.allinsearch.api.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {
    /**
     * 通用错误10000
     */
    NOT_OBJECT_FOUND(10001, "请求对象不存在"),

    UNKNOWN_ERROR(10002, "未知错误"),

    NO_HANDLER_FOUND(10003, "找不到执行的路径操作"),

    BIND_EXCEPTION_EEOR(10004, "请求参数错误"),

    PARAMETER_VALIDATION_ERROR(10004, "请求参数校验失败"),

    /**
     * 用户服务相关的错误类型 20000
     */
    REGISTER_DUP_FAIL(20001, "用户已存在"),

    LOGIN_FAIL(20002, "手机号或密码错误"),

    /**
     * admin相关错误 30000
     */
    ADMIN_SHOULD_LOGIN(30001, "管理员需要先登录"),

    // 品类相关错误 40000
    CATEGORY_NAME_DUPLICATED(40001, "品类名已存在");

    /**
     * 错误码
     */
    private Integer errorCode;

    /**
     * 错误描述
     */
    private String errMsg;
}
