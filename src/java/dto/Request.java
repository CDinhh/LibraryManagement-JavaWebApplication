package dto;

import java.time.LocalDate;


public class Request {
    private int id;    
    private int userId;
    private String userName;
    private int bookId;
    private String bookName;
    private LocalDate requestDate;
    private String status;

    public Request() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Request(int id, int userId, int bookId, String bookName, LocalDate requestDate, String status) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.bookName = bookName;
        this.requestDate = requestDate;
        this.status = status;
    }

    public Request(int id, int userId, String userName, int bookId, String bookName, LocalDate requestDate, String status) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.bookId = bookId;
        this.bookName = bookName;
        this.requestDate = requestDate;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request{" + "id=" + id + ", userId=" + userId + ", userName=" + userName + ", bookId=" + bookId + ", bookName=" + bookName + ", requestDate=" + requestDate + ", status=" + status + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
