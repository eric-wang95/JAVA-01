import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * @author colinwang
 */
public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) {
        String filePath = "src\\main\\resources\\Hello.xlass";
        try {
            //加载类对象
            Class<?> helloClass = new HelloClassLoader().findClass("Hello", filePath);
            //获取对象实例
            Object hello = helloClass.newInstance();
            //依据方法名获取类方法
            Method helloMethod = helloClass.getMethod("hello");
            //调用方法
            helloMethod.invoke(hello);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    protected Class<?> findClass(String name,String filePath) throws ClassNotFoundException {
        //读取文件，并转化为byte数组
        byte[] fileByte = getBytesByFile(filePath);
        //解码
        byte[] decode = convert(fileByte);

        return defineClass(name,decode,0,decode.length);

    }
    public byte[] convert(byte[] bytes){
        for (int i = 0; i < bytes.length; i++) {
            bytes[i]= (byte) (255-bytes[i]);
        }
        return bytes;
    }

    //将文件转换成Byte数组
    public static byte[] getBytesByFile(String pathStr) {
        File file = new File(pathStr);
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
