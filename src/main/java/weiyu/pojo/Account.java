package weiyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author：Weiyu
 * @Version:
 * @Since:
 * @date：2021-12-24_周五 12:59
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    private Integer id;
    private String accountNumber;
    private Float balance;
}
