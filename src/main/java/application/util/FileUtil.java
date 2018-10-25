package application.util;

import application.rpc.HelloService;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import sun.misc.ProxyGenerator;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.*;

public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public final static String feedbackUrl = "feedback.json";
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
     public static void readFileByLines(String fileName) {
         File file = new File(fileName);
         BufferedReader reader = null;
         try {
                 System.out.println("以行为单位读取文件内容，一次读一整行：");
                 reader = new BufferedReader(new FileReader(file));
                 String tempString = null;
                 int line = 1;
                 // 一次读入一行，直到读入null为文件结束
                 while ((tempString = reader.readLine()) != null) {
                         // 显示行号
                         System.out.println("line " + line + ": " + tempString);
                         line++;
                     }
                 reader.close();
             } catch (IOException e) {
                 e.printStackTrace();
             } finally {
                 if (reader != null) {
                         try {
                                 reader.close();
                             } catch (IOException e1) {
                             }
                     }
             }
     }

    /**
     * 判断一个字符是中文还是英文
     */
    public static boolean IsChinese(char c) {
        //通过字节码进行判断
        return c >= 0x4E00 && c <= 0x9FA5;
    }

    public static Map formatParam(String param){
        Map map = new HashMap();
        String trimPram = param.replace(" ","");
        char[] c = trimPram.toCharArray();
        int index=0,n = 0;
        for(int j=0;j<c.length;j++){
            //第一次为中文
            if(FileUtil.IsChinese(c[j])){
                int in;
                if(n == 0){
                    in = trimPram.indexOf(c[j]);
                    map.put("id",trimPram.substring(0,in));
                    trimPram = trimPram.substring(in,c.length);
                }else{
                    //防中文字重复
                    in = trimPram.lastIndexOf(c[j]);
                }
                index = in ;
                n++;
            }
        }
        //处理只有1个中文字的
        if(n == 1 ){
            index = 0;
        }
        map.put("name",trimPram.substring(0,index+1));
        map.put("phone",trimPram.substring(index+1,trimPram.toCharArray().length));
        if(map.get("id")==null || map.get("name")==null || map.get("phone")==null){
            map = new HashMap();
        }
        return map;
    }


    /**
     * 根据列中的的数据进行排序
     */
    public static <T>List<T> sortList(List<T> list, String sortOrder, String sortField){
        Collections.sort(list, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                try {
                    Object data1 = getGetMethod(o1,sortField);
                    Object data2 = getGetMethod(o2,sortField);
                    if("desc".equals(sortOrder)){
                        if(data1 == null){
                            data1 = "99999999";
                        }
                        if(data2 == null){
                            data2 = "99999999";
                        }
                        return Integer.valueOf(data1.toString()).compareTo(Integer.valueOf(data2.toString()));
                    }else {
                        //其他情况默认升序
                        if (data1 == null) {
                            data1 = "-99999999";
                        }
                        if (data2 == null) {
                            data2 = "-99999999";
                        }
                        return Integer.valueOf(data2.toString()).compareTo(Integer.valueOf(data1.toString()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return list;
    }

    /**
     * 根据列名和值进行筛选
     * @param list
     * @param screenValue
     * @param screenField
     * @param <T>
     * @return
     */
    public static <T>List<T> getScreen(List<T> list,String screenField,String screenValue){
        List<T> nList = new ArrayList<>();
        //筛选的值
        String[] filed = screenValue.split(",");
        Map<String,String> map = new HashMap<>();
        for (String aFiled : filed) {
            map.put(aFiled.toUpperCase(), aFiled.toUpperCase());
        }
        for(T param:list){
            try {
                Object obj = getGetMethod(param, screenField);
                if(obj == null){
                    obj = "";
                }
                String data = obj.toString().trim().toUpperCase();
                if(map.get(data) != null){
                    nList.add(param);
                }
            }catch (Exception e){ e.printStackTrace();}
        }

        return nList;
    }

    /**
     * 动态代理，生成class文件
     * @param clazz
     * @param proxyName
     */
    public static void generateClassFile(Class clazz, String proxyName) {

        //根据类信息和提供的代理类名称，生成字节码
        byte[] classFile = ProxyGenerator.generateProxyClass(proxyName, clazz.getInterfaces());
        String paths = clazz.getResource(".").getPath();
        System.out.println(paths);
        FileOutputStream out = null;

        try {
            //保留到硬盘中
            out = new FileOutputStream(paths + proxyName + ".class");
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object getGetMethod(Object ob , String name)throws Exception{
        Method[] m = ob.getClass().getMethods();
        for (Method aM : m) {
            if ((name).toLowerCase().equals(aM.getName().toLowerCase())) {
                return aM.invoke(ob);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        generateClassFile(HelloService.class,"hellos");
    }



    public static Map<String,Object> uploadFileByHTTP(File postFile, String postUrl, Map<String,String> postParam) {

        Map<String, Object> resultMap = new HashMap<>();
        HttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(postUrl);
            ContentBody cbFile = new FileBody(postFile);
            MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
            //相当于<input type="file" name="media"/>
            multipartEntity.addPart(postFile.getName(), cbFile);
            Set<String> keySet = postParam.keySet();
            for (String key : keySet) {
                multipartEntity.addPart(key, new StringBody(postParam.get(key), ContentType.create("text/plain", Consts.UTF_8)));
            }
            HttpEntity reqEntity = multipartEntity.build();
            httpPost.setEntity(reqEntity);
            logger.info("发起请求的页面地址 " + httpPost.getRequestLine());
            //发起请求   并返回请求的响应
            HttpResponse response = httpClient.execute(httpPost);
            try {
                logger.info("----------------------------------------");
                //打印响应状态
                //log.info(response.getStatusLine());
                resultMap.put("statusCode", response.getStatusLine().getStatusCode());
                //获取响应对象
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    //打印响应长度
                    logger.info("Response content length: " + resEntity.getContentLength());
                    //打印响应内容
                    resultMap.put("data", EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
                }
                //销毁
                EntityUtils.consume(resEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ClientProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        logger.info("uploadFileByHTTP result:" + resultMap);
        return resultMap;
    }

    public static void flushFile(String url){
        FileWriter fw = null;
        File file = null;
        try {
            file = new File(url);
            fw = new FileWriter(file);
            fw.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
