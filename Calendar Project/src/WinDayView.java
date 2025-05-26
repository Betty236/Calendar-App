// Import necessary package
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;


public class WinDayView extends JFrame {
    private EventManger eventManger;
    private ArrayList<Appointment> appointment1;
    private int originalDay;
    private String originalMonthName;
    private LocalDate originalDate;

    // Set up the initial view
    public WinDayView(int day, String monthName, EventManger eventManger) {
        this.eventManger = eventManger;
        this.originalDate = LocalDate.of(LocalDate.now().getYear(), Month.valueOf(monthName.toUpperCase()), day);
        this.appointment1 = new ArrayList<>();
        setupWindow(day, monthName);
        JPanel topPanel = createTopPanel(day, monthName);
        add(topPanel, BorderLayout.NORTH);
        setupHourButtonsPanel();
        setupContentPanel();
        setVisible(true);
    }
    
    // Sets up the main window properties
    private void setupWindow(int day, String monthName) {
        String windowTitle = setWindowLabel(day, monthName);
        setTitle(windowTitle);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(700, 800));
        setLocationRelativeTo(null);
    }

    // Creates the top panel containing the date label and action buttons.
    private JPanel createTopPanel(int day, String monthName) {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JLabel dayDateLabel = createDayDateLabel(day, monthName);
        topPanel.add(dayDateLabel, BorderLayout.WEST);

        JPanel buttonPanel = createButtonPanel();
        topPanel.add(buttonPanel, BorderLayout.EAST);

        return topPanel;
    }

    // Creates and returns a label for the date
    private JLabel createDayDateLabel(int day, String monthName) {
        String windowTitle = setWindowLabel(day, monthName);
        JLabel dayDateLabel = new JLabel(windowTitle, JLabel.LEFT);
        dayDateLabel.setFont(new Font("Serif", Font.ITALIC, 28));
        dayDateLabel.setForeground(Color.GRAY);
        dayDateLabel.setOpaque(true);
        dayDateLabel.setBackground(Color.WHITE);
        return dayDateLabel;
    }

    // Creates the "Add Event" and "Add Reminder" buttons.
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton addEventButton = new JButton("Add Event");
        styleButton(addEventButton);
        addEventButton.addActionListener(e -> showAddEventDialog(null)); // No specific time
        buttonPanel.add(addEventButton);

        JButton addReminderButton = new JButton("Add Reminder");
        styleButton(addReminderButton);
        addReminderButton.addActionListener(e -> showAddReminderDialog());
        buttonPanel.add(addReminderButton);

        return buttonPanel;
    }

    // Method to show the Add Reminder Dialog
    private void showAddReminderDialog() {
        ReminderDialog reminderDialog = new ReminderDialog(this, LocalDate.now(), eventManger);
        reminderDialog.setVisible(true);
    }

        // Method to show the Add Event Dialog with an optional start time
        private void showAddEventDialog(LocalTime startTime) {
            EventDialog eventDialog = new EventDialog(this, originalDate, eventManger, this); // Pass the correct date
            if (startTime != null) {
                eventDialog.setStartTime(startTime);
            }

            eventDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    refreshHourButtonsPanel();
                }
            });

            eventDialog.setVisible(true);
        }

        // Method to refresh the hour buttons panel
        private void refreshHourButtonsPanel() {
            getContentPane().removeAll(); // Remove all components

            // Recreate and add the top panel and hour buttons panel using the original day and month
            JPanel topPanel = createTopPanel(originalDate.getDayOfMonth(), originalDate.getMonth().toString());
            add(topPanel, BorderLayout.NORTH);

            setupHourButtonsPanel(); // Recreate the hour buttons panel

            revalidate(); // Revalidate the frame to apply the changes
            repaint();    // Repaint the frame to display the changes
        }



    // Configures the main content
    private void setupHourButtonsPanel() {
        HourButtonsPanel hourButtonsPanel = new HourButtonsPanel();
        JScrollPane scrollPane = new JScrollPane(hourButtonsPanel);
        customizeScrollBars(scrollPane);

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Scroll to the position where 06:00 is at the top
        SwingUtilities.invokeLater(() ->
            scrollPane.getVerticalScrollBar().setValue(hourButtonsPanel.getHeight() / 24 * 6)
        );

        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupContentPanel() {
        if (appointment1.isEmpty()) {
            // Exit the method if there are no appointments
            return;
        }

        JPanel contentPanel = new JPanel(new BorderLayout());

        add(contentPanel, BorderLayout.CENTER);
    }

    // Customizes the scroll bars
    private void customizeScrollBars(JScrollPane scrollPane) {
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new CustomScrollBarUI());

        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setUI(new CustomScrollBarUI());
    }

    // Style the "Add" buttons
    private void styleButton(JButton button) {
        button.setFont(new Font("Roboto", Font.BOLD, 19));
        button.setBackground(new Color(84, 155, 155));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
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

    // Set window date title
    private String setWindowLabel(int day, String monthName) {
        String label;
        if (day % 100 >= 11 && day % 100 <= 13) {
            label = monthName + " " + day + "th";
        } else {
            switch (day % 10) {
                case 1 -> label = monthName + " " + day + "st";
                case 2 -> label = monthName + " " + day + "nd";
                case 3 -> label = monthName + " " + day + "rd";
                default -> label = monthName + " " + day + "th";
            }
        }
        return label;
    }


    


    // Custom ScrollBarUI class
    class CustomScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(84, 155, 155); // Color of the scroll thumb
            this.trackColor = new Color(229, 255, 254); // Color of the scroll track
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }
    }


    // Custom JPanel class to create hour buttons
class HourButtonsPanel extends JPanel {
    private int hourHeight = 50; // Set a fixed height for each hour
    private int hours = 24; // Define the number of hours to display

    public HourButtonsPanel() {
        setLayout(new GridLayout(hours, 1)); // GridLayout with one column and 'hours' rows

        // Use the original date to retrieve events
        List<Event> eventsForDay = eventManger.getAllEventsForDay(originalDate);

        for (int i = 0; i < hours; i++) {
            int hourToDisplay = i; // Hours start from 00:00 to 23:00
            LocalTime startTime = LocalTime.of(hourToDisplay, 0);

            // Create a button with custom layout
            JButton hourButton = new JButton(String.format("%02d:00", hourToDisplay));
            hourButton.setFont(new Font("Roboto", Font.ITALIC, 18));
            hourButton.setHorizontalAlignment(SwingConstants.LEFT);
            hourButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220)),
                    new EmptyBorder(10, 20, 10, 20)));
            hourButton.setFocusPainted(false);
            hourButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            hourButton.setBackground(Color.WHITE); // Default background

            // Check if this hour falls within the duration of any event
            for (Event event : eventsForDay) {
                if (!event.getStartTime().isAfter(startTime) && !event.getEndTime().isBefore(startTime)) {
                    hourButton.setText(String.format("%02d:00", hourToDisplay) + " " + event.getName()); // Set the event name with time
                    hourButton.setBackground(getColorFromString(event.getColor())); // Set the button's background color
                    hourButton.setForeground(Color.WHITE); // Set the text color to white for better contrast
                    break; // Stop after finding the first matching event
                }
            }

            hourButton.addActionListener(e -> showAddEventDialog(startTime)); // Pass the clicked hour as the start time

            add(hourButton); // Add each button to the panel
        }

        // Set preferred size to allow scrolling
        setPreferredSize(new Dimension(600, hours * hourHeight));
    }

    // Helper method to convert color string to Color object
    private Color getColorFromString(String colorStr) {
        try {
            // Try to decode as hex (e.g., "#FF5733")
            return Color.decode(colorStr);
        } catch (NumberFormatException e) {
            // If it's not a hex string, try to match by name
            switch (colorStr.toLowerCase()) {
                case "red":
                    return Color.RED;
                case "blue":
                    return Color.BLUE;
                case "green":
                    return Color.GREEN;
                case "yellow":
                    return Color.YELLOW;
                case "orange":
                    return Color.ORANGE;
                case "magenta":
                    return Color.MAGENTA;
                case "gray":
                    return Color.GRAY;
                case "black":
                    return Color.BLACK;
                case "white":
                    return Color.WHITE;
                case "cyan":
                    return Color.CYAN;
                default:
                    return Color.WHITE; // Default color if no match found
            }
        }
    }
}
}
