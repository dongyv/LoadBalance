package application.ApiException;

import application.config.ConstantUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 响应参数缺失
 *
 * @author S.H.
 * @email huangshenghao@aliyun.com
 * @date 2018-03-19
 */
public class ResponseParamMissException extends ApiException {
    private static final long serialVersionUID = 1L;
    private static final int statusCode = HttpServletResponse.SC_BAD_REQUEST;

    public ResponseParamMissException(Class clazz, String... params) {
        this("Response parameter : " + clazz.getName() + ": " + Arrays.toString(params) + " is missing!");
    }

    public ResponseParamMissException(String... params) {
        this("Response parameter : " + Arrays.toString(params) + " is missing!");
    }

    public ResponseParamMissException() {
        this(ConstantUtil.ZUUL_EXCEPTION_RESPONSE_PARAM_MISSING_ERROR);
    }

    public ResponseParamMissException(String message) {
        super(message);
    }

    public ResponseParamMissException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseParamMissException(Throwable cause) {
        super(cause);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
