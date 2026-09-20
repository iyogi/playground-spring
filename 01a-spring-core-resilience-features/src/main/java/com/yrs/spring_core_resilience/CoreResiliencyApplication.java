package com.yrs.spring_core_resilience;

import com.yrs.common.CommonMarker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {CoreResiliencyApplication.class, CommonMarker.class})
public class CoreResiliencyApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoreResiliencyApplication.class, args);
    }
}
