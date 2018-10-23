package application.rpc.observer;

import application.rpc.RPCFrameWork;

//注册服务
public class ClientProvider {
	public static void main(String args[]) throws Exception{
		Client client = new UserClient();
		RPCFrameWork.export(client, 1111);
	}
}
