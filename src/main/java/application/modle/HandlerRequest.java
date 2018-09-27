package application.modle;

import javax.servlet.http.HttpServletRequest;

/**
 * 具体生产请求
 */
public class HandlerRequest implements IHandlerRequest{

    private RequestHandler requestHandler;
    private HttpServletRequest request;

    public HandlerRequest(RequestHandler requestHandler, HttpServletRequest request){
        this.requestHandler = requestHandler;
        this.request = request;
    }

    @Override
    public String getUrl() {
        return requestHandler.handleRequest(request);
    }


}
