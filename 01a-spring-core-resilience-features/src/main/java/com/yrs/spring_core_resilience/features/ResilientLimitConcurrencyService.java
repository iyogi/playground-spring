package com.yrs.spring_core_resilience.features;

import com.yrs.spring_core_resilience.service.LimitedExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.resilience.annotation.ConcurrencyLimit.ThrottlePolicy;
import org.springframework.stereotype.Service;

@Service
public class ResilientLimitConcurrencyService {

    private final LimitedExecutionService limitedExecutionService;

    @Autowired
    public ResilientLimitConcurrencyService(LimitedExecutionService limitedExecutionService) {
        this.limitedExecutionService = limitedExecutionService;
    }

    public Integer executeWithNoLimit() {
        return limitedExecutionService.execute();
    }

    @ConcurrencyLimit(limit = 10, policy = ThrottlePolicy.BLOCK)
    public Integer executeWithLimit() {
        return limitedExecutionService.execute();
    }
}
