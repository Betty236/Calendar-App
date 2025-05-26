import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.*;

public class ReminderDialog extends JDialog {
    private JTextField nameField;
    private JComboBox<Integer> hourComboBox, minuteComboBox;
    private JTextArea descriptionArea;
    private JSpinner datePicker; 
    private final LocalDate reminderDate;
    private final EventManger eventManger;

    // Constructor to initialize the dialog window
    public ReminderDialog(JFrame parentFrame, LocalDate date, EventManger eventManger) {
        super(parentFrame, "Add New Reminder", true);
        this.reminderDate = date;
        this.eventManger = eventManger;

        setupForm();
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(parentFrame);
    }

    // Sets up the form by creating and arranging the components in the dialog
    private void setupForm() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);

        JButton addButton = createAddButton();
        add(addButton, BorderLayout.SOUTH);
    }

    // Creates the main panel containing the input fields
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        nameField = createStyledTextField();
        mainPanel.add(createLabelAndFieldPanel("Reminder Name:", nameField));

        hourComboBox = createHourComboBox();
        minuteComboBox = createMinuteComboBox();
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timePanel.setBackground(Color.WHITE);
        timePanel.add(hourComboBox);
        timePanel.add(new JLabel(":"));
        timePanel.add(minuteComboBox);
        mainPanel.add(createLabelAndFieldPanel("Remind Time:", timePanel));
       
        // Date picker 
        datePicker = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(datePicker, "dd/MM/yyyy");
        datePicker.setEditor(dateEditor);
        datePicker.setValue(java.sql.Date.valueOf(reminderDate)); // Set to the initial event date
        styleSpinner(datePicker);
        mainPanel.add(createLabelAndFieldPanel("Reminder Date:", datePicker)); 
        
        

        descriptionArea = createTextArea();
        mainPanel.add(createLabelAndFieldPanel("Description:", descriptionArea));

        return mainPanel;
    }
    
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

    // Creates the "Add Reminder" button at the bottom of the dialog
    private JButton createAddButton() {
        JButton addButton = new JButton("Add Reminder");
        styleButton(addButton);
        addButton.addActionListener(e -> addReminder());
        return addButton;
    }

    // Adding new reminder
    private void addReminder() {
        try {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a name for the reminder.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int hour = (int) hourComboBox.getSelectedItem();
            int minute = (int) minuteComboBox.getSelectedItem();
            LocalTime remindTime = LocalTime.of(hour, minute);

            String description = descriptionArea.getText();

            Reminder newReminder = new Reminder(name, reminderDate, "DefaultColor", remindTime, remindTime, description, remindTime);
            eventManger.addEvent(newReminder);

            dispose(); // Close the dialog after adding the reminder

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
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

    // Creates a styled label with the given text
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Roboto", Font.BOLD, 18));
        label.setForeground(new Color(0, 102, 102));
        return label;
    }

    // Creates a styled text field with a consistent appearance
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Roboto", Font.PLAIN, 18));
        textField.setForeground(new Color(0, 102, 102));
        textField.setBorder(BorderFactory.createLineBorder(new Color(229, 255, 254), 1));
        textField.setBackground(new Color(229, 255, 254));
        return textField;
    }

    // Helper method to create a styled text area
    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea(3, 20);
        textArea.setFont(new Font("Roboto", Font.ITALIC, 18));
        textArea.setForeground(new Color(0, 102, 102));
        textArea.setBorder(BorderFactory.createLineBorder(new Color(229, 255, 254), 1));
        textArea.setBackground(new Color(229, 255, 254));
        return textArea;
    }

    // Styles the combo box to match the application's theme
    private JComboBox<Integer> createHourComboBox() {
        JComboBox<Integer> comboBox = new JComboBox<>();
        for (int i = 0; i < 24; i++) {
            comboBox.addItem(i);
        }
        styleComboBox(comboBox);
        return comboBox;
    }

    private JComboBox<Integer> createMinuteComboBox() {
        JComboBox<Integer> comboBox = new JComboBox<>();
        for (int i = 0; i < 60; i += 5) {
            comboBox.addItem(i);
        }
        styleComboBox(comboBox);
        return comboBox;
    }

    // Styles the combo box
    private void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(new Font("Roboto", Font.PLAIN, 18));
        comboBox.setForeground(new Color(0, 102, 102));
        comboBox.setBackground(new Color(229, 255, 254));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(229, 255, 254), 1));
    }

    // Styles the button
    private void styleButton(JButton button) {
        button.setFont(new Font("Roboto", Font.BOLD, 18));
        button.setBackground(new Color(84, 155, 155));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(64, 135, 135)); // Slightly darker on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(84, 155, 155)); // Original color when not hovering
            }
        });
    }
     
}
