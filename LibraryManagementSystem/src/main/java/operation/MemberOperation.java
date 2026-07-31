package operation;

import connection.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class MemberOperation {

    Scanner sc = new Scanner(System.in);


    // 1. Add Member
    public void addMember() {

        try {

            Connection con = MySqlConnection.getConnection();


            System.out.print("Enter Member Name: ");
            String name = sc.nextLine();


            System.out.print("Enter Email: ");
            String email = sc.nextLine();


            System.out.print("Enter Mobile Number: ");
            String mobile = sc.nextLine();


            System.out.print("Enter Address: ");
            String address = sc.nextLine();



            String query =
                    "INSERT INTO member(member_name,email,mobile,address) VALUES(?,?,?,?)";


            PreparedStatement ps = con.prepareStatement(query);


            ps.setString(1,name);
            ps.setString(2,email);
            ps.setString(3,mobile);
            ps.setString(4,address);



            int result = ps.executeUpdate();



            if(result > 0)
                System.out.println("Member Added Successfully");



            con.close();


        }
        catch(Exception e){

            System.out.println(e.getMessage());

        }

    }





    // 2. Display Members
    public void displayMembers(){


        try {


            Connection con = MySqlConnection.getConnection();


            String query="SELECT * FROM member";


            PreparedStatement ps=con.prepareStatement(query);


            ResultSet rs=ps.executeQuery();



            System.out.println("\n------ MEMBER DETAILS ------");



            while(rs.next()){


                System.out.println("Member ID : "+rs.getInt("member_id"));

                System.out.println("Name : "+rs.getString("member_name"));

                System.out.println("Email : "+rs.getString("email"));

                System.out.println("Mobile : "+rs.getString("mobile"));

                System.out.println("Address : "+rs.getString("address"));

                System.out.println("---------------------------");


            }


            con.close();


        }
        catch(Exception e){

            System.out.println(e.getMessage());

        }

    }







    // 3. Search Member
    public void searchMember(){


        try{


            Connection con=MySqlConnection.getConnection();


            System.out.print("Enter Member ID: ");
            int id=sc.nextInt();



            String query="SELECT * FROM member WHERE member_id=?";



            PreparedStatement ps=con.prepareStatement(query);


            ps.setInt(1,id);



            ResultSet rs=ps.executeQuery();



            if(rs.next()){


                System.out.println("Member Found");


                System.out.println("ID : "+rs.getInt("member_id"));

                System.out.println("Name : "+rs.getString("member_name"));

                System.out.println("Email : "+rs.getString("email"));

                System.out.println("Mobile : "+rs.getString("mobile"));

                System.out.println("Address : "+rs.getString("address"));


            }
            else{


                System.out.println("Member Not Found");


            }



            con.close();


        }
        catch(Exception e){

            System.out.println(e.getMessage());

        }


    }








    // 4. Update Member
    public void updateMember(){


        try{


            Connection con=MySqlConnection.getConnection();



            System.out.print("Enter Member ID to Update: ");
            int id=sc.nextInt();

            sc.nextLine();



            System.out.print("Enter New Name: ");
            String name=sc.nextLine();



            System.out.print("Enter New Email: ");
            String email=sc.nextLine();



            System.out.print("Enter New Mobile: ");
            String mobile=sc.nextLine();



            System.out.print("Enter New Address: ");
            String address=sc.nextLine();




            String query=
                    "UPDATE member SET member_name=?,email=?,mobile=?,address=? WHERE member_id=?";



            PreparedStatement ps=con.prepareStatement(query);



            ps.setString(1,name);

            ps.setString(2,email);

            ps.setString(3,mobile);

            ps.setString(4,address);

            ps.setInt(5,id);



            int result=ps.executeUpdate();



            if(result>0)

                System.out.println("Member Updated Successfully");

            else

                System.out.println("Member Not Found");



            con.close();



        }
        catch(Exception e){

            System.out.println(e.getMessage());

        }

    }








    // 5. Delete Member
    public void deleteMember(){


        try{


            Connection con=MySqlConnection.getConnection();



            System.out.print("Enter Member ID to Delete: ");

            int id=sc.nextInt();




            String query="DELETE FROM member WHERE member_id=?";



            PreparedStatement ps=con.prepareStatement(query);



            ps.setInt(1,id);




            int result=ps.executeUpdate();




            if(result>0)

                System.out.println("Member Deleted Successfully");

            else

                System.out.println("Member Not Found");



            con.close();



        }
        catch(Exception e){

            System.out.println(e.getMessage());

        }

    }


}