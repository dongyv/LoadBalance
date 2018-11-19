package application.ThreadPool.MoreToMore.Lock;

public class Consumer extends Thread{
    private Food food;
    public Consumer(Food food){
        super();
        this.food = food;
    }

    @Override
    public void run(){
        while (true){
            food.consume();
        }
    }
}
