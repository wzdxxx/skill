package wzdxxx.hotDeploy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

// 自定义类加载器
public class HotClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            // 将类名转换为文件路径
            String path = name.replace('.', '/') + ".class";
            File file = new File("C:\\my\\codes\\demo\\skill\\" + path);

            if (file.exists()) {
                // 读取类文件
                byte[] bytes = Files.readAllBytes(file.toPath());

                // 定义类
                return defineClass(name, bytes, 0, bytes.length);
            }
        } catch (IOException e) {
            throw new ClassNotFoundException("Failed to load class " + name, e);
        }

        return super.findClass(name);
    }
}
