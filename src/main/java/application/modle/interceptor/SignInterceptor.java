package application.modle.interceptor;

import application.ApiException.ApiException;
import application.ApiException.MediaTypeNotSupportedException;
import application.config.ConstantUtil;
import application.config.ModuleConfig;
import application.util.CheckSignUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SignInterceptor implements InterceptorHandler{
    private InterceptorHandler handlerNext;

    @Override
    public boolean hasInterceptorHandler(HttpServletRequest request, Object handler) {
        String sign = request.getParameter(ModuleConfig.ParamEnum.SIGN.getName());
        String method = request.getMethod();
        Map param;
        if (HttpMethod.POST.matches(method)) {
            param = this.getPostParams(request);
        } else if (HttpMethod.GET.matches(method)) {
            param = this.getGetParams(request);
        } else {
            param = new HashMap();
        }
        if (CheckSignUtil.check(param, sign)) {
            return true;
        }
        if(handlerNext != null){
            return handlerNext.hasInterceptorHandler(request,handler);
        }
        return false;
    }

    @Override
    public void setNextInterceptor(InterceptorHandler interceptorHandler) {
        this.handlerNext = interceptorHandler;
    }

    /**
     * 获取 请求参数 中的数据
     *
     * @return
     */
    private Map getGetParams(HttpServletRequest request) throws ApiException {
        Map<String, String> map_param = new HashMap();

        Map map_request = request.getParameterMap();
        Iterator iterator = map_request.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = String.valueOf(entry.getKey());
            map_param.put(key, request.getParameter(key));
        }

        return map_param;
    }

    /**
     * 获取 post请求参数 中的数据
     *
     * @return
     */
    private Map getPostParams(HttpServletRequest request) throws ApiException {
        Map map_param = new HashMap();

        if (StringUtils.isEmpty(request.getHeader("Content-type"))) {
            throw new MediaTypeNotSupportedException("null");
        }

        MediaType mediaType = MediaType.parseMediaType(request.getHeader("Content-type"));

        if (MediaType.APPLICATION_JSON.equals(mediaType) || MediaType.APPLICATION_JSON_UTF8.equals(mediaType)) {
            map_param = this.getJsonParam(request);
        } else if (MediaType.MULTIPART_FORM_DATA.includes(mediaType)) {
            map_param = this.getMultipartFormDataParam(request);
        } else if (MediaType.APPLICATION_FORM_URLENCODED.includes(mediaType)) {
            map_param = this.getGetParams(request);
        }

        return map_param;
    }

    /**
     * application/json 请求参数
     *
     * @param request
     * @return
     */
    private Map getJsonParam(HttpServletRequest request) throws ApiException {
        String body = "";
        try {
            body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map map = null;
        try {
            map = JSONObject.parseObject(body);
        } catch (ClassCastException ex) {
            // 参数为数组
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApiException(ConstantUtil.ZUUL_EXCEPTION_INTERNAL_SERVER_ERROR);
        }

        return map;
    }

    /**
     * multipart/form-data 请求参数
     *
     * @param request
     * @return
     */
    private Map getMultipartFormDataParam(HttpServletRequest request) {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);

        Map<String, String[]> map = multipartRequest.getParameterMap();
        Map map_param = new HashMap();

        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            map_param.put(entry.getKey(), entry.getValue()[entry.getValue().length - 1]);
        }
        return map_param;
    }

}
