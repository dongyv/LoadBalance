package application.ApiException;

import application.config.ConstantUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 请求参数缺失
 *
 * @author S.H.
 * @email huangshenghao@aliyun.com
 * @date 2018-03-19
 */
public class RequestParamMissException extends ApiException {
    private static final long serialVersionUID = 1L;
    private static final int statusCode = HttpServletResponse.SC_BAD_REQUEST;

    public RequestParamMissException(Class clazz, String... params) {
        this("Request parameter : " + (clazz == null ? "" : (clazz.getName() + ": ")) + Arrays.toString(params) + " is missing!");
    }

    public RequestParamMissException(String... params) {
        this("Request parameter : " + Arrays.toString(params) + " is missing!");
    }

    public RequestParamMissException() {
        this(ConstantUtil.ZUUL_EXCEPTION_REQUEST_PARAM_MISSING_ERROR);
    }

    public RequestParamMissException(String message) {
        super(message);
    }

    public RequestParamMissException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestParamMissException(Throwable cause) {
        super(cause);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
