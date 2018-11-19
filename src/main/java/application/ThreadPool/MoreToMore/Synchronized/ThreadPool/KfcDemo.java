package application.ThreadPool.MoreToMore.Synchronized.ThreadPool;

import application.ThreadPool.MoreToMore.Synchronized.Consumer;
import application.ThreadPool.MoreToMore.Synchronized.Food;
import application.ThreadPool.MoreToMore.Synchronized.Producter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多對多 生产者和消费者模型
 * 线程池实现
 */
public class KfcDemo {
    public static void main(String[] args) {
        Food food = new Food();

        Producter producter1 = new Producter(food);
        Producter producter2 = new Producter(food);
        Consumer consumer1 = new Consumer(food);
        Consumer consumer2 = new Consumer(food);

        ExecutorService pool = Executors.newCachedThreadPool();
        pool.execute(producter1);
        pool.execute(producter2);
        pool.execute(consumer1);
        pool.execute(consumer2);
    }
}
