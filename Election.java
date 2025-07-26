package scorner;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Stack;

// Class to represent a Leader with attributes like id, name, party, etc.
class Leader {
    String id;
    String name;
    String party;
    String district;
    String taluka;
    String partyImagePath;
    int voteCount = 0;

    // Constructor to initialize Leader object
    Leader(String id, String name, String party, String taluka, String district, String partyImagePath) {
        this.id = id;
        this.name = name;
        this.party = party;
        this.district = district;
        this.taluka = taluka;
        this.partyImagePath = partyImagePath;
    }
}

// Main class for the Election Management System Frame
public class FrameDemo extends JFrame implements ActionListener {
    // Declare GUI components
    private JTextArea displayArea;
    private JButton existingVoterBtn, newVoterBtn, showLeadersBtn, existingLeaderBtn, newLeaderBtn, 
                    goToVoteBtn, showResultBtn, showVotersBtn, deleteLeaderBtn, emptyTrashBtn, retrieveFromTrashBtn;
    private HashMap<String, Leader> leaders = new HashMap<>();  // A HashMap to store Leader objects using their ID as key
    private VoterBST voterBST = new VoterBST();  // Binary Search Tree to store voters
    private Stack<Leader> trash = new Stack<>();  // Stack to simulate trash functionality for deleted leaders
    private static final long serialVersionUID = 1L;
    private JTextField txtDisplayScreen;

    // Constructor to set up the JFrame and initialize components
    public FrameDemo() {
        // Set up the frame properties
        setTitle("Election Management System");
        setSize(650, 650);  // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit on close
        getContentPane().setLayout(null);  // Use absolute layout for positioning components

        // Title Label
        JLabel titleLabel = new JLabel("Election Management System");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        titleLabel.setForeground(new Color(0, 51, 102));
        titleLabel.setBounds(180, 10, 250, 30);
        getContentPane().add(titleLabel);

        // Leaders Panel
        JPanel leadersPanel = new JPanel();
        leadersPanel.setLayout(null);
        leadersPanel.setBounds(10, 50, 180, 200);
        leadersPanel.setBorder(new LineBorder(Color.BLACK, 2));

        // Label for the Leaders panel
        JLabel leadersLabel = new JLabel("Leaders");
        leadersLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        leadersLabel.setBounds(60, 10, 100, 20);
        leadersPanel.add(leadersLabel);

        // Buttons for the Leaders Panel
        newLeaderBtn = new JButton("New Leader");
        newLeaderBtn.setForeground(new Color(220, 20, 60));
        newLeaderBtn.setBounds(10, 40, 150, 30);
        newLeaderBtn.addActionListener(this);  // Register action listener for button click
        leadersPanel.add(newLeaderBtn);

        existingLeaderBtn = new JButton("Update Leader");
        existingLeaderBtn.setForeground(new Color(220, 20, 60));
        existingLeaderBtn.setBounds(10, 80, 150, 30);
        existingLeaderBtn.addActionListener(this);
        leadersPanel.add(existingLeaderBtn);

        deleteLeaderBtn = new JButton("Delete Leader");
        deleteLeaderBtn.setForeground(new Color(220, 20, 60));
        deleteLeaderBtn.setBounds(10, 120, 150, 30);
        deleteLeaderBtn.addActionListener(this);
        leadersPanel.add(deleteLeaderBtn);

        showLeadersBtn = new JButton("Show All Leaders");
        showLeadersBtn.setForeground(new Color(220, 20, 60));
        showLeadersBtn.setBounds(10, 160, 150, 30);
        showLeadersBtn.addActionListener(this);
        leadersPanel.add(showLeadersBtn);

        getContentPane().add(leadersPanel);  // Add the leaders panel to the frame

        // Voters Panel
        JPanel votersPanel = new JPanel();
        votersPanel.setLayout(null);
        votersPanel.setBounds(210, 50, 180, 200);
        votersPanel.setBorder(new LineBorder(Color.BLACK, 2));

        // Label for the Voters panel
        JLabel votersLabel = new JLabel("Voters");
        votersLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        votersLabel.setBounds(60, 10, 100, 20);
        votersPanel.add(votersLabel);

        // Buttons for the Voters Panel
        newVoterBtn = new JButton("New Voter");
        newVoterBtn.setForeground(new Color(220, 20, 60));
        newVoterBtn.setBounds(10, 40, 150, 30);
        newVoterBtn.addActionListener(this);
        votersPanel.add(newVoterBtn);

        existingVoterBtn = new JButton("Existing Voter");
        existingVoterBtn.setForeground(new Color(220, 20, 60));
        existingVoterBtn.setBounds(10, 80, 150, 30);
        existingVoterBtn.addActionListener(this);
        votersPanel.add(existingVoterBtn);

        showVotersBtn = new JButton("Show All Voters");
        showVotersBtn.setForeground(new Color(220, 20, 60));
        showVotersBtn.setBounds(10, 120, 150, 30);
        showVotersBtn.addActionListener(this);
        votersPanel.add(showVotersBtn);

        getContentPane().add(votersPanel);  // Add the voters panel to the frame

        // Trash Panel
        JPanel trashPanel = new JPanel();
        trashPanel.setLayout(null);
        trashPanel.setBounds(410, 50, 180, 200);
        trashPanel.setBorder(new LineBorder(Color.BLACK, 2));

        // Label for the Trash panel
        JLabel trashLabel = new JLabel("Trash");
        trashLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        trashLabel.setBounds(60, 10, 100, 20);
        trashPanel.add(trashLabel);

        // Buttons for the Trash Panel
        emptyTrashBtn = new JButton("Empty Trash");
        emptyTrashBtn.setForeground(new Color(220, 20, 60));
        emptyTrashBtn.setBounds(10, 40, 150, 30);
        emptyTrashBtn.addActionListener(this);
        trashPanel.add(emptyTrashBtn);

        retrieveFromTrashBtn = new JButton("Retrieve from Trash");
        retrieveFromTrashBtn.setForeground(new Color(220, 20, 60));
        retrieveFromTrashBtn.setBounds(10, 80, 150, 30);
        retrieveFromTrashBtn.addActionListener(this);
        trashPanel.add(retrieveFromTrashBtn);

        getContentPane().add(trashPanel);  // Add the trash panel to the frame

        // Additional Buttons
        goToVoteBtn = new JButton("Go to Vote");
        goToVoteBtn.setForeground(new Color(128, 0, 64));
        goToVoteBtn.setBounds(105, 520, 150, 30);
        goToVoteBtn.addActionListener(this);
        getContentPane().add(goToVoteBtn);

        showResultBtn = new JButton("Show Result");
        showResultBtn.setForeground(new Color(128, 0, 64));
        showResultBtn.setBounds(364, 520, 150, 30);
        showResultBtn.addActionListener(this);
        getContentPane().add(showResultBtn);

        // Display Area
        displayArea = new JTextArea();
        displayArea.setBackground(new Color(192, 192, 192));
        displayArea.setEditable(false);  // Display area should not be editable

        // Add JTextArea to JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBounds(50, 300, 500, 200);
        getContentPane().add(scrollPane);

        txtDisplayScreen = new JTextField("Display Screen");
        txtDisplayScreen.setFont(new Font("Tahoma", Font.BOLD, 10));
        txtDisplayScreen.setBackground(new Color(192, 192, 192));
        txtDisplayScreen.setBounds(50, 280, 100, 20);
        getContentPane().add(txtDisplayScreen);

        // Initialize some dummy data
        initializeLeaders();
        initializeVoters();
    }

    // Method to initialize some leader data
    private void initializeLeaders() {
        // Add a new Leader to the HashMap with the leader's ID as the key
        leaders.put("101", new Leader("101", "Devendra Fadnavis", "BJP", "Haveli", "Pune", "E:\\Srushti\\Hashfunction\\images\\bjp-logo-png-1024x1024.png"));
        leaders.put("101", new Leader("101", "Devendra Fadnavis", "BJP", "Haveli", "Pune", "E:\\Srushti\\Hashfunction\\images\\bjp-logo-png-1024x1024.png"));
        leaders.put("102", new Leader("102", "Chandrakant Patil", "BJP", "Kothrud", "Pune", "E:\\Srushti\\Hashfunction\\images\\bjp-logo-png-1024x1024.png"));
        leaders.put("103", new Leader("103", "Mangesh Kudalkar","Shiv Sena","Kurla","Mumbai","E:\\Srushti\\Hashfunction\\images\\Logo_of_Shiv_Sena.svg.png"));
        leaders.put("104", new Leader("104", "Satyajeet Tambe", "Congress","Haveli","Pune","E:\\Srushti\\Hashfunction\\images\\Indian-National-Congress-Logo-PNG_1666.jpeg"));
        leaders.put("105", new Leader("105", "Prashant Jagtap","Congress","Kothrud","Pune","E:\\Srushti\\Hashfunction\\images\\Indian-National-Congress-Logo-PNG_1666.jpeg"));
        leaders.put("106", new Leader("106", "Suresh Soni ","BJP","Kurla","Mumbai", "E:\\Srushti\\Hashfunction\\images\\bjp-logo-png-1024x1024.png"));
        leaders.put("107", new Leader("107", "Shahnawz Qureshi","rashtrawadi congress","Kurla","Mumbai", "E:\\Srushti\\Hashfunction\\images\\PPS07-800x800.jpeg"));
        leaders.put("108", new Leader("108", "Eknath Shinde","Shiv Sena","Haveli","Pune", "E:\\Srushti\\Hashfunction\\images\\Logo_of_Shiv_Sena.svg.png"));
        leaders.put("109", new Leader("109", "Ashok Jadhav","Congress","Kurla","Mumbai","E:\\Srushti\\Hashfunction\\images\\Indian-National-Congress-Logo-PNG_1666.jpeg"));
        leaders.put("1010", new Leader("1010", "Sanjay Upadhyay", "BJP","Borivali","Mumbai","E:\\Srushti\\Hashfunction\\images\\bjp-logo-png-1024x1024.png"));
        leaders.put("1011", new Leader("1011", "Priya Dutt","Congress","Borivali","Mumbai","E:\\Srushti\\Hashfunction\\images\\Indian-National-Congress-Logo-PNG_1666.jpeg"));
        leaders.put("1012", new Leader("1012", "Ajit Pawar","rashtrawadi congress","Haveli","Pune","E:\\Srushti\\Hashfunction\\images\\PPS07-800x800.jpeg"));
        leaders.put("1013", new Leader("1013", "Varsha Gaikwad","rashtrawadi congress","Borivali","Mumbai","E:\\Srushti\\Hashfunction\\images\\PPS07-800x800.jpeg"));
        leaders.put("1014", new Leader("1014", "Ramesh Latke", "Shiv Sena","Andheri","Mumbai","E:\\Srushti\\Hashfunction\\images\\Logo_of_Shiv_Sena.svg.png"));
        leaders.put("1017", new Leader("1017", "Chandrakant Mokate","Shiv Sena","Kothrud","Pune","E:\\Srushti\\Hashfunction\\images\\Logo_of_Shiv_Sena.svg.png"));
        leaders.put("1018", new Leader("1018", "Ameet Satam","BJP","Andheri","Mumbai","E:\\Srushti\\Hashfunction\\images\\bjp-logo-png-1024x1024.png"));
        leaders.put("1015", new Leader("1015", "Krishna Hegde","Congress","Andheri","Mumbai","E:\\Srushti\\Hashfunction\\images\\Indian-National-Congress-Logo-PNG_1666.jpeg"));
        leaders.put("1019", new Leader("1019", "Chetan Tupe", "rashtrawadi congress","Kothrud","Pune", "E:\\Srushti\\Hashfunction\\images\\PPS07-800x800.jpeg"));
        leaders.put("1020", new Leader("1020", "Sandeep Bansal","rashtrawadi congress","Andheri","Mumbai","E:\\Srushti\\Hashfunction\\images\\PPS07-800x800.jpeg"));
        leaders.put("1021", new Leader("1021", "Shankar Jagtap","BJP","Chinchwad","Pune", "E:\\Srushti\\Hashfunction\\images\\bjp-logo-png-1024x1024.png"));
        leaders.put("1022", new Leader("1022", "Vilas Lande","Congress","Chinchwad","Pune","E:\\Srushti\\Hashfunction\\images\\Indian-National-Congress-Logo-PNG_1666.jpeg"));
    }

    // Method to initialize some voter data
    private void initializeVoters() {
        // Insert a new voter into the Binary Search Tree (BST) for voters
        voterBST.insert(new node("Arjun Patil", "VTR101", "12 Shanti Apt", "Main St", "Haveli", "Pune", 32, 9876543211L));
        voterBST.insert(new node("Sneha Joshi","VTR102", "45 Unity Tower", "River Rd", "Haveli", "Pune", 28, 9876543212L));
        voterBST.insert(new node("Rahul Mehta","VTR103", "33 Lotus Villa", "City Square", "Haveli", "Pune", 37, 9876543213L));
        voterBST.insert(new node("Priya Shinde",  "VTR104", "78 Rajeshwar Complex", "Temple St", "Haveli", "Pune", 29, 9876543214L));
        voterBST.insert(new node("Ameya Deshpande", "VTR105", "50 Ganga Residency", "Lake Rd", "Haveli", "Pune", 40, 9876543215L));
        voterBST.insert(new node("Varun Kulkarni",  "VTR106", "90 Silver Residency", "Market St", "Haveli", "Pune", 34, 9876543216L));
        voterBST.insert(new node("Ritika Phadke", "VTR107", "23 Greenwoods", "Garden Rd", "Haveli", "Pune", 27, 9876543217L));
        voterBST.insert(new node("Vikram Pawar", "VTR108", "56 Sunrise Apartments", "Main Rd", "Haveli", "Pune", 39, 9876543218L));
        voterBST.insert(new node("Swati Deshmukh", "VTR109", "41 Hilltop", "Park Ave", "Haveli", "Pune", 36, 9876543219L));
        voterBST.insert(new node("Rohit Gokhale",  "VTR110", "77 Whispering Pines", "Lakeview St", "Haveli", "Pune", 31, 9876543220L));
        // Kothrud, Pune District
        voterBST.insert(new node("Sanjay Rao", "VTR111", "17 Tulip Towers", "West End", "Kothrud", "Pune", 41, 9123456711L));
        voterBST.insert(new node("Isha Nene", "VTR112", "64 Maple Complex", "South Ave", "Kothrud", "Pune", 26, 9123456712L));
        voterBST.insert(new node("Ajay Pawar", "VTR113", "32 Rutuja Residency", "East View", "Kothrud", "Pune", 33, 9123456713L));
        voterBST.insert(new node("Meera Naik", "VTR114", "22 Shubham Apts", "Central Rd", "Kothrud", "Pune", 35, 9123456714L));
        voterBST.insert(new node("Kiran Desai", "VTR115", "49 Lavender Villa", "North Rd", "Kothrud", "Pune", 29, 9123456715L));
        voterBST.insert(new node("Tanvi Gaikwad", "VTR116", "98 Green Enclave", "South Park", "Kothrud", "Pune", 38, 9123456716L));
        voterBST.insert(new node("Pranav Patil", "VTR117", "16 Bloom Apartments", "Sunrise Rd", "Kothrud", "Pune", 30, 9123456717L));
        voterBST.insert(new node("Snehal Chavan", "VTR118", "89 Woodland Villas", "West Lake", "Kothrud", "Pune", 27, 9123456718L));
        voterBST.insert(new node("Amol Jadhav", "VTR119", "29 Coral Garden", "East Ave", "Kothrud", "Pune", 39, 9123456719L));
        voterBST.insert(new node("Nisha More", "VTR120", "58 Amber Park", "North View", "Kothrud", "Pune", 25, 9123456720L));

        // Chinchwad, Pune District
        voterBST.insert(new node("Kailash Kamble", "VTR121", "66 City Center", "MG Rd", "Chinchwad", "Pune", 44, 9988776611L));
        voterBST.insert(new node("Ravi Gaonkar", "VTR122", "35 Empire Residency", "Park Rd", "Chinchwad", "Pune", 28, 9988776612L));
        voterBST.insert(new node("Reshma Kadam", "VTR123", "12 Orchid Towers", "Palm Rd", "Chinchwad", "Pune", 31, 9988776613L));
        voterBST.insert(new node("Yash Khedkar", "VTR124", "50 Skyline Apt", "Star Ave", "Chinchwad", "Pune", 38, 9988776614L));
        voterBST.insert(new node("Minal Kulkarni", "VTR125", "40 Ruby Villa", "Greenfield Rd", "Chinchwad", "Pune", 26, 9988776615L));
        voterBST.insert(new node("Nitin Shirodkar", "VTR126", "78 Blue Ridge", "Ocean Ave", "Chinchwad", "Pune", 45, 9988776616L));
        voterBST.insert(new node("Suresh Sonawane", "VTR127", "90 Phoenix Heights", "MG Rd", "Chinchwad", "Pune", 40, 9988776617L));
        voterBST.insert(new node("Anjali Deshpande", "VTR128", "20 Amora Residences", "Lake Rd", "Chinchwad", "Pune", 27, 9988776618L));
        voterBST.insert(new node("Rakesh More", "VTR129", "19 Rosewood Park", "Garden Rd", "Chinchwad", "Pune", 33, 9988776619L));
        voterBST.insert(new node("Pooja Shetty", "VTR130", "14 White Villas", "South Rd", "Chinchwad", "Pune", 29, 9988776620L));

        // Andheri, Mumbai District
        voterBST.insert(new node("Rohan Agarwal", "VTR201", "45 Garden View", "West Lane", "Andheri", "Mumbai", 29, 9123457801L));
        voterBST.insert(new node("Priti Shah", "VTR202", "27 Oak Tower", "East Ave", "Andheri", "Mumbai", 31, 9123457802L));
        voterBST.insert(new node("Vivek Desai", "VTR203", "19 Pearl Residency", "North Rd", "Andheri", "Mumbai", 37, 9123457803L));
        voterBST.insert(new node("Nisha Iyer", "VTR204", "90 Summit Apt", "South Rd", "Andheri", "Mumbai", 25, 9123457804L));
        voterBST.insert(new node("Gaurav Singh", "VTR205", "55 Maple Square", "Market Rd", "Andheri", "Mumbai", 35, 9123457805L));
        voterBST.insert(new node("Shruti Mehta", "VTR206", "10 Silver Park", "East Side", "Andheri", "Mumbai", 34, 9123457806L));
        voterBST.insert(new node("Rajesh Patel", "VTR207", "78 Amber Heights", "North Plaza", "Andheri", "Mumbai", 40, 9123457807L));
        voterBST.insert(new node("Deepa Chawla", "VTR208", "33 Elite Towers", "West End", "Andheri", "Mumbai", 27, 9123457808L));
        voterBST.insert(new node("Aditya Saxena", "VTR209", "24 Ridge Residency", "Lake View", "Andheri", "Mumbai", 30, 9123457809L));
        voterBST.insert(new node("Komal Arora", "VTR210", "63 Grand Vista", "Hill Top", "Andheri", "Mumbai", 26, 9123457810L));

        // Borivali, Mumbai
        voterBST.insert(new node("Sunil Singh", "VTR211", "15 Lakeview Towers", "Park Lane", "Borivali", "Mumbai", 42, 9234567801L));
        voterBST.insert(new node("Meghna Kapoor", "VTR212", "21 Crystal Palace", "River Road", "Borivali", "Mumbai", 29, 9234567802L));
        voterBST.insert(new node("Ankit Verma", "VTR213", "34 Blue Diamond", "Central St", "Borivali", "Mumbai", 31, 9234567803L));
        voterBST.insert(new node("Pooja Rathi", "VTR214", "11 Sunshine Towers", "Maple Ave", "Borivali", "Mumbai", 27, 9234567804L));
        voterBST.insert(new node("Vikas Ahuja", "VTR215", "88 Sunset Villa", "North Ave", "Borivali", "Mumbai", 36, 9234567805L));
        voterBST.insert(new node("Neha Tiwari", "VTR216", "54 Opal Residency", "West Corner", "Borivali", "Mumbai", 25, 9234567806L));
        voterBST.insert(new node("Amit Bhatia", "VTR217", "32 Grand Arena", "Garden St", "Borivali", "Mumbai", 39, 9234567807L));
        voterBST.insert(new node("Kavita Sinha", "VTR218", "20 Horizon View", "East Lane", "Borivali", "Mumbai", 33, 9234567808L));
        voterBST.insert(new node("Nikhil Rathore", "VTR219", "90 Jade Palace", "South Rd", "Borivali", "Mumbai", 28, 9234567809L));
        voterBST.insert(new node("Sanjana Gupta", "VTR220", "77 Bella Vista", "Lake Rd", "Borivali", "Mumbai", 34, 9234567810L));

        // Kurla, Mumbai
        voterBST.insert(new node("Aakash Joshi", "VTR221", "49 Palm Meadows", "East Blvd", "Kurla", "Mumbai", 30, 9345678901L));
        voterBST.insert(new node("Sara Khan", "VTR222", "63 Golden Estate", "Market Rd", "Kurla", "Mumbai", 27, 9345678902L));
        voterBST.insert(new node("Raj Malhotra", "VTR223", "28 River Residency", "Park Rd", "Kurla", "Mumbai", 35, 9345678903L));
        voterBST.insert(new node("Tina D'Souza", "VTR224", "75 Harmony Enclave", "Station Rd", "Kurla", "Mumbai", 32, 9345678904L));
        voterBST.insert(new node("Deepak Chauhan", "VTR225", "14 Summit Square", "North Blvd", "Kurla", "Mumbai", 37, 9345678905L));
        voterBST.insert(new node("Rita Paul", "VTR226", "90 Lake Towers", "South St", "Kurla", "Mumbai", 26, 9345678906L));
        voterBST.insert(new node("Mohit Soni", "VTR227", "12 Green Valley", "East Ave", "Kurla", "Mumbai", 28, 9345678907L));
        voterBST.insert(new node("Sana Merchant", "VTR228", "65 Ocean Heights", "West End", "Kurla", "Mumbai", 33, 9345678908L));
        voterBST.insert(new node("Karan Singh", "VTR229", "31 Regal Court", "Lake Ave", "Kurla", "Mumbai", 29, 9345678909L));
        voterBST.insert(new node("Priya Shetty", "VTR230", "44 Sky Towers", "Central Rd", "Kurla", "Mumbai", 31, 9345678910L));
    }
 // Class representing a Voter (node in the binary search tree)
    class node {
        String name;
        String voter_Id;
        String flat_no;
        String city;
        String tal;
        String dist;
        int age;
        long mobile;
        node left;  // Left and right pointers for the BST
        node right; // Track voting status (default is "no")
        String voted;  // This field will be used to track voting status

        // Constructor to initialize voter details
        node(String name, String voter_Id, String flat_no, String city, String tal, String dist, int age, long mobile) {
            this.name = name;
            this.voter_Id = voter_Id;
            this.flat_no = flat_no;
            this.city = city;
            this.tal = tal;
            this.dist = dist;
            this.age = age;
            this.mobile = mobile;
            this.left = null;
            this.right = null;
            this.voted = "no";  // Default value is "no", meaning they haven't voted yet
        }
    }

    // Class to manage the Binary Search Tree (BST) of voters
    class VoterBST {
        private node root;

        // Method to get the root of the BST
        public node getRoot() {
            return root;  // Root of the BST
        }

        // Method to insert a new voter into the BST
        public void insert(node voter) {
            root = insertRecursive(root, voter);
        }

        // Recursive method to insert a voter into the BST
        private node insertRecursive(node root, node voter) {
            if (root == null) {
                return voter;
            }
            // Insert based on comparison of voter IDs (lexicographically)
            if (voter.voter_Id.compareTo(root.voter_Id) < 0) {
                root.left = insertRecursive(root.left, voter);
            } else if (voter.voter_Id.compareTo(root.voter_Id) > 0) {
                root.right = insertRecursive(root.right, voter);
            }
            return root;
        }

        // Method to get the list of voters in an in-order traversal format
        public String inOrder() {
            StringBuilder sb = new StringBuilder();
            inOrderRecursive(root, sb);
            return sb.toString();
        }

        // Recursive method to traverse the tree in-order and append to StringBuilder
        private void inOrderRecursive(node root, StringBuilder sb) {
            if (root != null) {
                inOrderRecursive(root.left, sb);
                sb.append(root.name).append(" (").append(root.voter_Id).append(")\n");
                inOrderRecursive(root.right, sb);
            }
        }

        // Method to search for a voter by voter ID
        public node search(String voterId) {
            return searchRecursive(root, voterId);
        }

        // Recursive method to search for a voter by voter ID
        private node searchRecursive(node root, String voterId) {
            if (root == null) {
                return null;  // Voter not found
            }
            // Search based on comparison of voter IDs
            if (voterId.compareTo(root.voter_Id) < 0) {
                return searchRecursive(root.left, voterId);  // Search left subtree
            } else if (voterId.compareTo(root.voter_Id) > 0) {
                return searchRecursive(root.right, voterId);  // Search right subtree
            }
            return root;  // Voter found
        }
    }

    // Handles actions for various buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newVoterBtn) {
            addNewVoter();  // Action to add a new voter
        } else if (e.getSource() == existingVoterBtn) {
            updateVoter();  // Action to update an existing voter
        } else if (e.getSource() == newLeaderBtn) {
            addNewLeader();  // Action to add a new leader
        } else if (e.getSource() == existingLeaderBtn) {
            updateLeader();  // Action to update an existing leader
        } else if (e.getSource() == goToVoteBtn) {
            showVotingOptions();  // Action to show voting options
        } else if (e.getSource() == showResultBtn) {
            showResults();  // Action to show election results
        } else if (e.getSource() == showVotersBtn) {
            showVoters();  // Action to show the list of voters
        } else if (e.getSource() == deleteLeaderBtn) {
            deleteLeader();  // Action to delete the leader
        } else if (e.getSource() == showLeadersBtn) {
            // Call initializeLeaders to get leader data and display it
            showLeaders();
        } else if (e.getSource() == emptyTrashBtn) {
            emptyTrash();  // Action to empty Trash
        } else if (e.getSource() == retrieveFromTrashBtn) {
            retrieveFromTrash();  // Action to retrieve from trash
        }
    }

    private void addNewLeader() {
        // Prompt the user for leader details
        String id = JOptionPane.showInputDialog("Enter Leader ID:");
        String name = JOptionPane.showInputDialog("Enter Leader Name:");
        String party = JOptionPane.showInputDialog("Enter Party Name:");
        String taluka = JOptionPane.showInputDialog("Enter Taluka:");
        String district = JOptionPane.showInputDialog("Enter District:");
        String partyImagePath = JOptionPane.showInputDialog("Enter Party Image Path:");
        
        // Create a new leader object and add it to the leaders map
        Leader leader = new Leader(id, name, party, taluka, district, partyImagePath);
        leaders.put(id, leader);
        
        // Display message in the text area
        displayArea.setText("New Leader Added:\n" + name);
    }

    private void updateLeader() {
        String id = JOptionPane.showInputDialog("Enter Leader ID to Update:");
        Leader leader = leaders.get(id);
        if (leader != null) {
            // Prompt the user for new leader details
            leader.name = JOptionPane.showInputDialog("Enter New Name:", leader.name);
            leader.district = JOptionPane.showInputDialog("Enter New District:", leader.district);
            leader.taluka = JOptionPane.showInputDialog("Enter New Taluka:", leader.taluka);
            
            // Display message in the text area
            displayArea.setText("Leader Updated:\n" + leader.name);
        } else {
            displayArea.setText("Leader not found.");
        }
    }

    private void showLeaders() {
        // Call the method to initialize the leaders data
        initializeLeaders();
        
        // Now display the leaders data in the JTextArea
        StringBuilder sb = new StringBuilder("List of Leaders:\n");
        for (Leader leader : leaders.values()) {
            sb.append("ID: ").append(leader.id)
              .append(", Name: ").append(leader.name)
              .append(", Party: ").append(leader.party)
              .append("\n");
        }
        displayArea.setText(sb.toString());
    }

    private void deleteLeader() {
        String id = JOptionPane.showInputDialog("Enter Leader ID to Delete:");
        Leader leader = leaders.remove(id);  // Remove the leader from the map
        if (leader != null) {
            // Push the leader onto the trash stack
            trash.push(leader);
            displayArea.setText("Leader Deleted and moved to trash:\n" + leader.name);
        } else {
            displayArea.setText("Leader not found.");
        }
    }

    private void emptyTrash() {
        trash.clear();
        displayArea.setText("Trash has been emptied.");
    }

    private void retrieveFromTrash() {
        if (trash.isEmpty()) {
            displayArea.setText("Trash is empty.");
        } else {
            Leader retrievedLeader = trash.pop();  // Retrieve and remove the most recent leader from trash
            leaders.put(retrievedLeader.id, retrievedLeader);  // Restore the leader back to the map
            displayArea.setText("Leader retrieved from trash:\n" + retrievedLeader.name);
        }
    }

    private void addNewVoter() {
        String voter_Id = JOptionPane.showInputDialog("Enter Voter ID:");
        String name = JOptionPane.showInputDialog("Enter Voter Name:");
        String flat_no = JOptionPane.showInputDialog("Address:\n Enter flat Name:");
        String city = JOptionPane.showInputDialog("Enter city Name:");
        String tal = JOptionPane.showInputDialog("Enter Taluka:");
        String dist = JOptionPane.showInputDialog("Enter District:");
        int age = Integer.parseInt(JOptionPane.showInputDialog("Enter Age:"));
        long mobile = Long.parseLong(JOptionPane.showInputDialog("Enter Mobile Number:"));
        
        node voter = new node(name, voter_Id, flat_no, city, tal, dist, age, mobile);
        voterBST.insert(voter);
        
        displayArea.setText("New Voter Added:\n" + name);
    }

    private void updateVoter() {
        String id = JOptionPane.showInputDialog("Enter Voter ID to Update:");
        node voter = voterBST.search(id);
        if (voter != null) {
            voter.name = JOptionPane.showInputDialog("Enter New Name:", voter.name);
            voter.flat_no = JOptionPane.showInputDialog("Enter New Flat Name:", voter.flat_no);
            voter.city = JOptionPane.showInputDialog("Enter New City Name:", voter.city);
            voter.tal = JOptionPane.showInputDialog("Enter New Taluka:", voter.tal);
            voter.dist = JOptionPane.showInputDialog("Enter New District:", voter.dist);
            voter.age = Integer.parseInt(JOptionPane.showInputDialog("Enter New Age:", voter.age));
            voter.mobile = Long.parseLong(JOptionPane.showInputDialog("Enter New Mobile Number:", voter.mobile));
            
            displayArea.setText("Voter Updated:\n" + voter.name);
        } else {
            displayArea.setText("Voter not found.");
        }
    }

    private void showVoters() {
        displayArea.setText("List of Voters:\n" + voterBST.inOrder());
    }
 // Displays the voting options for a voter	
    private void showVotingOptions() {
        String voterId = JOptionPane.showInputDialog("Enter Voter ID:");
        node voter = voterBST.search(voterId);

        if (voter != null) {
            if ("yes".equals(voter.voted)) {  // Check if the voter has already voted
                JOptionPane.showMessageDialog(this, "You have already voted.");
                return;  // Exit the voting process if they have already voted
            }

            String district = voter.dist;
            String taluka = voter.tal;

            JFrame voteFrame = new JFrame("District: " + district + ", Taluka: " + taluka);
            voteFrame.setSize(500, 500);  // Adjust the window size as needed
            voteFrame.getContentPane().setLayout(new BorderLayout());
            voteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            voteFrame.setLocationRelativeTo(null);

            // Create a JLabel for the custom title with bold district and taluka names
            JLabel titleLabel = new JLabel("<html>District: <b>" + district + "</b>, Taluka: <b>" + taluka + "</b></html>", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            voteFrame.getContentPane().add(titleLabel, BorderLayout.NORTH);

            JPanel leaderPanelContainer = new JPanel();
            leaderPanelContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center-align leader boxes

            boolean leadersFound = false;
            for (Leader leader : leaders.values()) {
                if (leader.district.equals(district) && leader.taluka.equals(taluka)) {
                    leadersFound = true;

                    // Create a panel for each leader
                    JPanel leaderPanel = new JPanel();
                    leaderPanel.setLayout(new BoxLayout(leaderPanel, BoxLayout.Y_AXIS));
                    leaderPanel.setBorder(BorderFactory.createTitledBorder("Leader"));
                    leaderPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);  // Center align the leader panel itself

                    // Leader's Image
                    ImageIcon icon = new ImageIcon(new ImageIcon(leader.partyImagePath)
                            .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                    JLabel logoLabel = new JLabel(icon);
                    logoLabel.setPreferredSize(new Dimension(100, 100));
                    logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

                    // Leader Info (Name & ID)
                    JPanel infoPanel = new JPanel();
                    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
                    JLabel nameLabel = new JLabel("<html><b>Name:</b> " + leader.name + "</html>");
                    JLabel idLabel = new JLabel("<html><b>ID:</b> " + leader.id + "</html>");
                    infoPanel.add(nameLabel);
                    infoPanel.add(idLabel);
                    infoPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

                    // Vote Button
                    JButton voteButton = new JButton("Vote");
                    voteButton.setPreferredSize(new Dimension(100, 30));
                    voteButton.addActionListener(e -> {
                        leader.voteCount++;  // Increment vote count for the leader
                        voter.voted = "yes";  // Mark the voter as having voted
                        JOptionPane.showMessageDialog(voteFrame, "Vote casted for Leader: " + leader.name);
                        voteFrame.dispose();
                    });
                    voteButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

                    // Add components to the leader panel
                    leaderPanel.add(logoLabel);
                    leaderPanel.add(infoPanel);
                    leaderPanel.add(voteButton);

                    // Add leader panel to the container
                    leaderPanelContainer.add(leaderPanel);
                }
            }

            if (!leadersFound) {
                JOptionPane.showMessageDialog(this, "No leaders available in this district and taluka.");
                return;
            }

            // Add the leader panel container directly to the frame
            voteFrame.getContentPane().add(leaderPanelContainer, BorderLayout.CENTER);
            voteFrame.setVisible(true);

        } else {
            displayArea.setText("Voter not found.");
        }
    }

    public void resetVotes(node root) {
        if (root != null) {
            root.voted = "no"; // Reset the 'voted' status of each voter
            resetVotes(root.left);  // Recursively reset left subtree
            resetVotes(root.right); // Recursively reset right subtree
        }
    }

    private void showResults() {
        // Initial display setup
        displayArea.setText("Election Results:\n");
        StringBuilder result = new StringBuilder("Election Results:\n");
        HashMap<String, Integer> partyVotes = new HashMap<>();
        HashMap<String, Leader> talukaWinners = new HashMap<>();

        Leader winningLeader = null; // To track the leader of the winning party

        // Calculate party votes and determine taluka-wise winners
        for (Leader leader : leaders.values()) {
            partyVotes.put(leader.party, partyVotes.getOrDefault(leader.party, 0) + leader.voteCount);

            // Append leader results
            result.append("Leader: ").append(leader.name)
                    .append(" (").append(leader.party).append(") - Votes: ")
                    .append(leader.voteCount).append("\n");

            // Determine taluka-wise winners
            String taluka = leader.taluka;
            if (leader.voteCount > 0 &&
                    (!talukaWinners.containsKey(taluka) || leader.voteCount > talukaWinners.get(taluka).voteCount)) {
                talukaWinners.put(taluka, leader);
            } else if (leader.voteCount > 0 && leader.voteCount == talukaWinners.get(taluka).voteCount) {
                // Tie detected in taluka
                JOptionPane.showMessageDialog(null, "Tie detected in Taluka: " + taluka + ". A re-election is required.");
                resetTalukaVotes(taluka);
                resetVotes(voterBST.getRoot());
                return;
            }
        }

        // Append total party votes
        result.append("\nParty Votes:\n");
        for (String party : partyVotes.keySet()) {
            result.append("Party: ").append(party)
                    .append(" - Total Votes: ").append(partyVotes.get(party)).append("\n");
        }

        // Check for overall tie among parties
        String overallWinningParty = null;
        int maxVotes = 0;
        boolean tieDetected = false;
        for (String party : partyVotes.keySet()) {
            int votes = partyVotes.get(party);
            if (votes > maxVotes) {
                maxVotes = votes;
                overallWinningParty = party;
                winningLeader = null;
                tieDetected = false;
            } else if (votes == maxVotes) {
                tieDetected = true; // Tie detected among parties
            }
        }

        // Find any leader belonging to the winning party to get the image path
        for (Leader leader : leaders.values()) {
            if (leader.party.equals(overallWinningParty)) {
                winningLeader = leader;
                break;
            }
        }

        if (tieDetected) {
            JOptionPane.showMessageDialog(null, "Tie detected among parties. A re-election is required.");
            resetAllVotes();
            resetVotes(voterBST.getRoot());
            return;
        }

        // Append taluka-wise winners
        result.append("\nTaluka-wise Winners:\n");
        for (String taluka : talukaWinners.keySet()) {
            Leader winner = talukaWinners.get(taluka);
            result.append("Taluka: ").append(taluka)
                    .append(" - Winner Leader: ").append(winner.name)
                    .append(" (").append(winner.party).append(") - Votes: ")
                    .append(winner.voteCount).append("\n");
        }

        // Display results in a new window
        JFrame resultFrame = new JFrame("Election Results");
        resultFrame.setSize(400, 600);
        resultFrame.getContentPane().setLayout(new BorderLayout());

        JTextArea resultsArea = new JTextArea(result.toString());
        resultsArea.setEditable(false);
        resultFrame.getContentPane().add(new JScrollPane(resultsArea), BorderLayout.CENTER);

        // Display winning party with passport-sized image
        if (overallWinningParty != null && !tieDetected) {
            JPanel winnerPanel = new JPanel();
            winnerPanel.setLayout(new BorderLayout());

            JLabel winnerLabel = new JLabel("Overall Winning Party: " + overallWinningParty, JLabel.CENTER);
            JLabel partyImageLabel = new JLabel(new ImageIcon(getPartyImage(winningLeader, 100, 100))); // Resized to 100x100
            winnerPanel.add(partyImageLabel, BorderLayout.CENTER);
            winnerPanel.add(winnerLabel, BorderLayout.SOUTH);

            resultFrame.getContentPane().add(winnerPanel, BorderLayout.NORTH);
        }

        resultFrame.setVisible(true);
    }

    // Helper method to reset all votes for re-election
    private void resetAllVotes() {
        for (Leader leader : leaders.values()) {
            leader.voteCount = 0;
        }
    }

    // Helper method to reset votes in a specific taluka for re-election
    private void resetTalukaVotes(String taluka) {
        for (Leader leader : leaders.values()) {
            if (leader.taluka.equals(taluka)) {
                leader.voteCount = 0;
            }
        }
    }

    // Updated method to fetch and resize party image to passport size
    private Image getPartyImage(Leader leader, int width, int height) {
        if (leader != null && leader.partyImagePath != null && !leader.partyImagePath.isEmpty()) {
            ImageIcon originalIcon = new ImageIcon(leader.partyImagePath);
            return originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
        return new ImageIcon("path/to/default_image.png").getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    // Main method 
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FrameDemo frame = new FrameDemo();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
