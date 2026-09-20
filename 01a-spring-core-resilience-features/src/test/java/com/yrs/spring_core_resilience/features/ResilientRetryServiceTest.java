package com.yrs.spring_core_resilience.features;

import com.yrs.common.exceptions.GenericRuntimeException;
import com.yrs.spring_core_resilience.BaseTest;
import com.yrs.spring_core_resilience.service.RandomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResilientRetryServiceTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(ResilientRetryServiceTest.class);

    @Autowired
    private ResilientRetryService resilientRetryService;

    @Autowired
    private RandomService randomService;

    @BeforeEach
    void setUp() {
        randomService.reset();
    }

    @Test
    void testExecuteErrorProneLogic_NoResilence() {
        // call 1
        assertThrows(GenericRuntimeException.class, () -> {
            resilientRetryService.executeErrorProneLogic();
        });

        // call 2
        assertThrows(GenericRuntimeException.class, () -> {
            resilientRetryService.executeErrorProneLogic();
        });

        // call 3
        assertThrows(GenericRuntimeException.class, () -> {
            resilientRetryService.executeErrorProneLogic();
        });

        assertThat(resilientRetryService.executeErrorProneLogic()).isEqualTo(42);
    }

    @Test
    void testExecuteErrorProneLogic_Resilence_OldSchool() {
        assertThat(resilientRetryService.executeErrorProneLogicWithResilienceOldSchool()).isEqualTo(42);
    }

    @Test
    void testExecuteErrorProneLogic_Resilence_Annotation() {
        assertThat(resilientRetryService.executeErrorProneLogicWithResilienceAnnotation()).isEqualTo(42);
    }

    @Test
    void testExecuteErrorProneLogic_Resilence_Programmatic() {
        assertThat(resilientRetryService.executeErrorProneLogicWithResilienceProgrammatic()).isEqualTo(42);
    }
}
