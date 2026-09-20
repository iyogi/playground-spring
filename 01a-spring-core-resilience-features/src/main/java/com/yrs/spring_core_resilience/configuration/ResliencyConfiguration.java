package com.yrs.spring_core_resilience.configuration;

import com.yrs.common.exceptions.GenericRuntimeException;
import com.yrs.spring_core_resilience.features.listeners.RetryTemplateRetryListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.retry.RetryPolicy;
import org.springframework.core.retry.RetryTemplate;
import org.springframework.resilience.annotation.EnableResilientMethods;

import java.time.Duration;

// Enables Spring's core resilience features for method invocations: @Retryable as well as @ConcurrencyLimit.
@EnableResilientMethods // without this annotation, @Retryable and @ConcurrencyLimit will not work!
@Configuration
public class ResliencyConfiguration {


    @Bean
    public RetryPolicy retryPolicy() {
        return RetryPolicy.builder()
                // Add jitter to avoid thundering herd problem
                .jitter(Duration.ofMillis(100))
                .maxRetries(5)
                // timeout for the maximum amount of elapsed time allowed for the initial invocation and any subsequent retry attempts, including delays.
                .timeout(Duration.ofMillis(5000))
                // Specify the base delay after the initial invocation. Default 1000 milliseconds
                .delay(Duration.ofMillis(1200))
                // maximum delay for any retry attempt, limiting how far jitter and the multiplier can increase the delay. Default = unlimited
                .maxDelay(Duration.ofMillis(2000))
                // only retry if it is a retryable exception
                .predicate((e) -> e instanceof GenericRuntimeException gre && gre.isRetryable())
                // retry for any exception that is GenericRuntimeException or subclass of GenericRuntimeException
                .includes(GenericRuntimeException.class)
                .build();
    }
    @Bean
    public RetryTemplate retryTemplate(RetryPolicy retryPolicy, RetryTemplateRetryListener retryListener){
        RetryTemplate retryTemplate = new RetryTemplate(retryPolicy);
        retryTemplate.setRetryListener(retryListener);
        return    retryTemplate;
    }
}
