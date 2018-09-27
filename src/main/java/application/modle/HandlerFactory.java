package application.modle;

import javax.servlet.http.HttpServletRequest;

public class HandlerFactory {

    private RequestHandler requestHandler;
    private HttpServletRequest request;

    public HandlerFactory(RequestHandler requestHandler, HttpServletRequest request){
        this.requestHandler = requestHandler;
        this.request = request;
    }
    public IHandlerRequest productRequest(){
        return new HandlerRequest(requestHandler,request);
    }

}
