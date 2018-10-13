package application.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCFrameWork {
	
	/**
	 * 暴露接口
	 * @param service
	 * @param port
	 * @throws Exception
	 */
	public static void export(final Object service,int port) throws Exception{
		if(service == null) 
			throw new IllegalArgumentException("service instatnce == null");
		if(port <= 0 || port >= 65535) 
			throw new IllegalArgumentException("service port = "+port);
		System.out.println("service name =" + service.getClass().getName());
		ServerSocket server = new ServerSocket(port);
		for(;;) {
			final Socket socket = server.accept();
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						//反序列化 参数返回
						ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
						try {
							String methodName = input.readUTF();
							Class<?>[] parameterTypes = (Class<?>[])input.readObject();
							Object[] arguments = (Object[])input.readObject();
							ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
								try {
									Method method = service.getClass().getMethod(methodName, parameterTypes);
									Object result = method.invoke(service, arguments);
									output.writeObject(result);
									} catch (IllegalAccessException e) {
										e.printStackTrace();
									} catch (IllegalArgumentException e) {
										e.printStackTrace();
									} catch (InvocationTargetException e) {
										e.printStackTrace();
									} catch(Throwable t) {
										output.writeObject(t);
									}finally {
										output.close();
									}
						} catch (SecurityException e) {
							e.printStackTrace();
						}finally {
							input.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}catch (ClassNotFoundException e) {
						e.printStackTrace();
					}finally {
						try {
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}).start();
			
		}
		
	}
	/**
	 * 引用服务
	 * @param interfaceClass
	 * @param host
	 * @param port
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T refer(final Class<T> interfaceClass,final String host, final int port) throws Exception{
		if(interfaceClass == null) 
			throw new IllegalArgumentException("Interface class == null");
		if(!interfaceClass.isInterface())
			throw new IllegalArgumentException("The "+interfaceClass.getName()+"must be interface class!");
		if(host == null || host.length() ==0) 
			throw new IllegalArgumentException("Host == null");
		if(port <= 0 || port >= 65535) 
			throw new IllegalArgumentException("service port = "+port);
		System.out.println("Get remote service " + interfaceClass.getName() + " from server " + host +":"+port); 
		return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass}, new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Socket socket = new Socket(host,port);
				try {
					ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
					try {
						System.out.println("参数:"+args[0]);
						output.writeUTF(method.getName());
						System.out.println("当前调用方法的名字:"+method.getName());
						output.writeObject(method.getParameterTypes());
						System.out.println("当前调用方法的类型:"+method.getParameterTypes());
						output.writeObject(args);
						ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
						try {
							Object result = input.readObject();
							System.out.println("返回结果:"+result);
							if(result instanceof Throwable) {
								throw (Throwable)result;
							}
							return result;
						}finally {
							input.close();
						}
					}finally {
						output.close();
					}
				}finally {
					socket.close();
				}
			}
		});
		
		
	}
}
