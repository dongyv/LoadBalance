package application.ChainOfResponsibility;

import javax.servlet.http.HttpServletRequest;

interface RequestHandler {

    int handleRequest(HttpServletRequest request);

    void setNextHandler(RequestHandler next);
}
