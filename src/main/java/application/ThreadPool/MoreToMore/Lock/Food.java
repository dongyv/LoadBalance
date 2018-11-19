package application.ThreadPool.MoreToMore.Lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Food {
    /**
     * 默认数量
     */
    public int count = 1;
    /**
     * count为100时，生产者停止生产
     */
    public int maxCount = 20;
    /**
     * 创建锁
     */
    Lock lock =  new ReentrantLock();
    /**
     * 生产者锁条件（监视器）对象
     */
    Condition pro_ = lock.newCondition();
    /**
     * 消费者锁条件（监视器）对象
     */
    Condition con_ = lock.newCondition();

    //生产
    public void product(){
        lock.lock();//获得锁
        try{
            while(count>=maxCount){
                try {
                    pro_.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            count++;
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" 生产一个汉堡，当前汉堡数："+count);

            if(count == maxCount){
                con_.signalAll();
            }
        } finally {
            lock.unlock();
        }

    }

    //消费
    public void consume(){
        lock.lock();
        try{
            while(count <= 0){
                try {
                    con_.await();//当前线程等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            count--;
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" 消费一个汉堡，当前汉堡数："+count);
            if(count==0){
                pro_.signalAll();
            }
        } finally {
            lock.unlock();
        }

    }
}
