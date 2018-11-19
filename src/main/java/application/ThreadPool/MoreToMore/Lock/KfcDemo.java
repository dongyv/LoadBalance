package application.ThreadPool.MoreToMore.Lock;


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
