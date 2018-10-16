package application.modle.resource;

import application.util.ClassUtil;
import application.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanBalanceHandler {

    private static final Logger log = LoggerFactory.getLogger(LoanBalanceHandler.class);

    private LoanBalance loanBalance;

    private static Map<Integer,Class> map;

    static {
        try{
            List<Class<?>> classes = ClassUtil.getAllAssignedClass(LoanBalance.class);
            if(classes != null && classes.size() > 0){
                map = new HashMap<>();
                for (int i =0 ;i<classes.size();i++){
                    map.put(i,classes.get(i));
                }
            }
        }catch (Exception e){log.error("获取ip地址错误:"+e);}
    }

    public LoanBalanceHandler(LoanBalance balance){
        this.loanBalance = balance;
    }

    public static String getRandomIp(){
        java.util.Random random = new java.util.Random();
        int time = random.nextInt(map.keySet().size());
        String ip = "";
        try{
            ip = FileUtil.getGetMethod(map.get(time),"getServer").toString();
        }catch (Exception e){ log.error("获取类方法失败:"+e);}
        return ip;
    }

}
