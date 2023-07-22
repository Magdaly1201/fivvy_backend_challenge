package com.magdy.challlenge.fivvy.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends Exception {

    protected String systemMessage;

}