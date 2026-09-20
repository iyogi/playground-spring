package com.yrs.spring_core_resilience.features.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.resilience.retry.MethodRetryEvent;
import org.springframework.stereotype.Component;

@Component
public class MethodRetryEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(MethodRetryEventListener.class);

    // called when a method annotated with @Retryable is retried due to a retryable exception
    @EventListener
    public void handleMethodRetryEvent(MethodRetryEvent event) {
        LOG.info("MethodRetryEventListener:  MethodRetryEvent {}", event);
    }
}
