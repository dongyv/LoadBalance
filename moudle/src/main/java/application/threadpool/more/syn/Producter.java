package application.threadpool.more.syn;

public class Producter extends Thread {
    private Food food;
    public Producter(Food food){
        super();
        this.food = food;
    }

    @Override
    public void run(){
        while (true){
            food.product();
        }
    }
}
