package application.socket;


import java.net.Socket;

public class DataForwardingClientSocket {
    private Socket socket;
    private int LocalPort;//当前端口
    private SocketTimerTask socketTimerTask;
    private boolean isRun;
    public DataForwardingClientSocket(Socket socket) {
        this.socket = socket;
        this.socketTimerTask = new SocketTimerTask(this);
        setRun(true);
    }
    public boolean isRun() {
        return isRun;
    }
    public void setRun(boolean isRun) {
        this.isRun = isRun;
    }
    public int getLocalPort() {
        return LocalPort;
    }
    public void setLocalPort(int localPort) {
        LocalPort = localPort;
    }
    public SocketTimerTask getSocketTimerTask() {
        return socketTimerTask;
    }
    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
