package wzdxxx.hotDeploy;

public class MyServiceImpl implements MyService {
    private static int version = 3;

    @Override
    public void doSomething() {
        System.out.println("服务实现版本 " + version + "，时间戳: " + System.currentTimeMillis());
    }
}

