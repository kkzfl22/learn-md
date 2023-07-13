# 响应式编程-WEBFLux

## 1. 响应式流规范

>响应式流（Reactive Stream）规范，规定了异步组件之间使用背压进行交互。
>
>响应式流在java9中使用FlowApi适配。FLow API是互操作的规范，而不是具体的实现，它的语义跟响应式流规范一致。

响应式流规范接口如下：

**Publisher**

表示数据流的生产者或数据源，包含一个方法让订阅者注册到发布者，publisher代表了发布者和订阅者直接连接的标准化入口。

```java
public interface Publisher<T> {
    void subscribe(Subscriber<? super T> var1);
}
```

**Subscriber**

表示消费者，onSubscribe方法为我们提供了一种标准化的方式来通知Subscriber订阅成功。

```java
public interface Subscriber<T> {
    public void onSubscribe(Subscription s);
    public void onNext(T t);
    public void onError(Throwable t);
    public void onComplete();
}
```

- `onSubscribe`发布者在开始处理之前调用，并向订阅者传递一个订阅票据对象（Subscription）.
- `onNext`用于通知订阅者发布者发布了新的数据项。
- `onError`用于通知订阅者，发布者遇到了异常，不再发布数据事件。
- `onComplete`用于通知订阅者所有的数所事件都已经发布完。

**Subscription**

同时，onSubscribe方法的传入参数引入一个名为Subscription(订阅)的订阅票据。Subscription为控制元素的生产提供了基础。有如下方法

```java
public interface Subscription {
    public void request(long n);
    public void cancel();
}
```





