package com.rantomah.boilerplate.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected <T> ResponseEntity<T> created(T dto) {
        if (dto != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        }
        return noContent();
    }

    protected <T> ResponseEntity<T> success(T dto) {
        if (dto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        return noContent();
    }

    protected <T> ResponseEntity<T> noContent() {
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<Void> ok() {
        return ResponseEntity.ok().build();
    }

    protected ResponseEntity<Void> accepted() {
        return ResponseEntity.accepted().build();
    }

    protected <T> ResponseEntity<T> accepted(T body) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(body);
    }

}
