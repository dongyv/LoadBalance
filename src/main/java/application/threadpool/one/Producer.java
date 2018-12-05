package application.threadpool.one;

/**
 * 生产者
 */
public class Producer implements Runnable{
    //共享资源对象
    Resource r = null;
    public Producer(Resource r){
        this.r = r;
    }
    @Override
    public void run() {
        //生产对象
        for(int i = 0 ; i < 50 ; i++){
            //如果是偶数，那么生产对象 Tom--11;如果是奇数，则生产对象 Marry--21
            if(i%2==0){
                r.push("Tom", 11);
            }else{
                r.push("Marry", 21);
            }
        }
    }
}
