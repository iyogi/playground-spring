---
marp: true
theme: gaia
_class: lead
paginate: true
backgroundColor: #f5f5f5

# Core Resilience Features in Spring 7 & Spring Boot 4
### Leaving External Dependencies Behind 🚀
**Presented by:** [Your Name]

---

## 1. The Paradigm Shift 🔄

* **Before Spring 7:** To protect microservices from transient failures, we had to rely on separate libraries like `spring-retry` or `Resilience4j`.
* **Now in Spring 7:** Fault tolerance moves into the core framework (`org.springframework.resilience`).
* **The Goal:** Elevate resilience patterns (`@Retryable`, `@ConcurrencyLimit`) to first-class status alongside core features like `@Async` and `@Scheduled`.

---

## 2. Activating the Infrastructure ⚙️

Just like caching or scheduling, core resilience relies on Spring AOP proxies activated via a single configuration hook:

```java
@Configuration
@EnableResilientMethods // 👈 The universal master switch
public class ResilienceConfig {
}
```

* Under the hood, this registers the necessary `BeanPostProcessor` engines to intercept annotated method calls.

---

## 3. Pattern 1: Native `@Retryable` 🔄

Automatically retries operations that encounter transient failures (network drops, third-party 5xx responses).

* **Key Features:** Supports max retries, exponential backoff multipliers, randomized `jitter` (to prevent thundering herds), and natively scales into **Reactive Pipelines** (`Mono`/`Flux`).

```java
@Retryable(
    includes = {RemoteTimeoutException.class}, 
    maxRetries = 4, 
    delayString = "500ms", 
    multiplier = 2.0
)
public String fetchProductDetails() { ... }
```

---

## 4. Pattern 2: `@ConcurrencyLimit` 🛡️

Implements a method-level **Bulkhead Pattern**. It restricts the number of threads allowed to execute a target method concurrently to prevent downstream starvation.

* **The Virtual Thread Factor:** Essential for Java 21+ Virtual Threads. Because virtual threads scale almost infinitely, `@ConcurrencyLimit` prevents unbounded thread bursts from crashing your databases or downstream REST APIs.

```java
@ConcurrencyLimit(limit = 3) // Only 3 threads allowed simultaneously
public void processPayment() { ... }
```

---

## 5. Live Demo Checklist 🧪

We will now look at a demo application showing:
1. **Dynamic Retries** handling random 500 errors.
2. **Concurrency Throttling** safely queuing excessive traffic.