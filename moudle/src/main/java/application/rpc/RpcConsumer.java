package application.rpc;
//引用服务
public class RpcConsumer {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HelloService service = RPCFrameWork.refer(HelloService.class, "127.0.0.1", 1234);
		for(int i=0;i<Integer.MAX_VALUE;i++) {
			String hello = service.Hello("World " + i);
			System.out.println(hello);
			Thread.sleep(1000);
		}
	}

}
