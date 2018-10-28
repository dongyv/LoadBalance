package application.service;

/**
 * 新建抢票程序
 *
 * @author xiachenhang
 */
public class TicketProvider implements Runnable{
    int tickets = 100;

    @Override
    public void run() {
        String str = "哈哈";
        while (true) {
            synchronized (str) {
                if (tickets > 0) {
                    System.out.println(Thread.currentThread().getName()+"线程卖掉了第 "+tickets+"张票");
                    --tickets;
                }
            }
            if(tickets == 0){
                break;
            }
        }
    }
}