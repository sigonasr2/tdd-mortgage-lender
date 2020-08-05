package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

final class LenderTest {

    @Test
    void getAvailableFundsReturnsZeroByDefault() {
        //Setup
        final Lender lender = new Lender();
        final long expected = 0L;

        //Exercise
        final long actual = lender.getAvailableFunds();

        //Assert
        assertEquals(expected,actual
        		,"Test initializing funds.");

        //Teardown
    }
    @Test
    void addFundsThrowsAnExceptionForZero(){
        //Setup
        final Lender lender = new Lender();
        final String expected = "Amount added must be greater than zero.";

        //Exercise
        final Exception actual = assertThrows(IllegalArgumentException.class,
                () -> lender.addFunds(0L));

        //Assert
        assertEquals(expected,actual.getMessage()
        		,"Test increasing by less than 1");
    }
    @Test
    void addFundsIncreasesAvailableFundsByOne(){
        //Setup
        final Lender lender = new Lender();
        final long expected = 1L;

        //Exercise
        lender.addFunds(expected);

        final long actual = lender.getAvailableFunds();

        //Assert
        assertEquals(expected,actual
        		,"Increase available funds by 1");
    }
    @Test
    void addFundsThrowsAnExceptionForNegativeValue(){
        //Setup
        final Lender lender = new Lender();
        final String expected = "Amount added must be greater than zero.";

        //Exercise
        final Exception actual = assertThrows(IllegalArgumentException.class, 
            () -> lender.addFunds(Long.MIN_VALUE));

        //Assert
        assertEquals(expected,actual.getMessage()
        		,"Test increasing by less than 1");
    }
    @Test
    void addFundsIncreasesAvailableFundsByTwo(){
        //Setup
        final Lender lender = new Lender();
        final long expected = 2L;

        //Exercise
        lender.addFunds(expected);

        final long actual = lender.getAvailableFunds();

        //Assert
        assertEquals(expected,actual
        		,"Test Increasing available funds");
    }
    @Test
    void addFundsIncreasesAvailableFundsByThreeThenFour(){
        //Setup
        final Lender lender = new Lender();
        final long expected = 7L;

        //Exercise
        lender.addFunds(3L);
        lender.addFunds(4L);

        final long actual = lender.getAvailableFunds();

        //Assert
        assertEquals(expected,actual
        		,"Test Increasing available funds multiple times");
    }
    @Test
    void addFundsThrowExceptionIfOverflowWillHappen(){
        //Setup
        final Lender lender = new Lender();
        lender.addFunds(Long.MAX_VALUE);
        final String expected = "long overflow";

        //Exercise
        final Exception actual = assertThrows(ArithmeticException.class, 
            () -> lender.addFunds(1L));

        //Assert
        assertEquals(expected,actual.getMessage()
        		,"Prevent overflow errors");
    }
    @Test
    void rejectHighLoanAmounts(){
        //Setup
        final Lender lender = new Lender();
        lender.addFunds(3200l);
        
        LoanApplicant applicant = new LoanApplicant(640000,7900l,81829,0,860);


        //Assert
        assertEquals(false,lender.checkRequest(applicant)
        		,"Deny applicants that offer high loan amounts");
    }
    @Test
    void acceptLowLoanAmounts(){
        //Setup
        final Lender lender = new Lender();
        lender.addFunds(3200l);
        
        LoanApplicant applicant = new LoanApplicant(640000,3200l,81829,0,860);


        //Assert
        assertEquals(true,lender.checkRequest(applicant)
        		,"Accept applicants that offer low loan amounts");
    }
    @Test
    void calculateMonthlyMortgagePayment(){
        //Setup
        final Lender lender = new Lender();
        
        //monthlyPayment = principal * ((interestRate/12) * (1+(interestRate/12))^numberOfPayments) / ((1 + (interestRate/12))^numberOfPayments - 1)
        long principal = 250000l;
        float annualRate = 0.04f;
        int numPayments = 360;
        
        //Assert
        assertEquals(1193.54d,lender.calculateMonthlyPayment(principal,annualRate,numPayments)
        		,"Calculate Monthly Mortgage Payments properly");
    }
    @Test
    void isGoodLoanCandidate(){
        //Setup
        final Lender lender = new Lender();
        lender.addFunds(3200l);
        
        LoanApplicant applicant = new LoanApplicant(640000,3200l,81829,0,860);
        
        //Assert
        assertEquals(true,lender.isGoodLoanCandidate(applicant)
        		,"Accept good Debt-to-Income applicants");
    }
    @Test
    void denyRequestedMortageDebtToIncomeTooHigh(){
        //Setup
        final Lender lender = new Lender();
        lender.addFunds(3200l);
        
        LoanApplicant applicant = new LoanApplicant(640000,3200l,400,200,860);
        
        //Assert
        assertEquals(false,lender.isGoodLoanCandidate(applicant)
        		,"Deny bad Debt-to-Income applicants");
    }
    @Test
    void denyRequestedMortageDebtToIncomeTooHigh2(){
        //Setup
        final Lender lender = new Lender();
        lender.addFunds(3200l);
        
        LoanApplicant applicant = new LoanApplicant(640000,3200l,1000,360,860);
        
        //Assert
        assertEquals(false,lender.isGoodLoanCandidate(applicant)
        		,"Deny bad Debt-to-Income applicants");
    }
    @Test
    void acceptRequestedMortageDebtToIncomeGoodEnough(){
        //Setup
        final Lender lender = new Lender();
        lender.addFunds(3200l);
        
        LoanApplicant applicant = new LoanApplicant(640000,3200l,1000,359,860);
        
        //Assert
        assertEquals(true,lender.isGoodLoanCandidate(applicant)
        		,"Accept good Debt-to-Income applicants");
    }
    @Test
    void acceptRequestedMortageDebtCreditScoreGoodEnough(){
        //Setup
        final Lender lender = new Lender();
        lender.addFunds(3200l);
        
        LoanApplicant applicant = new LoanApplicant(640000,3200l,1000,359,620);
        
        //Assert
        assertEquals(true,lender.isGoodLoanCandidate(applicant)
        		,"Accept good credit scores");
    }
    @Test
    void denyRequestedMortageDebtCreditScoreTooLow(){
        //Setup
        final Lender lender = new Lender();
        lender.addFunds(3200l);
        
        LoanApplicant applicant = new LoanApplicant(640000,3200l,1000,359,619);
        
        //Assert
        assertEquals(false,lender.isGoodLoanCandidate(applicant)
        		,"Deny credit scores that are too low");
    }
    @Test
    void denyRequestedMortageDebtAmountSavedTooLow(){
        //Setup
        final Lender lender = new Lender();
        lender.addFunds(3200l);
        
        LoanApplicant applicant = new LoanApplicant(640000,12801l,1000,359,620);
        
        //Assert
        assertEquals(false,lender.isGoodLoanCandidate(applicant)
        		,"Deny Amount Saved too low.");
    }
    @Test
    void acceptRequestedMortageDebtAmountSavedHighEnough(){
        //Setup
        final Lender lender = new Lender();
        lender.addFunds(3200l);
        
        LoanApplicant applicant = new LoanApplicant(640000,12800l,1000,359,620);
        
        //Assert
        assertEquals(true,lender.isGoodLoanCandidate(applicant)
        		,"Accept Amount Saved is 25% or more of requested amount.");
    }
}