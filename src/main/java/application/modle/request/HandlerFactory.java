package application.modle.request;

import javax.servlet.http.HttpServletRequest;

public class HandlerFactory {

    private static HandlerFactory handlerFactory;

    public HandlerFactory(){}

    public static HandlerFactory createInstance(){
         if(handlerFactory == null){
             synchronized (HandlerFactory.class) {
                 if(handlerFactory == null){
                     handlerFactory = new HandlerFactory();
                     }
                 }
         }
         return handlerFactory;
     }

    public IHandlerRequest productRequest(HttpServletRequest request,RequestHandler requestHandler){
        return new HandlerRequest(request,requestHandler);
    }

}
