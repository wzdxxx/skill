package wzdxxx.classloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomerLoader extends ClassLoader{
    // 类文件所在的目录
    private final String classDirectory;

    public CustomerLoader(String classDirectory) {
        this.classDirectory = classDirectory;
    }

    public CustomerLoader(String classDirectory, ClassLoader parent) {
        super(parent);
        this.classDirectory = classDirectory;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 直接尝试加载类，不委托给系统类加载器
        // 使用传入的 classDirectory 参数构建类文件路径
        String classPath = classDirectory + "/" + name.replace('.', '/') + ".class";
            
        try (InputStream inputStream = new java.io.FileInputStream(classPath)) {
                // 读取类文件内容
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int bytesRead;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteStream.write(buffer, 0, bytesRead);
            }

            byte[] classBytes = byteStream.toByteArray();

            // 定义类
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Error reading class file: " + classPath, e);
        }

    }

    // 打破双亲委派模型
//    @Override
//    public Class<?> loadClass(String name) throws ClassNotFoundException {
//        // 检查是否是核心类
//        if (name.startsWith("java.")) {
//            return super.loadClass(name);
//        }
//        // 打破双亲委派
//        try {
//            return findClass(name);
//        } catch (ClassNotFoundException e) {
//            return super.loadClass(name);
//        }
//    }

}
