package application.load;

import java.io.*;

/**
 * @author xiachenhang
 */
public class PathClassLoader extends ClassLoader {

    private String classPath;

    public PathClassLoader(String classPath){
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try{
            byte[] classData = getData(name);
            if(classData == null){
                throw new ClassNotFoundException();
            }else{
                //defineClass方法将字节码转化为类
                return defineClass(name,classData,0,classData.length);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return super.findClass(name);
        }
    }

    private byte[] getData(String className){
        InputStream in = null;
        ByteArrayOutputStream out = null;
        String path = classPath + File.separatorChar + className.replace('.',File.separatorChar)+".class";
        System.out.println(path);
        try {
            in=new FileInputStream(path);
            out=new ByteArrayOutputStream();
            byte[] buffer=new byte[2048];
            int len=0;
            while((len=in.read(buffer))!=-1){
                out.write(buffer,0,len);
            }
            return out.toByteArray();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main ( String [] args ) throws ClassNotFoundException, UnsupportedEncodingException {
        String classPath = PathClassLoader.class.getClassLoader().getResource("").toString();
        classPath = java.net.URLDecoder.decode(classPath,"utf-8");
        System.out.println(classPath);
        PathClassLoader myClassLoader = new PathClassLoader(classPath);
        try {
            myClassLoader.loadClass("java.io.InputStream");
            myClassLoader.loadClass("TestServer");
        }catch ( ClassNotFoundException e ){
            e.printStackTrace();
        }
    }
}
