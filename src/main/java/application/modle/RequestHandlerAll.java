package application.modle;

public class RequestHandlerAll {

    public static RequestHandler getRequestHandler(String ip){
        RequestHandler userHandler = new UserHandler(ip);
        RequestHandler memberHandler = new MemberHandler(ip);
        userHandler.setNextHandler(memberHandler);
        return userHandler;
    }
}
