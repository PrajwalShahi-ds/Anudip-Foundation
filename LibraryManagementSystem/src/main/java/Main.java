import operation.BookOperation;
import operation.MemberOperation;
import operation.IssueBookOperation;
import operation.DisplayOperation;
import java.util.Scanner;


public class Main {


    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);


        BookOperation book = new BookOperation();
        MemberOperation member = new MemberOperation();
        IssueBookOperation issue = new IssueBookOperation();
        DisplayOperation display = new DisplayOperation();

        while(true) {


            System.out.println("\n========== LIBRARY MANAGEMENT SYSTEM ==========");

            System.out.println("\n----- BOOK MANAGEMENT -----");
            System.out.println("1. Add Book");
            System.out.println("2. Display Books");
            System.out.println("3. Search Book");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");


            System.out.println("\n----- MEMBER MANAGEMENT -----");
            System.out.println("6. Add Member");
            System.out.println("7. Display Members");
            System.out.println("8. Search Member");
            System.out.println("9. Update Member");
            System.out.println("10. Delete Member");


            System.out.println("\n----- ISSUE MANAGEMENT -----");
            System.out.println("11. Issue Book");
            System.out.println("12. Return Book");


            System.out.println("\n----- REPORTS -----");
            System.out.println("13. Display Issue Details");
            System.out.println("14. Display Issued Books");
            System.out.println("15. Display Returned Books");


            System.out.println("\n16. Exit");

            System.out.print("\nEnter Your Choice: ");

            int choice = sc.nextInt();


            switch(choice){

                case 1:
                    book.addBook();
                    break;

                case 2:
                    book.displayBooks();
                    break;

                case 3:
                    book.searchBook();
                    break;

                case 4:
                    book.updateBook();
                    break;

                case 5:
                    book.deleteBook();
                    break;


                case 6:
                    member.addMember();
                    break;

                case 7:
                    member.displayMembers();
                    break;

                case 8:
                    member.searchMember();
                    break;

                case 9:
                    member.updateMember();
                    break;

                case 10:
                    member.deleteMember();
                    break;


                case 11:
                    issue.issueBook();
                    break;

                case 12:
                    issue.returnBook();
                    break;


                case 13:
                    display.displayIssuedBooks();
                    break;

                case 14:
                    display.displayOnlyIssued();
                    break;

                case 15:
                    display.displayReturnedBooks();
                    break;


                case 16:
                    System.out.println("Thank You!");
                    System.exit(0);
                    break;


                default:
                    System.out.println("Invalid Choice");

            }


        }


    }


}