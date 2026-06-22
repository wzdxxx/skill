package wzdxxx.hotDeploy;

import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
// 热部署管理器
public class HotDeployManager {
    private Map<String, HotClassLoader> classLoaders = new HashMap<>();
    private Map<String, Class<?>> loadedClasses = new HashMap<>();

    public <T> T getService(Class<T> clazz) throws Exception {
        // 如果是接口，加载对应的实现类
        if (clazz.isInterface()) {
            String implClassName = clazz.getName() + "Impl";
            return getServiceImpl(implClassName);
        }

        // 如果是具体类，直接加载
        String className = clazz.getName();
        return getServiceImpl(className);
    }

    private <T> T getServiceImpl(String className) throws Exception {
        // 如果类已经加载，直接返回实例
        if (loadedClasses.containsKey(className)) {
            Class<?> loadedClass = loadedClasses.get(className);
            return (T) loadedClass.getDeclaredConstructor().newInstance();
        }

        // 创建新的类加载器
        HotClassLoader classLoader = new HotClassLoader();
        Class<?> loadedClass = classLoader.loadClass(className);
        loadedClasses.put(className, loadedClass);
        classLoaders.put(className, classLoader);

        return (T) loadedClass.getDeclaredConstructor().newInstance();
    }

    public void reloadClass(String className) throws Exception {
        // 创建新的类加载器
        HotClassLoader newClassLoader = new HotClassLoader();
        Class<?> newClass = newClassLoader.loadClass(className);

        // 更新类和类加载器映射
        loadedClasses.put(className, newClass);
        classLoaders.put(className, newClassLoader);

        // 如果是接口实现类，同时更新对应的接口引用
        if (className.endsWith("Impl")) {
            String interfaceName = className.substring(0, className.length() - "Impl".length());
            if (loadedClasses.containsKey(interfaceName)) {
                // 更新接口引用，指向新的实现类
                loadedClasses.put(interfaceName, newClass);
                System.out.println("成功更新接口引用: " + interfaceName);
            }
        }

        System.out.println("成功热部署类: " + className);
    }

    public void watchClassChanges() throws Exception {
        // 创建文件监控服务
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get("C:\\my\\codes\\demo\\skill");
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        while (true) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                String changedFile = event.context().toString();
                if (changedFile.endsWith(".class")) {
                    // 移除.class后缀，获取类名
                    String className = changedFile.substring(0, changedFile.length() - 6).replace('/', '.');

                    // 检查是否是接口实现类
                    if (className.endsWith("Impl") && loadedClasses.containsKey(className)) {
                        // 热部署实现类
                        reloadClass(className);
                    }
                    // 检查是否是接口类
                    else if (!className.endsWith("Impl") && loadedClasses.containsKey(className)) {
                        // 热部署接口类
                        reloadClass(className);
                    }
                }
            }
            key.reset();
        }
    }
}
