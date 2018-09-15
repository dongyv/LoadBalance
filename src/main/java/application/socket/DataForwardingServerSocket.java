package application.socket;

import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


//开启本地服务
public class DataForwardingServerSocket {
    private ServerSocket serverSocket;
    private List<DataForwardingClientSocket> dataForwardingClientSocketsList = new ArrayList<DataForwardingClientSocket>();
    private OkHttpClient mHttpClient = new OkHttpClient();

    public DataForwardingServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public List<DataForwardingClientSocket> getDataForwardingClientSocketsList() {
        return dataForwardingClientSocketsList;
    }

    public void setDataForwardingClientSocketsList(List<DataForwardingClientSocket> dataForwardingClientSocketsList) {
        this.dataForwardingClientSocketsList = dataForwardingClientSocketsList;
    }

    private void startServer() {//启动所有本地服务
        for (int port : SocketUtil.writeport) {
            try {
                SocketUtil.WriteServerSocketMap.put(port, new DataForwardingServerSocket(new ServerSocket(port)));
                new ReadThread(SocketUtil.WriteServerSocketMap.get(port).getServerSocket()).start();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    class ReadThread extends Thread {//每个服务创建一个独立线程
        ServerSocket serverSocket;

        public ReadThread(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            try {
                while (true) {
                    Socket socket = serverSocket.accept();
                    DataForwardingClientSocket dataForwardingClientSocket = new DataForwardingClientSocket(socket);
                    dataForwardingClientSocket.setLocalPort(socket.getLocalPort());
                    SocketUtil.WriteServerSocketMap.get(socket.getLocalPort()).getDataForwardingClientSocketsList().add(dataForwardingClientSocket);
                    if (SocketUtil.WriteServerSocketMap.get(serverSocket.getLocalPort()).getDataForwardingClientSocketsList().size() > 500) {
                        SocketUtil.CloseSocket(dataForwardingClientSocket, null);
                    } else {
                        new ReadDataThread(dataForwardingClientSocket).start();
                    }

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    class ReadDataThread extends Thread {//每个socket一个独立的线程
        DataForwardingClientSocket dataForwardingClientSocket;

        public ReadDataThread(DataForwardingClientSocket dataForwardingClientSocket) {
            this.dataForwardingClientSocket = dataForwardingClientSocket;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            List<DataForwardingClientSocket> dataForwardingClientSocketList = SocketUtil.WriteServerSocketMap.get(dataForwardingClientSocket.getSocket().getLocalPort()).getDataForwardingClientSocketsList();
            try {
                InputStream inputStream = dataForwardingClientSocket.getSocket().getInputStream();
                long sj1 = System.currentTimeMillis();//开始计时  报文接收超出当前时间1秒则清空buff
                byte buff[] = new byte[]{};
                int socketByte;
                while (this.dataForwardingClientSocket.isRun() && (socketByte = inputStream.read()) != -1) {
                    buff = SocketUtil.byteMerger(buff, new byte[]{(byte) socketByte});
                    if (this.dataForwardingClientSocket.getLocalPort() == SocketUtil.writeport[0]) {//6666 端口为代理功能 目前不支持https代理 没时间研究
                        String item = new String(buff);
                        if (item.contains("\n\r")) {
                            buff = new byte[]{};
//							System.out.println(item);
                            new SendHttpThread(this.dataForwardingClientSocket, item).start();
                            buff = new byte[]{};
                        }
                    } else if (this.dataForwardingClientSocket.getLocalPort() == SocketUtil.writeport[1]) {//6667 socket转发功能 当前端口下的所有的连接可以相互通信（群发）
                        this.dataForwardingClientSocket.getSocketTimerTask().setTime(60);

//						if(isComplete(buff)){
//							byte[] data = buff;
//						System.out.print((char)socketByte);
                        for (DataForwardingClientSocket dataForwardingClientSocket : dataForwardingClientSocketList) {
//								if(this.dataForwardingClientSocket.getSocket().getPort() != clientSocket.getPort() || !this.dataForwardingClientSocket.getSocket().getInetAddress().getHostName().equals(clientSocket.getInetAddress().getHostName())){//如果是自己就不发送
                            if (this.dataForwardingClientSocket.isRun()) {
                                SocketUtil.sendData(dataForwardingClientSocket, new byte[]{(byte) socketByte});
                            }
//								}
                        }
                        buff = new byte[]{};
//						}
                    } else {
                        SocketUtil.CloseSocket(this.dataForwardingClientSocket, "此端口功能还未开放".getBytes());
                    }
//					System.out.println();
//					if((System.currentTimeMillis()-sj1)>=5000)//超出当前时间5秒
//					{
//						sj1  = System.currentTimeMillis();		//重新计时
//						sendData(dataForwardingClientSocket, "writer timeout".getBytes());
//						buff = new byte[]{};
//					}
                }
                SocketUtil.CloseSocket(this.dataForwardingClientSocket, null);
            } catch (IOException e) {
                SocketUtil.CloseSocket(this.dataForwardingClientSocket, null);
            }
        }
    }
    class SendHttpThread extends Thread {//代理线程
        DataForwardingClientSocket dataForwardingClientSocket;
        String data;

        public SendHttpThread(DataForwardingClientSocket dataForwardingClientSocket, String data) {
            this.dataForwardingClientSocket = dataForwardingClientSocket;
            this.data = data;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            try {
                try {
                    String get = "";
                    get = data.substring(0, data.indexOf("\r"));
                    data.replace(get, "");
                    get = data.substring(data.indexOf(" ") + 1, data.length());
                    get = get.substring(0, get.indexOf(" "));
                    if (data.contains("GET")) {
                        Request request = new Request.Builder()
                                .url(get)
                                .build();
                        mHttpClient.newCall(request).enqueue(new Callback() {
                            public void onFailure(Call call, IOException e) {

                            }
                            public void onResponse(Call call, Response response) throws IOException {
                                try {
                                    SocketUtil.sendData(dataForwardingClientSocket, response.body().bytes());
                                    SocketUtil.CloseSocket(dataForwardingClientSocket, null);
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else if (data.contains("CONNECT")) {
                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {
            }
        }
    }
}
