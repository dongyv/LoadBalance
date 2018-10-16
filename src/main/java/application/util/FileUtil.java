package application.util;

import application.rpc.HelloService;
import sun.misc.ProxyGenerator;

import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class FileUtil {

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
     * 从网络Url中读取text文件
     * @param urlStr
     * @throws IOException
     */
    public static String  downLoadFromUrl(String urlStr) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        String s = readInputStream(inputStream);

        if(inputStream!=null){
            inputStream.close();
        }

        return s;
    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        String b =bos.toString();
        bos.close();
        return b;
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
}
