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
public class MediaTypeNotSupportedException extends ApiException {
    private static final long serialVersionUID = 1L;

    private static final int statusCode = HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
    private String mediaType;
    private String[] supportedMediaTypes;

    public MediaTypeNotSupportedException(String mediaType) {
        super("Content type '" + mediaType + "' not supported!");
        this.mediaType = mediaType;
    }

    public MediaTypeNotSupportedException() {
        super(ConstantUtil.ZUUL_EXCEPTION_METHOD_NOT_SUPPORTED_ERROR);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
