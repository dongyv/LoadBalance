package application.modle;

import javax.servlet.http.HttpServletRequest;

public interface RequestHandler {

    String handleRequest(HttpServletRequest request);

    void setNextHandler(RequestHandler next);
}
