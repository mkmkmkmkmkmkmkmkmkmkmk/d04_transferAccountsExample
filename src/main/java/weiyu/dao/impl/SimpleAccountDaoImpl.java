package weiyu.dao.impl;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import weiyu.dao.AccountDao;
import weiyu.pojo.Account;

import java.sql.SQLException;

/**
 * @Author：Weiyu
 * @Version:
 * @Since:
 * @date：2021-12-24_周五 14:23
 **/
@Repository
public class SimpleAccountDaoImpl implements AccountDao {
    @Autowired
    private QueryRunner qr;

    @Override
    public Account findByAccountNumber(String sourceAccountNumber) {
       String sql="select * from t_accounts where accountNumber=?";
        try {
            return qr.query(sql,new BeanHandler<>(Account.class),sourceAccountNumber);
        } catch (SQLException e) {
           throw new RuntimeException("不存在此账号的账户");
        }
    }

    /**
     * 根据主键id修改主余额
     * @param account
     */
    @Override
    public void updateAccountBalence(Account account) {
        if(account ==null) throw new IllegalArgumentException("账户为空");
        if(account.getId() ==  null) throw  new IllegalArgumentException("主键为空");
        String sql="update t_accounts set accountNumber=?,balance=? where id=?";
        try {
            qr.update(sql,account.getAccountNumber(),account.getBalance(),account.getId());
        } catch (SQLException e) {
            throw new RuntimeException("更新余额失败"+e);
        }
    }
}
