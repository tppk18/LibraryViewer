package com.sokolov.libraryviewer;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    public String libraryCard = "";
    public String recordNumber = "";
    public List<Book> bookList = new ArrayList<Book>();
    public UserData() {}
    public UserData(String libraryCard, String recordNumber) {
        this.libraryCard = libraryCard;
        this.recordNumber = recordNumber;
    }

}
