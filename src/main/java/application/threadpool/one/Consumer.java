package application.threadpool.one;

public class Consumer implements Runnable {
    //共享资源对象
    Resource r = null;
    public Consumer(Resource r) {
        this.r = r;
    }

    @Override
    public void run() {
        for(int i = 0 ; i < 50 ; i++){
            //消费对象
            r.pop();
        }
    }
}
