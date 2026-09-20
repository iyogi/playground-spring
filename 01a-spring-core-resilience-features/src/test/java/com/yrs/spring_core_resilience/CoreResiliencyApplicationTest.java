package com.yrs.spring_core_resilience;

import com.yrs.common.service.CommonService;
import com.yrs.spring_core_resilience.features.ResilientRetryService;
import com.yrs.spring_core_resilience.service.RandomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CoreResiliencyApplicationTest extends BaseTest {

    @Autowired
    private CoreResiliencyApplication application;

    @Autowired
    private RandomService randomService;

    @Autowired
    private ResilientRetryService resilientRetryService;

    @Autowired
    private CommonService commonService;

    @Test
    void contextLoads() {
        assertThat(application).isNotNull();
        assertThat(resilientRetryService).isNotNull();
        assertThat(randomService).isNotNull();
        assertThat(commonService).isNotNull();
    }

}
