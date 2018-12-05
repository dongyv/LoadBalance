package application.module.request;

import javax.servlet.http.HttpServletRequest;

public interface RequestHandler {

    String handleRequest(HttpServletRequest request);

}
