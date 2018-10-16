package application.modle.request;

/**
 *
 * 工厂模式的产品。生产请求
 *
 */
public interface IHandlerRequest {
    /**
     * 获取请求中的url
     * @return
     */
    String getUrl();
}
