package com.yrs.spring_core_resilience.features.listeners;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.retry.RetryListener;
import org.springframework.core.retry.RetryPolicy;
import org.springframework.core.retry.RetryState;
import org.springframework.core.retry.Retryable;
import org.springframework.stereotype.Component;

@Component
public class RetryTemplateRetryListener implements RetryListener {

    private static final Logger LOG = LoggerFactory.getLogger(RetryTemplateRetryListener.class);

    @Override
    public void onRetryableExecution(RetryPolicy retryPolicy, Retryable<?> retryable, RetryState retryState) {
        LOG.info("RetryTemplateRetryListener:  onRetryableExecution:  retryable={}, retryState={}", retryable, retryState);
    }

    @Override
    public void onRetryFailure(RetryPolicy retryPolicy, Retryable<?> retryable, Throwable throwable) {
        LOG.info("RetryTemplateRetryListener:  onRetryFailure:  retryable={}, throwable={}", retryable, throwable.toString());
    }

    @Override
    public void onRetrySuccess(RetryPolicy retryPolicy, Retryable<?> retryable, @Nullable Object result) {
        LOG.info("RetryTemplateRetryListener:  onRetrySuccess:  retryable={}, result={}", retryable, result);
    }
}
