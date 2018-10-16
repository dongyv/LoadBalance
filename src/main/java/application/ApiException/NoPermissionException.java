package application.ApiException;

import application.config.ConstantUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * 没有权限，未开通产品
 *
 * @author S.H.
 * @email huangshenghao@aliyun.com
 * @date 2018-05-31
 */
public class NoPermissionException extends ApiException {
    private static final long serialVersionUID = 1L;
    private static final int statusCode = HttpServletResponse.SC_FORBIDDEN;

    public NoPermissionException() {
        this(ConstantUtil.ZUUL_EXCEPTION_RESPONSE_NO_PERMISSION_ERROR);
    }

    public NoPermissionException(String message) {
        super(message);
    }

    public NoPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPermissionException(Throwable cause) {
        super(cause);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
