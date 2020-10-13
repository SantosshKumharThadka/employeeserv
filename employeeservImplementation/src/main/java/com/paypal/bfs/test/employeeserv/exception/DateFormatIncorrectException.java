package com.paypal.bfs.test.employeeserv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DateFormatIncorrectException extends RuntimeException {

    public DateFormatIncorrectException(String exception) {
	super(exception);
    }

}
