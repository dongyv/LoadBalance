import application.rpc.HelloService;
import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class demo {

    @Test
    public void testGenerateProxyClass() {
        this.writeClassToDisk("D:/$Proxy4.class");
    }

    public static void writeClassToDisk(String path){

        byte[] classFile = ProxyGenerator.generateProxyClass("$proxy4", new Class[]{HelloService.class});
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write(classFile);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
