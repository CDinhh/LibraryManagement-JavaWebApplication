/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.time.LocalDate;

/**
 *
 * @author Dan Huy
 */
public class Fine {
    private int id;
    private int borrow_id;
    private String user_name;
    private LocalDate borrow_date;
    private LocalDate return_date;
    private double fine_amount;
    private String paid_status;

    public Fine() {
    }

    public Fine(int id, int borrow_id, String user_name, LocalDate borrow_date, LocalDate return_date, double fine_amount, String paid_status) {
        this.id = id;
        this.borrow_id = borrow_id;
        this.user_name = user_name;
        this.borrow_date = borrow_date;
        this.return_date = return_date;
        this.fine_amount = fine_amount;
        this.paid_status = paid_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBorrow_id() {
        return borrow_id;
    }

    public void setBorrow_id(int borrow_id) {
        this.borrow_id = borrow_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public LocalDate getBorrow_date() {
        return borrow_date;
    }

    public void setBorrow_date(LocalDate borrow_date) {
        this.borrow_date = borrow_date;
    }

    public LocalDate getReturn_date() {
        return return_date;
    }

    public void setReturn_date(LocalDate return_date) {
        this.return_date = return_date;
    }

    public double getFine_amount() {
        return fine_amount;
    }

    public void setFine_amount(double fine_amount) {
        this.fine_amount = fine_amount;
    }

    public String getPaid_status() {
        return paid_status;
    }

    public void setPaid_status(String paid_status) {
        this.paid_status = paid_status;
    }
    
}