package com.example.moty.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuditEnum {

    HELLO("Hello");
    
    private String action;


    
}
