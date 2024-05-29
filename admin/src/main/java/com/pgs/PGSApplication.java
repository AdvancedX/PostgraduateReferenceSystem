package com.pgs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author pgs
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class PGSApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(PGSApplication.class, args);
        System.out.println("服务启动成功");
    }
}