package application.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketUtil {

    static int[] writeport = {6666, 6667, 6668, 6669, 6610, 6611};//本地服务端口

    static Map<Integer, DataForwardingServerSocket> WriteServerSocketMap = new HashMap<Integer, DataForwardingServerSocket>();

    /*报文校验规则*/
    private boolean isComplete(byte[] buff) {
        try {
//			System.out.println("+++++++++++++++++");
//			byte[] type = new byte[]{buff[1],buff[2]};	//获取报文长度
//			int datal = Integer.parseInt(Integer.toHexString(buff[1] & 0xFF)+Integer.toHexString(buff[0] & 0xFF), 16);	//报文长度为 数据体长度+报头+包异或值
//
//			System.out.println(datal);
//
//			if(buff[0] == 0x02 && buff.length > 5){
//				return true;
//			}
        } catch (Exception e) {
            e.printStackTrace();

        }
        return true;
    }

//	static byte[] getAscii(String s){
//
//		byte[] b = s.getBytes();
//
//		byte[] in = new byte[b.length];
//		for (int i = 0; i < in.length; i++) {
//		in[i] = b[i];
////		System.out.println(Integer.toString(in[i], 0x10));
//		}
//		return b;
//
//	}

    /**
     * int转成4个字节的byte[]
     * 16进制码表示，低字节在前
     */
    byte[] intToByteArray1(int i) {
        byte[] result = new byte[4];
        result[3] = (byte) ((i >> 24) & 0xFF);
        result[2] = (byte) ((i >> 16) & 0xFF);
        result[1] = (byte) ((i >> 8) & 0xFF);
        result[0] = (byte) (i & 0xFF);
        return result;
    }

    /**
     * 合并两个byte数组
     */
    static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {

        if (byte_1 != null && byte_2 == null) {
            return byte_1;
        }
        if (byte_1 == null && byte_2 != null) {
            return byte_2;
        }
        if (byte_1 == null || byte_2 == null) {
            return new byte[]{};
        }
        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    public static void CloseSocket(DataForwardingClientSocket dataForwardingClientSocket, byte[] mas) {
        Socket socket = null;
        try {
            if (dataForwardingClientSocket.isRun()) {
                dataForwardingClientSocket.setRun(false);
                SocketUtil.WriteServerSocketMap.get(dataForwardingClientSocket.getLocalPort()).getDataForwardingClientSocketsList().remove(dataForwardingClientSocket);
                dataForwardingClientSocket.getSocketTimerTask().cancel();
                socket = dataForwardingClientSocket.getSocket();
                System.out.println(dataForwardingClientSocket.getSocket().getPort() + "断开连接");
                if (mas != null && !mas.equals("")) {
                    sendData(dataForwardingClientSocket, mas);
                }
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        for (int port : writeport) {
            DataForwardingServerSocket dSocket = SocketUtil.WriteServerSocketMap.get(port);
            System.out.println(("本地端口" + port + ": 还有" + dSocket.getDataForwardingClientSocketsList().size() + "个外部连接"));
        }
    }


    public static synchronized void sendData(DataForwardingClientSocket dataForwardingClientSocket, byte[] buff) {
        try {
            dataForwardingClientSocket.getSocket().getOutputStream().write(buff);
        } catch (Exception e) {
            e.printStackTrace();
            CloseSocket(dataForwardingClientSocket, null);
        }
    }
}

