package org.example.validator;

public interface Validator<T> {

    boolean isValid(T dto);
}
