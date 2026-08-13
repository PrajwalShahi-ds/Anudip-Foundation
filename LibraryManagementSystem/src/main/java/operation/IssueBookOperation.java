package operation;

import connection.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class IssueBookOperation {


    Scanner sc = new Scanner(System.in);



    // Issue Book

    public void issueBook(){


        try{


            Connection con = MySqlConnection.getConnection();



            System.out.print("Enter Book ID: ");
            int bookId = sc.nextInt();



            System.out.print("Enter Member ID: ");
            int memberId = sc.nextInt();



            // Check book quantity

            String checkBook =
                    "SELECT quantity FROM book WHERE book_id=?";


            PreparedStatement ps1 =
                    con.prepareStatement(checkBook);


            ps1.setInt(1,bookId);


            ResultSet rs = ps1.executeQuery();



            if(rs.next()){


                int quantity = rs.getInt("quantity");


                if(quantity <= 0){

                    System.out.println("Book Not Available");
                    return;

                }



                // Insert issue record

                String insert =
                        "INSERT INTO issue_book(book_id,member_id,issue_date,status) VALUES(?,?,CURDATE(),'Issued')";



                PreparedStatement ps2 =
                        con.prepareStatement(insert);



                ps2.setInt(1,bookId);

                ps2.setInt(2,memberId);



                ps2.executeUpdate();




                // Reduce quantity


                String update =
                        "UPDATE book SET quantity=quantity-1 WHERE book_id=?";



                PreparedStatement ps3 =
                        con.prepareStatement(update);



                ps3.setInt(1,bookId);



                ps3.executeUpdate();



                System.out.println("Book Issued Successfully");


            }

            else{

                System.out.println("Book ID Not Found");

            }



            con.close();



        }
        catch(Exception e){

            System.out.println(e.getMessage());

        }


    }







    // Return Book


    public void returnBook(){


        try{


            Connection con =
                    MySqlConnection.getConnection();



            System.out.print("Enter Issue ID: ");

            int issueId=sc.nextInt();




            // Find book id


            String find =
                    "SELECT book_id FROM issue_book WHERE issue_id=?";



            PreparedStatement ps1 =
                    con.prepareStatement(find);



            ps1.setInt(1,issueId);



            ResultSet rs =
                    ps1.executeQuery();



            if(rs.next()){


                int bookId =
                        rs.getInt("book_id");




                // update issue status


                String updateIssue =
                        "UPDATE issue_book SET status='Returned', return_date=CURDATE() WHERE issue_id=?";



                PreparedStatement ps2 =
                        con.prepareStatement(updateIssue);



                ps2.setInt(1,issueId);



                ps2.executeUpdate();




                // increase quantity


                String updateBook =
                        "UPDATE book SET quantity=quantity+1 WHERE book_id=?";



                PreparedStatement ps3 =
                        con.prepareStatement(updateBook);



                ps3.setInt(1,bookId);



                ps3.executeUpdate();



                System.out.println("Book Returned Successfully");


            }

            else{

                System.out.println("Issue ID Not Found");

            }



            con.close();



        }
        catch(Exception e){

            System.out.println(e.getMessage());

        }


    }



}