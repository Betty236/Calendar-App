import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.*;

public class EventDialog extends JDialog {
    private JTextField nameField;
    private JComboBox<String> colorField;
    private JComboBox<Integer> startHourComboBox, startMinuteComboBox, endHourComboBox, endMinuteComboBox;
    private JSpinner datePicker; 
    private JTextField locationField, descriptionField;
    private JComboBox<Integer> durationComboBox;
    private JCheckBox publicHolidayCheckBox;
    private JTextArea descriptionArea;
    private JComboBox<String> eventTypeComboBox;
    private JPanel cardPanel;
    LocalTime startTime;
    private final LocalDate eventDate;
    private final EventManger eventManger;
    private WinDayView dayView;

    // Constructor to initialize the dialog window
    public EventDialog(JFrame parentFrame, LocalDate date, EventManger EventManger, WinDayView dayView) {
        super(parentFrame, "Add New Event", true);
        this.eventDate = date;
        this.eventManger = EventManger;
        this.dayView = dayView; 

        setupForm();
        setSize(400, 500); 
        setResizable(false);
        setLocationRelativeTo(parentFrame);
    }


    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    
    // Sets up the form by creating and arranging the components in the dialog
    private void setupForm() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);

        JPanel descriptionPanel = createDescriptionPanel();
        add(descriptionPanel, BorderLayout.SOUTH);

        JButton addButton = createAddButton();
        add(addButton, BorderLayout.SOUTH);

        switchCard(); 
    }

    // Creates the main panel containing two columns of input fields
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        return mainPanel;
    }

    // Creates the left panel with input fields for general event details
    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        leftPanel.setBackground(Color.WHITE);

        eventTypeComboBox = createComboBox();
        leftPanel.add(createLabelAndFieldPanel("Event Type:", eventTypeComboBox));
        leftPanel.add(createLabelAndFieldPanel("Event Name:", nameField = createStyledTextField()));

        // Date picker 
        datePicker = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(datePicker, "dd/MM/yyyy");
        datePicker.setEditor(dateEditor);
        datePicker.setValue(java.sql.Date.valueOf(eventDate)); // Set to the initial event date
        styleSpinner(datePicker);
        leftPanel.add(createLabelAndFieldPanel("Event Date:", datePicker)); 

        // Start time 
        startHourComboBox = createHourComboBox();
        startMinuteComboBox = createMinuteComboBox();
        JPanel startTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        startTimePanel.setBackground(Color.WHITE);
        startTimePanel.add(startHourComboBox);
        startTimePanel.add(new JLabel(":"));
        startTimePanel.add(startMinuteComboBox);
        leftPanel.add(createLabelAndFieldPanel("Start Time:", startTimePanel));

        // End time 
        endHourComboBox = createHourComboBox();
        endMinuteComboBox = createMinuteComboBox();
        JPanel endTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        endTimePanel.setBackground(Color.WHITE);
        endTimePanel.add(endHourComboBox);
        endTimePanel.add(new JLabel(":"));
        endTimePanel.add(endMinuteComboBox);
        leftPanel.add(createLabelAndFieldPanel("End Time:", endTimePanel));

        return leftPanel;
    }

    // Creates the right panel with input fields specific to the selected event type (Appointment or Holiday)
    private JPanel createRightPanel() {
    	 JPanel rightPanel = new JPanel(new GridLayout(0, 1, 10, 10));
         rightPanel.setBackground(Color.WHITE);

         // Color picker
         colorField = new JComboBox<>(new String[]{
             "Red", "Green", "Blue", "Yellow", "Cyan", "Magenta", "Orange", "Purple", "Gray", "Black"
         });
         styleComboBox(colorField);
         rightPanel.add(createLabelAndFieldPanel("Color:", colorField));

         cardPanel = new JPanel(new CardLayout());
         cardPanel.setBackground(Color.WHITE);

         // Event type
         cardPanel.add(createAppointmentPanel(), "Appointment");
         cardPanel.add(createHolidayPanel(), "Holiday");

         rightPanel.add(cardPanel);
         return rightPanel;
    }

    // Creates the panel for Appointment-specific input fields
    private JPanel createAppointmentPanel() {
        JPanel appointmentPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        appointmentPanel.setBackground(Color.WHITE);

        locationField = createStyledTextField();
        descriptionField = createStyledTextField();
        appointmentPanel.add(createLabelAndFieldPanel("Location:", locationField));
        appointmentPanel.add(createLabelAndFieldPanel("Description:", descriptionField));

        return appointmentPanel;
    }

    // Creates the panel for Holiday-specific input fields
    private JPanel createHolidayPanel() {
        JPanel holidayPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        holidayPanel.setBackground(Color.WHITE);
        
        durationComboBox = new JComboBox<>();
        for (int i = 1; i <= 30; i++) {
            durationComboBox.addItem(i);
        }
        styleComboBox(durationComboBox);

        publicHolidayCheckBox = new JCheckBox("Is Public Holiday?");
        publicHolidayCheckBox.setFont(new Font("Roboto", Font.PLAIN, 18));
        publicHolidayCheckBox.setBackground(Color.WHITE);
        publicHolidayCheckBox.setForeground(new Color(0, 102, 102));

        holidayPanel.add(createLabelAndFieldPanel("Duration (Days):", durationComboBox));
        holidayPanel.add(publicHolidayCheckBox);

        return holidayPanel;
    }

    // Creates the description panel at the bottom of the dialog
    private JPanel createDescriptionPanel() {
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBackground(Color.WHITE);

        descriptionPanel.add(createStyledLabel("Description:"), BorderLayout.NORTH);

        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setFont(new Font("Roboto", Font.ITALIC, 18));
        descriptionArea.setForeground(new Color(0, 102, 102));
        descriptionArea.setBorder(BorderFactory.createLineBorder(new Color(229, 255, 254), 1));
        descriptionArea.setBackground(new Color(229, 255, 254));

        descriptionPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        return descriptionPanel;
    }

    // Creates the "Add Event" button at the bottom of the dialog
    private JButton createAddButton() {
        JButton addButton = new JButton("Add Event");
        styleButton(addButton);
        addButton.addActionListener(this::addEvent);
        return addButton;
    }

    // Utility method to create a panel with a label above an input field
    private JPanel createLabelAndFieldPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);

        JLabel label = createStyledLabel(labelText);
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    // Creates the combo box for selecting the event type
    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Appointment", "Holiday"});
        styleComboBox(comboBox);
        comboBox.addActionListener(e -> switchCard());
        return comboBox;
    }

    // Switches the visible panel based on the selected event type
    private void switchCard() {
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
        String selectedCard = (String) eventTypeComboBox.getSelectedItem();
        cardLayout.show(cardPanel, selectedCard);
        validate();
        repaint();
    }

    // Adding new event dialog
    private void addEvent(ActionEvent e) {
        try {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a name for the event.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Get event color
            String color = (String) colorField.getSelectedItem();
            
            // Get the event date
            LocalDate selectedDate = ((java.util.Date) datePicker.getValue()).toInstant()
                                      .atZone(java.time.ZoneId.systemDefault())
                                      .toLocalDate();
    
            // Get start time
            int startHour = (int) startHourComboBox.getSelectedItem();
            int startMinute = (int) startMinuteComboBox.getSelectedItem();
            LocalTime startTime = LocalTime.of(startHour, startMinute);
    
            // Get end time
            int endHour = (int) endHourComboBox.getSelectedItem();
            int endMinute = (int) endMinuteComboBox.getSelectedItem();
            LocalTime endTime = LocalTime.of(endHour, endMinute);
    
            if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
                showErrorDialog("Start time must be before end time.");
                return;
            }
    

            String selectedType = (String) eventTypeComboBox.getSelectedItem();
    
            if ("Appointment".equals(selectedType)) {
                String location = locationField.getText();
                String description = descriptionField.getText();
                Appointment newAppointment = new Appointment(name, selectedDate , color, startTime, endTime, description, location, description);
                eventManger.addEvent(newAppointment);
            } else if ("Holiday".equals(selectedType))  {
                int duration = (int) durationComboBox.getSelectedItem();
                boolean isPublicHoliday = publicHolidayCheckBox.isSelected();

                // Create a holiday for each day in the duration
                for (int i = 0; i < duration; i++) {
                    LocalDate holidayDate = selectedDate.plusDays(i);
                    //when duration is more then a day days make them full days and ignore time start and end
                    if (duration >1 ) {
                        LocalTime holidayStartTime = LocalTime.of(0, 0);
                        LocalTime holidayEndTime = LocalTime.of(23, 55);
                        Holiday newHoliday = new Holiday(name, holidayDate, color, holidayStartTime, holidayEndTime, "Holiday", duration, isPublicHoliday);
                        eventManger.addEvent(newHoliday);
                    }
                    else {
                    	LocalTime holidayStartTime = LocalTime.of(startHour, startMinute);
                        LocalTime holidayEndTime = LocalTime.of(endHour, endMinute);
                        Holiday newHoliday = new Holiday(name, holidayDate, color, holidayStartTime, holidayEndTime, "Holiday", duration, isPublicHoliday);
                        eventManger.addEvent(newHoliday);
                    }
                    
                }
            }
            
            dispose(); // Close the dialog after adding the event
    
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Utility method to create a styled label
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Roboto", Font.PLAIN, 18));
        label.setForeground(new Color(0, 102, 102));
        return label;
    }

    // Utility method to create a styled text field
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Roboto", Font.PLAIN, 18));
        textField.setForeground(new Color(0, 102, 102));
        textField.setBorder(BorderFactory.createLineBorder(new Color(229, 255, 254), 1));
        textField.setBackground(new Color(229, 255, 254));
        return textField;
    }


    // Helper method to create hour combo boxes
    private JComboBox<Integer> createHourComboBox() {
        JComboBox<Integer> comboBox = new JComboBox<>();
        for (int i = 0; i < 24; i++) {
            comboBox.addItem(i);
        }
        styleComboBox(comboBox);
        return comboBox;
    }

 // Helper method to create minute combo boxes
    private JComboBox<Integer> createMinuteComboBox() {
        JComboBox<Integer> comboBox = new JComboBox<>();
        for (int i = 0; i < 60; i += 5) {
            comboBox.addItem(i);
        }
        styleComboBox(comboBox);
        return comboBox;
    }

    // Utility method to style a combo box
    private void styleComboBox(JComboBox<?> comboBox) 
    {
        comboBox.setFont(new Font("Roboto", Font.PLAIN, 18));
        comboBox.setForeground(new Color(0, 102, 102));
        comboBox.setBackground(new Color(229, 255, 254));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(229, 255, 254), 1));
    }

    // Utility method to style a button
    private void styleButton(JButton button) 
    {
        button.setFont(new Font("Roboto", Font.BOLD, 18));
        button.setBackground(new Color(84, 155, 155));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(64, 135, 135));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(84, 155, 155));
            }
        });
    }


    // Utility method to style a spinner 
    private void styleSpinner(JSpinner spinner) 
    {
        spinner.setFont(new Font("Roboto", Font.PLAIN, 18));
        spinner.setForeground(new Color(0, 102, 102));
        spinner.setBackground(new Color(229, 255, 254));
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) 
        {
            JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor) editor;
            defaultEditor.getTextField().setBackground(new Color(229, 255, 254));
            defaultEditor.getTextField().setForeground(new Color(0, 102, 102));
            defaultEditor.getTextField().setBorder(BorderFactory.createLineBorder(new Color(229, 255, 254), 1));
        }
    }
    
    private void showErrorDialog(String message) {
        // Create a custom panel for the message
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create a custom label with the message
        JLabel label = new JLabel(message);
        label.setFont(new Font("Roboto", Font.PLAIN, 18));
        label.setForeground(new Color(0, 102, 102));
        
        panel.add(label, BorderLayout.CENTER);
    
        // Customize the OK button
        UIManager.put("OptionPane.background", new Color(229, 255, 254));
        UIManager.put("Panel.background", new Color(229, 255, 254));
        UIManager.put("Button.background", new Color(84, 155, 155));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", new Font("Roboto", Font.BOLD, 18));
    
        JOptionPane.showMessageDialog(this, panel, "Input Error", JOptionPane.ERROR_MESSAGE);
    }
    
 // Method to close the dialog
    private void closeDialog() {
        dispose(); 
    }

    // Method for if successfully added or the user cancels the dialog
    private void onEventAdded() {
        closeDialog(); // Close the dialog after the event is added
    }
}
