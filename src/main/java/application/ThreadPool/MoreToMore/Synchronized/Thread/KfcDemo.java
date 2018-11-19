package application.ThreadPool.MoreToMore.Synchronized.Thread;

import application.ThreadPool.MoreToMore.Synchronized.Consumer;
import application.ThreadPool.MoreToMore.Synchronized.Food;
import application.ThreadPool.MoreToMore.Synchronized.Producter;

/**
 * 多對多 生产者和消费者模型
 * 多线程实现
 */
public class KfcDemo {
    public static void main(String[] args) {
        Food food = new Food();

        Producter producter1 = new Producter(food);
        Producter producter2 = new Producter(food);
        Consumer consumer1 = new Consumer(food);
        Consumer consumer2 = new Consumer(food);

        producter1.start();
        producter2.start();
        consumer1.start();
        consumer2.start();
    }
}
