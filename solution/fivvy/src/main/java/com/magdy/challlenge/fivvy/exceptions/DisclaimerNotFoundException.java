package com.magdy.challlenge.fivvy.exceptions;

public class DisclaimerNotFoundException extends CustomException {

    private static final String exceptionMessage = "Disclaimer not found";

    public DisclaimerNotFoundException() {
        super(exceptionMessage);
    }
}