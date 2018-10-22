package application.rpc.observer;

/**
 * @author xiachenhang
 */
public class UserClient implements Client {

    @Override
    public void receiveNotify(String... interfaces) {
        int c;
        System.out.println("接收到接口名字:"+(c=interfaces.length));
        for(int i=0;i<c;i++){
            System.out.println("当前名字:"+interfaces[i]);
        }
    }
}
