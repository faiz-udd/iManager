import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class connect {
    //Just for learning purpose Here are the steps for JDBC connection
    //1. Register the Database Driver class
    //2. Create the connection String
    //3. Creating Statements
    //4. Running the Sql queries
    //5. Closing the Sql Connections


    // Creating the Object of the Connection Class
    Connection c;
    //Create an Object for the Statement Class


    Statement s;
    //Creating the statement for the sql Queries

    public connect(){
        try{
            //Registering the Database Driver Class
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Setting the Connection
            c= DriverManager.getConnection("jdbc:mysql:///employeemanagementsystem", "root", "4565");

            //Creating the Statement
            s=c.createStatement();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
