package wzdxxx;

public class Hello implements HelloMBean {
    private String name;
    private int age;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void printHello(String message){
        System.out.println("你这边输入了："+message);
    }
}
