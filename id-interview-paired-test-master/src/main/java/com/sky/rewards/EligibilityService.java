package com.sky.rewards;

public interface EligibilityService {
    boolean isEligible(String accountNumber) throws TechnicalFailureException, InvalidAccountNumberException;
}
