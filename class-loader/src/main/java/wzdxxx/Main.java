package wzdxxx;

import lombok.extern.slf4j.Slf4j;
import wzdxxx.classloader.CustomerLoader;
import wzdxxx.hotDeploy.HotDeployManager;
import wzdxxx.hotDeploy.MyService;

import java.lang.reflect.Method;


public class Main {
    public static void main(String[] args) throws Exception {
        // 类加载器层级
        // parentDelegation();
        // 自定义类加载器
        // customClassLoader();
        // 热部署
        hotDeploy();
    }

    private static void hotDeploy() throws Exception {
        // 创建热部署管理器
        HotDeployManager manager = new HotDeployManager();

        // 初始加载Service类
        MyService service = manager.getService(MyService.class);

        // 启动文件监控线程
        new Thread(() -> {
            try {
                manager.watchClassChanges();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // 定期调用服务方法，观察热部署效果
        while (true) {
            service.doSomething();
            Thread.sleep(2000);
        }
    }
//
//    /**
//     * 双亲委派
//     */
//    private static void parentDelegation() {
//        // 获取系统类加载器
//        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
//        System.out.println("系统类加载器: {}"+ systemClassLoader);
//
//        // 获取扩展类加载器
//        ClassLoader extClassLoader = systemClassLoader.getParent();
//        System.out.println("扩展类加载器: {}"+ extClassLoader);
//
//        // 获取引导类加载器
//        ClassLoader bootstrapClassLoader = extClassLoader != null ? extClassLoader.getParent() : null;
//        System.out.println("引导类加载器: {}"+ bootstrapClassLoader);
//
//        // 当前类的类加载器
//        ClassLoader currentClassLoader = Main.class.getClassLoader();
//        System.out.println("当前类的类加载器: {}"+currentClassLoader);
//
//        // String类的类加载器 (通常由引导类加载器加载)
//        ClassLoader stringClassLoader = String.class.getClassLoader();
//        System.out.println("String类的类加载器: {}"+stringClassLoader);
//
//        // 测试类加载器的层次结构
//        System.out.println("\n类加载器层次结构:");
//        printClassLoaderHierarchy(systemClassLoader, 0);
//    }
//
//    /**
//     * 打印类加载器的层次结构
//     */
//    private static void printClassLoaderHierarchy(ClassLoader loader, int level) {
//        if (loader == null) {
//            return;
//        }
//
//        StringBuilder indent = new StringBuilder();
//        for (int i = 0; i < level; i++) {
//            indent.append("  ");
//        }
//
//        System.out.println(indent + "└─ " + loader);
//
//        // 获取父加载器
//        ClassLoader parent = loader.getParent();
//        if (parent != null) {
//            printClassLoaderHierarchy(parent, level + 1);
//        }
//    }
//
//    /**
//     * 自定义类加载器
//     */
//    private static void customClassLoader() {
//        // 指定类文件所在目录（使用绝对路径）
//        // Person类文件位于 C:\my\codes\demo\skill\class-loader\src\main\java\wzdxxx\other 目录下
//        String classDirectory = "C:\\my\\codes\\demo\\skill\\class-loader\\src\\main\\java";
//        try {
//            // 创建自定义类加载器
//            CustomerLoader customClassLoader = new CustomerLoader(classDirectory);
//
//            // 加载示例类
//            Class<?> exampleClass = customClassLoader.loadClass("wzdxxx.other.Person");
//
//            // 创建实例
//            Object instance = exampleClass.getDeclaredConstructor().newInstance();
//            log.info("Person类加载器：{}",instance.getClass().getClassLoader());
//
//            // 调用方法并获取返回值
//            Method showMessageMethod = exampleClass.getMethod("giveCard");
//            Object result = showMessageMethod.invoke(instance);
//            log.info("方法返回值: {}", result);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
