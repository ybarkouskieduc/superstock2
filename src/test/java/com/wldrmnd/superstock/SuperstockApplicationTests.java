package com.wldrmnd.superstock;

import com.wldrmnd.superstock.jooq.user.UserJooqRepository;
import com.wldrmnd.superstock.mapper.UserRecordMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SuperstockApplicationTests {

    @Autowired
    private UserJooqRepository userJooqRepository;

    @Autowired
    private UserRecordMapper userRecordMapper;

    @Test
    void contextLoads() {

    }
}
