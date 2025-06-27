package com.hexaware.lms.service;

import com.hexaware.lms.entity.Loan;
import com.hexaware.lms.exception.InvalidLoanException;
import java.util.List;

public interface ILoanRepository {

    // a. Apply for loan
    void applyLoan(Loan loan) throws Exception; // confirm from user before storing

    // b. Calculate interest from loanId
    double calculateInterest(int loanId) throws InvalidLoanException;

    // b.i Overloaded interest calculation
    double calculateInterest(double principalAmount, double interestRate, int loanTerm);

    // c. Determine loan status based on credit score
    String loanStatus(int loanId) throws InvalidLoanException;

    // d. Calculate EMI using loanId
    double calculateEMI(int loanId) throws InvalidLoanException;

    // d.i Overloaded EMI calculation
    double calculateEMI(double principalAmount, double interestRate, int loanTerm);

    // e. Handle repayment logic
    void loanRepayment(int loanId, double amount) throws InvalidLoanException;

    // f. Get and display all loan details
    List<Loan> getAllLoan();

    // g. Get loan by ID and display details
    Loan getLoanById(int loanId) throws InvalidLoanException;
}
