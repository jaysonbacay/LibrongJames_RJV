package com.jaysonbacay.lastna.data;

import androidx.annotation.NonNull;

public class LibraryModel {
    public int libBookID;
    public String libBookName;
    public String libBookAuthor;
    public String libBookGenre;
    public String libBookPublishDate;
    public int bookID;
    public String libImage;



    public LibraryModel(int libBookID, String libBookName, String libBookAuthor, String libBookGenre, String libBookPublishDate,int bookID, String libImage){
        this.libBookID = libBookID;
        this.libBookName = libBookName;
        this.libBookAuthor = libBookAuthor;
        this.libBookGenre = libBookGenre;
        this.libBookPublishDate = libBookPublishDate;
        this.bookID = bookID;
        this.libImage = libImage;
    }
    @NonNull
    @Override
    public String toString() {
        String s = "" + bookID + ": " + libBookName;
        return s;
    }

    public String getBookName() {
        return libBookName;
    }
}

