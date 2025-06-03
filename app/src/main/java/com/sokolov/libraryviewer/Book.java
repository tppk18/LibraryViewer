package com.sokolov.libraryviewer;

import android.content.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class Book {
    private String desc;
    private long issueDate;
    private long planReturnDate;

    public Book(String desc, Date issueDate, Date planReturnDate) {
        this.desc = desc;
        this.issueDate = issueDate.getTime();
        this.planReturnDate = planReturnDate.getTime();
    }

    public String getDesc() { return desc; }
    public Date getIssueDate() { return new Date(issueDate); }
    public Date getPlanDate() { return new Date(planReturnDate); }

    public static void loadBooks(Context context) {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                String query = "SELECT * FROM `bookissuance` WHERE LibraryCard = ? and OkReturnDate is null";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, Integer.parseInt(UserDataManager.getUser().libraryCard));
                ResultSet rs = stmt.executeQuery();

                UserDataManager.getUser().bookList.clear();
                int i = 0;
                while (rs.next()) {
                    Date issueDate = rs.getDate("IssueDate");
                    Date planReturnDate = rs.getDate("PlanReturnDate");
                    String desc = rs.getString("BiblDesc");
                    UserDataManager.getUser().bookList.add(new Book(desc, issueDate, planReturnDate));
                }
                UserDataManager.saveUserData(context, UserDataManager.getUser());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

