package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Random;

/**
 * 在响应式编程中实现try-with-resources
 *
 * @author nullnull
 * @since 2024/12/7
 */
public class TestFluxUsing {

    static class Connection implements AutoCloseable {
        private final Random rand = new Random();

        static Connection newConnection() {
            System.out.println("创建Connection对象");
            return new Connection();
        }

        public Iterable<String> getData() {
            if (rand.nextInt(10) < 3) {
                throw new RuntimeException("通信异常");
            }
            return Arrays.asList("数据1", "数据2");
        }

        /**
         * 关闭资源的方法，即使发生错误，也得关闭
         *
         * @throws Exception
         */
        @Override
        public void close() {
            System.out.println("关闭Connection连接");
        }
    }

    @Test
    public void old() {
        try (Connection connection = Connection.newConnection()) {
            connection.getData().forEach(dat -> System.out.println("接收到的数据:" + dat));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void using() {
        Flux.using(
                //获取连接
                Connection::newConnection,
                //正常执行方法,将连接转换为响应式流
                connection -> Flux.fromIterable(connection.getData()),
                //执行关闭
                Connection::close
        ).subscribe(
                item -> System.out.println("next:" + item),
                e -> System.out.println("error:" + e),
                () -> System.out.println("finish")
        );
    }

}
