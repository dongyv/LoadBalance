package application.threadpool.dbpool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * 对数据库连接池进行操作
 * @author xiachenhang
 */
public class ConnectionPool {
    // MySQL 默认连接池的大小
    public static final int JDBC_POOL_INIT_SIZE = 5;
    // MySQL 连接池最大线程
    public static final int JDBC_POOL_MAX_SIZE = 10;
    /**
     * 工作者队列
     */
    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialSize){
        if(initialSize > 0){
            for(int i = 0 ;i< initialSize;i++){
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public ConnectionPool(){
        this(JDBC_POOL_INIT_SIZE);
    }

    public int getConnectionSize(){
        return pool.size();
    }

    public void releaseConnection(Connection connection){
        if(connection != null){
            synchronized (pool){
                //连接释放后需要进行通知，这样消费者能够感知到连接池里已经归还了一个连接
                pool.addLast(connection);
                System.out.println("释放链接，工作线程目前:"+getConnectionSize());
                pool.notifyAll();
            }
        }
    }

    /**
     * 在mills内无法获取到连接，将会返回null
     */
    public Connection fetchConnection(long mills) throws InterruptedException{
        synchronized (pool){
            //完全超时
            if(mills <= 0){
                while (pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else{
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while(pool.isEmpty() && remaining > 0){
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if(!pool.isEmpty()){
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }

    public static void main(String[] args) {
        int threadCount = 200;
        ConnectionPool pools = new ConnectionPool();
        for(int i=0;i<threadCount;i++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Connection connection = null;
                    try {
                        connection = pools.fetchConnection(1000);
                        if(connection != null) {
                            connection.createStatement();
                            System.out.println("线程池中工作线程的个数为:"+pools.getConnectionSize());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }finally {
                        pools.releaseConnection(connection);
                    }
                }
            }, "ConnectionRunnerThread");
            thread.start();
        }
        System.out.println();
    }
}
