import com.toedter.calendar.JDateChooser;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Random;

import static javax.swing.SwingUtilities.invokeLater;

public class Home extends JFrame implements ActionListener {

    private JPanel mainPanel, topBarPanel, sideBarPanel,panel;
    TextField tfName, tfFName, tfsalary, tfAddress, tfPhoneNumebr,tfDesignation, tfEmail, tfCNIC, tfEducation;
    JDateChooser choseDOB;
    JComboBox<String> ddEduction, cEmpDesignation;
    Random randomNumber = new Random();
    int number = randomNumber.nextInt(999999);
    JLabel lblEmpID;
    JButton AddEmployeeDetailsBtn, UpdateEmpDetailsBtn, SearchEmpBtn, printEmpBtn, updateEmpBtn, deleteEmpBtn, AddProjectBtn;
    JButton pSearchBtn, pRemoveBtn;
    TextField tfProjectID, tfProjectTitle, tfProjectManager;
    JDateChooser pChoseStartDate, pChoseDueDate;
    JComboBox<String> Project_Status;
    String employeeID;

    JTable table;
    Choice cEmployeeID, cProjectID;//One for Updating and one for Deleting

    JButton rSearchBtn;
    Choice cSearchEmpLeaveRequest;
    String LeaveRequestEmp;
    JComboBox<String> Approved;
    String LeaveApproval;
    String Admin;

    //Update Class Constructor
    public Home(String AdminName) {
        this.Admin = AdminName;
        //Set Title
        setTitle("Admin Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLayout(new BorderLayout());

        //Create Different BarPanels

        createSideBar();
        createTopBar();
        createMainPanel();

        //Add them to Main JFrame

        add(sideBarPanel, BorderLayout.WEST);
        add(topBarPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }// Class Constructor
    private void createTopBar() {
        // Top bar with name and email address
        topBarPanel = new JPanel(null);
        topBarPanel.setBackground(new Color(51, 51, 51)); // Set top bar background color
        topBarPanel.setBounds(600, 0, 1000, 40);

        // Replace placeholders with actual user details
        JLabel nameLabel = new JLabel(Admin+": WELCOME TO EMS");
        nameLabel.setFont(new Font("Monotype Corsiva", Font.BOLD | Font.ITALIC, 30));
        nameLabel.setForeground(Color.WHITE);

        topBarPanel.add(nameLabel);
        topBarPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topBarPanel.add(Box.createRigidArea(new Dimension(300, 0))); // Add spacing
    }//TopBar Welcome Panel
    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        //Main Dashboard View
        DashBoard();
    } //Main Panel where contents are displayed Dynamically
    private void createSideBar() {
        sideBarPanel = new JPanel();
        sideBarPanel.setLayout(new BoxLayout(sideBarPanel, BoxLayout.Y_AXIS));
        sideBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        sideBarPanel.setBackground(new Color(51, 51, 51)); // Set background color

        String[] buttonLabels = {"Projects","Add New Project","Add Employee", "View and Update", "Remove Employee","Leave Requests","Log Out"};
        for (String label : buttonLabels) {
            JButton button = createButton(label);
            sideBarPanel.add(button);
            sideBarPanel.add(Box.createRigidArea(new Dimension(10, 10))); // Add spacing between buttons
        }

    }//Side Bar Panel for Holding the Button
    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addActionListener(new SideBarButtonClickListener());
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
        button.setForeground(Color.WHITE); // Set text color
        button.setBackground(new Color(51, 51, 51)); // Set background color
        button.setBorder(new Splash.RoundedBorder(15));
        return button;
    } //Creating Button in the siderbarPanel
    private void DashBoard() {
        mainPanel.setLayout(null);

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("Icons/icon.jpg"));
        Image image = imageIcon.getImage().getScaledInstance(200,200, Image.SCALE_DEFAULT);
        ImageIcon iMge = new ImageIcon(image);
        JLabel Img = new JLabel(iMge);
        mainPanel.add(Img);
        mainPanel.setVisible(true);

        JLabel Instructions = new JLabel("Let's Do");
        Instructions.setBounds(200, 80, 600, 50);
        Instructions.setFont(new Font("Monotype Corsiva", Font.PLAIN, 30));
        mainPanel.add(Instructions);

        // Create HTML-formatted text for bullet points
        String businessWisdomText = "<html>" +
                "<ul>" +
                "<li> Set Clear Business Objectives for the Team.\t</li>" +
                "<li> Foster a Culture of Innovation and Continuous Improvement.\n</li>" +
                "<li> Communicate Effectively with Team Members and Stakeholders.\n</li>" +
                "<li> Encourage Professional Development and Learning Opportunities.\n</li>" +
                "</ul>" +
                "</html>";


        // Create a JLabel to display bullet points
        JLabel bulletPointsLabel = new JLabel(businessWisdomText);
        bulletPointsLabel.setBounds(150, 120, 1000, 200);
        bulletPointsLabel.setFont(new Font("MonoType Corsiva", Font.PLAIN, 25));
        // Add the JLabel to the JFrame
        mainPanel.add(bulletPointsLabel);


    } //Dashboard View, This will be shown in the main panel, we can include what ever we want here
    private void ViewProjects() {
            mainPanel.setLayout(null);

            //Create Instance of the Table to view task Details
            table = new JTable();
            mainPanel.add(table);

            // Label
            // for search box
            JLabel searchLbl = new JLabel("Search By Project ID");
            searchLbl.setBounds(10, 80, 110, 20);
            mainPanel.add(searchLbl);

            //Choose Among different Tasks
            cProjectID = new Choice();
            cProjectID.setBounds(140, 80, 130, 20);
            mainPanel.add(cProjectID);

            //Try Fetching Data from Database
            try {
                //database Connection
                connect con = new connect();
                String Query = "Select * from project";
                ResultSet rs = con.s.executeQuery(Query);

                //load EmployeeID Dynamically
                while (rs.next()) {
                    cProjectID.add(rs.getString("ID"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                //database Connection
                connect con = new connect();
                String Query = "Select * from project";
                ResultSet rs = con.s.executeQuery(Query);

                //Now to set the result in a table
                table.setModel(DbUtils.resultSetToTableModel(rs));

            } catch (Exception e) {
                e.printStackTrace();
            }

            //Scroll the table  if It is large, Pass the object of the table which you want to put scroll on
            JScrollPane Jsp = new JScrollPane(table);
            //resize the table
            Jsp.setBounds(0, 150, 1000, 400);
            mainPanel.add(Jsp);


            //Search Button for searching Tasks by ID
            pSearchBtn = new JButton("Search");
            pSearchBtn.setBounds(310, 80, 80, 20);
            pSearchBtn.addActionListener(new ProjectViewButtonClickListener());
            mainPanel.add(pSearchBtn);

            //For Updating the Employee data
            pRemoveBtn = new JButton("Remove");
            pRemoveBtn.setBounds(310, 100, 80, 20);
            pRemoveBtn.addActionListener(new ProjectViewButtonClickListener());
            mainPanel.add(pRemoveBtn);
    }//View all the projects which have been assigned or created sofar
    private void AddProjects(){

        //Write Heading or Label of the Page
        JLabel Heading = new JLabel("Project Detail");
        Heading.setBounds(50, 20, 150, 40);
        Heading.setFont(new Font("Monotype Corsiva", Font.BOLD, 25));
        mainPanel.add(Heading);

        //Form for Employee Details
        //Label and TextField for Task ID
        JLabel lblTaskID =new JLabel("Project ID");
        lblTaskID.setBounds(20, 100, 50, 20);
        mainPanel.add(lblTaskID);

         tfProjectID = new TextField();
        tfProjectID.setBounds(75, 100, 80, 20);
        mainPanel.add(tfProjectID);


        //Label and TxtField for task Title
        JLabel TaskTitle =new JLabel("Title");
        TaskTitle.setBounds(160, 100, 40, 20);
        mainPanel.add(TaskTitle);

        //Father's Name TextField
        tfProjectTitle = new TextField();
        tfProjectTitle.setBounds(200, 100, 150, 20);
        mainPanel.add(tfProjectTitle);

        //Project Start Date
        JLabel StartDate =new JLabel("Create Date");
        StartDate.setBounds(355, 100, 100, 20);
        mainPanel.add(StartDate);

        //Choose the Date of Start Date
        pChoseStartDate=  new JDateChooser();
        pChoseStartDate.setBounds(430, 100, 100, 20);
        mainPanel.add(pChoseStartDate);


        //Project Due Date
        JLabel DueDate =new JLabel("Due Date");
        DueDate.setBounds(530, 100, 80, 20);
        mainPanel.add(DueDate);

        //Choose the Date of Start Date
        pChoseDueDate=  new JDateChooser();
        pChoseDueDate.setBounds(600, 100, 100, 20);
        mainPanel.add(pChoseDueDate);

        //Assignne, Which employee will have the project
        JLabel Assignee = new JLabel("Project Manger");
        Assignee.setBounds(20, 150, 100, 20);
        mainPanel.add(Assignee);

        //textField

        tfProjectManager = new TextField();
        tfProjectManager.setBounds(130, 150, 100, 20);
        mainPanel.add(tfProjectManager);


        //Task Status /by the way it should be Defualt_Todo
        JLabel lblStatus = new JLabel("Status");
        lblStatus.setBounds(240, 150, 50, 20);
        mainPanel.add(lblStatus);

        //textfield
        String [] courses = {"TODO","Completed","Pending"};
        Project_Status= new JComboBox<>(courses);
        Project_Status.setBounds(300, 150, 100, 20);
        mainPanel.add(Project_Status);


        AddProjectBtn = new JButton("Add Project");
        AddProjectBtn.setBounds(100, 200, 150, 30);
        AddProjectBtn.setBorder(new Splash.RoundedBorder(15));
        AddProjectBtn.setBackground(new Color(173, 216, 230));
        AddProjectBtn.addActionListener(new ProjectViewButtonClickListener());
        mainPanel.add(AddProjectBtn);
    } //Add New projects and Assign them to Managers
    private void AddEmployee() {
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.white);

        //Write Heading or Label of the Page
        JLabel Heading = new JLabel("Add Employee Detail");
        Heading.setBounds(200, 20, 400, 40);
        Heading.setFont(new Font("Monotype Corsiva", Font.BOLD, 35));
        mainPanel.add(Heading);

        //Form for Employee Details
        JLabel lblUserName = new JLabel("Name");
        lblUserName.setBounds(50, 100, 50, 30);
        mainPanel.add(lblUserName);

        //UserName TextField
        tfName = new TextField();
        tfName.setBounds(100, 100, 300, 30);
        mainPanel.add(tfName);


        //Father's Name
        JLabel lblFName = new JLabel("Father's Name");
        lblFName.setBounds(450, 100, 100, 30);
        mainPanel.add(lblFName);

        //Father's Name TextField
        tfFName = new TextField();
        tfFName.setBounds(550, 100, 300, 30);
        mainPanel.add(tfFName);

        //DOB
        JLabel lblDob = new JLabel("DOB");
        lblDob.setBounds(50, 150, 50, 30);
        mainPanel.add(lblDob);

        //Choose the Date of Birth
        choseDOB = new JDateChooser();
        choseDOB.setBounds(100, 150, 300, 30);
        mainPanel.add(choseDOB);

        // Employee Salary
        JLabel lblSalary = new JLabel("Salary");
        lblSalary.setBounds(450, 150, 100, 30);
        mainPanel.add(lblSalary);

        //TextField of Salary
        tfsalary = new TextField();
        tfsalary.setBounds(550, 150, 300, 30);
        mainPanel.add(tfsalary);

        //Employee Address
        JLabel lblAddress = new JLabel("Address");
        lblAddress.setBounds(50, 200, 50, 30);
        mainPanel.add(lblAddress);

        //TextField of Phone address
        tfAddress = new TextField();
        tfAddress.setBounds(100, 200, 300, 30);
        mainPanel.add(tfAddress);

        //Employee Phone Number
        JLabel lblPhoneNumebr = new JLabel("Contact");
        lblPhoneNumebr.setBounds(450, 200, 100, 30);
        mainPanel.add(lblPhoneNumebr);

        //textfield
        tfPhoneNumebr = new TextField();
        tfPhoneNumebr.setBounds(550, 200, 300, 30);
        mainPanel.add(tfPhoneNumebr);

        //Employee Email
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(50, 250, 50, 30);
        mainPanel.add(lblEmail);

        //textfield
        tfEmail = new TextField();
        tfEmail.setBounds(100, 250, 300, 30);
        mainPanel.add(tfEmail);

        //Employee Education
        JLabel lblEducationLevel = new JLabel("Qualification");
        lblEducationLevel.setBounds(450, 250, 100, 30);
        mainPanel.add(lblEducationLevel);

        //textfield
        String[] courses = {"Matric", "FSc", "BS", "BA", "MS", "M.Phil", "PhD", "PostDoc"};
        ddEduction = new JComboBox<>(courses);
        ddEduction.setBounds(550, 250, 300, 30);
        mainPanel.add(ddEduction);

        //CNIC Number of the Employee
        JLabel CNIC = new JLabel("CNIC");
        CNIC.setBounds(50, 300, 50, 30);
        mainPanel.add(CNIC);

        //textfield
        tfCNIC = new TextField();
        tfCNIC.setBounds(100, 300, 300, 30);
        mainPanel.add(tfCNIC);

        //Employee ID
        JLabel lblEmployeeID = new JLabel("Employee ID");
        lblEmployeeID.setBounds(450, 300, 50, 30);
        mainPanel.add(lblEmployeeID);

        //textfield
        lblEmpID = new JLabel("" + number);
        lblEmpID.setBounds(550, 300, 300, 30);
        mainPanel.add(lblEmpID);


        //Employee Designation
        JLabel lblDesignation = new JLabel("Designation");
        lblDesignation.setBounds(50, 350, 100, 30);
        mainPanel.add(lblDesignation);

        //Choose Desination
        String[] Designation = {"Manager","Employee"};
        cEmpDesignation = new JComboBox<>(Designation);
        cEmpDesignation.setBounds(150, 350, 250, 30);
        mainPanel.add(cEmpDesignation);

        //Add Two buttons

        AddEmployeeDetailsBtn = new JButton("Add Employee");
        AddEmployeeDetailsBtn.setBounds(700, 400, 120, 30);
        AddEmployeeDetailsBtn.setBorder(new Splash.RoundedBorder(15));
        AddEmployeeDetailsBtn.setBackground(new Color(173, 216, 230));
        AddEmployeeDetailsBtn.addActionListener(new EmpViewButtonClickListener());
        mainPanel.add(AddEmployeeDetailsBtn);



    }//  //Add New Employee Adding
    private void ViewEmployee() {
        //set the defualt layout to null
        mainPanel.setLayout(null);

        //Table Object to store the data
        table = new JTable();

        // Lable for search box
        JLabel searchLbl = new JLabel("Search Employee by ID");
        searchLbl.setBounds(20, 20, 150, 20);
        mainPanel.add(searchLbl);


        //Search Box
        cEmployeeID = new Choice();
        cEmployeeID.setBounds(180, 20, 150, 20);
        mainPanel.add(cEmployeeID);

        try {
            //database Connection
            connect con = new connect();
            String Query = "Select * from employee";
            ResultSet rs = con.s.executeQuery(Query);

            //load EmployeeID Dynamically
            while (rs.next()) {
                cEmployeeID.add(rs.getString("employeid"));
            }

        } catch (Exception e) {
            e.getStackTrace();
        }


        try {
            //database Connection
            connect con = new connect();
            String Query = "Select * from employee";
            ResultSet rs = con.s.executeQuery(Query);

            //Now to set the result in a table
            table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            e.getStackTrace();
        }
        //Scroll the data if It is large, Pass the object of the table which you want to put scroll on
        JScrollPane Jsp = new JScrollPane(table);
        Jsp.setBounds(50, 100, 900, 500);
        mainPanel.add(Jsp);


        //Search Button for searching Employees by ID
        SearchEmpBtn = new JButton("Search");
        SearchEmpBtn.setBounds(20, 70, 80, 20);
        SearchEmpBtn.addActionListener(new EmpViewButtonClickListener());
        mainPanel.add(SearchEmpBtn);

        //Print Button for Printing Employees Data
        printEmpBtn = new JButton("Print");
        printEmpBtn.setBounds(120, 70, 80, 20);
        printEmpBtn.addActionListener(new EmpViewButtonClickListener());
        mainPanel.add(printEmpBtn);

        //For Updating the Employee data
        updateEmpBtn = new JButton("Update");
        updateEmpBtn.setBounds(220, 70, 80, 20);
        updateEmpBtn.addActionListener(new ActionListener() {
            JPanel panel= new JPanel();
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(panel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                UpdateEmployee(cEmployeeID.getSelectedItem());
            }
        });
        mainPanel.add(updateEmpBtn);
    }//View all the Employee
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals("updateEmpBtn")){
            mainPanel.removeAll();
            mainPanel.revalidate();
            mainPanel.repaint();
            UpdateEmployee(cEmployeeID.getSelectedItem());
        }
    }//Action Listener Implemented to perform action on Button Click
    private void UpdateEmployee(String employeid){
        this.employeeID = employeid;
         final TextField tfFName, tfsalary, tfAddress,tfEducation, tfPhoneNumebr, tfEmail, tfDesignation;
         final JLabel lblEmpID;

        //set the background
        setLayout(null);

        //Write Heading or Label of the Page
        JLabel Heading = new JLabel("Update Employee Detail");
        Heading.setBounds(420, 20, 300, 40);
        Heading.setFont(new Font("MonoType Corsiva", Font.BOLD, 25));
        mainPanel.add(Heading);

        //Form for Employee Details
        JLabel lblName =new JLabel("Name");
        lblName.setBounds(50, 150, 150, 30);
        mainPanel.add(lblName);

        //UserName TextField
        JLabel lbltfName = new JLabel();
        lbltfName.setBounds(200, 150, 150, 30);
        mainPanel.add(lbltfName);


        //Father's Name
        JLabel lblFName =new JLabel("Father's Name");
        lblFName.setBounds(400, 150, 150, 30);
        mainPanel.add(lblFName);

        //Father's Name TextField
        tfFName = new TextField();
        tfFName.setBounds(600, 150, 150, 30);
        mainPanel.add(tfFName);

        //DOB
        JLabel lblDob =new JLabel("Date of Birth");
        lblDob.setBounds(50, 200, 150, 30);
        mainPanel.add(lblDob);

        //Choose the Date of Birth
        JLabel lbltfDOB=  new JLabel();
        lbltfDOB.setBounds(200, 200, 150, 30);
        mainPanel.add(lbltfDOB);

        // Employee Salary
        JLabel lblSalary = new JLabel("Salary");
        lblSalary.setBounds(400, 200, 150,30);
        mainPanel.add(lblSalary);

        //TextField of Salary
        tfsalary = new TextField();
        tfsalary.setBounds(600, 200, 150, 30);
        mainPanel.add(tfsalary);

        //Employee Address
        JLabel lblAddress = new JLabel("Address");
        lblAddress.setBounds(50, 250, 150,30);
        mainPanel.add(lblAddress);

        //TextField of Phone address
        tfAddress = new TextField();
        tfAddress.setBounds(200, 250, 150, 30);
        mainPanel.add(tfAddress);

        //Employee Phone Number
        JLabel lblPhoneNumebr = new JLabel("Phone Number");
        lblPhoneNumebr.setBounds(400, 250, 150, 30);
        mainPanel.add(lblPhoneNumebr);

        //textfield
        tfPhoneNumebr = new TextField();
        tfPhoneNumebr.setBounds(600, 250, 150, 30);
        mainPanel.add(tfPhoneNumebr);

        //Employee Email
        JLabel lblEmail = new JLabel("Email Address");
        lblEmail.setBounds(50, 300, 150, 30);
        mainPanel.add(lblEmail);

        //textfield
        tfEmail = new TextField();
        tfEmail.setBounds(200, 300, 150, 30);
        mainPanel.add(tfEmail);

        //Employee Education
        JLabel lblEducationLevel = new JLabel("Highest Education Level");
        lblEducationLevel.setBounds(400, 300, 150, 30);
        mainPanel.add(lblEducationLevel);

        //textfield
        tfEducation = new TextField();
        tfEducation.setBounds(600, 300, 150, 30);
        mainPanel.add(tfEducation);

        //CNIC Number of the Employee
        JLabel CNIC = new JLabel("CNIC");
        CNIC.setBounds(50, 350, 150, 30);
        mainPanel.add(CNIC);

        //textfield
        JLabel lbltfCNIC = new JLabel();
        lbltfCNIC.setBounds(200, 350, 150, 30);
        mainPanel.add(lbltfCNIC);

        //Employee ID
        JLabel  lblEmployeeID= new JLabel("Employee ID");
        lblEmployeeID.setBounds(400, 350, 150, 30);
        mainPanel.add(lblEmployeeID);

        //textfield
        lblEmpID= new JLabel();
        lblEmpID.setBounds(600, 350, 150, 30);
        mainPanel.add(lblEmpID);


        //Employee Designation
        JLabel lblDesignation = new JLabel("Designation");
        lblDesignation.setBounds(50, 400, 150, 30);
        mainPanel.add(lblDesignation);

        //textfield
        tfDesignation = new TextField();
        tfDesignation.setBounds(200, 400, 150, 30);
        mainPanel.add(tfDesignation);

        //Update the Employee Details
        try {
            //Employees_Files.connect to DB
            connect con = new connect();
            String query = "select * from employee where employeid = '"+employeid+"'";
            ResultSet rs = con.s.executeQuery(query);

            //while we have values from the select employeeid
            while(rs.next()){
                lbltfName.setText(rs.getString("name"));
                tfFName.setText(rs.getString("fname"));
                lbltfDOB.setText(rs.getString("dob"));
                tfsalary.setText(rs.getString("salary"));
                tfAddress.setText(rs.getString("address"));
                tfPhoneNumebr.setText(rs.getString("phonenumber"));
                tfEmail.setText(rs.getString("email"));
                tfEducation.setText(rs.getString("education"));
                lbltfCNIC.setText(rs.getString("cnic"));
                lblEmpID.setText(rs.getString("employeid"));
                tfDesignation.setText(rs.getString("designation"));

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        //Add Two buttons
        UpdateEmpDetailsBtn = new JButton("Update Details");
        UpdateEmpDetailsBtn.setBounds(600, 400, 120, 30);
        UpdateEmpDetailsBtn.setBorder(new Splash.RoundedBorder(15));
        UpdateEmpDetailsBtn.setBackground(new Color(173, 216, 230));
        UpdateEmpDetailsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Perform the Adding details the Database
                //First Get the given inputs first
                String fName = tfFName.getText();
                String Salary = tfsalary.getText();
                String Address = tfAddress.getText();
                String PhoneNumber = tfPhoneNumebr.getText();
                String Email = tfEmail.getText();
                String Education = tfEducation.getText();
                String EmployeeID = lblEmpID.getText();
                String Designation = tfDesignation.getText();
                try {

                    //Connection Class Object
                    connect con = new connect();

                    //Sql Query to Insert Values into the Table
                    String Query = "update employee set fname='" + fName + "', salary='" + Salary + "', address='" + Address + "', phonenumber='" + PhoneNumber + "',email='" + Email + "',education ='" + Education + "',designation = '" + Designation + "' where employeid='" + EmployeeID + "'";
                    //execute the above query
                    con.s.executeUpdate(Query);
                    JOptionPane.showMessageDialog(null, " Details Updated Successfully");
                    SwingUtilities.invokeLater(() -> {
                        panel = new JPanel();
                        mainPanel.removeAll();
                        mainPanel.add(panel, BorderLayout.CENTER);
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        ViewEmployee();
                    });


                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        });
        mainPanel.add(UpdateEmpDetailsBtn);


    } //Update each Employee Based on Their ID
    private void RemoveEmployee() {
        //set the Layout to Null
        mainPanel.setLayout(null);

        //Employee Label
        JLabel lblEmployeeID = new JLabel("Employee ID");
        lblEmployeeID.setBounds(50, 50, 100, 30);
        mainPanel.add(lblEmployeeID);

        //textfield
        cEmployeeID = new Choice();
        cEmployeeID.setBounds(200, 50, 150, 30);
        mainPanel.add(cEmployeeID);


        try {
            connect con = new connect();
            String query = "select * from employee";
            ResultSet rs = con.s.executeQuery(query);
            while (rs.next()) {
                cEmployeeID.add(rs.getString("employeid"));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        JLabel lblName = new JLabel("Name");
        lblName.setBounds(50, 100, 100, 30);
        mainPanel.add(lblName);

        JLabel LabelName = new JLabel();
        LabelName.setBounds(150, 100, 100, 30);
        mainPanel.add(LabelName);

        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setBounds(50, 130, 100, 30);
        mainPanel.add(lblPhone);

        JLabel LabelPhone = new JLabel();
        LabelPhone.setBounds(150, 130, 100, 30);
        mainPanel.add(LabelPhone);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(50, 160, 100, 30);
        mainPanel.add(lblEmail);

        JLabel LabelEmail = new JLabel();
        LabelEmail.setBounds(150, 160, 100, 30);
        mainPanel.add(LabelEmail);

        //Let's Check the values again the selected employe ID
        cEmployeeID.addItemListener(e -> {

            //Let's show Some of the Employee Details Here
            try {
                connect con = new connect();
                String query = "select * from employee where employeid ='"+ cEmployeeID.getSelectedItem()+"'";
                ResultSet rs = con.s.executeQuery(query);
                while (rs.next()) {
                    LabelName.setText(rs.getString("name"));
                    LabelPhone.setText(rs.getString("phonenumber"));
                    LabelEmail.setText(rs.getString("email"));

                }
            } catch (Exception ee) {
                ee.getStackTrace();
            }

        });

        //Delete Button

        deleteEmpBtn = new JButton("Delete");
        deleteEmpBtn.setBounds(80, 300, 100, 30);
        deleteEmpBtn.setBorder(new Splash.RoundedBorder(15));
        deleteEmpBtn.setBackground(new Color(173, 216, 230));
        deleteEmpBtn.addActionListener(new RemEmpButtonClickListener());
        mainPanel.add(deleteEmpBtn);


    } //Remove Employee
    private void LeaveRequests(){
        mainPanel.setLayout(null);
        //Create Instance of the Table to view task Details
        table = new JTable();
        mainPanel.add(table);

        // Label
        // for search box
        JLabel searchLbl = new JLabel("Search Leave Requests");
        searchLbl.setBounds(10, 80, 110, 20);
        mainPanel.add(searchLbl);

        //Choose Among different Tasks
        cSearchEmpLeaveRequest = new Choice();
        cSearchEmpLeaveRequest.setBounds(140, 80, 130, 20);
        mainPanel.add(cSearchEmpLeaveRequest);

        //Try Fetching Data from Database
        try {
            //database Connection
            connect con = new connect();
            String Query = "Select * from leaveapplication";
            ResultSet rs = con.s.executeQuery(Query);

            //load EmployeeID Dynamically
            while (rs.next()) {
                cSearchEmpLeaveRequest.add(rs.getString("EmployeeID"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //database Connection
            connect con = new connect();
            String Query = "Select * from leaveapplication";
            ResultSet rs = con.s.executeQuery(Query);

            //Now to set the result in a table
            table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Scroll the table  if It is large, Pass the object of the table which you want to put scroll on
        JScrollPane Jsp = new JScrollPane(table);
        //resize the table
        Jsp.setBounds(0, 150, 1000, 400);
        mainPanel.add(Jsp);


        //Search Button for searching Tasks by ID
        rSearchBtn = new JButton("Search");
        rSearchBtn.setBounds(310, 80, 80, 20);
        rSearchBtn.addActionListener(new LeaveRequestViewButtonClickListener());
        mainPanel.add(rSearchBtn);

    } //Leave Request Applications
    private void ReviewApplication(String empID){
        JPanel panel = new JPanel();
        this.LeaveRequestEmp = empID;
        //Write Heading or Label of the Page
        JLabel Heading = new JLabel("Leave Requests");
        Heading.setBounds(300, 20, 300, 40);
        Heading.setFont(new Font("MonoType Corsiva", Font.BOLD, 25));
        mainPanel.add(Heading);

        //Form for Employee Details
        //EmpID
        JLabel lblEmpID =new JLabel("ID");
        lblEmpID.setBounds(20, 70, 50, 30);
        mainPanel.add(lblEmpID);

        JLabel empIDField = new JLabel();
        empIDField.setBounds(90, 70, 150, 30);
        mainPanel.add(empIDField);

        //Name
        JLabel lblEmpName =new JLabel("Name");
        lblEmpName.setBounds(20, 100, 50, 30);
        mainPanel.add(lblEmpName);

        JLabel nameField = new JLabel();
        nameField.setBounds(90, 100, 150, 30);
        mainPanel.add(nameField);

        //Leave Type
        JLabel leaveTypeLabel = new JLabel("Leave Type:");
        leaveTypeLabel.setBounds(20, 150, 70, 30);
        mainPanel.add(leaveTypeLabel);

        JLabel tfLeaveType = new JLabel();
        tfLeaveType.setBounds(90, 150, 150, 30);
        mainPanel.add(tfLeaveType);


        //DOS
        JLabel StartDate =new JLabel("Start Date");
        StartDate.setBounds(20, 200, 70, 30);
        mainPanel.add(StartDate);

        //Choose the Date of Start Date
        JLabel cLStartDate=  new JLabel();
        cLStartDate.setBounds(90, 200, 150, 30);
        mainPanel.add(cLStartDate);


        //End Date
        JLabel DuetDate =new JLabel("End Date");
        DuetDate.setBounds(20, 250, 70, 30);
        mainPanel.add(DuetDate);

        //Choose the Date of Start Date
        JLabel cLEndDate=  new JLabel();
        cLEndDate.setBounds(90, 250, 150, 30);
        mainPanel.add(cLEndDate);

        JLabel reasonLabel = new JLabel("Reason:");
        reasonLabel.setBounds(20, 300, 70, 30);
        mainPanel.add(reasonLabel);

        JLabel reasonTextArea = new JLabel();
        reasonTextArea.setBounds(90, 300, 400, 200);
        reasonTextArea.setBackground(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(reasonTextArea);
        mainPanel.add(reasonTextArea);
        mainPanel.add(scrollPane);

        //Label Task Status
        JLabel lblStatus = new JLabel("Approval");
        lblStatus.setBounds(20, 510,70, 30);
        mainPanel.add(lblStatus);

        String[] Approval = {"yes", "no"};
        Approved = new JComboBox<>(Approval);
        Approved.setBounds(75, 510, 150, 30);
        mainPanel.add(Approved);


        //Update the Employee Details
        try {
            //Employees_Files.connect to DB
            connect con = new connect();
            String query = "select * from leaveapplication where EmployeeID = '" +LeaveRequestEmp+"'";
            ResultSet rs = con.s.executeQuery(query);

            //while we have values from the select employeeid
            while (rs.next()) {
                empIDField.setText(rs.getString("EmployeeID"));
                nameField.setText(rs.getString("Name"));
                tfLeaveType.setText(rs.getString("LeaveType"));
                cLStartDate.setText(rs.getString("LstartDate"));
                cLEndDate.setText(rs.getString("LendDate"));
                reasonTextArea.setText(rs.getString("Reason"));
                //After Review ALl this Either your can Approve or Not
                LeaveApproval = (String) Approved.getSelectedItem();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        JButton ApprovalBtn = new JButton("Approve/Reject");
        ApprovalBtn.setBounds(350, 520, 150, 30);
        ApprovalBtn.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
        ApprovalBtn.setBorder(new Splash.RoundedBorder(15));
        ApprovalBtn.setBackground(new Color(173, 216, 230));
        mainPanel.add(ApprovalBtn);
        ApprovalBtn.addActionListener(e -> {
            try{

                //Connection Class Object
                connect con = new connect();
                //Sql Query to Insert Values into the Table
                String Query = "update leaveapplication set Approval='"+Approved.getSelectedItem()+"' where EmployeeID='"+ cSearchEmpLeaveRequest.getSelectedItem()+"'" ;
                //execute the above query
                con.s.executeUpdate(Query);
                if(LeaveApproval.equals("no")){
                    JOptionPane.showMessageDialog(null,"Application Rejected ");
                    mainPanel.removeAll();
                    mainPanel.add(panel, BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    LeaveRequests();
                }else if(LeaveApproval.equals("yes")){
                    JOptionPane.showMessageDialog(null, "Application Accepted");
                    mainPanel.removeAll();
                    mainPanel.add(panel, BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    LeaveRequests();
                }else{
                    new Home("");
                }
            }catch(Exception ee){
                ee.printStackTrace();
            }

        });
    }//Review Application Either Approve or reject
    private class SideBarButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JPanel panel = new JPanel();
            panel.setLayout(null);
            //Let's Create a new Panel Here
            String buttonText = ((JButton) e.getSource()).getText();
            // Replace the content panel in the main panel
            // Replace the content panel in the main panel
            switch (buttonText) {
                case "Projects"->{
                    mainPanel.removeAll();
                    mainPanel.add(panel, BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    ViewProjects();

                }
                case "Add New Project"->{
                    mainPanel.removeAll();
                    mainPanel.add(panel, BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    AddProjects();
                }
                case "Add Employee" -> {
                    mainPanel.removeAll();
                    mainPanel.add(panel, BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    AddEmployee();
                }
                case "View and Update" -> {
                    mainPanel.removeAll();
                    mainPanel.add(panel, BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    ViewEmployee();
                }
                case "Remove Employee" -> {
                    //load the remove button Panel
                    mainPanel.removeAll();
                    mainPanel.add(panel, BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    RemoveEmployee();
                }
                case "Dashboard" -> {
                    mainPanel.removeAll();
                    mainPanel.add(panel, BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    DashBoard();
                }
                case "Leave Requests"->{
                    mainPanel.removeAll();
                    mainPanel.add(panel, BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    LeaveRequests();
                }
                default -> {
                    //log out.
                    setVisible(false);
                    invokeLater(() -> new Splash().setVisible(true));
                }
            }
        }
    } //Click Listeners on Each Button to Load the necessary Panels against each button
    private class EmpViewButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Adding Employee Detail from Add Employee Panel
            if (e.getSource() == AddEmployeeDetailsBtn) {
                //Perform the Adding details the Database
                //First Get the given inputs first
                String Name = tfName.getText();
                String Fname = tfFName.getText();
                String DOB = ((JTextField) choseDOB.getDateEditor().getUiComponent()).getText();
                String Salary = tfsalary.getText();
                String Address = tfAddress.getText();
                String PhoneNumber = tfPhoneNumebr.getText();
                String Email = tfEmail.getText();
                String Education = (String) ddEduction.getSelectedItem();
                String CNIC = tfCNIC.getText();
                String EmpID = lblEmpID.getText();
                String Designation = (String)cEmpDesignation.getSelectedItem();

                try {

                    // Connection Class Object
                    connect con = new connect();

                // Sql Query to Insert Values into the Table
                    if ("Employee".equals(Designation)){
                        String AddEmpQuery = "INSERT INTO employee VALUES ('" + Name + "', '" + Fname + "', '" + DOB + "','" + Salary + "', '" + Address + "','" + PhoneNumber + "','" + Email + "', '" + Education + "','" + CNIC + "','" + EmpID + "','" + Designation + "')";
                        con.s.executeUpdate(AddEmpQuery);
                        JOptionPane.showMessageDialog(null, "Employee Added Successfully");
                        new Home("");
                    } else {
                        String AddManagerQuery = "INSERT INTO manager VALUES ('" +EmpID+ "','"+Name+"', '"+Fname + "', '"+DOB + "','" +Salary + "', '" + Address + "', '" + Education + "','" + PhoneNumber + "','" + Email + "','" + CNIC + "', '" + Designation+"')";
                        con.s.executeUpdate(AddManagerQuery);
                        JOptionPane.showMessageDialog(null, "Manager Added Successfully");
                        setVisible(false);
                        new Home("");
                    }



                } catch (Exception ee) {
                    ee.getStackTrace();
                }


            }//Search Button From EmployeeView Page
            else if (e.getSource() == SearchEmpBtn) {
                String query = "select * from employee where employeid = '"+ cEmployeeID.getSelectedItem()+"'";
                try {
                    connect c = new connect();
                    ResultSet rs = c.s.executeQuery(query);
                    table.setModel(DbUtils.resultSetToTableModel(rs));

                } catch (Exception ae) {
                    ae.getStackTrace();
                }
            }
            //Print Button in EmployeeView Panel
            else if (e.getSource() == printEmpBtn) {
                try {
                    table.print();
                } catch (Exception ee) {
                    ee.getStackTrace();
                }

            }
            //Add New Project
            else{
                    //go Back to the Home page
                    setVisible(false);
                    invokeLater(() -> new Home("").setVisible(true));
                }
            }

       }//EmpView Click Listerner Implemented
    private class RemEmpButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == deleteEmpBtn) {
                try {
                    connect con = new connect();
                    employeeID = cEmployeeID.getSelectedItem();
                    String query = "delete from employee where employeid ='"+ employeeID +"'";
                    con.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Employee removed Successfully");
                    panel = new JPanel();
                    mainPanel.removeAll();
                    mainPanel.add(panel, BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    ViewEmployee();
                } catch (Exception ea) {
                    ea.getStackTrace();
                }

            }
        }
    }//Remove Employee Action Listerners Implemented
    private class ProjectViewButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            JPanel panel = new JPanel();
            panel.setLayout(null);
            //Add New Projects
            if(e.getSource()==AddProjectBtn){
                //Perform the Adding details the Database
                //First Get the given inputs first
                String pID= tfProjectID.getText();
                String pTitle =tfProjectTitle.getText();
                String pStartDate = ((JTextField) pChoseStartDate.getDateEditor().getUiComponent()).getText();
                String pDueDate= ((JTextField) pChoseDueDate.getDateEditor().getUiComponent()).getText();
                String pStatus = (String) Project_Status.getSelectedItem();
                String pManager =tfProjectManager.getText();

                try{

                    //Connection Class Object
                    connect con = new connect();

                    //Sql Query to Insert Values into the Table
                    String Query = "insert into project values('"+pID+"', '"+pTitle+"', '"+pStartDate+"','"+pDueDate+"', '"+pManager+"','"+pStatus+"')";
                    //execute the above query
                    con.s.executeUpdate(Query);
                    JOptionPane.showMessageDialog(null," Details Added Successfully");
                    SwingUtilities.invokeLater(() -> {
                            mainPanel.removeAll();
                            mainPanel.add(panel, BorderLayout.CENTER);
                            mainPanel.revalidate();
                            mainPanel.repaint();

                    });


                }catch(Exception ee){
                    ee.printStackTrace();
                }
            }
            //Search the Project
            else if(e.getSource()==pSearchBtn){
                String query = "select * from project where ID= '"+cProjectID.getSelectedItem()+"'";
                try{
                    connect c= new connect();
                    ResultSet rs = c.s.executeQuery(query);
                    table.setModel(DbUtils.resultSetToTableModel(rs));

                }catch (Exception ae){
                    ae.printStackTrace();
                }
            }else if (e.getSource()==pRemoveBtn) {
                try {
                    connect con = new connect();
                    String projectID = cProjectID.getSelectedItem().toString();
                    // Check if there are tasks related to the project
                    String checkQuery = "SELECT * FROM tasks WHERE ProjectID = '" + projectID + "'";
                    if (con.s.executeQuery(checkQuery).next()) {
                        JOptionPane.showMessageDialog(null, "Remove Tasks related to this project first");
                    } else {
                        // No tasks related, proceed with project deletion
                        String deleteQuery = "DELETE FROM project WHERE ID = '" + projectID + "'";
                        con.s.executeUpdate(deleteQuery);
                        JOptionPane.showMessageDialog(null, "Project Removed");
                    }
                } catch (Exception ea) {
                    ea.printStackTrace();
                }



            }else{
                setVisible(false);
                new Home("");
            }
        }


    }//Projects view Action Listener Implemented
    private class LeaveRequestViewButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            JPanel panel = new JPanel();
            if(e.getSource()==rSearchBtn){
                mainPanel.removeAll();
                mainPanel.add(panel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
               ReviewApplication(cSearchEmpLeaveRequest.getSelectedItem());
            }
        }
    }
//    public static void main(String[] args)
//    {
//            invokeLater(() -> new Home("").setVisible(true));
//    }
}

