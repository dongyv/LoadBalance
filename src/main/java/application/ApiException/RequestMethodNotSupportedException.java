package application.ApiException;

import application.config.ConstantUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * 参数异常
 *
 * @author S.H.
 * @email huangshenghao@aliyun.com
 * @date 2018-03-19
 */
public class RequestMethodNotSupportedException extends ApiException {
    private static final long serialVersionUID = 1L;

    private static final int statusCode = HttpServletResponse.SC_METHOD_NOT_ALLOWED;
    private String method;
    private String[] supportedMethods;

    public RequestMethodNotSupportedException(String method) {
        super("Request method '" + method + "' not supported!");
        this.method = method;
    }

    public RequestMethodNotSupportedException() {
        super(ConstantUtil.ZUUL_EXCEPTION_METHOD_NOT_SUPPORTED_ERROR);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
