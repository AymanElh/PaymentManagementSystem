package com.paymentmanagement.validation;

import com.paymentmanagement.exception.ValidationException;
import com.paymentmanagement.model.Payment;
import com.paymentmanagement.util.DateUtil;

import java.time.LocalDate;
import java.util.Date;

public class PaymentValidator implements Validator<Payment> {

    @Override
    public void validate(Payment payment) throws ValidationException {
        if(payment == null) {
            throw new ValidationException("Payment cannot be null");
        }

        if(payment.getAgent() == null) {
            throw new ValidationException("Agent cannot be null");
        }

        validateAmount(payment.getAmount());
        validatePaymentDate(payment.getPaymentDate());
    }

    private void validateAmount(double amount) {
        if(amount <= 0) {
            throw new ValidationException("Amount cannot must be less than 0");
        }
    }

    private void validatePaymentDate(Date date) {
        LocalDate localDate = DateUtil.convertToLocalDate(date);
        LocalDate today = LocalDate.now();
        LocalDate threeDaysLater = today.plusDays(3);
        if (localDate.isBefore(today)) {
            throw new ValidationException("Date cannot be before today.");
        }

        if (localDate.isAfter(threeDaysLater)) {
            throw new ValidationException("Date cannot be after 3 days from today.");
        }
    }
}
