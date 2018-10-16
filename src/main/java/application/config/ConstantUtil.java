package application.config;

/**
 * 常量定义
 *
 * @author S.H.
 * @email xiachenhang
 * @date 2018-03-18
 */
public class ConstantUtil {
    public static final String PRIVATE_PATH = "/private";
    public static final String NEED_RESET_PARAMS = "UPMALL_NEED_RESET_PARAMS";
    public static final String RESET_PARAMS = "UPMALL_RESET_PARAMS";
    public static final String NO_NEED_CHECKSING = "UPMALL_NO_NEED_CHECKSING";

    /* *************************************** zuul 异常信息常量 *************************************** */
    public static final String ZUUL_EXCEPTION_THIRD_INTERNAL_SERVER_ERROR =
            "An internal error has occurred on the third party server. Please contact the service provider!";

    public static final String ZUUL_EXCEPTION_INTERNAL_SERVER_ERROR =
            "An internal error has occurred on the server. Please contact the service provider!";

    public static final String ZUUL_EXCEPTION_METHOD_NOT_SUPPORTED_ERROR =
            "Request method not supported!";

    public static final String ZUUL_EXCEPTION_REQUEST_PARAM_ERROR_ERROR =
            "Request parameter is error!";

    public static final String ZUUL_EXCEPTION_REQUEST_PARAM_MISSING_ERROR =
            "Request parameter is missing!";

    public static final String ZUUL_EXCEPTION_RESPONSE_PARAM_MISSING_ERROR =
            "Response parameter is missing!";

    public static final String ZUUL_EXCEPTION_UNSUPPORTED_MEDIA_TYPE_ERROR =
            "Unsupported media type!";

    public static final String ZUUL_EXCEPTION_RESPONSE_NO_PERMISSION_ERROR =
            "No permission. Please contact the service provider to open the product!";

    public static final String ZUUL_EXCEPTION_RESPONSE_CHECK_SIGN_FAIL_ERROR =
            "Sign check fail!";
}
