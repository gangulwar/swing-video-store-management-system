package com.gangulwar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class VideoStoreManagementApp extends JFrame implements ActionListener {
    private JPanel navigationPanel;
    private JButton homeButton, addCustomerButton, addVideoButton, issueVideoButton;
    private JButton viewCustomersButton, viewVideosButton, viewIssuedVideosButton;

    private JPanel mainPanel;
    private CardLayout cardLayout;

    private Connection connection;
    private Statement statement;

    private JPanel addCustomerPanel, addVideoPanel, issueVideoPanel;

    private JTextField videoTitleField, yearField;


    private DefaultTableModel customerTableModel, videoTableModel, issuedVideoTableModel;
    private JTable customerTable, videoTable, issuedVideoTable;

    private JLabel titleLabel;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel mobileLabel;
    private JTextField mobileField;
    private JLabel genderLabel;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private ButtonGroup genderGroup;
    private JLabel dobLabel;
    private JComboBox<String> cusDateComboBox;
    private JComboBox<String> cusMonthComboBox;
    private JComboBox<String> cusYearComboBox;
    private JLabel addressLabel;
    private JTextArea addressTextArea;
    private JCheckBox termsCheckBox;
    private JButton submitButton;
    private JButton resetButton;
    private JLabel resultLabel;
    private JComboBox<Integer> customerIdComboBox, videoIdComboBox;
    private JLabel videoIdLabel;

    private JComboBox<String> returnDateComboBox;
    private JComboBox<String> returnMonthComboBox;
    private JComboBox<String> returnYearComboBox;

    private JComboBox<String> genreComboBox;

    private JLabel customerIdLabel, issueVideoTitleLabel;

    private JComboBox<String> monthComboBox, yearComboBox, hourComboBox, minuteComboBox;

    private JComboBox<String> dateComboBox;

    public VideoStoreManagementApp() {
        super("Video Store Management");

        navigationPanel = new JPanel();
        navigationPanel.setLayout(new GridLayout(7, 1));

        homeButton = createButton("Home");
        addCustomerButton = createButton("Add Customer");
        addVideoButton = createButton("Add Video");
        issueVideoButton = createButton("Issue Video");
        viewCustomersButton = createButton("View Customers");
        viewVideosButton = createButton("View Videos");
        viewIssuedVideosButton = createBiggerButton("View Issued \nVideos");

        homeButton.addActionListener(this);
        addCustomerButton.addActionListener(this);
        addVideoButton.addActionListener(this);
        issueVideoButton.addActionListener(this);
        viewCustomersButton.addActionListener(this);
        viewVideosButton.addActionListener(this);
        viewIssuedVideosButton.addActionListener(this);

        navigationPanel.add(homeButton);
        navigationPanel.add(addCustomerButton);
        navigationPanel.add(addVideoButton);
        navigationPanel.add(issueVideoButton);
        navigationPanel.add(viewCustomersButton);
        navigationPanel.add(viewVideosButton);
        navigationPanel.add(viewIssuedVideosButton);

        add(navigationPanel, BorderLayout.WEST);

        add(navigationPanel, BorderLayout.WEST);

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        JPanel homePanel = createPanel();
        homePanel.setLayout(new GridBagLayout());

        JLabel welcomeLabel = new JLabel("Welcome to Video Store Management System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 0, 0, 0);
        homePanel.add(welcomeLabel, gbc);

        mainPanel.add(homePanel, "home");

        customerTableModel = new DefaultTableModel();
        videoTableModel = new DefaultTableModel();
        issuedVideoTableModel = new DefaultTableModel();

        customerTable = new JTable(customerTableModel);
        videoTable = new JTable(videoTableModel);
        issuedVideoTable = new JTable(issuedVideoTableModel);

        viewCustomersButton.addActionListener(e -> displayCustomers());
        viewVideosButton.addActionListener(e -> displayVideos());
        viewIssuedVideosButton.addActionListener(e -> displayIssuedVideos());

        JScrollPane customerScrollPane = new JScrollPane(customerTable);
        JScrollPane videoScrollPane = new JScrollPane(videoTable);
        JScrollPane issuedVideoScrollPane = new JScrollPane(issuedVideoTable);

        JPanel viewCustomersPanel = createPanel();
        viewCustomersPanel.add(customerScrollPane);
        mainPanel.add(viewCustomersPanel, "viewCustomers");

        JPanel viewVideosPanel = createPanel();
        viewVideosPanel.add(videoScrollPane);
        mainPanel.add(viewVideosPanel, "viewVideos");

        JPanel viewIssuedVideosPanel = createPanel();
        viewIssuedVideosPanel.add(issuedVideoScrollPane);
        mainPanel.add(viewIssuedVideosPanel, "viewIssuedVideos");

        addCustomerPanel = createPanel();
        addCustomerPanel.setLayout(null);

        titleLabel = new JLabel("Registration Form");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        titleLabel.setBounds(300, 30, 300, 30);
        addCustomerPanel.add(titleLabel);

        nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        nameLabel.setBounds(100, 100, 100, 20);
        addCustomerPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 15));
        nameField.setBounds(200, 100, 190, 20);
        addCustomerPanel.add(nameField);

        mobileLabel = new JLabel("Mobile");
        mobileLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        mobileLabel.setBounds(100, 150, 100, 20);
        addCustomerPanel.add(mobileLabel);

        mobileField = new JTextField();
        mobileField.setFont(new Font("Arial", Font.PLAIN, 15));
        mobileField.setBounds(200, 150, 150, 20);
        addCustomerPanel.add(mobileField);

        genderLabel = new JLabel("Gender");
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        genderLabel.setBounds(100, 200, 100, 20);
        addCustomerPanel.add(genderLabel);

        maleRadioButton = new JRadioButton("Male");
        maleRadioButton.setFont(new Font("Arial", Font.PLAIN, 15));
        maleRadioButton.setSelected(true);
        maleRadioButton.setBounds(200, 200, 75, 20);
        addCustomerPanel.add(maleRadioButton);

        femaleRadioButton = new JRadioButton("Female");
        femaleRadioButton.setFont(new Font("Arial", Font.PLAIN, 15));
        femaleRadioButton.setSelected(false);
        femaleRadioButton.setBounds(275, 200, 80, 20);
        addCustomerPanel.add(femaleRadioButton);

        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        dobLabel = new JLabel("DOB");
        dobLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        dobLabel.setBounds(100, 250, 100, 20);
        addCustomerPanel.add(dobLabel);

        cusDateComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        cusDateComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        cusDateComboBox.setBounds(200, 250, 50, 20);
        addCustomerPanel.add(cusDateComboBox);

        cusMonthComboBox = new JComboBox<>(new String[]{"Jan", "Feb", "Mar", "Apr", "May"});
        cusMonthComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        cusMonthComboBox.setBounds(250, 250, 60, 20);
        addCustomerPanel.add(cusMonthComboBox);

        cusYearComboBox = new JComboBox<>(new String[]{"2022", "2023", "2024", "2025", "2026"});
        cusYearComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        cusYearComboBox.setBounds(320, 250, 60, 20);
        addCustomerPanel.add(cusYearComboBox);

        addressLabel = new JLabel("Address");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        addressLabel.setBounds(100, 300, 100, 20);
        addCustomerPanel.add(addressLabel);

        addressTextArea = new JTextArea();
        addressTextArea.setFont(new Font("Arial", Font.PLAIN, 15));
        addressTextArea.setBounds(200, 300, 200, 75);
        addressTextArea.setLineWrap(true);
        addCustomerPanel.add(addressTextArea);

        termsCheckBox = new JCheckBox("Accept Terms And Conditions.");
        termsCheckBox.setFont(new Font("Arial", Font.PLAIN, 15));
        termsCheckBox.setBounds(150, 400, 250, 20);
        addCustomerPanel.add(termsCheckBox);

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 15));
        submitButton.setBounds(150, 450, 100, 20);
        submitButton.addActionListener(e -> addCustomer());

        addCustomerPanel.add(submitButton);

        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 15));
        resetButton.setBounds(270, 450, 100, 20);
        resetButton.addActionListener(this);
        addCustomerPanel.add(resetButton);

        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        resultLabel.setBounds(100, 500, 500, 25);
        addCustomerPanel.add(resultLabel);

        mainPanel.add(addCustomerPanel, "addCustomer");


        addVideoPanel = createPanel();
        addVideoPanel.setLayout(null);

        JLabel addVideoTitleLabel = new JLabel("Add Video");
        addVideoTitleLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        addVideoTitleLabel.setBounds(300, 30, 200, 30);
        addVideoPanel.add(addVideoTitleLabel);

        JLabel videoTitleLabel = new JLabel("Title");
        videoTitleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        videoTitleLabel.setBounds(100, 100, 100, 20);
        addVideoPanel.add(videoTitleLabel);

        videoTitleField = new JTextField();
        videoTitleField.setFont(new Font("Arial", Font.PLAIN, 15));
        videoTitleField.setBounds(200, 100, 190, 20);
        addVideoPanel.add(videoTitleField);

        JLabel genreLabel = new JLabel("Genre");
        genreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        genreLabel.setBounds(100, 150, 100, 20);
        addVideoPanel.add(genreLabel);

        genreComboBox = new JComboBox<>(new String[]{"Action", "Comedy", "Drama", "Horror", "Sci-Fi"});
        genreComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        genreComboBox.setBounds(200, 150, 150, 20);
        addVideoPanel.add(genreComboBox);

        JLabel yearLabel = new JLabel("Year");
        yearLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        yearLabel.setBounds(100, 200, 100, 20);
        addVideoPanel.add(yearLabel);

        yearField = new JTextField();
        yearField.setFont(new Font("Arial", Font.PLAIN, 15));
        yearField.setBounds(200, 200, 150, 20);
        addVideoPanel.add(yearField);

        JButton addVideoButton = new JButton("Add Video");
        addVideoButton.setFont(new Font("Arial", Font.PLAIN, 15));
        addVideoButton.setBounds(150, 250, 120, 20);
        addVideoButton.addActionListener(e -> addVideo());
        addVideoPanel.add(addVideoButton);

        mainPanel.add(addVideoPanel, "addVideo");

        issueVideoPanel = createPanel();
        issueVideoPanel.setLayout(null);

        issueVideoTitleLabel = new JLabel("Issue Video");
        issueVideoTitleLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        issueVideoTitleLabel.setBounds(300, 30, 200, 30);
        issueVideoPanel.add(issueVideoTitleLabel);


        customerIdLabel = new JLabel("Customer ID");
        customerIdLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        customerIdLabel.setBounds(100, 100, 150, 20);
        issueVideoPanel.add(customerIdLabel);

        customerIdComboBox = new JComboBox<>();
        customerIdComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        customerIdComboBox.setBounds(250, 100, 150, 20);
        issueVideoPanel.add(customerIdComboBox);

        videoIdLabel = new JLabel("Video ID");
        videoIdLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        videoIdLabel.setBounds(100, 150, 100, 20);
        issueVideoPanel.add(videoIdLabel);

        videoIdComboBox = new JComboBox<>();
        videoIdComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        videoIdComboBox.setBounds(250, 150, 150, 20);
        issueVideoPanel.add(videoIdComboBox);

        JLabel issueDateLabel = new JLabel("Issue Date");
        issueDateLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        issueDateLabel.setBounds(100, 200, 100, 20);
        issueVideoPanel.add(issueDateLabel);

        JLabel issueTimeLabel = new JLabel("Issue Time");
        issueTimeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        issueTimeLabel.setBounds(100, 230, 100, 20);
        issueVideoPanel.add(issueTimeLabel);

        String[] dates = new String[31];
        for (int i = 0; i < 31; i++) {
            dates[i] = String.valueOf(i + 1);
        }


        dateComboBox = new JComboBox<>(dates);
        dateComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        dateComboBox.setBounds(250, 200, 60, 20);
        issueVideoPanel.add(dateComboBox);

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};


        monthComboBox = new JComboBox<>(months);
        monthComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        monthComboBox.setBounds(320, 200, 60, 20);
        issueVideoPanel.add(monthComboBox);

        String[] years = {"2022", "2023", "2024", "2025", "2026"};
        yearComboBox = new JComboBox<>(years);
        yearComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        yearComboBox.setBounds(390, 200, 70, 20);
        issueVideoPanel.add(yearComboBox);

        String[] hours = new String[24];
        for (int i = 0; i < 24; i++) {
            hours[i] = String.format("%02d", i);
        }
        hourComboBox = new JComboBox<>(hours);
        hourComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        hourComboBox.setBounds(250, 230, 60, 20);
        issueVideoPanel.add(hourComboBox);

        String[] minutes = new String[60];
        for (int i = 0; i < 60; i++) {
            minutes[i] = String.format("%02d", i);
        }
        minuteComboBox = new JComboBox<>(minutes);
        minuteComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        minuteComboBox.setBounds(320, 230, 60, 20);
        issueVideoPanel.add(minuteComboBox);

        JButton issueVideoButton = new JButton("Issue Video");
        issueVideoButton.setFont(new Font("Arial", Font.PLAIN, 15));
        issueVideoButton.setBounds(150, 300, 120, 20);
        issueVideoButton.addActionListener(e -> issueVideo());
        issueVideoPanel.add(issueVideoButton);

        JLabel returnDateLabel = new JLabel("Return Date");
        returnDateLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        returnDateLabel.setBounds(100, 260, 150, 20);
        issueVideoPanel.add(returnDateLabel);

        returnDateComboBox = new JComboBox<>(dates);
        returnDateComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        returnDateComboBox.setBounds(250, 260, 60, 20);
        issueVideoPanel.add(returnDateComboBox);

        returnMonthComboBox = new JComboBox<>(months);
        returnMonthComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        returnMonthComboBox.setBounds(320, 260, 60, 20);
        issueVideoPanel.add(returnMonthComboBox);

        returnYearComboBox = new JComboBox<>(years);
        returnYearComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        returnYearComboBox.setBounds(390, 260, 70, 20);
        issueVideoPanel.add(returnYearComboBox);

        mainPanel.add(issueVideoPanel, "issueVideo");

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/videostore", "root", "aarsh23");
            statement = connection.createStatement();
            updateValues();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        add(mainPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VideoStoreManagementApp::new);
    }

    private void updateValues() throws SQLException {

        ResultSet customerResultSet = statement.executeQuery("SELECT id FROM customers");
        while (customerResultSet.next()) {
            int customerId = customerResultSet.getInt("id");
            customerIdComboBox.addItem(customerId);
        }

        ResultSet videoResultSet = statement.executeQuery("SELECT id FROM videos");
        while (videoResultSet.next()) {
            int videoId = videoResultSet.getInt("id");
            videoIdComboBox.addItem(videoId);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeButton) {
            cardLayout.show(mainPanel, "home");
        } else if (e.getSource() == addCustomerButton) {
            cardLayout.show(mainPanel, "addCustomer");
        } else if (e.getSource() == addVideoButton) {
            cardLayout.show(mainPanel, "addVideo");
        } else if (e.getSource() == issueVideoButton) {
            cardLayout.show(mainPanel, "issueVideo");
        } else if (e.getSource() == viewCustomersButton) {
            cardLayout.show(mainPanel, "viewCustomers");
        } else if (e.getSource() == viewVideosButton) {
            cardLayout.show(mainPanel, "viewVideos");
        } else if (e.getSource() == viewIssuedVideosButton) {
            cardLayout.show(mainPanel, "viewIssuedVideos");
        }
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }

    private JButton createBiggerButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setPreferredSize(new Dimension(175, 40));
        return button;
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10));
        return panel;
    }

    private void addCustomer() {
        try {
            String name = nameField.getText();
            String contactNumber = mobileField.getText();
            String gender = maleRadioButton.isSelected() ? "Male" : "Female";
            String dob = cusYearComboBox.getSelectedItem() + "-" +
                    getMonthNumber((String) cusMonthComboBox.getSelectedItem()) + "-" +
                    cusDateComboBox.getSelectedItem();
            String address = addressTextArea.getText();

            String query = "INSERT INTO customers (name, contact_number, sex, dob, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, contactNumber);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, dob);
            preparedStatement.setString(5, address);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Customer added successfully!");
            updateValues();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding customer. Please check your input.");
        }
    }

    private String getMonthNumber(String monthName) {
        switch (monthName) {
            case "Jan":
                return "01";
            case "Feb":
                return "02";
            case "Mar":
                return "03";
            case "Apr":
                return "04";
            case "May":
                return "05";
            case "Jun":
                return "06";
            case "Jul":
                return "07";
            case "Aug":
                return "08";
            case "Sep":
                return "09";
            case "Oct":
                return "10";
            case "Nov":
                return "11";
            case "Dec":
                return "12";
            default:
                return "01";
        }
    }


    private void addVideo() {
        try {
            String title = videoTitleField.getText();
            String genre = (String) genreComboBox.getSelectedItem();
            int year = Integer.parseInt(yearField.getText());

            String query = "INSERT INTO videos (title, genre, release_year) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, genre);
            preparedStatement.setInt(3, year);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Video added successfully!");
            updateValues();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding video. Please check your input.");
        }
    }


    private void issueVideo() {
        try {
            int customerId = Integer.parseInt(customerIdComboBox.getSelectedItem().toString());
            int videoId = Integer.parseInt(videoIdComboBox.getSelectedItem().toString());

            int selectedDay = Integer.parseInt((String) dateComboBox.getSelectedItem());
            int selectedMonthIndex = monthComboBox.getSelectedIndex();
            int selectedYear = Integer.parseInt((String) yearComboBox.getSelectedItem());

            int selectedHour = Integer.parseInt((String) hourComboBox.getSelectedItem());
            int selectedMinute = Integer.parseInt((String) minuteComboBox.getSelectedItem());

            LocalDate issueLocalDate = LocalDate.of(selectedYear, selectedMonthIndex + 1, selectedDay);
            LocalTime issueLocalTime = LocalTime.of(selectedHour, selectedMinute);

            int selectedReturnDay = Integer.parseInt((String) returnDateComboBox.getSelectedItem());
            int selectedReturnMonthIndex = returnMonthComboBox.getSelectedIndex();
            int selectedReturnYear = Integer.parseInt((String) returnYearComboBox.getSelectedItem());

            LocalDate returnLocalDate = LocalDate.of(selectedReturnYear, selectedReturnMonthIndex + 1, selectedReturnDay);

            java.sql.Date issueDateSQL = java.sql.Date.valueOf(issueLocalDate);
            java.sql.Time issueTimeSQL = java.sql.Time.valueOf(issueLocalTime);

            String query = "INSERT INTO issued_videos (customer_id, video_id, issue_date, issue_time, return_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, videoId);
            preparedStatement.setDate(3, issueDateSQL);
            preparedStatement.setTime(4, issueTimeSQL);
            preparedStatement.setDate(5, java.sql.Date.valueOf(returnLocalDate));
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Video issued successfully!");
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error issuing video. Please check your input.");
        }
    }


    private void displayCustomers() {
        try {
            customerTableModel.setColumnCount(0);
            customerTableModel.setRowCount(0);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");
            ResultSetMetaData metaData = resultSet.getMetaData();

            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                customerTableModel.addColumn(metaData.getColumnLabel(i));
            }

            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                customerTableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching customers data.");
        }
    }

    private void displayVideos() {
        try {
            videoTableModel.setColumnCount(0);
            videoTableModel.setRowCount(0);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM videos");
            ResultSetMetaData metaData = resultSet.getMetaData();

            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                videoTableModel.addColumn(metaData.getColumnLabel(i));
            }

            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                videoTableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching videos data.");
        }
    }

    private void displayIssuedVideos() {
        try {
            issuedVideoTableModel.setColumnCount(0);
            issuedVideoTableModel.setRowCount(0);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM issued_videos");
            ResultSetMetaData metaData = resultSet.getMetaData();

            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                issuedVideoTableModel.addColumn(metaData.getColumnLabel(i));
            }

            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                issuedVideoTableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching issued videos data.");
        }
    }
}