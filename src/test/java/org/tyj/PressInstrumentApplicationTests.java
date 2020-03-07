package org.tyj;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PressInstrumentApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(PressInstrumentApplicationTests.class);

    @Test
    void contextLoads() {
        logger.info("完成单元测试!");
    }

    @Test
    void testSuccess() {
        logger.info("完成单元测试!ok!");
    }

}
