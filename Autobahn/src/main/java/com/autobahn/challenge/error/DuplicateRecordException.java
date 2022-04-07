package com.autobahn.challenge.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason = "This is Duplicate Record")
public class DuplicateRecordException extends RuntimeException{

	private static final long serialVersionUID = 1L;

}
