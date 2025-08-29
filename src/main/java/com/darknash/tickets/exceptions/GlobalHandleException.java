package com.darknash.tickets.exceptions;

import com.darknash.tickets.dtos.AppResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandleException {

    private static ResponseEntity<AppResponse<String>> getAppResponseResponseEntity(Exception e, HttpStatus httpStatus) {
        AppResponse<String> appResponse = new AppResponse<>();
        appResponse.setCode(httpStatus.value());
        appResponse.setMessage(e.getMessage());
        appResponse.setData(httpStatus.getReasonPhrase());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(appResponse);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<AppResponse<String>> handleUserNotFoundException(UserNotFoundException e) {
        return getAppResponseResponseEntity(e, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(TicketTypeNotFoundException.class)
    public ResponseEntity<AppResponse<String>> handleTicketTypeNotFoundException(TicketTypeNotFoundException e) {
        return getAppResponseResponseEntity(e, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<AppResponse<String>> handleEventNotFoundException(EventNotFoundException e) {
        return getAppResponseResponseEntity(e, HttpStatus.NOT_FOUND);
    }


}
