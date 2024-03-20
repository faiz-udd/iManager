import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;


public class ManagerLogin extends JFrame implements ActionListener {

    TextField tfUserName;
    JPasswordField  tfPassword;
    ManagerLogin(){

            //set the background
            getContentPane().setBackground(Color.WHITE);
            setLayout(null);


            //TextLabel and TextField for UserName
            JLabel lblUserName = new JLabel("User Name");
            lblUserName.setBounds(40,20, 100, 30);
            add(lblUserName);

            tfUserName = new TextField();
            tfUserName.setBounds(150,20, 150, 30);
            add(tfUserName);

            //TextLabel and TextField For User Password
            JLabel lblPassword = new JLabel("Password");
            lblPassword.setBounds(40,70, 100, 30);
            add(lblPassword);

            tfPassword = new JPasswordField();
            tfPassword.setBounds(150,70, 150, 30);
            add(tfPassword);

            //Set a Login Image
            ImageIcon icon1 = new ImageIcon(ClassLoader.getSystemResource("Icons/LoginIcon.jpg"));
            Image Image2 = icon1.getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT);
            ImageIcon Image3 = new ImageIcon(Image2);
            JLabel Image = new JLabel(Image3);
            Image.setBounds(350, 0, 200,200);
            add(Image);

            //Set Button at the End
            JButton Button = new JButton("Login");
            Button.setBounds(150, 140, 150,30);
            Button.addActionListener(this);
            Button.setBorder(new Splash.RoundedBorder(15));
            Button.setBackground(new Color(173, 216, 230));
            add(Button);



            //set the Location and size of the Frame
            setSize(600, 300);
            setLocation(450, 200);
            setVisible(true);


        }
//        public  static void main(String[] args){
//            new ManagerLogin();
//        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String UserName =tfUserName.getText();
                if(UserName==null){
                    JOptionPane.showMessageDialog(null, "User Name is empty");
                }
                String UserPassword = tfPassword.getText();
                if(UserPassword==null){
                    JOptionPane.showMessageDialog(null, "User Password is Empty");
                }

                //Connection Class Object
                connect c = new connect();

                //Sql Query to Check the Login Credentials
                String query = "select * from Manager where Name ='"+UserName+"' and ManagerID = '"+UserPassword+"'";

                //Execute the above Query by calling it using the Employees_Files.connect object through StatementObject
                ResultSet rs= c.s.executeQuery(query);
                //Check if There is any value in RS
                if(rs.next()){
                    setVisible(false);
                    //Open the Home Page
                    SwingUtilities.invokeLater(() -> {
                        new ManagerHome(UserName).setVisible(true);
                    });

                }else{
                    JOptionPane.showMessageDialog(null, "Invalid Credentials");
                    setVisible(false);
                }
            }catch (Exception ee){
                ee.printStackTrace();
            }

        }
    }
