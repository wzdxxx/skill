package wzdxxx;

import lombok.extern.slf4j.Slf4j;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@Slf4j
public class JmxMain {
    public static void main(String[] args) throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        String name = "wzdxxx.jmx:type=hello";
        ObjectName objectName = new ObjectName(name);
        Hello hello = new Hello();
        hello.setAge(18);
        hello.setName("zhangsan");
        mBeanServer.registerMBean(hello,objectName);
        while (true) {
            Thread.sleep(3000);
            log.info("hello-{}", hello);
        }
    }
}
