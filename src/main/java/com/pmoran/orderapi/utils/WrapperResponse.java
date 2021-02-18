package com.pmoran.orderapi.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WrapperResponse<T> {
    private boolean success;
    private String message;
    private T body;

    public ResponseEntity<T> createResponse(){
        return new ResponseEntity(this, HttpStatus.OK);
    }

    public ResponseEntity<T> createResponse(HttpStatus status){
        return new ResponseEntity(this, status);
    }

}
