package application.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HttpUtil {
    public static void main(String[] args) {
        try {
            //获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
            InetAddress address = InetAddress.getLocalHost();
            //192.168.0.121
            String hostAddress = address.getHostAddress();
            //获取的是该网站的ip地址，比如我们所有的请求都通过nginx的，所以这里获取到的其实是nginx服务器的IP地
            InetAddress address1 = InetAddress.getByName("www.wodexiangce.cn");
            //124.237.121.122
            String hostAddress1 = address1.getHostAddress();
            //根据主机名返回其可能的所有InetAddress对象
            InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
            for (InetAddress addr : addresses) {
                //www.baidu.com/14.215.177.38
                System.out.println(addr);
                //www.baidu.com/14.215.177.37
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void getRedircet(HttpServletResponse response,String url){
        try{
            if(!StringUtils.isEmpty(url)){
                response.sendRedirect(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

