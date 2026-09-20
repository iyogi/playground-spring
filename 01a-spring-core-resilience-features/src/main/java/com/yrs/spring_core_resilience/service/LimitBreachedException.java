package com.yrs.spring_core_resilience.service;

public class LimitBreachedException extends RuntimeException {

    public LimitBreachedException(String message) {
        super(message);
    }

}
