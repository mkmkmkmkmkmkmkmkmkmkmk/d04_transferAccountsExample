package weiyu.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 自定义的事务管理器
 */
@Component
public class MyTransctionManager {
    @Autowired
    private SimpleThreadLocal threadLocal;
    @Autowired
    private DataSource dataSource;

    /**
     *获取连接
     * @return
     */
    public Connection getConnection() {
        Connection nowConnection = threadLocal.getConnetcion();//从当前线程上获取连接
        if (nowConnection == null) {
            try {
                nowConnection = dataSource.getConnection();//从连接池中获取一个连接
                threadLocal.set(nowConnection);//绑定到当前线程上面
            } catch (SQLException e) {
                throw new RuntimeException("获取连接池连接失败："+e);
            }

        }
        return nowConnection;
    }

    /**
     * 开启事务
     */
    public  void starrtTransaction(){
        Connection connection=getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException("开启事务失败："+e);
        }
    }
    /**
     * 提交事务
     */
    public void commit(){
        Connection connection=getConnection();
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("提交事务失败："+e);
        }
    }
    /**
     * 事务回滚
     */
    public  void rollBack(){
        Connection connection=getConnection();
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("提交事务回滚失败："+e);
        }
    }
    /**
     * 还回数据库连接
     */
    public void release(){
        Connection connection=getConnection();
        try {
            connection.close();
            threadLocal.removeConnection();
        } catch (SQLException e) {
            throw new RuntimeException("提交事务回滚失败："+e);
        }
    }
}
