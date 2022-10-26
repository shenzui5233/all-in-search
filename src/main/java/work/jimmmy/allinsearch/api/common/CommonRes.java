package work.jimmmy.allinsearch.api.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonRes {
    /**
     * 处理结果 success/failed
     */
    private String status;

    /**
     * success时，data=json数据
     * failed时，使用通用的错误码对应的格式
     */
    private Object data;

    public static CommonRes create(Object result) {
        return CommonRes.create(result, "success");
    }

    public static CommonRes create(Object result, String status) {
        CommonRes commonRes = new CommonRes();
        commonRes.setStatus(status);
        commonRes.setData(result);
        return commonRes;
    }
}
