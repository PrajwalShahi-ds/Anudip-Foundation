package operation;

import connection.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class DisplayOperation {


    // Display all issued books

    public void displayIssuedBooks(){


        try{


            Connection con =
                    MySqlConnection.getConnection();



            String query =
                    "SELECT issue_book.issue_id," +
                            "book.book_name," +
                            "member.member_name," +
                            "issue_book.issue_date," +
                            "issue_book.return_date," +
                            "issue_book.status " +

                            "FROM issue_book " +

                            "JOIN book ON issue_book.book_id = book.book_id " +

                            "JOIN member ON issue_book.member_id = member.member_id";



            PreparedStatement ps =
                    con.prepareStatement(query);



            ResultSet rs =
                    ps.executeQuery();



            System.out.println("\n------ ISSUE DETAILS ------");



            while(rs.next()){


                System.out.println(
                        "Issue ID : "
                                + rs.getInt("issue_id")
                );


                System.out.println(
                        "Book Name : "
                                + rs.getString("book_name")
                );


                System.out.println(
                        "Member Name : "
                                + rs.getString("member_name")
                );


                System.out.println(
                        "Issue Date : "
                                + rs.getDate("issue_date")
                );


                System.out.println(
                        "Return Date : "
                                + rs.getDate("return_date")
                );


                System.out.println(
                        "Status : "
                                + rs.getString("status")
                );


                System.out.println("--------------------------");

            }



            con.close();



        }
        catch(Exception e){

            System.out.println(e.getMessage());

        }


    }





    // Display only issued books


    public void displayOnlyIssued(){


        try{


            Connection con =
                    MySqlConnection.getConnection();



            String query =
                    "SELECT * FROM issue_book WHERE status='Issued'";



            PreparedStatement ps =
                    con.prepareStatement(query);



            ResultSet rs =
                    ps.executeQuery();



            System.out.println("\nCurrently Issued Books");



            while(rs.next()){


                System.out.println(
                        "Issue ID : "
                                +rs.getInt("issue_id")
                );


                System.out.println(
                        "Book ID : "
                                +rs.getInt("book_id")
                );


                System.out.println(
                        "Member ID : "
                                +rs.getInt("member_id")
                );


                System.out.println(
                        "Date : "
                                +rs.getDate("issue_date")
                );


                System.out.println("----------------");

            }



            con.close();


        }
        catch(Exception e){

            System.out.println(e.getMessage());

        }


    }





    // Display returned books


    public void displayReturnedBooks(){


        try{


            Connection con =
                    MySqlConnection.getConnection();



            String query =
                    "SELECT * FROM issue_book WHERE status='Returned'";



            PreparedStatement ps =
                    con.prepareStatement(query);



            ResultSet rs =
                    ps.executeQuery();



            System.out.println("\nReturned Books");



            while(rs.next()){


                System.out.println(
                        "Issue ID : "
                                +rs.getInt("issue_id")
                );


                System.out.println(
                        "Book ID : "
                                +rs.getInt("book_id")
                );


                System.out.println(
                        "Member ID : "
                                +rs.getInt("member_id")
                );


                System.out.println(
                        "Return Date : "
                                +rs.getDate("return_date")
                );


                System.out.println("----------------");

            }



            con.close();



        }
        catch(Exception e){

            System.out.println(e.getMessage());

        }


    }


}