package com.darknash.tickets.exceptions;

import com.darknash.tickets.dtos.AppResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandleException {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<AppResponse<String>> handleUserNotFoundException(UserNotFoundException e) {
        AppResponse appResponse = new AppResponse();
        appResponse.setCode(HttpStatus.NOT_FOUND.value());
        appResponse.setMessage(e.getMessage());
        appResponse.setData(HttpStatus.NOT_FOUND.getReasonPhrase());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(appResponse);
    }


}
