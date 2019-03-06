package com.dongyv.blog.web;

import java.sql.*;
import java.util.Random;

/**
 * 添加数据库
 * 10线程：  2000                    4线程:     800              15线程    3000
 *           200条数据 25190          200     70772                   200   419958
 *           500       567744
 *           1000      1184425
 *  批处理
 *      500,0000数据  221189
 * @author xiachenhang
 */
public class Demo {
    static long allTime = 0;
    static Object object = new Object();
    final static String driver="com.mysql.jdbc.Driver";
    final static String url="jdbc:mysql://localhost:3306/test-data?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
    final static String user="root";
    final static String password="root";
    final static String value = "abcdefghijklmnopqrstuvwxyz1234567890";

    public static void main(String[] args) {
        getOne("kk103292537");
    }

    //插入500w条测试数据
    static void connection(){
        char[] chars= value.toCharArray();
        long time = 0;
        Connection conn = null;
        //总数据
        int temp = 5000000;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            if (!conn.isClosed()) {
                System.out.println("数据库连接成功");
            }
            long startTime = System.currentTimeMillis();
            String sql = "insert into data(data) values (?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            for(int i=0;i<temp;i++){
                System.out.println("当前线程为"+Thread.currentThread().getName()+"还剩下"+(temp-i));
                int k = new Random().nextInt(chars.length);
                String s = chars[k] +"k"+k+i;
                ps.setString(1,s);
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
            //执行完后，手动提交事务
            conn.commit();
            //在把自动提交打开
            conn.setAutoCommit(true);
            long endTime = System.currentTimeMillis();
            time = endTime-startTime;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }finally {
           System.out.println("线程"+Thread.currentThread().getName()+"运行"+temp+"条数据完成,花费"+ time +"时间");
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Id 查询 有索引 3500000 1061ms
     * data 查询 无索引       9709ms
     * @param
     * @return
     */
    static ResultSet getOne(String s){
        String sql = "select data from data where data = \""+s+"\"";

        System.out.println(sql);
        long startTime = System.currentTimeMillis();
        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        long endTime = 0;
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                System.out.println("查询的data是:"+rs.getString("Data"));
            }
            endTime = System.currentTimeMillis();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            System.out.println("数据查询时间:"+(endTime-startTime));
        }
        return rs;
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动没有安装");
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

}
