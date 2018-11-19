package application.ThreadPool.MoreToMore.Synchronized;

public class Food {
    public int count = 1;//默认数量
    public int maxCount = 20;//count为100时，生产者停止生产

    //生产
    public synchronized void product(){
        while(count>=maxCount){
            try {
                wait();
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
            notifyAll();
        }
    }

    //消费
    public synchronized void consume(){
        while(count <= 0){
            try {
                wait();//当前线程等待
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
            notifyAll();
        }
    }
}
