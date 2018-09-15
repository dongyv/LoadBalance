package application.socket;

import java.util.Timer;
import java.util.TimerTask;

public class SocketTimerTask extends TimerTask {//socket定时器 超时踢下线

    DataForwardingClientSocket dataForwardingClientSocket;
    int second = 60;
    Timer timer;

    public SocketTimerTask(DataForwardingClientSocket dataForwardingClientSocket) {
        this.dataForwardingClientSocket = dataForwardingClientSocket;
        timer = new Timer();
        timer.schedule(this, 0, 1000);
    }

    public void setTime(int second) {
        this.second = second;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
//				System.out.println(this.dataForwardingClientSocket.getSocket().getPort()+": "+second);
//				sendData(dataForwardingClientSocket,("超时时间: "+second).getBytes("gbk"));
            if (second-- < 1) {//超时退出
//					sendData(dataForwardingClientSocket,"timeout exit".getBytes());
                SocketUtil.CloseSocket(dataForwardingClientSocket, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
