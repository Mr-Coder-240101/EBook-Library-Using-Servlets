package edu.ebook.library.model.dao;

import javax.persistence.*;

@Entity
@Table(name = "books_data")
public class BooksData {
    @Id
    private String id;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
