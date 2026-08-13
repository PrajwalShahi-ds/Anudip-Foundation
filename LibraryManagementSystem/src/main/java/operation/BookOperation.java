package operation;

import connection.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class BookOperation {

    Scanner sc = new Scanner(System.in);


    // 1. Add Book
    public void addBook() {

        try {

            Connection con = MySqlConnection.getConnection();

            System.out.print("Enter Book Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Author Name: ");
            String author = sc.nextLine();

            System.out.print("Enter Publisher Name: ");
            String publisher = sc.nextLine();

            System.out.print("Enter Price: ");
            double price = sc.nextDouble();

            System.out.print("Enter Quantity: ");
            int quantity = sc.nextInt();
            sc.nextLine();


            String query =
                    "INSERT INTO book(book_name,author_name,publisher,price,quantity) VALUES(?,?,?,?,?)";


            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, author);
            ps.setString(3, publisher);
            ps.setDouble(4, price);
            ps.setInt(5, quantity);


            int result = ps.executeUpdate();


            if(result > 0)
                System.out.println("Book Added Successfully");


            con.close();


        } catch(Exception e){

            System.out.println(e.getMessage());

        }

    }




    // 2. Display Books
    public void displayBooks(){

        try{

            Connection con = MySqlConnection.getConnection();


            String query="SELECT * FROM book";


            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();


            System.out.println("\n------ BOOK DETAILS ------");


            while(rs.next()){

                System.out.println("Book ID : " + rs.getInt("book_id"));
                System.out.println("Book Name : " + rs.getString("book_name"));
                System.out.println("Author : " + rs.getString("author_name"));
                System.out.println("Publisher : " + rs.getString("publisher"));
                System.out.println("Price : " + rs.getDouble("price"));
                System.out.println("Quantity : " + rs.getInt("quantity"));
                System.out.println("-------------------------");

            }


            con.close();


        }catch(Exception e){

            System.out.println(e.getMessage());

        }

    }




    // 3. Search Book
    public void searchBook(){

        try{


            Connection con = MySqlConnection.getConnection();


            System.out.print("Enter Book ID: ");
            int id=sc.nextInt();



            String query="SELECT * FROM book WHERE book_id=?";


            PreparedStatement ps=con.prepareStatement(query);

            ps.setInt(1,id);


            ResultSet rs=ps.executeQuery();



            if(rs.next()){


                System.out.println("Book Found");

                System.out.println("Book ID : "+rs.getInt("book_id"));
                System.out.println("Book Name : "+rs.getString("book_name"));
                System.out.println("Author : "+rs.getString("author_name"));
                System.out.println("Publisher : "+rs.getString("publisher"));
                System.out.println("Price : "+rs.getDouble("price"));
                System.out.println("Quantity : "+rs.getInt("quantity"));


            }
            else{

                System.out.println("Book Not Found");

            }


            con.close();


        }catch(Exception e){

            System.out.println(e.getMessage());

        }

    }




    // 4. Update Book
    public void updateBook(){


        try{


            Connection con=MySqlConnection.getConnection();


            System.out.print("Enter Book ID to Update: ");
            int id=sc.nextInt();
            sc.nextLine();



            System.out.print("Enter New Book Name: ");
            String name=sc.nextLine();


            System.out.print("Enter New Author Name: ");
            String author=sc.nextLine();


            System.out.print("Enter New Publisher: ");
            String publisher=sc.nextLine();


            System.out.print("Enter New Price: ");
            double price=sc.nextDouble();


            System.out.print("Enter New Quantity: ");
            int quantity=sc.nextInt();



            String query=
                    "UPDATE book SET book_name=?,author_name=?,publisher=?,price=?,quantity=? WHERE book_id=?";



            PreparedStatement ps=con.prepareStatement(query);



            ps.setString(1,name);
            ps.setString(2,author);
            ps.setString(3,publisher);
            ps.setDouble(4,price);
            ps.setInt(5,quantity);
            ps.setInt(6,id);



            int result=ps.executeUpdate();



            if(result>0)
                System.out.println("Book Updated Successfully");
            else
                System.out.println("Book ID Not Found");



            con.close();



        }catch(Exception e){

            System.out.println(e.getMessage());

        }


    }




    // 5. Delete Book
    public void deleteBook(){


        try{


            Connection con=MySqlConnection.getConnection();


            System.out.print("Enter Book ID to Delete: ");
            int id=sc.nextInt();



            String query="DELETE FROM book WHERE book_id=?";


            PreparedStatement ps=con.prepareStatement(query);


            ps.setInt(1,id);



            int result=ps.executeUpdate();



            if(result>0)
                System.out.println("Book Deleted Successfully");
            else
                System.out.println("Book Not Found");



            con.close();



        }catch(Exception e){

            System.out.println(e.getMessage());

        }

    }


}