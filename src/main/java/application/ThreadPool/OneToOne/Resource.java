package application.ThreadPool.OneToOne;

/**
 * 共享资源
 */
public class Resource {
    private String name;
    private int age;
    //判斷生产者是否生产
    private boolean flag = false;
    Object obj = new Object();
    /**
     * 生产数据
     * @param name
     * @param age
     */
    public synchronized void push(String name,int age){
        //进入到while语句内，说明 isEmpty==false，那么表示有数据了，不能生产，必须要等待消费者消费
        while(flag){
            try {
                this.wait();//导致当前线程等待，进入等待池中，只能被其他线程唤醒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.name = name;
        this.age = age;
        flag = true;
        this.notify();
    }
    /**
     * 取数据，消费数据
     * @return
     */
    public synchronized void pop(){
        while(!flag){
            try {
                this.wait();//导致当前线程等待，进入等待池中，只能被其他线程唤醒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this.name + "---" + this.age);
        flag = false;
        this.notify();
    }
}
