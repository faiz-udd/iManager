import com.toedter.calendar.JDateChooser;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class EmployeeHome extends JFrame {
    private JPanel sideBarPanel;
    private JPanel topBarPanel;
    private static JPanel mainPanel;
    private JPanel panel;
    //View Tasks Variables
    static JTable table;
    Choice TaskID;
    JButton search, update, UpdateDetails;
    private JButton AddDetails;
    String task_ID, status;

    JTextField nameField, empIDField;
    JComboBox<String> leaveTypeComboBox;
    JDateChooser cLStartDate, cLEndDate;
    JTextArea reasonTextArea;
    JButton submitButton;
    String EmployeeName;
    public EmployeeHome(String EmployeeName) {
        this.EmployeeName = EmployeeName;
        setTitle("Employee Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLayout(new BorderLayout());

        createSideBar();
        createTopBar();
        createMainPanel();

        add(sideBarPanel, BorderLayout.WEST);
        add(topBarPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    } //Main Constructor
    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        JLabel Instructions = new JLabel("YOU MUST");
        Instructions.setBounds(200, 80, 600, 50);
        Instructions.setFont(new Font("serif", Font.PLAIN, 30));
        mainPanel.add(Instructions);

        // Create HTML-formatted text for motivational words
        String motivationalText = "<html>" +
                "<ul>" +
                "<li> Believe in Yourself and Your Abilities.\t</li>" +
                "<li> Embrace Challenges as Opportunities for Growth.\n</li>" +
                "<li> Your Hard Work Today Sets the Path for Success Tomorrow.\n</li>" +
                "<li> Celebrate Small Wins and Keep Moving Forward.\n</li>" +
                "</ul>" +
                "</html>";

        // Create a JLabel to display bullet points
        JLabel bulletPointsLabel = new JLabel(motivationalText);
        bulletPointsLabel.setBounds(150, 120, 1000, 200);
        bulletPointsLabel.setFont(new Font("MonoType Corsiva", Font.PLAIN, 25));
        // Add the JLabel to the JFrame
        mainPanel.add(bulletPointsLabel);

    }//Main Panel where all the Operations are Shown
    private void createSideBar() {
        sideBarPanel = new JPanel();
        sideBarPanel.setLayout(new BoxLayout(sideBarPanel, BoxLayout.Y_AXIS));
        sideBarPanel.setBorder(BorderFactory.createEmptyBorder(80, 10, 10, 10)); // Add padding
        sideBarPanel.setBackground(new Color(51, 51, 51)); // Set background color

        String[] buttonLabels = {"Dashboard", "Tasks", "Apply for Leave", "Leave Status", "Logout"};
        for (String label : buttonLabels) {
            JButton button = createButton(label);
            sideBarPanel.add(button);
            sideBarPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between buttons
        }

    } //SideBar where out Buttons will be Shown
    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addActionListener(new ButtonClickListener());
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
        button.setForeground(Color.WHITE); // Set text color
        button.setBorder(new Splash.RoundedBorder(15));
        button.setBackground(new Color(51, 51, 51)); // Set background color
        return button;
    } //Button on the Side Bar
    private void createTopBar() {
        topBarPanel = new JPanel();
        //topBarPanel.setPreferredSize(new Dimension(getWidth(), 50));
        topBarPanel.setBackground(new Color(51, 51, 51)); // Set background color

        JLabel welcomeLabel = new JLabel("Welcome: "+ EmployeeName);
        welcomeLabel.setFont(new Font("Monotype Corsiva", Font.BOLD | Font.ITALIC, 30));
        welcomeLabel.setForeground(Color.WHITE);


        topBarPanel.add(welcomeLabel);
    } //TopBar Panel to Mention Name of the Person Who Logged in
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Let's Create a new Panel Here
            panel = new JPanel();
            panel.setLayout(null);
            String buttonText = ((JButton) e.getSource()).getText();
            // Replace the content panel in the main panel
            if("Dashboard".equals(buttonText)){
                mainPanel.removeAll();
                mainPanel.add(panel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                DashBoard();
            } else if("Tasks".equals(buttonText)){
                mainPanel.removeAll();
                mainPanel.add(panel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                LoadTasks();

            }else if("Apply for Leave".equals(buttonText)){
                //load the remove button Panel
                mainPanel.removeAll();
                mainPanel.add(panel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                ApplyForLeave();

            }else if("Leave Status".equals(buttonText)) {
                mainPanel.removeAll();
                mainPanel.add(panel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                LeaveStatus();
            }
            else{
                //log out.
                setVisible(false);
                SwingUtilities.invokeLater(() -> new Splash().setVisible(true));
            }
        }
    } //Private Button ClickListern Class for the Sidebar Button
    public void DashBoard(){
        // Button for tasks with icon and name
        JButton tasksButton = new JButton("Tasks", new ImageIcon("task_icon.png")); // Replace with actual icon
        tasksButton.setBounds(50, 50,100, 50 );
        tasksButton.setBorder(new Splash.RoundedBorder(15));
        tasksButton.setBackground(new Color(173, 216, 230));
        mainPanel.add(tasksButton);
        tasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                mainPanel.add(panel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
                LoadTasks();
            }
        });
    } //Main Dashboard to show the Tasks
    private void LoadTasks() {
        mainPanel.setLayout(null);

        //Create Instance of the Table to view task Details
        table = new JTable();
        mainPanel.add(table);

        // Label
        // for search box
        JLabel searchLbl = new JLabel("Search By Tasks ID");
        searchLbl.setBounds(10, 80, 110, 20);
        mainPanel.add(searchLbl);

        //Choose Among different Tasks
        TaskID = new Choice();
        TaskID.setBounds(140, 80, 130, 20);
        mainPanel.add(TaskID);

        //Try Fetching Data from Database
        try {
            //database Connection
            connect con = new connect();
            String Query = "Select * from tasks";
            ResultSet rs = con.s.executeQuery(Query);

            //load EmployeeID Dynamically
            while (rs.next()) {
                TaskID.add(rs.getString("TaskID"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //database Connection
            connect con = new connect();
            String Query = "Select * from tasks where assignee ='"+EmployeeName+"'";
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
    }//Load all the Task given to the Employee
    private void UpdateTasks(String task_ID) {
        this.task_ID = task_ID;
         JLabel lbltfAssignee;
         JLabel lblTfStartDate;
         JLabel lbltfProjectID;
        //Write Heading or Label of the Page
        JLabel Heading = new JLabel("Update Task");
        Heading.setBounds(50, 50, 350, 40);
        Heading.setFont(new Font("MonoType Corsiva", Font.BOLD, 25));
        mainPanel.add(Heading);

        //Form for Task Details
        //Label Task ID
        JLabel lblTaskID = new JLabel("Task ID");
        lblTaskID.setBounds(50, 100, 150, 30);
        mainPanel.add(lblTaskID);

        //Update Task Variables
        JLabel lbltfTaskID = new JLabel();
        lbltfTaskID.setBounds(200, 100, 150, 30);
        mainPanel.add(lbltfTaskID);

        //Label Task Title
        JLabel lblTitle = new JLabel("Title");
        lblTitle.setBounds(50, 150, 150, 30);
        mainPanel.add(lblTitle);

        JLabel lbltfTitle = new JLabel();
        lbltfTitle.setBounds(200, 150, 150, 30);
        mainPanel.add(lbltfTitle);

        //Label Task Status
        JLabel lblStatus = new JLabel("Status");
        lblStatus.setBounds(50, 200, 150, 30);
        mainPanel.add(lblStatus);

        //textfield
        String[] courses = {"Pending", "Completed", "ToDo"};
        JComboBox<String>lbltfStatus = new JComboBox<>(courses);
        lbltfStatus.setBounds(200, 200, 150, 30);
        mainPanel.add(lbltfStatus);

        //Label Task Assignee
        JLabel lblAssignee = new JLabel("Assignee");
        lblAssignee.setBounds(50, 250, 150, 30);
        mainPanel.add(lblAssignee);

        lbltfAssignee = new JLabel();
        lbltfAssignee.setBounds(200, 250, 150, 30);
        mainPanel.add(lbltfAssignee);

        //Label Start Date
        JLabel lblStartDate = new JLabel("Start Date");
        lblStartDate.setBounds(50, 300, 150, 30);
        mainPanel.add(lblStartDate);

        lblTfStartDate = new JLabel();
        lblTfStartDate.setBounds(200, 300, 150, 30);
        mainPanel.add(lblTfStartDate);

        //Label Start Date
        JLabel lblProjectID = new JLabel("Project ID");
        lblProjectID.setBounds(50, 350, 150, 30);
        mainPanel.add(lblProjectID);

        lbltfProjectID = new JLabel();
        lbltfProjectID.setBounds(200, 350, 150, 30);
        mainPanel.add(lbltfProjectID);


        //Update the Employee Details
        try {
            //Employees_Files.connect to DB
            connect con = new connect();
            String query = "select * from tasks where taskID = '" +task_ID+"'";
            ResultSet rs = con.s.executeQuery(query);

            //while we have values from the select employeeid
            while (rs.next()) {
                lbltfTaskID.setText(rs.getString("TaskID"));
                lbltfTitle.setText(rs.getString("TaskTitle"));
                status = (String) lbltfStatus.getSelectedItem();
                lbltfAssignee.setText(rs.getString("Assignee"));
                lblTfStartDate.setText(rs.getString("StartDate"));
                lbltfProjectID.setText(rs.getString("ProjectID"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Add  buttons
        UpdateDetails = new JButton("Update Details");
        UpdateDetails.setBounds(350, 450, 150, 50);
        UpdateDetails.setBorder(new Splash.RoundedBorder(15));
        UpdateDetails.setBackground(new Color(173, 216, 230));
        UpdateDetails.addActionListener(new ViewButtonClickListener());
        mainPanel.add(UpdateDetails);
        UpdateDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    //Connection Class Object
                    connect con = new connect();

                    //Sql Query to Insert Values into the Table
                    String Query = "update tasks set Status='"+lbltfStatus.getSelectedItem()+"' where TaskID='"+TaskID.getSelectedItem()+"'" ;
                    //execute the above query
                    con.s.executeUpdate(Query);
                    JOptionPane.showMessageDialog(null," Details Updated Successfully");
                    SwingUtilities.invokeLater(() -> {
                        mainPanel.removeAll();
                        mainPanel.add(panel, BorderLayout.CENTER);
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        LoadTasks();
                    });


                }catch(Exception ee){
                    ee.printStackTrace();
                }
            }
        });
    }//Update the tasks Status as per what you have done
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
                String query = "select * from tasks where TaskID = '"+TaskID.getSelectedItem()+"'";
                try{
                    connect c= new connect();
                    ResultSet rs = c.s.executeQuery(query);
                    table.setModel(DbUtils.resultSetToTableModel(rs));

                }catch (Exception ae){
                    ae.printStackTrace();
                }
            }else if (e.getSource()==update) {
                mainPanel.removeAll();
                mainPanel.add(panel, BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();

                UpdateTasks(TaskID.getSelectedItem());

            }else{
                DashBoard();
            }
        }
    }//perform Click Listener on all button in each View Panels
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                new EmployeeHome("").setVisible(true);
//            }
//        });
//    }//Main Function
}
