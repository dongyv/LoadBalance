package application.threadpool.one;

public class PCThreadDemo {
    public static void main(String[] args) {
        Resource rs = new Resource();
        Thread producer = new Thread(new Producer(rs));
        Thread consumer = new Thread(new Consumer(rs));
        producer.start();
        consumer.start();
    }
}
