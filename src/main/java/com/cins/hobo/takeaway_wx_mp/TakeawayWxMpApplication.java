package com.cins.hobo.takeaway_wx_mp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hobo
 */
@SpringBootApplication
@MapperScan("com.cins.hobo.takeaway_wx_mp.dao")
public class TakeawayWxMpApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeawayWxMpApplication.class, args);
    }

}
