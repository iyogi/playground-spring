package com.yrs.spring_core_resilience.service.impl;


import com.yrs.spring_core_resilience.service.LimitBreachedException;
import com.yrs.spring_core_resilience.service.LimitedExecutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import static com.yrs.common.utils.CommonUtil.sleep;

@Service
public class LimitedExecutionServiceImpl implements LimitedExecutionService {

    private static final Logger LOG = LoggerFactory.getLogger(LimitedExecutionServiceImpl.class);

    private static final int MAX_CONCURRENT_EXECUTIONS = 10;

    private final Semaphore semaphore = new Semaphore(MAX_CONCURRENT_EXECUTIONS, true);

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Integer execute() {
        if (!semaphore.tryAcquire()) {
            throw new LimitBreachedException("Maximum concurrent executions exceeded [Permits = " + semaphore.availablePermits() + "]");
        }
        try {
            // do some useful work here. Only MAX_CONCURRENT_EXECUTIONS threads can execute this block concurrently.
            sleep(1000);
            return counter.incrementAndGet();
        } finally {
            semaphore.release();
        }
    }

    @Override
    public void reset() {
        semaphore.drainPermits();
        semaphore.release(MAX_CONCURRENT_EXECUTIONS);
        counter.set(0);
    }
}
