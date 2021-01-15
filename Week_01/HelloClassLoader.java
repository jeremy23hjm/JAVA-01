import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader{

    public static void main(String[] args) {
        try {
            Class<?> clazz = new HelloClassLoader().findClass("Hello");
            Object obj = clazz.newInstance();
            Method method = clazz.getMethod("hello");
            method.invoke(obj);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = new byte[0];
        try{
            bytes = decodeData(name);
        }catch (IOException e){
            e.printStackTrace();
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] decodeData(String name) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(name + ".xlass");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int byteValue;
        while ((byteValue = inputStream.read()) != -1) {
            outputStream.write(255 - byteValue);
        }
        return outputStream.toByteArray();
    }
}
