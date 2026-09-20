package com.yrs.spring_core_resilience;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

@SpringBootTest
public abstract class BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);

    @BeforeAll
    public static void logStartAll(TestInfo testInfo) {
        LOG.info("START unit-tests {} ----------",  testInfo.getTestClass().map(Class::getSimpleName).orElse("X"));
    }

    @BeforeEach
    public void logStart(TestInfo testInfo) {
        LOG.info(
                "START unit-test {}.{}():{} ----------",
                testInfo.getTestClass().map(Class::getSimpleName).orElse("X"),
                testInfo.getTestMethod().map(Method::getName).orElse("X"),
                testInfo.getDisplayName());
    }

    @AfterEach
    public void logEnd(TestInfo testInfo) {
        LOG.info(
            "END unit-test {}.{}():{} ----------\n",
            testInfo.getTestClass().map(Class::getSimpleName).orElse("X"),
            testInfo.getTestMethod().map(Method::getName).orElse("X"),
            testInfo.getDisplayName());
    }

    @AfterAll
    public static void logEndAll(TestInfo testInfo) {
        LOG.info("END unit-tests {} ----------\n\n\n",  testInfo.getTestClass().map(Class::getSimpleName).orElse("X"));
    }
}
