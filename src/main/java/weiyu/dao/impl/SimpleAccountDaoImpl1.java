package weiyu.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import weiyu.commons.MyTransctionManager;
import weiyu.dao.AccountDao;
import weiyu.pojo.Account;

import java.sql.SQLException;

/**
 *  2.使用事务管理器
 * 从方式1改造成
 * QueryRunner ( ) ;
 * query (Connection , string sql);update (Connection) ;
 *
 *     private <T> T query(Connection conn, boolean closeConn, String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
 *         if (conn == null) {
 *             throw new SQLException("Null connection");
 *         } else if (sql == null) {
 *             if (closeConn) {
 *                 this.close(conn);
 *             }
 *
 *             throw new SQLException("Null SQL statement");
 *         } else if (rsh == null) {
 *             if (closeConn) {
 *                 this.close(conn);
 *             }
 *
 *             throw new SQLException("Null ResultSetHandler");
 *         } else {
 *             PreparedStatement stmt = null;
 *             ResultSet rs = null;
 *             Object result = null;
 *
 *             try {
 *                 stmt = this.prepareStatement(conn, sql);
 *                 this.fillStatement(stmt, params);
 *                 rs = this.wrap(stmt.executeQuery());
 *                 result = rsh.handle(rs);
 *             } catch (SQLException var33) {
 *                 this.rethrow(var33, sql, params);
 *             } finally {
 *                 try {
 *                     this.close(rs);
 *                 } finally {
 *                     this.close(stmt);
 *                     if (closeConn) {
 *                         this.close(conn);
 *                     }
 *
 *                 }
 *             }
 *
 *             return result;
 *         }
 *
 */
@Repository
public class SimpleAccountDaoImpl1  implements AccountDao {
    @Autowired
    private QueryRunner qr;
    @Autowired
    private MyTransctionManager txManager;
    @Override
    public Account findByAccountNumber(String sourceAccountNumber) {
        String sql="select * from t_accounts where accountNumber=?";
        try {
            return qr.query(txManager.getConnection(),sql,new BeanHandler<>(Account.class),sourceAccountNumber);
        } catch (SQLException e) {
            throw new RuntimeException("不存在此账号的账户"+e);
        }
    }

    @Override
    public void updateAccountBalence(Account account) {
        if(account ==null) throw new IllegalArgumentException("账户为空");
        if(account.getId() ==  null) throw  new IllegalArgumentException("主键为空");
        String sql="update t_accounts set accountNumber=?,balance=? where id=?";
        try {
            qr.update(txManager.getConnection(),sql,account.getAccountNumber(),account.getBalance(),account.getId());
        } catch (SQLException e) {
            throw new RuntimeException("更新余额失败"+e);
        }
    }
}
