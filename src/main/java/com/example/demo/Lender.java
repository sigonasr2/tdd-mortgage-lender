package com.example.demo;

public class Lender {
	long funds=0l;
	public long getAvailableFunds() {
		return funds;
	}
	public void addFunds(long amt) {
		if (amt<=0) {
			 throw new IllegalArgumentException("Amount added must be greater than zero.");
		} else {
			funds=Math.addExact(funds, amt);
		}
	}
	public boolean checkRequest(LoanApplicant applicant) {
		return applicant.getRequestedAmount()<=funds;
	}
	public double calculateMonthlyPayment(long principal,float annualRate,int numPayments) {
		return Math.round(((principal*((annualRate/12)*Math.pow((1+annualRate/12),numPayments)))/(Math.pow(1+(annualRate/12),numPayments)-1)*100))/100d;
	}
	public boolean isGoodLoanCandidate(LoanApplicant applicant) {
		if (applicant.getMonthlyGrossIncome()<=0) {
			return false;
		}
		return applicant.getCreditScore()>=620&&funds>=applicant.getRequestedAmount()*0.25&&(double)applicant.getMonthlyDebtLoad()/applicant.getMonthlyGrossIncome()<0.36;
	}
}
