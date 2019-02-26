package com.dongyv.blog.web;

import java.sql.*;

/**
 * 添加数据库 200条数据 25190
 * @author xiachenhang
 */
public class Demo {
    static long allTime = 0;
    static Object object = new Object();
    /**
     *   线程总数
     */
    static final int THREAD_NUM = 10;

    public static void main(String[] args) {
        for(int i=0;i<THREAD_NUM;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    connection();
                }
            },"线程"+i).start();
        }
    }

    static void connection(){
        String driver="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/test-data";
        String user="root";
        String password="root";
        String value = "abcdefghijklmnopqrstuvwxyz1234567890";
        char[] chars= value.toCharArray();
        Connection con = null;
        long time = 0;
        //总数据
        int temp = 20;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed()) {
                System.out.println("数据库连接成功");
            }
            Statement statement = con.createStatement();

            long startTime = System.currentTimeMillis();
            for(int i=0;i<temp;i++){
                System.out.println("当前线程为"+Thread.currentThread().getName()+"还剩下"+(temp-i));
                int k =i% chars.length;
                String s = chars[k] +"k"+k+i;
                String sql = "insert into data(data) values (\""+s+"\");";
                long exectStartTime = System.currentTimeMillis();
                statement.executeUpdate(sql);
                long exectEndTime = System.currentTimeMillis();
                System.out.println("插入数据库时间："+"花费"+ (exectEndTime - exectStartTime) +"时间");
            }
            long endTime = System.currentTimeMillis();
            time = endTime-startTime;
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动没有安装");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }finally {
            System.out.println("线程"+Thread.currentThread().getName()+"运行"+temp+"条数据完成,花费"+ time +"时间");
            synchronized (object){
                allTime += time;
            }
            System.out.println("线程"+Thread.currentThread().getName()+"时间添加完成，当前线程添加完成总时间为----->"+allTime);
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
