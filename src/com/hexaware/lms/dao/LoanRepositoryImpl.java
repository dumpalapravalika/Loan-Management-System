package com.hexaware.lms.dao;

import com.hexaware.lms.entity.*;
import com.hexaware.lms.exception.InvalidLoanException;
import com.hexaware.lms.service.ILoanRepository;
import com.hexaware.lms.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoanRepositoryImpl implements ILoanRepository {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void applyLoan(Loan loan) throws Exception {
        System.out.print("Do you want to apply for the loan? (Yes/No): ");
        String confirm = scanner.nextLine().trim();
        if (!confirm.equalsIgnoreCase("Yes")) {
            System.out.println("Loan application cancelled.");
            return;
        }

        try (Connection con = DBUtil.getDBConn()) {

            // Insert Customer if not exists
            String checkCustomer = "SELECT customer_id FROM customer WHERE customer_id = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkCustomer);
            checkStmt.setInt(1, loan.getCustomer().getCustomerId());
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                String insertCustomer = "INSERT INTO customer (customer_id, name, email, phone, address, credit_score) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement custStmt = con.prepareStatement(insertCustomer);
                custStmt.setInt(1, loan.getCustomer().getCustomerId());
                custStmt.setString(2, loan.getCustomer().getName());
                custStmt.setString(3, loan.getCustomer().getEmailAddress());
                custStmt.setString(4, loan.getCustomer().getPhoneNumber());
                custStmt.setString(5, loan.getCustomer().getAddress());
                custStmt.setInt(6, loan.getCustomer().getCreditScore());
                custStmt.executeUpdate();
            }

            // Insert Loan
            String insertLoan = "INSERT INTO loan (loan_id, customer_id, principal_amount, interest_rate, loan_term, loan_type, loan_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement loanStmt = con.prepareStatement(insertLoan);
            loanStmt.setInt(1, loan.getLoanId());
            loanStmt.setInt(2, loan.getCustomer().getCustomerId());
            loanStmt.setDouble(3, loan.getPrincipalAmount());
            loanStmt.setDouble(4, loan.getInterestRate());
            loanStmt.setInt(5, loan.getLoanTerm());
            loanStmt.setString(6, loan.getLoanType());
            loanStmt.setString(7, loan.getLoanStatus());
            loanStmt.executeUpdate();

            // Insert into CarLoan
            if (loan.getLoanType().equalsIgnoreCase("CarLoan") && loan instanceof CarLoan) {
                CarLoan carLoan = (CarLoan) loan;
                String carLoanSql = "INSERT INTO car_loan (loan_id, car_model, car_value) VALUES (?, ?, ?)";
                PreparedStatement carStmt = con.prepareStatement(carLoanSql);
                carStmt.setInt(1, carLoan.getLoanId());
                carStmt.setString(2, carLoan.getCarModel());
                carStmt.setInt(3, carLoan.getCarValue());
                carStmt.executeUpdate();
            }

            // Insert into HomeLoan
            if (loan.getLoanType().equalsIgnoreCase("HomeLoan") && loan instanceof HomeLoan) {
                HomeLoan homeLoan = (HomeLoan) loan;
                String homeLoanSql = "INSERT INTO home_loan (loan_id, property_address, property_value) VALUES (?, ?, ?)";
                PreparedStatement homeStmt = con.prepareStatement(homeLoanSql);
                homeStmt.setInt(1, homeLoan.getLoanId());
                homeStmt.setString(2, homeLoan.getPropertyAddress());
                homeStmt.setInt(3, homeLoan.getPropertyValue());
                homeStmt.executeUpdate();
            }

            System.out.println("✅ Loan application submitted successfully.");

        } catch (SQLException e) {
            System.out.println("❌ Database error while applying loan: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public double calculateInterest(int loanId) throws InvalidLoanException {
        Loan loan = getLoanById(loanId);
        return calculateInterest(loan.getPrincipalAmount(), loan.getInterestRate(), loan.getLoanTerm());
    }

    @Override
    public double calculateInterest(double principal, double rate, int term) {
        return (principal * rate * term) / 12;
    }

    @Override
    public String loanStatus(int loanId) throws InvalidLoanException {
        Loan loan = getLoanById(loanId);
        String status = loan.getCustomer().getCreditScore() > 650 ? "Approved" : "Rejected";

        try (Connection con = DBUtil.getDBConn()) {
            String sql = "UPDATE loan SET loan_status = ? WHERE loan_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, status);
            pst.setInt(2, loanId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public double calculateEMI(int loanId) throws InvalidLoanException {
        Loan loan = getLoanById(loanId);
        return calculateEMI(loan.getPrincipalAmount(), loan.getInterestRate(), loan.getLoanTerm());
    }

    @Override
    public double calculateEMI(double principal, double rate, int term) {
        double r = rate / 12 / 100;
        return (principal * r * Math.pow(1 + r, term)) / (Math.pow(1 + r, term) - 1);
    }

    @Override
    public void loanRepayment(int loanId, double amount) throws InvalidLoanException {
        double emi = calculateEMI(loanId);
        if (amount < emi) {
            System.out.println("Insufficient amount to pay even one EMI. Payment rejected.");
        } else {
            int emiPaid = (int)(amount / emi);
            System.out.println("You have paid " + emiPaid + " EMI(s). Remaining amount ignored.");
        }
    }

    @Override
    public List<Loan> getAllLoan() {
        List<Loan> loanList = new ArrayList<>();
        try (Connection con = DBUtil.getDBConn()) {
            String sql = "SELECT * FROM loan";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int custId = rs.getInt("customer_id");
                Customer cust = getCustomerById(custId, con);
                Loan loan = new Loan(
                        rs.getInt("loan_id"),
                        cust,
                        rs.getDouble("principal_amount"),
                        rs.getDouble("interest_rate"),
                        rs.getInt("loan_term"),
                        rs.getString("loan_type"),
                        rs.getString("loan_status")
                );
                loanList.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanList;
    }

    @Override
    public Loan getLoanById(int loanId) throws InvalidLoanException {
        try (Connection con = DBUtil.getDBConn()) {
            String sql = "SELECT * FROM loan WHERE loan_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, loanId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int custId = rs.getInt("customer_id");
                Customer cust = getCustomerById(custId, con);

                return new Loan(
                        rs.getInt("loan_id"),
                        cust,
                        rs.getDouble("principal_amount"),
                        rs.getDouble("interest_rate"),
                        rs.getInt("loan_term"),
                        rs.getString("loan_type"),
                        rs.getString("loan_status")
                );
            } else {
                throw new InvalidLoanException("Loan with ID " + loanId + " not found.");
            }
        } catch (SQLException e) {
            throw new InvalidLoanException("Database error: " + e.getMessage());
        }
    }

    private Customer getCustomerById(int customerId, Connection con) throws SQLException {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, customerId);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getInt("credit_score")
            );
        }
        return null;
    }
}
