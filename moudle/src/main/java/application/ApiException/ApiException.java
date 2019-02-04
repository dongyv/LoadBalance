package application.ApiException;

import javax.servlet.http.HttpServletResponse;

/**
 * </div> api 异常 <p/>
 * </div> 其他具体异常继承此异常 <p/>
 * </div> 若过滤器中不捕获该异常，统一进入 errorFilte 中进行处理 <p/>
 * </div> 缺省响应状态码为500 <p/>
 */
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public int statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public ApiException(String message, Throwable cause) {
        super(new Exception(message, cause));
    }

    public ApiException(Throwable cause) {
        super(new Exception(cause));
    }

    public int getStatusCode() {
        return statusCode;
    }
}
