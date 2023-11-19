package org.example.proxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.cash.Cacheable;
import org.example.cash.impl.CacheLFU;
import org.example.dao.pojo.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.cash.CacheFactory.createCache;


@Aspect
public class CacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    private static Cacheable cache = createCache();


    @Pointcut("@annotation(org.example.proxy.annotation.GetCustomer)")
    public void findByIdCall() {}

    @Around(value = "findByIdCall()")
    public Customer cacheFindById(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Proxy work");
        Object[] args = joinPoint.getArgs();
        Integer id = (Integer) args[0];

        if (cache.get(id) != null) {
            System.out.println("Using cache for ID: " + id);
            return (Customer) cache.get(id);
        }

        Object result = joinPoint.proceed();

        if (result != null) {
            cache.put(id, (Customer) result);
        }

        return (Customer) result;
    }
}
