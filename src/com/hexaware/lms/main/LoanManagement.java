package com.hexaware.lms.main;

import com.hexaware.lms.dao.LoanRepositoryImpl;
import com.hexaware.lms.entity.*;
import com.hexaware.lms.exception.InvalidLoanException;
import com.hexaware.lms.service.ILoanRepository;

import java.util.List;
import java.util.Scanner;

public class LoanManagement {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ILoanRepository loanRepo = new LoanRepositoryImpl();

        while (true) {
            System.out.println("\n===== LOAN MANAGEMENT SYSTEM =====");
            System.out.println("1. Apply Loan");
            System.out.println("2. getAllLoan");
            System.out.println("3. getLoan");
            System.out.println("4. loanRepayment");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    try {
                        System.out.print("Enter Loan ID: ");
                        int loanId = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Customer ID: ");
                        int custId = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Customer Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();

                        System.out.print("Enter Phone: ");
                        String phone = sc.nextLine();

                        System.out.print("Enter Address: ");
                        String address = sc.nextLine();

                        System.out.print("Enter Credit Score: ");
                        int score = Integer.parseInt(sc.nextLine());

                        Customer cust = new Customer(custId, name, email, phone, address, score);

                        System.out.print("Enter Principal Amount: ");
                        double principal = Double.parseDouble(sc.nextLine());

                        System.out.print("Enter Interest Rate: ");
                        double rate = Double.parseDouble(sc.nextLine());

                        System.out.print("Enter Loan Term (months): ");
                        int term = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Loan Type (HomeLoan/CarLoan): ");
                        String loanType = sc.nextLine();

                        Loan loan;

                        if (loanType.equalsIgnoreCase("HomeLoan")) {
                            System.out.print("Enter Property Address: ");
                            String propertyAddr = sc.nextLine();

                            System.out.print("Enter Property Value: ");
                            int propertyVal = Integer.parseInt(sc.nextLine());

                            loan = new HomeLoan(loanId, cust, principal, rate, term, loanType, "Pending", propertyAddr, propertyVal);

                        } else if (loanType.equalsIgnoreCase("CarLoan")) {
                            System.out.print("Enter Car Model: ");
                            String carModel = sc.nextLine();

                            System.out.print("Enter Car Value: ");
                            int carVal = Integer.parseInt(sc.nextLine());

                            loan = new CarLoan(loanId, cust, principal, rate, term, loanType, "Pending", carModel, carVal);

                        } else {
                            System.out.println("Invalid loan type.");
                            break;
                        }

                        loanRepo.applyLoan(loan);

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "2":
                    List<Loan> loans = loanRepo.getAllLoan();
                    for (Loan l : loans) {
                        System.out.println(l);
                    }
                    break;

                case "3":
                    try {
                        System.out.print("Enter Loan ID: ");
                        int id = Integer.parseInt(sc.nextLine());
                        Loan loan = loanRepo.getLoanById(id);
                        System.out.println(loan);
                    } catch (InvalidLoanException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "4":
                    try {
                        System.out.print("Enter Loan ID: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Amount to Pay: ");
                        double amount = Double.parseDouble(sc.nextLine());
                        loanRepo.loanRepayment(id, amount);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "0":
                    System.out.println("Thank you for using Loan Management System!");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please enter a number between 0 and 4.");
            }
        }
    }
}
