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
public class BorrowRecord {
    private int id;
    private int user_id;
    private String user_name;
    private int book_id;
    private String book_name;
    private LocalDate borrow_date; 
    private LocalDate due_date;
    private LocalDate return_date;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public BorrowRecord() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public BorrowRecord(int id, int user_id, String user_name, int book_id, String book_name, LocalDate borrow_date, LocalDate due_date, LocalDate return_date,String status) {
        this.id = id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.book_id = book_id;
        this.book_name = book_name;
        this.borrow_date = borrow_date;
        this.due_date = due_date;
        this.return_date = return_date;
        this.status = status;
    }
    
    public BorrowRecord(int id, int user_id, int book_id, LocalDate borrow_date, LocalDate due_date, LocalDate return_date) {
        this.id = id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.borrow_date = borrow_date;
        this.due_date = due_date;
        this.return_date = return_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public LocalDate getBorrow_date() {
        return borrow_date;
    }

    public void setBorrow_date(LocalDate borrow_date) {
        this.borrow_date = borrow_date;
    }

    public LocalDate getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDate due_date) {
        this.due_date = due_date;
    }

    public LocalDate getReturn_date() {
        return return_date;
    }

    public void setReturn_date(LocalDate return_date) {
        this.return_date = return_date;
    }
    
}
