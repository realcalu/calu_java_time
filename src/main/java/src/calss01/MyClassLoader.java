package calss01;

import java.io.*;
import java.lang.reflect.Method;

class MyClassLoader extends ClassLoader{
    private static void xor(InputStream in, OutputStream out) throws Exception {
        int ch;
        while (-1 != (ch = in.read())) {
            ch = 255 -  ch ;
            out.write(ch);
        }
    }

    public static byte[] decrypt(File src) throws Exception {
        InputStream in = new FileInputStream(src);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        xor(in, bos);
        byte[] data = bos.toByteArray();

        in.close();
        bos.close();
        return data;
    }
    protected Class<?> findClass(String name,byte[] bytes) throws ClassNotFoundException {
        Class<?> aClass = defineClass(name, bytes, 0, bytes.length);
        return aClass;
    }

    public static void main(String[] args) throws Exception {
        String url = MyClassLoader.class.getClassLoader().getResource("").toString();
        File src = new File(url +"Hello.xlass");
        byte[] bytes = decrypt(src);
        Class<?> helloclass = new MyClassLoader().findClass("Hello", bytes);
        Object o = helloclass.newInstance();
        Method hello = helloclass.getMethod("hello");
        hello.invoke(o);
    }
}