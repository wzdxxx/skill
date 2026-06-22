package wzdxxx;

public interface HelloMBean {
    String getName();
    void setName(String name);
    int getAge();
    void setAge(int age);
    void printHello(String message);
}