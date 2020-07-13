package com.xyz0751.f0t1.mybatis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.*;
import java.sql.*;
import java.util.Properties;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcTest {
    private Logger log = LogManager.getLogger(JdbcTest.class);

    private String url;
    private String username;
    private String password;

    private String getDateSql;

    @BeforeAll
    void loadProperties() {
        Properties properties = new Properties();
        try(InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties")) {
            properties.load(is);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

            getDateSql = properties.getProperty("getDateSql");
        } catch (IOException e) {
            log.error("加载jdbc.properties失败！", e);
        }
    }

    @Test
    @DisplayName("query without parameter using jdbc")
    void queryWithoutParam() {
        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            try(Statement ps = conn.createStatement()) {
                try (ResultSet rs = ps.executeQuery(getDateSql)) {
                    while (rs.next()) {
                       log.info("currentTime: {}", rs.getObject("currentTime"));
                    }
                }
            }
        } catch (SQLException e) {
            log.error("查询SQL出现异常！", e);
        }
    }
}
