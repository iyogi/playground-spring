package com.yrs.spring_core_resilience.service.impl;

import com.yrs.common.exceptions.GenericRuntimeException;
import com.yrs.spring_core_resilience.service.RandomService;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RandomServiceImpl implements RandomService {

    AtomicInteger counter = new AtomicInteger(0);

    // will always throw an exception for the first 3 calls, then return 42 for the next call, and repeat
    @Override
    public Integer errorProneLogic() {
        if (counter.getAndIncrement() < 3) {
            throw new GenericRuntimeException("Simulated error", true);
        }
        counter.set(0);
        return 42; // return a valid result after 3 failures
    }

    @Override
    public void reset() {
        counter.set(0);
    }
}
