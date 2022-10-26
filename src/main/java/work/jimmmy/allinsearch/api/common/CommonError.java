package work.jimmmy.allinsearch.api.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommonError {
    /**
     * 错误码
     */
    private Integer errorCode;

    /**
     * 错误描述
     */
    private String errMsg;

    public CommonError(ErrorCodeEnum codeEnum) {
        this.errorCode = codeEnum.getErrorCode();
        this.errMsg = codeEnum.getErrMsg();
    }
}
