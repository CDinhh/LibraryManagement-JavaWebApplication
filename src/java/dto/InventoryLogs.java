
package dto;

import java.time.LocalDate;

public class InventoryLogs {
    private int id;
    private LocalDate createAt;
    private int bookId;
    private int total;
    private String description;

    public InventoryLogs() {
    }

    public InventoryLogs(int id, LocalDate createAt, int bookId, int total, String description) {
        this.id = id;
        this.createAt = createAt;
        this.bookId = bookId;
        this.total = total;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
