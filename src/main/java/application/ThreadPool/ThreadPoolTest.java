package application.ThreadPool;

import java.util.ArrayList;
import java.util.List;

public class ThreadPoolTest {
    static int tickets = 100;
    static Object object = new Object();
    static volatile boolean flag = true;
    public static void main(String[] args) {
        ThreadPool pool = new DefaultThreadPool();
//        List<FlagThread> lists = new ArrayList<>();
        Thread ticket = new Thread(new CountThread());
//        5个人进行买票
        for(int i=0;i<100;i++){
            pool.execute(ticket);
        }
//        for(int i=0;i<500;i++){
//            lists.add(new FlagThread());
//        }
//        for(int i=0;i<lists.size();i++){
//            Thread flag = new Thread(lists.get(i),String.valueOf(i));
//            flag.start();
//        }
//        FlagThread runnable = new FlagThread();
//        for(int i=0;i<5;i++){
//            Thread flag = new Thread(runnable);
//            flag.start();
//            if(i == 0){
//                runnable.setFlag(false);
//            }
//        }
    }

    static class CountThread implements Runnable{
        @Override
        public void run() {
            synchronized (object){
                tickets -- ;
                System.out.println("剩余的票还剩:"+tickets+"张");
            }
        }
    }

    /**
     * 个人理解:
     * 如果开启的是四个线程，那么volatile变量则存在于每个线程的本地内存中，其中的主内存主要针对的每个线程中的本地内存，一个内存一份。
     * 如果一个线程，针对了一个共享资源，即通过Runnable来实现线程。那么共享内存则是当前Runnable内存。
     */
    static class FlagThread implements Runnable{


        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"当前线程，则此值是:"+flag);
            while(flag){
                System.out.println(Thread.currentThread().getName()+"当前进入循环");
            }
            System.out.println(Thread.currentThread().getName()+"退出循环");

        }

        public void setFlag(boolean flag) {
            ThreadPoolTest.flag = flag;
        }
    }
}
