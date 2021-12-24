package weiyu.commons;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：Weiyu
 * @Version:
 * @Since:
 * @date：2021-12-24_周五 17:05
 **/
@Component
public class SimpleThreadLocal {
    private Map<Runnable, Connection> map= Collections.synchronizedMap(new HashMap<>());

    /**
     * 将一个连接绑定到当前线程上
     * @param connection
     */
    public void set(Connection connection){
        map.put(Thread.currentThread(),connection);
    }

    /**
     *从当前线程上获取绑定的连接
     * @return
     */
    public Connection getConnetcion(){
        return map.get(Thread.currentThread());
    }

    public void removeConnection(){
        map.remove(Thread.currentThread());
    }
}
