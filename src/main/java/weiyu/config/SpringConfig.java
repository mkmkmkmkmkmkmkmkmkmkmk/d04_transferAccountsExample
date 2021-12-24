package weiyu.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *@Bean的作用
 * 11.配置类配置spring的数据源.@PropertySource， @value
 * ① @PropertySource：引入外部的属性配置文件
 * ⑥ @value：获取属性文件的key值
 **/
//@Configuration
@ComponentScan("weiyu")
@PropertySource("classpath:jdbc1.properties")//加载配置文件
public class SpringConfig {
    //值必须使用${}获取jdbc.properties 中值
    @Value("${jdbc.driverClassName}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.user}")
    private String username;
    @Value("${jdbc.pass}")
    private  String password;


    /**
     *  11.@Bean的作用:
     * 不仅有@Autowird的作用还能将方法的返回对象加入到spring容器
     * 1、带有该注解的方法,将会由spring容器调用;
     * 2、如果当前方法带有参数:将会先按照 参数的类型 从容器中找对象并注入进来
     * 3、如果从容器中找到 相同类型的对象有多个 ，则会按照 参数的名称 再次查找对象,如果没有指定名称，默认名称是当前的方法名dataSource
     * 4、将方法的返回值对象加入到spring容器中(这也是为什么不能使用Autowired的原因)
     *
     * */
    //@Bean("dataSource")
     @Bean
    public HikariDataSource dataSource() {
        HikariDataSource hikariDataSource=new HikariDataSource();
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
         return hikariDataSource;
    }
    //自动将上面的dataSource注入进来
    //@Quilifier("指定要注入对象的名字")
    @Bean
    public QueryRunner queryRunner(@Qualifier("dataSource") HikariDataSource dataSource){
        return new QueryRunner(dataSource);
    }

}
