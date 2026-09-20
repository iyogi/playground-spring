package com.yrs.spring_core_resilience.features;

import com.yrs.common.exceptions.GenericRuntimeException;
import com.yrs.spring_core_resilience.service.RandomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.retry.RetryTemplate;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static com.yrs.common.utils.CommonUtil.sleep;

@Component
public class ResilientRetryService {

    private static final Logger LOG = LoggerFactory.getLogger(ResilientRetryService.class);

    private final RandomService randomService;
    private final RetryTemplate retryTemplate;

    @Autowired
    public ResilientRetryService(RandomService randomService, RetryTemplate retryTemplate) {
        this.randomService = randomService;
        this.retryTemplate = retryTemplate;
    }

    public Integer executeErrorProneLogic() {
        return randomService.errorProneLogic();
    }

    public Integer executeErrorProneLogicWithResilienceOldSchool() {
        int maxRetry = 5;
        for (int i = 1; i <= maxRetry; i++) {
            try {
                return randomService.errorProneLogic();
            } catch (GenericRuntimeException e) {
                LOG.info("OldSchool resilience:  Attempt {} failed with exception: {}", i, e.toString());
                if (i >= maxRetry) {
                    LOG.info("OldSchool resilience:  Max retry attempt {} reached with exception: {}", i, e.toString());
                    throw e; // rethrow the exception if max retries reached
                }
                sleep(Duration.ofMillis(1000));
            }
        }
        throw new GenericRuntimeException("Dead code. should not reach here", true);
    }

    @Retryable
    public Integer executeErrorProneLogicWithResilienceAnnotation() {
        return randomService.errorProneLogic();
    }

    public Integer executeErrorProneLogicWithResilienceProgrammatic() {
        return retryTemplate.invoke(randomService::errorProneLogic);
    }
}
