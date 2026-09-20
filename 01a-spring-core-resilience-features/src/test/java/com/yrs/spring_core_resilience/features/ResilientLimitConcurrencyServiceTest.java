package com.yrs.spring_core_resilience.features;

import com.yrs.spring_core_resilience.BaseTest;
import com.yrs.spring_core_resilience.service.LimitBreachedException;
import com.yrs.spring_core_resilience.service.LimitedExecutionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ResilientLimitConcurrencyServiceTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(ResilientLimitConcurrencyServiceTest.class);

    @Autowired
    private ResilientLimitConcurrencyService resilientLimitConcurrencyService;

    @Autowired
    private LimitedExecutionService limitedExecutionService;

    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    @BeforeEach
    void setUp() {
        limitedExecutionService.reset();
    }

    @Test
    void testExecuteWithNoLimit() {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                Integer result = resilientLimitConcurrencyService.executeWithNoLimit();
                LOG.info("Available permits: {}", result);
                return result;
            }, executorService));
        }

        assertThatThrownBy(() -> CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join())
                .isInstanceOf(CompletionException.class)
                .hasCause(new LimitBreachedException("Maximum concurrent executions exceeded [Permits = 0]"));
    }

    @Test
    void testExecuteWithLimit() {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                Integer result = resilientLimitConcurrencyService.executeWithLimit();
                LOG.info("Count: {}", result);
                return result;
            }, executorService));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
