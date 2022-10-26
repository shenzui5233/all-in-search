package work.jimmmy.allinsearch.api.exception;

import lombok.Getter;
import lombok.Setter;
import work.jimmmy.allinsearch.api.common.CommonError;
import work.jimmmy.allinsearch.api.common.ErrorCodeEnum;

@Getter
@Setter
public class BusinessException extends Exception {
    private CommonError commonError;

    public BusinessException(ErrorCodeEnum codeEnum) {
        super();
        this.commonError = new CommonError(codeEnum);
    }

    public BusinessException(ErrorCodeEnum businessError, String errorMsg) {
        super();
        this.commonError = new CommonError(businessError);
        this.commonError.setErrMsg(errorMsg);
    }
}
