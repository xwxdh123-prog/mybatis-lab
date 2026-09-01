package com.lab.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class MyBatisUtil {
    private static final String PASSWORD_ENV = "MYBATIS_DB_PASSWORD";

    private MyBatisUtil() {
    }

    public static SqlSession openSession() {
        return FactoryHolder.FACTORY.openSession();
    }

    public static SqlSession openSession(boolean autoCommit) {
        return FactoryHolder.FACTORY.openSession(autoCommit);
    }

    private static SqlSessionFactory buildFactory() {
        String password = System.getenv(PASSWORD_ENV);
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalStateException("请先设置环境变量 " + PASSWORD_ENV);
        }

        Properties runtimeProperties = new Properties();
        runtimeProperties.setProperty("jdbc.password", password);

        try (InputStream input = Resources.getResourceAsStream("mybatis-config.xml")) {
            SqlSessionFactory factory = new MybatisSqlSessionFactoryBuilder()
                    .build(input, null, runtimeProperties);
            MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
            factory.getConfiguration().addInterceptor(interceptor);
            return factory;
        } catch (IOException exception) {
            throw new IllegalStateException("读取 mybatis-config.xml 失败", exception);
        }
    }

    private static final class FactoryHolder {
        private static final SqlSessionFactory FACTORY = buildFactory();
    }
}
