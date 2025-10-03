package com.paymentmanagement.validation;

import com.paymentmanagement.exception.ValidationException;

public interface Validator<T> {
    void validate(T object) throws ValidationException;
}
