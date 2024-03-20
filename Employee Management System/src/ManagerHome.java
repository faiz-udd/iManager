import com.toedter.calendar.JDateChooser;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class ManagerHome extends JFrame {
    private JPanel sideBarPanel, topBarPanel, mainPanel, panel;
    //View Tasks Variables
    JTable table;
    Choice cProjectID, cTaskID;
    JButton search, update, UpdateDetailsBtn, ShowTasksButton;
    TextField tfTaskID, tfTaskTitle,tfProjectID, tfAssignee;

    private   JLabel lbltfProject_ID, pLbltfTitle, pLbltfAssignee, pLbltfStartDate, pLblTfDueDate;
    private JComboBox plbltfStatus;
    String project_ID, pStatus;
    private JButton tSearchBtn, tRemoveBtn, AddTasksDetailsBtn;
    JDateChooser ctStartDate, ctDueDate;
    JComboBox Task_Status;

    JTextField nameField, empIDField;
    JComboBox<String> leaveTypeComboBox;
    JDateChooser cLStartDate, cLEndDate;
    JTextArea reasonTextArea;
    JButton submitButton;
    String ManagerName;
    public ManagerHome(String ManagerName) {
        this.ManagerName = ManagerName;

        //Set Title
        setTitle("Managers Home");
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
    }// ManagerHome Constructor
    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        JLabel Instructions = new JLabel("Do You?");
        Instructions.setBounds(200, 80, 600, 50);
        Instructions.setFont(new Font("serif", Font.PLAIN, 30));
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

    }//This is the MainPanel where All your Contents will be shown
    private void createSideBar() {
        sideBarPanel = new JPanel();
        sideBarPanel.setLayout(new BoxLayout(sideBarPanel, BoxLayout.Y_AXIS));
        sideBarPanel.setBorder(BorderFactory.createEmptyBorder(80, 10, 10, 10)); // Add padding
        sideBarPanel.setBackground(new Color(51, 51, 51)); // Set background color

        String[] buttonLabels = {"Dashboard", "Project","Add Tasks","Apply for Leave", "Leave Status", "Logout"};
        for (String label : buttonLabels) {
            JButton button = createButton(label);
            sideBarPanel.add(button);
            sideBarPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between buttons
        }

    }//Create Side BarPanel, This Panel will help you Hold different Buttons
    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addActionListener(new ButtonClickListener());
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
        button.setForeground(Color.WHITE); // Set text color
        button.setBackground(new Color(51, 51, 51)); // Set background color
        button.setBorder(new Splash.RoundedBorder(15));
        return button;
    }//Create the SiderBarPanel Buttons
    private void createTopBar() {
        // Top bar with name and email address
        topBarPanel = new JPanel(null);
        topBarPanel.setBackground(new Color(51, 51, 51)); // Set top bar background color
        topBarPanel.setBounds(400, 0, 1000, 40);

        // Replace placeholders with actual user details
        JLabel nameLabel = new JLabel("WELCOME:  "+ManagerName);
        nameLabel.setFont(new Font("Monotype Corsiva", Font.BOLD | Font.ITALIC, 30));
        nameLabel.setForeground(Color.WHITE);

        topBarPanel.add(nameLabel);
        topBarPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topBarPanel.add(Box.createRigidArea(new Dimension(300, 0))); // Add spacing
    }//Create TopBar Panel
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Let's Create a new Panel Here
            panel = new JPanel();
            panel.setLayout(null);
            String buttonText = ((JButton) e.getSource()).getText();
            // Replace the content panel in the main panel
            if(buttonText=="Dashboard"){
                mainPanel.removeAll();
                mainPanel.add(panel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                loadDashboard();
            } else if(buttonText=="Project"){
                mainPanel.removeAll();
                mainPanel.add(panel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                loadProjects();

            }else if(buttonText== "Add Tasks"){
                mainPanel.removeAll();
                mainPanel.add(panel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                AddTasks();
            } else if(buttonText=="Apply for Leave"){
                //load the remove button Panel
                mainPanel.removeAll();
                mainPanel.add(panel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                ApplyForLeave();

            }else if(buttonText=="Leave Status") {
                mainPanel.removeAll();
                mainPanel.add(panel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                LeaveStatus();
            }
            else{
                //log out.
                setVisible(false);
                SwingUtilities.invokeLater(() -> {
                    new Splash().setVisible(true);
                });
            }
        }
    }//Button Click Listener to Implement Operations on Button CLick in the Sider bar
    private void loadDashboard() {

        // Create a dashboard panel
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setBackground(new Color(200, 200, 200)); // Set background color
        mainPanel.setLayout(null);

        // Button for tasks with icon and name
        ShowTasksButton = new JButton("Tasks"); // Replace with actual icon
        ImageIcon icon = new ImageIcon("Icons/task_icon.png");
        ShowTasksButton.setIcon(icon);
        ShowTasksButton.setBounds(50, 50,150, 60 );
        ShowTasksButton.setBorder(new Splash.RoundedBorder(15));
        ShowTasksButton.setBackground(new Color(173, 216, 230));
        ShowTasksButton.addActionListener(new TaskViewButtonClickListener());
        mainPanel.add(ShowTasksButton);

    }//DashBoard Panel, This will be loaded when the Dashboard button will be clicked
    private void loadProjects() {
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
            String Query = "Select * from project Where Manager='"+ManagerName+"'";
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
        search = new JButton("Search");
        search.setBounds(310, 80, 80, 20);
        search.addActionListener(new ViewButtonClickListener());
        mainPanel.add(search);

        //For Updating the Employee data
        update = new JButton("Update");
        update.setBounds(310, 100, 80, 20);
        update.addActionListener(new ViewButtonClickListener());
        mainPanel.add(update);
    }//Show all the Projects in the main panel
    public void UpdateProject(String project_ID) {
        this.project_ID = project_ID;
        //Write Heading or Label of the Page
        JLabel Heading = new JLabel("Update Project");
        Heading.setBounds(50, 50, 350, 40);
        Heading.setFont(new Font("MonoType Corsiva", Font.BOLD, 25));
        mainPanel.add(Heading);

        //Form for Task Details
        //Label Task ID
        JLabel lblProjectID = new JLabel("Project ID");
        lblProjectID.setBounds(40, 100, 100, 30);
        mainPanel.add(lblProjectID);

        lbltfProject_ID = new JLabel();
        lbltfProject_ID.setBounds(170, 100, 100, 30);
        mainPanel.add(lbltfProject_ID);

        //Label Task Title
        JLabel lblTitle = new JLabel("Title");
        lblTitle.setBounds(40, 150, 100, 30);
        mainPanel.add(lblTitle);

        pLbltfTitle= new JLabel();
        pLbltfTitle.setBounds(150, 150, 100, 30);
        mainPanel.add(pLbltfTitle);

        //Label Task Status
        JLabel lblStatus = new JLabel("Status");
        lblStatus.setBounds(40, 200, 100, 30);
        mainPanel.add(lblStatus);

        //textfield
        String[] courses = {"Pending", "Completed", "ToDo"};
        plbltfStatus = new JComboBox(courses);
        plbltfStatus.setBounds(150, 200, 100, 30);
        mainPanel.add(plbltfStatus);

        //Label Task Assignee
        JLabel lblAssignee = new JLabel("Manager");
        lblAssignee.setBounds(40, 250, 100, 30);
        mainPanel.add(lblAssignee);

        pLbltfAssignee = new JLabel();
        pLbltfAssignee.setBounds(150, 250, 100, 30);
        mainPanel.add(pLbltfAssignee);

        //Label Start Date
        JLabel lblStartDate = new JLabel("Start Date");
        lblStartDate.setBounds(40, 300, 100, 30);
        mainPanel.add(lblStartDate);

        pLbltfStartDate = new JLabel();
        pLbltfStartDate.setBounds(150, 300, 100, 30);
        mainPanel.add(pLbltfStartDate);

        //Label Start Date
        JLabel lblDueDate = new JLabel("Due Date");
        lblDueDate.setBounds(40, 350, 100, 30);
        mainPanel.add(lblDueDate);

        pLblTfDueDate = new JLabel();
        pLblTfDueDate.setBounds(150, 350, 100, 30);
        mainPanel.add(pLblTfDueDate);


        //Update the Employee Details
        try {
            //Employees_Files.connect to DB
            connect con = new connect();
            String query = "select * from project where ID = '" +project_ID+"'";
            ResultSet rs = con.s.executeQuery(query);

            //while we have values from the select project
            while (rs.next()) {
                lbltfProject_ID.setText(rs.getString("ID"));
                pLbltfTitle.setText(rs.getString("Title"));
                pLbltfAssignee.setText(rs.getString("Manager"));
                pLbltfStartDate.setText(rs.getString("StartDate"));
                pLblTfDueDate.setText(rs.getString("DueDate"));
                pStatus = (String) plbltfStatus.getSelectedItem();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Add Two buttons
        UpdateDetailsBtn = new JButton("Update Project Details");
        UpdateDetailsBtn.setBounds(350, 450, 250, 50);
        UpdateDetailsBtn.setBorder(new Splash.RoundedBorder(15));
        UpdateDetailsBtn.setBackground(new Color(173, 216, 230));
        UpdateDetailsBtn.addActionListener(new ViewButtonClickListener());
        mainPanel.add(UpdateDetailsBtn);
    }//Update the projects, whether they are done or Not
    private void AddTasks(){

        //Write Heading or Label of the Page
        JLabel Heading = new JLabel("Task Detail");
        Heading.setBounds(50, 20, 150, 40);
        Heading.setFont(new Font("MonoType Corsiva", Font.BOLD, 25));
        mainPanel.add(Heading);

        //Form for Employee Details
        //Label and TextField for Task ID
        JLabel lblTaskID =new JLabel("Task ID");
        lblTaskID.setBounds(20, 100, 50, 30);
        mainPanel.add(lblTaskID);

        tfTaskID = new TextField();
        tfTaskID.setBounds(75, 100, 100, 30);
        mainPanel.add(tfTaskID);


        //Label and TxtField for task Title
        JLabel TaskTitle =new JLabel("Title");
        TaskTitle.setBounds(180, 100, 50, 30);
        mainPanel.add(TaskTitle);

        //Father's Name TextField
        tfTaskTitle = new TextField();
        tfTaskTitle.setBounds(240, 100, 100, 30);
        mainPanel.add(tfTaskTitle);

        //DOS
        JLabel StartDate =new JLabel("Create Date");
        StartDate.setBounds(355, 100, 100, 30);
        mainPanel.add(StartDate);

        //Choose the Date of Start Date
        ctStartDate=  new JDateChooser();
        ctStartDate.setBounds(460, 100, 100, 30);
        mainPanel.add(ctStartDate);


        //End Date
        JLabel DuetDate =new JLabel("Due Date");
        DuetDate.setBounds(20, 150, 100, 30);
        mainPanel.add(DuetDate);

        //Choose the Date of Start Date
        ctDueDate=  new JDateChooser();
        ctDueDate.setBounds(130, 150, 100, 30);
        mainPanel.add(ctDueDate);

        //Task Status /by the way it should be Defualt_Todo
        JLabel lblStatus = new JLabel("Status");
        lblStatus.setBounds(240, 150, 50, 30);
        mainPanel.add(lblStatus);

        //textfield
        String [] courses = {"TODO","Completed","Pending"};
        Task_Status= new JComboBox(courses);
        Task_Status.setBounds(300, 150, 100, 30);
        mainPanel.add(Task_Status);

        //Assignne, Which employee will have the project
        JLabel Assignee = new JLabel("Assignee");
        Assignee.setBounds(410, 150, 100, 30);
        mainPanel.add(Assignee);

        //textfield
        tfAssignee = new TextField();
        tfAssignee.setBounds(520, 150, 100, 30);
        mainPanel.add(tfAssignee);

        //Assignne, Which employee will have the project
        JLabel lblProjectID = new JLabel("Project ID");
        lblProjectID.setBounds(630, 150, 100, 30);
        mainPanel.add(lblProjectID);

        //textfield
        tfProjectID = new TextField();
        tfProjectID.setBounds(740, 150, 100, 30);
        mainPanel.add(tfProjectID);

        AddTasksDetailsBtn = new JButton("Add Details");
        AddTasksDetailsBtn.setBounds(400, 200, 150, 30);
        AddTasksDetailsBtn.setBorder(new Splash.RoundedBorder(15));
        AddTasksDetailsBtn.setBackground(new Color(173, 216, 230));
        AddTasksDetailsBtn.addActionListener(new TaskViewButtonClickListener());
        mainPanel.add(AddTasksDetailsBtn);
    } //Divide each Project into small tasks and Assign to employees
    private void ApplyForLeave() {

        //Write Heading or Label of the Page
        JLabel Heading = new JLabel("Leave Application");
        Heading.setBounds(300, 20, 300, 40);
        Heading.setFont(new Font("MonoType Corsiva", Font.BOLD, 25));
        mainPanel.add(Heading);

        //Form for Employee Details
        //EmpID
        JLabel lblEmpID =new JLabel("ID");
        lblEmpID.setBounds(20, 70, 50, 30);
        mainPanel.add(lblEmpID);

        empIDField = new JTextField();
        empIDField.setBounds(90, 70, 150, 30);
        mainPanel.add(empIDField);

        //Name
        JLabel lblEmpName =new JLabel("Name");
        lblEmpName.setBounds(20, 100, 50, 30);
        mainPanel.add(lblEmpName);

        nameField = new JTextField();
        nameField.setBounds(90, 100, 150, 30);
        mainPanel.add(nameField);

        //Leave Type
        JLabel leaveTypeLabel = new JLabel("Leave Type:");
        leaveTypeLabel.setBounds(20, 150, 70, 30);
        mainPanel.add(leaveTypeLabel);

        String[] leaveTypes = {"Vacation", "Sick Leave", "Personal Leave", "Other"};
        leaveTypeComboBox = new JComboBox<>(leaveTypes);
        leaveTypeComboBox.setBounds(90, 150, 150, 30);
        mainPanel.add(leaveTypeComboBox);


        //DOS
        JLabel StartDate =new JLabel("Start Date");
        StartDate.setBounds(20, 200, 70, 30);
        mainPanel.add(StartDate);

        //Choose the Date of Start Date
        cLStartDate=  new JDateChooser();
        cLStartDate.setBounds(90, 200, 150, 30);
        mainPanel.add(cLStartDate);


        //End Date
        JLabel DuetDate =new JLabel("End Date");
        DuetDate.setBounds(20, 250, 70, 30);
        mainPanel.add(DuetDate);

        //Choose the Date of Start Date
        cLEndDate=  new JDateChooser();
        cLEndDate.setBounds(90, 250, 150, 30);
        mainPanel.add(cLEndDate);

        JLabel reasonLabel = new JLabel("Reason:");
        reasonLabel.setBounds(20, 300, 70, 30);
        mainPanel.add(reasonLabel);


        reasonTextArea = new JTextArea();
        reasonTextArea.setBounds(90, 300, 400, 200);
        reasonTextArea.setBackground(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(reasonTextArea);
        mainPanel.add(reasonTextArea);
        mainPanel.add(scrollPane);


        submitButton = new JButton("Submit");
        submitButton.setBounds(180, 520, 80, 30);
        submitButton.setBorder(new Splash.RoundedBorder(15));
        submitButton.setBackground(new Color(173, 216, 230));
        mainPanel.add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitForm();
            }
        });

    } //Apply for Leave, Manager can request Leave to the admin
    private void submitForm() {
        // Retrieve and process the form data
        String empID = empIDField.getText();
        String name = nameField.getText();
        String leaveType = (String) leaveTypeComboBox.getSelectedItem();
        String startDate =  ((JTextField) cLStartDate.getDateEditor().getUiComponent()).getText();
        String endDate =  ((JTextField) cLEndDate.getDateEditor().getUiComponent()).getText();
        String reason = reasonTextArea.getText();
        String approved = "no";

        try{
            //Connection Class Object
            connect con = new connect();

            //Sql Query to Insert Values into the Table
            String Query = "insert into leaveapplication values('"+empID+"', '"+name+"', '"+leaveType+"','"+startDate+"', '"+endDate+"','"+reason+"','"+approved+"')";
            //execute the above query
            con.s.executeUpdate(Query);
            JOptionPane.showMessageDialog(null,"Leave Application Submitted Successfully");
            setVisible(false);
            new ManagerHome("");

        }catch(Exception ee){
            ee.printStackTrace();
        }

    }//SubmitForm Method, to submit the application Form
    private void LeaveStatus(){
        table =new JTable();
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
        Jsp.setBounds(80, 50, 800, 500);
        mainPanel.add(Jsp);
    }
    private class ViewButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Logic to handle view button clicks
            // Update mainPanel with different views based on the clicked button
            // This could involve removing current components and adding new ones
            if(e.getSource()==search){
                String query = "select * from project where ID = '"+cProjectID.getSelectedItem()+"'";
                try{
                    connect c= new connect();
                    ResultSet rs = c.s.executeQuery(query);
                    table.setModel(DbUtils.resultSetToTableModel(rs));

                }catch (Exception ae){
                    ae.printStackTrace();
                }
            }
            //Update Tasks
            else if (e.getSource()==update) {
                mainPanel.removeAll();
                mainPanel.add(panel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                UpdateProject(cProjectID.getSelectedItem());

            }
            //Add Details of the Task
            else if(e.getSource()==UpdateDetailsBtn){

                try{

                    //Connection Class Object
                    connect con = new connect();
                    //Sql Query to Insert Values into the Table
                    String Query = "update project set Status='"+plbltfStatus.getSelectedItem()+"' where ID='"+cProjectID.getSelectedItem()+"'" ;
                    //execute the above query
                    con.s.executeUpdate(Query);
                    JOptionPane.showMessageDialog(null," Details Updated Successfully");
                    mainPanel.removeAll();
                    mainPanel.add(panel, BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    loadProjects();


                }catch(Exception ee){
                    ee.printStackTrace();
                }

            }
            else{
                loadDashboard();
            }
        }
    } //Here the buttons which are inside the main view are Handled
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            new ManagerHome ("").setVisible(true);
//
//        });
//    } //Main Function, Starting point of the Panel
    private class TaskViewButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            JPanel panel = new JPanel();
            panel.setLayout(null);
            //Add New Projects
            if(e.getSource()==ShowTasksButton){
                mainPanel.removeAll();
                mainPanel.add(panel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                //
                mainPanel.setLayout(null);

                //Create Instance of the Table to view task Details
                table = new JTable();
                mainPanel.add(table);

                // Label
                // for search box
                JLabel searchLbl = new JLabel("Search By Task ID");
                searchLbl.setBounds(10, 80, 110, 20);
                mainPanel.add(searchLbl);

                //Choose Among different Tasks
                cTaskID = new Choice();
                cTaskID.setBounds(140, 80, 130, 20);
                mainPanel.add(cTaskID);

                //Try Fetching Data from Database
                try {
                    //database Connection
                    connect con = new connect();
                    String Query = "Select * from Tasks";
                    ResultSet rs = con.s.executeQuery(Query);

                    //load EmployeeID Dynamically
                    while (rs.next()) {
                        cTaskID.add(rs.getString("TaskID"));
                    }

                } catch (Exception ae) {
                    ae.printStackTrace();
                }
                try {
                    //database Connection
                    connect con = new connect();
                    String Query = "Select * from tasks";
                    ResultSet rs = con.s.executeQuery(Query);

                    //Now to set the result in a table
                    table.setModel(DbUtils.resultSetToTableModel(rs));

                } catch (Exception eee) {
                    eee.printStackTrace();
                }

                //Scroll the table  if It is large, Pass the object of the table which you want to put scroll on
                JScrollPane Jsp = new JScrollPane(table);
                //resize the table
                Jsp.setBounds(0, 150, 1000, 400);
                mainPanel.add(Jsp);


                //Search Button for searching Tasks by ID
                tSearchBtn = new JButton("Search");
                tSearchBtn.setBounds(310, 80, 80, 20);
                tSearchBtn.addActionListener(new TaskViewButtonClickListener());
                mainPanel.add(tSearchBtn);

                //For Updating the Employee data
                tRemoveBtn = new JButton("Remove");
                tRemoveBtn.setBounds(310, 100, 80, 20);
                tRemoveBtn.addActionListener(new TaskViewButtonClickListener());
                mainPanel.add(tRemoveBtn);
            }
            //Add Task Details
            else if(e.getSource()==AddTasksDetailsBtn){
                //Perform the Adding details the Database
                //First Get the given inputs first
                String tID= tfTaskID.getText();
                String tTitle =tfTaskTitle.getText();
                String tStartDate = ((JTextField) ctStartDate.getDateEditor().getUiComponent()).getText();
                String tDueDate= ((JTextField) ctDueDate.getDateEditor().getUiComponent()).getText();
                String tStatus = (String)Task_Status.getSelectedItem();
                String tAssignee =tfAssignee.getText();
                String pID = tfProjectID.getText();

                try{

                    //Connection Class Object
                    connect con = new connect();

                    //Sql Query to Insert Values into the Table
                    String Query = "insert into tasks values('"+tID+"', '"+tTitle+"','"+tStatus+"', '"+tAssignee+"', '"+tStartDate+"','"+tDueDate+"', '"+pID+"')";
                    //execute the above query
                    con.s.executeUpdate(Query);
                    JOptionPane.showMessageDialog(null," Details Added Successfully");
                    mainPanel.removeAll();
                    mainPanel.add(panel, BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    loadDashboard();


                }catch(Exception ee){
                    ee.printStackTrace();
                }
            }
            //Search the Project
            else if(e.getSource()==tSearchBtn){
                String query = "select * from tasks where TaskID= '"+cTaskID.getSelectedItem()+"'";
                try{
                    connect c= new connect();
                    ResultSet rs = c.s.executeQuery(query);
                    table.setModel(DbUtils.resultSetToTableModel(rs));

                }catch (Exception ae){
                    ae.printStackTrace();
                }
            }else if (e.getSource()==tRemoveBtn) {
                try {
                    connect con = new connect();
                        String deleteQuery = "DELETE FROM tasks WHERE TaskID = '" +cTaskID.getSelectedItem()+ "'";
                        con.s.executeUpdate(deleteQuery);
                        JOptionPane.showMessageDialog(null, "Task Removed");
                } catch (Exception ea) {
                    ea.printStackTrace();
                }



            }else{
                setVisible(false);
                new Home("");
            }
        }

    }// Buttons in the TaskView are Handled here
}
