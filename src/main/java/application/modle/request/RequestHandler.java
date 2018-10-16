package application.modle.request;

import javax.servlet.http.HttpServletRequest;

public interface RequestHandler {

    String handleRequest(HttpServletRequest request);

}
