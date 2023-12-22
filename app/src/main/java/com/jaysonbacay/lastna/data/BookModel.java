package com.jaysonbacay.lastna.data;

import androidx.annotation.NonNull;

public class BookModel {
    public int bookID;
    public String bookName;
    public String bookAuthor;
    public String bookGenre;
    public String bookPublishDate;
    public String synopsis;
    public String image;

    public BookModel(int bookID, String bookName, String bookAuthor, String bookGenre, String bookPublishDate, String synopsis,String image){
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookGenre = bookGenre;
        this.image = image;
        this.synopsis = synopsis;
        this.bookPublishDate =bookPublishDate;

    }

    @NonNull
    @Override
    public String toString() {
        String s = "" + bookID + ": " + bookName;
        return s;
    }

    public String getBookName() {
        return bookName;
    }
}


