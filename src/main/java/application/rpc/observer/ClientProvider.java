package application.rpc.observer;

import application.rpc.RPCFrameWork;

public class ClientProvider {
    public static void main(String[] args) throws Exception {
        Client service = new UserClient();
        RPCFrameWork.export(service, 2345);
    }
}
