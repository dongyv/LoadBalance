package application.modle.decorate;

import application.util.FileUtil;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Map;

public class FileDecorator extends DecoratorProvide{

    private static String feedbackUrl = FileUtil.feedbackUrl;

    private boolean flush = false;

    FileDecorator(DealData dealData) {
        super(dealData);
    }

    FileDecorator(DealData dealData,boolean flush) {
        this(dealData);
        this.flush = flush;
    }

    @Override
    public String processData(Map params){
        String data = super.processData(params);
        updateFile(data,flush);
        return "写入文件成功!";
    }

    /**
     *
     * @param data
     * @param flush 重新进行写入
     */
    private static void updateFile(String data, boolean flush){
        FileReader fr = null;
        FileWriter fileWritter = null;
        try{
            File file = new File(feedbackUrl);
            if(!file.exists()){
                file.createNewFile();
            }
            fr =  new FileReader (feedbackUrl);
            BufferedReader br = new BufferedReader (fr);
            String s = br.readLine();
            if(s!= null && !flush){
                StringBuffer sb = new StringBuffer("[");
                sb.append(s);
                sb.append("]");
                data = sb.toString() ;
                System.out.println("重构成功");
            }
            fileWritter = new FileWriter(file.getName(),flush);
            fileWritter.write(data);

        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                fileWritter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
