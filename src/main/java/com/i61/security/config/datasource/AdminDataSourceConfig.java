package com.i61.security.config.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;

/**
 * 六一信息科技
 *
 * @author: liyingjie
 * @date: 2018-07-12 23:55
 **/
@Configuration
@MapperScan(basePackages = "com.i61.security.mapper.admin", sqlSessionTemplateRef = "adminSqlSessionTemplate")
public class AdminDataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.admin")
    public DataSourceProperties adminDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "adminDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.admin")
    @Primary
    public DataSource adminDataSource() {
        return adminDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "adminSqlSessionFactory")
    @Primary
    public SqlSessionFactory adminSqlSessionFactory(@Qualifier("adminDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/admin/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "adminTransactionManager")
    @Primary
    public DataSourceTransactionManager adminTransactionManager(@Qualifier("adminDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "adminSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate adminSqlSessionTemplate(@Qualifier("adminSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
