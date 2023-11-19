package org.example.validator;

import org.example.dao.pojo.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerValidator implements Validator<Customer> {

    private static final Logger logger = LoggerFactory.getLogger(CustomerValidator.class);
    @Override
    public boolean isValid(Customer dto) {
        logger.info("Start validate object - " + dto);
        return validateName(dto.name) &&
                validateName(dto.lastName) &&
                validateName(dto.secondName) &&
                validateDocNum(dto.numDocument);
    }

    private boolean validateName(String name) {
        logger.info("Start validate string - " + name);
        return !name.contains(" ");
    }

    private boolean validateDocNum(String dockNum) {
        logger.info("Start validate document - " + dockNum);
        return dockNum.matches("^HB\\d{7}$");
    }
}
