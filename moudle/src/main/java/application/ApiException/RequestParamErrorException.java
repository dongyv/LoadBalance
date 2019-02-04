package application.ApiException;

import application.config.ConstantUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 请求参数有误
 *
 * @author S.H.
 * @email huangshenghao@aliyun.com
 * @date 2018-03-23
 */
public class RequestParamErrorException extends ApiException {
    private static final long serialVersionUID = 1L;
    private static final int statusCode = HttpServletResponse.SC_BAD_REQUEST;

    public RequestParamErrorException(Class clazz, String... params) {
        this("Request parameter : " + clazz.getName() + ": " + Arrays.toString(params) + " is error!");
    }

    public RequestParamErrorException(String... params) {
        this("Request parameter : " + Arrays.toString(params) + " is error!");
    }

    public RequestParamErrorException() {
        this(ConstantUtil.ZUUL_EXCEPTION_REQUEST_PARAM_MISSING_ERROR);
    }

    public RequestParamErrorException(String message) {
        super(message);
    }

    public RequestParamErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestParamErrorException(Throwable cause) {
        super(cause);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
