package org.example.proxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.cash.Cacheable;
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

    @Pointcut("@annotation(org.example.proxy.annotation.AddCustomer)")
    public void addNewCustomer() {}

    @Pointcut("@annotation(org.example.proxy.annotation.DeleteCustomer)")
    public void deleteCustomer() {}

    @Pointcut("@annotation(org.example.proxy.annotation.UpdCustomer)")
    public void updateCustomer() {}

    @Around(value = "findByIdCall()")
    public Customer cacheFindById(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Integer id = (Integer) args[0];

        if (cache.get(id) != null) {
            logger.info("Using cache for getByID: " + id);
            return (Customer) cache.get(id);
        }

        Object result = joinPoint.proceed();

        if (result != null) {
            cache.put(id, (Customer) result);
        }

        return (Customer) result;
    }

    @Around(value = "addNewCustomer()")
    public int cacheAddNewCustomer(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Customer customer = (Customer) args[0];

        int result = (int) joinPoint.proceed();

        if (result != 0) {
            customer.setId(result);
            cache.put(result, customer);
            logger.info("Add in cache new customer: " + customer);
        }

        return result;
    }

    @Around(value = "deleteCustomer()")
    public boolean cacheDeleteCustomer(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        int id = (int) args[0];

        boolean result = (boolean) joinPoint.proceed();

        if (result) {
            cache.remove(id);
            logger.info("Delete customer from cache with id: " + id);
        }

        return result;
    }

    @Around(value = "updateCustomer()")
    public boolean cacheUpdateCustomer(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Customer customer = (Customer) args[0];

        boolean result = (boolean) joinPoint.proceed();

        if (result) {
            cache.put(customer.id, customer);
            logger.info("Update customer in cache: " + customer);
        }

        return result;
    }
}
