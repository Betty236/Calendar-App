import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class CalendarApp {

    private static EventManger eventManger = new EventManger();
    private static YearMonth currentYearMonth; // Track the currently displayed month
    private static JLabel monthLabel; // Label to display the current month and year
    private static JPanel calendarPanel; // Panel for the days of the month to display
    
    // Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalendarApp::createAndShowGUI);
    }

    // Set the main view and components of the GUI
    private static void createAndShowGUI() {
        eventManger = new EventManger();
        JFrame frame = initializeFrame();
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Initialize current year and month
        currentYearMonth = YearMonth.now();
        JPanel titlePanel = createTitlePanel();
        calendarPanel = createCalendarPanel();
        JPanel sidebarPanel = createSidebarPanel(); // Create the sidebar

        // Add components to the main panel
        mainPanel.add(sidebarPanel, BorderLayout.WEST); // Add the sidebar to the left
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(calendarPanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    // Create the sidebar panel with a modern style that matches the calendar theme
    private static JPanel createSidebarPanel() {
    JPanel sidebarPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            // Gradient background matching the calendar theme
            GradientPaint gp = new GradientPaint(0, 0, new Color(229, 255, 254), width, height, new Color(84, 155, 155));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    };

    // Set fixed width and stretch the height
    sidebarPanel.setPreferredSize(new Dimension(250, 0)); // Fixed width, height will stretch
    sidebarPanel.setLayout(new BorderLayout()); // Use BorderLayout for vertical stretching
    sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the buttons

    // Create a panel to hold the buttons and align them vertically
    JPanel buttonContainer = new JPanel();
    buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS)); // Vertical arrangement
    buttonContainer.setOpaque(false); // Transparent background so the gradient shows

    // Create buttons to manage events and reminders
    JButton showAllEventsButton = createSidebarButton("All Events");
    JButton showAllRemindersButton = createSidebarButton("All Reminders");
    JButton createEventButton = createSidebarButton("Add Event");
    JButton createReminderButton = createSidebarButton("Add Reminder");

    // Add buttons to the button container panel
    buttonContainer.add(showAllEventsButton);
    buttonContainer.add(Box.createVerticalStrut(20)); // Add space between buttons
    buttonContainer.add(showAllRemindersButton);
    buttonContainer.add(Box.createVerticalStrut(20)); // Add space between buttons
    buttonContainer.add(createEventButton);
    buttonContainer.add(Box.createVerticalStrut(20)); // Add space between buttons
    buttonContainer.add(createReminderButton);

    sidebarPanel.add(buttonContainer, BorderLayout.NORTH); // Align buttons to the top

    showAllEventsButton.addActionListener(e -> showAllEvents());
    showAllRemindersButton.addActionListener(e -> showAllReminders());
    createEventButton.addActionListener(e -> openNewEventDialog());
    createReminderButton.addActionListener(e -> openNewReminderDialog());
    return sidebarPanel;
}
    
    // Method to create new event
    private static void openNewEventDialog() {
        EventDialog eventDialog = new EventDialog(null, LocalDate.now(), eventManger, null);
        eventDialog.setVisible(true);

        // Use a WindowListener to trigger the refresh when the dialog closes
        eventDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                System.out.println("Event dialog closed. Refreshing calendar...");
                refreshCalendar();
            }
        });
    }
    
    public static void refreshCalendar() {
        System.out.println("Refreshing calendar...");
        
        // Clear the calendar panel
        calendarPanel.removeAll();
        
        // Re-add the day labels and days of the month
        addDayLabels(calendarPanel);
        addDaysOfMonth(calendarPanel);
        
        // Force the frame to update
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private static void openNewReminderDialog() {
    	ReminderDialog reminderDialog = new ReminderDialog(null, LocalDate.now(), eventManger);
    	reminderDialog.setVisible(true);

        // After the dialog is closed, refresh the calendar if necessary
    	refreshCalendar();
}

    // Method to show all reminders
    private static void showAllReminders() {
    	ArrayList<Reminder> AllReminders = new ArrayList<>();
    	AllReminders.addAll(eventManger.getAllRem());  // Add all reminders
		
        ReminderListView reminderListView = new ReminderListView(null, eventManger);
        reminderListView.setVisible(true);
}

    // Customize the sidebar buttons to be larger, centered, and with a white background
    private static JButton createSidebarButton(String buttonName) 
    {
        JButton newButton = new JButton(buttonName);
        newButton.setFont(new Font("Roboto", Font.BOLD, 20)); 
        newButton.setForeground(new Color(0, 102, 102)); // Dark teal color 
        newButton.setFocusPainted(false);
        newButton.setContentAreaFilled(true); // paint the button background 
        newButton.setOpaque(true);
        newButton.setBackground(Color.WHITE); // White background for buttons
        newButton.setBorder(BorderFactory.createLineBorder(Color.white, 2)); 
        newButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Set the button size and alignment
        newButton.setPreferredSize(new Dimension(200, 50)); 
        newButton.setMaximumSize(new Dimension(200, 50)); 
        newButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        
        // Add hover effect
        newButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                newButton.setBackground(new Color(229, 255, 254)); 
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                newButton.setBackground(Color.WHITE);
            }
        });

        return newButton;
    }

    // Sets main window properties
    private static JFrame initializeFrame() {
        JFrame frame = new JFrame("Date Mate");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1500, 1000));
        frame.setLocationRelativeTo(null);
        return frame;
    }

    // Create the title and "next/current/previous" month buttons
    private static JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);

        // Create a panel for the buttons with GridLayout to ensure equal sizing
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0)); // 1 row, 3 columns, 10px horizontal gap
        buttonPanel.setBackground(Color.WHITE);

        JButton prevMonthButton = createNewMonthButton("<< Previous");
        prevMonthButton.addActionListener(e -> navigateToMonth(-1));

        JButton currentMonthButton = createNewMonthButton("Current Month");
        currentMonthButton.addActionListener(e -> navigateToCurrentMonth());

        JButton nextMonthButton = createNewMonthButton("Next >>");
        nextMonthButton.addActionListener(e -> navigateToMonth(1));

        // Add all three buttons to the buttonPanel
        buttonPanel.add(prevMonthButton);
        buttonPanel.add(currentMonthButton);
        buttonPanel.add(nextMonthButton);

        // Create the month label
        monthLabel = new JLabel(getMonthYearLabel(), JLabel.CENTER);
        monthLabel.setVerticalAlignment(SwingConstants.CENTER);
        monthLabel.setFont(new Font("Roboto", Font.BOLD, 28)); 
        monthLabel.setForeground(Color.GRAY); 

        // Add components to the title panel
        titlePanel.add(buttonPanel, BorderLayout.NORTH); // Add the button panel to the top
        titlePanel.add(monthLabel, BorderLayout.CENTER); // Add month label below the buttons

        return titlePanel;
    }

    // Customize the month navigation buttons
    private static JButton createNewMonthButton(String buttonName) {
        JButton newButton = new JButton(buttonName);
        newButton.setFont(new Font("Roboto", Font.BOLD, 20));
        newButton.setBackground(new Color(100, 180, 180)); 
        newButton.setForeground(Color.WHITE); 
        newButton.setFocusPainted(false);
        newButton.setBorder(new LineBorder(Color.WHITE, 2, true));
        newButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        newButton.setContentAreaFilled(false);
        newButton.setOpaque(true);
        newButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                newButton.setBackground(new Color(80, 160, 160)); // Slightly darker on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                newButton.setBackground(new Color(100, 180, 180)); // Original color when not hovering
            }
        });
        return newButton;
    }

    // Set the window label by the date
    private static String getMonthYearLabel() {
        return currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentYearMonth.getYear();
    }

    // Create layout for the days
    private static JPanel createCalendarPanel() {
        JPanel calendarPanel = new JPanel(new GridLayout(0, 7));
        calendarPanel.setBackground(Color.WHITE); // Light background
        addDayLabels(calendarPanel);
        addDaysOfMonth(calendarPanel);
        return calendarPanel;
    }

    // Add day of the week labels
    private static void addDayLabels(JPanel calendarPanel) {
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            JLabel dayLabel = new JLabel(day, JLabel.CENTER);
            dayLabel.setFont(new Font("Roboto", Font.ITALIC, 30)); // Modern font, lighter weight
            dayLabel.setOpaque(true);
            dayLabel.setBackground(Color.WHITE); // Light gray background for day labels
            dayLabel.setForeground(Color.GRAY); // Dark gray text color
            calendarPanel.add(dayLabel);
        }
    }

    // Add the day buttons
    private static void addDaysOfMonth(JPanel calendarPanel) {
        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        int daysInMonth = currentYearMonth.lengthOfMonth();

        // Adjust day of week index for display purposes (1=Monday, 7=Sunday)
        int adjustedDayOfWeek = dayOfWeek == 7 ? 0 : dayOfWeek;

        // Fill in empty cells before the first day of the month
        for (int i = 0; i < adjustedDayOfWeek; i++) {
            JLabel emptyLabel = new JLabel();
            emptyLabel.setOpaque(true); // Make the label opaque so the background color is visible
            emptyLabel.setBackground(Color.WHITE); 
            calendarPanel.add(emptyLabel);
        }

        // Fill in the days of the month
        for (int day = 1; day <= daysInMonth; day++) {
            JButton dayButton = createDayButton(day);
            calendarPanel.add(dayButton);
        }
    }

    // Create one day button
    private static JButton createDayButton(int day) {
        JButton dayButton = new JButton(String.valueOf(day));
        dayButton.setFont(new Font("Roboto", Font.BOLD, 18)); 
        dayButton.setBackground(new Color(220, 240, 240)); 
        dayButton.setFocusPainted(false);
        dayButton.setBorder(new LineBorder(Color.WHITE, 2, true));
        dayButton.setForeground(new Color(0, 102, 102)); 
        dayButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Get the date for this day button
        LocalDate date = currentYearMonth.atDay(day);

        // Check for events on this date
        List<Event> eventsForDay = eventManger.getAllEventsForDay(date);
        System.out.println("Day: " + day + " | Date: " + date + " | Events Found: " + eventsForDay.size()); // Debugging line

        if (!eventsForDay.isEmpty()) {
            Event event = eventsForDay.get(0);
            String eventColor = event.getColor();
            System.out.println("Event on day " + day + ": " + event.getName() + " | Color: " + eventColor); // Debugging line
            Color color = getColorFromString(eventColor);
            System.out.println("Parsed Color: " + color); // Debugging line
            dayButton.setBackground(color);
        }

        // Add hover effect
        dayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                dayButton.setBackground(Color.WHITE); // White on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Re-apply the event color (or default color if no event) when the mouse exits
                if (!eventsForDay.isEmpty()) {
                    dayButton.setBackground(getColorFromString(eventsForDay.get(0).getColor()));
                } else {
                    dayButton.setBackground(new Color(220, 240, 240)); // Original color
                }
            }
        });

        dayButton.addActionListener(e -> openDayWindow(day));
        return dayButton;
    }
    
    private static Color getColorFromString(String colorStr) {
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
                case "cyan":
                    return Color.CYAN;
                case "gray":
                    return Color.GRAY;
                case "black":
                    return Color.BLACK;
                case "white":
                    return Color.WHITE;
                default:
                    System.out.println("Unknown color: " + colorStr);
                    return Color.WHITE; // Default color if no match found
            }
        }
    }

    // Takes to the wanted month
    private static void navigateToMonth(int monthChange) {
        currentYearMonth = currentYearMonth.plusMonths(monthChange);

        // Update month label
        monthLabel.setText(getMonthYearLabel());

        // Refresh the calendar panel
        calendarPanel.removeAll();
        addDayLabels(calendarPanel);
        addDaysOfMonth(calendarPanel);
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    // Takes to the current month
    private static void navigateToCurrentMonth() {
        currentYearMonth = YearMonth.now();

        // Update month label
        monthLabel.setText(getMonthYearLabel());

        // Refresh the calendar panel
        calendarPanel.removeAll();
        addDayLabels(calendarPanel);
        addDaysOfMonth(calendarPanel);
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    // Open the day view
    private static void openDayWindow(int day) 
    {
        String monthName = currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());

        // Create and display the WinDayView window
        new WinDayView(day, monthName, eventManger);
    }

    private static void showAllEvents() {
        ArrayList<Event> allEvents = new ArrayList<>();
        allEvents.addAll(eventManger.getAllAppo()); // Add all appointments
        allEvents.addAll(eventManger.getAllholi()); // Add all holidays
    
        EventListView eventListView = new EventListView(null, eventManger);
        eventListView.setVisible(true);
    }
    
}
