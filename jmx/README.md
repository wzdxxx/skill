
# JMX 示例项目

这是一个展示如何使用Java Management Extensions (JMX)进行管理和监控的示例项目。

## 项目结构

- `HelloMBean.java` - 定义MBean接口
- `Hello.java` - 实现HelloMBean接口
- `JmxManager.java` - JMX管理器，用于注册和管理MBean
- `JmxDemoApplication.java` - 演示应用程序，展示如何使用JMX

## 功能说明

这个示例展示了以下JMX功能：

1. 创建和注册MBean
2. 通过JMX客户端远程访问和操作MBean
3. 管理MBean的属性和方法
4. 启动和停止JMX服务

## 运行方式

1. 使用Maven编译并运行项目：
   ```
   mvn clean compile exec:java -Dexec.mainClass="wzdxxx.jmx.JmxDemoApplication"
   ```

2. 使用JConsole或VisualVM连接JMX服务：
   - 连接地址: `localhost:9999`
   - 可以通过这些工具查看和管理MBean

## 使用JMX客户端工具

1. **JConsole**
   - 在JConsole中，选择"新建连接"，输入localhost:9999
   - 在"MBeans"选项卡下，可以找到`wzdxxx.jmx`域下的Hello MBean
   - 可以查看和修改属性，调用方法

2. **VisualVM**
   - 启动VisualVM，选择"添加JMX连接"
   - 输入localhost:9999
   - 在"MBeans"选项卡下，可以查看和操作MBean

## 扩展建议

1. 可以尝试创建更多的MBean，例如监控系统性能的MBean
2. 可以实现更复杂的JMX通知机制
3. 可以集成到Spring Boot应用中，实现更全面的监控和管理

## 注意事项

- 确保JMX端口(默认9999)没有被占用
- 在生产环境中，请注意JMX的安全性配置
