package com.yrs.spring_core_resilience.service;

public interface LimitedExecutionService {

    public Integer execute() throws LimitBreachedException;

    public void reset();

}
