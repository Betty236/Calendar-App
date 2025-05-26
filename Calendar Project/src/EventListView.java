import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EventListView extends JDialog {

    private final EventManger eventManager;
    private final JPanel holidayPanel;
    private final JPanel appointmentPanel;

    public EventListView(JFrame parentFrame, EventManger eventManager) 
    {
        super(parentFrame, "All Events", true);
        this.eventManager = eventManager;
        this.holidayPanel = new JPanel();
        this.appointmentPanel = new JPanel();
        setupUI();
        setSize(800, 400);
        setLocationRelativeTo(parentFrame);
    }


    // Sets up the UI components and layout
    private void setupUI() 
    {
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); 

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        setupHolidayPanel();
        setupAppointmentPanel();

        mainPanel.add(new JScrollPane(holidayPanel));
        mainPanel.add(new JScrollPane(appointmentPanel));

        add(mainPanel, BorderLayout.CENTER);
    }


    // Sets up the holiday panel
    private void setupHolidayPanel() 
    {
        holidayPanel.setLayout(new BoxLayout(holidayPanel, BoxLayout.Y_AXIS));
        holidayPanel.setBackground(Color.WHITE);

        JLabel holidayLabel = new JLabel("Holidays:");
        holidayLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        holidayLabel.setForeground(new Color(0, 102, 102)); // Dark teal text color
        holidayPanel.add(holidayLabel);

        List<Holiday> holidays = eventManager.getAllholi();
        if (holidays.isEmpty()) {
            JLabel nothingLabel = new JLabel("No holidays to see here", SwingConstants.CENTER);
            nothingLabel.setFont(new Font("Roboto", Font.PLAIN, 18));
            nothingLabel.setForeground(new Color(0, 102, 102)); // Dark teal text color
            holidayPanel.add(nothingLabel);
        } else {
            for (Holiday holiday : holidays) {
                JPanel holidayRow = createEventRow(holiday);
                holidayPanel.add(holidayRow);
                holidayPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between rows
            }
        }
    }

    // Sets up the appointment panel
    private void setupAppointmentPanel() {
        appointmentPanel.setLayout(new BoxLayout(appointmentPanel, BoxLayout.Y_AXIS));
        appointmentPanel.setBackground(Color.WHITE);

        JLabel appointmentLabel = new JLabel("Appointments:");
        appointmentLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        appointmentLabel.setForeground(new Color(0, 102, 102)); // Dark teal text color
        appointmentPanel.add(appointmentLabel);

        List<Appointment> appointments = eventManager.getAllAppo();
        if (appointments.isEmpty()) {
            JLabel nothingLabel = new JLabel("No appointments to see here", SwingConstants.CENTER);
            nothingLabel.setFont(new Font("Roboto", Font.PLAIN, 18));
            nothingLabel.setForeground(new Color(0, 102, 102)); // Dark teal text color
            appointmentPanel.add(nothingLabel);
        } else {
            for (Appointment appointment : appointments) {
                JPanel appointmentRow = createEventRow(appointment);
                appointmentPanel.add(appointmentRow);
                appointmentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between rows
            }
        }
    }

    // Creates a row with an event button and a delete button
    private JPanel createEventRow(Event event) {
        JPanel eventRow = new JPanel(new BorderLayout());
        eventRow.setBackground(Color.WHITE);

        JButton eventButton = createEventButton(event);
        JButton deleteButton = createDeleteButton(event, eventRow);

        eventRow.add(eventButton, BorderLayout.CENTER);
        eventRow.add(deleteButton, BorderLayout.EAST);

        return eventRow;
    }

    // Creates a button for an event with an action listener to display event details
    private JButton createEventButton(Event event) {
        JButton eventButton = new JButton(event.getName() + " on " + event.getDate());
        eventButton.setFont(new Font("Roboto", Font.PLAIN, 18));
        eventButton.setForeground(new Color(0, 102, 102)); // Dark teal text color
        eventButton.setBackground(new Color(229, 255, 254)); // Light cyan background
        eventButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        eventButton.setFocusPainted(false);
        eventButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        eventButton.setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left
        eventButton.setToolTipText("Click to view details of " + event.getName()); // Tooltip

        // Set rounded corners
        eventButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Mouse hover effect
        eventButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                eventButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                eventButton.setBackground(new Color(229, 255, 254));
            }
        });

        eventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEventDetails(event);
            }
        });

        return eventButton;
    }


    private JButton createDeleteButton(Event event, JPanel eventRow) {
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Roboto", Font.PLAIN, 16));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(255, 102, 102)); // Red background
        deleteButton.setFocusPainted(false);
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show confirmation dialog
                int confirm = showConfirmDialog();
    
                if (confirm == JOptionPane.YES_OPTION) {
                    // Remove the event using EventManger's RemoveEvent method
                    boolean removed = eventManager.RemoveEvent(event);
    
                    if (removed) {
                        // Remove the event row from the UI
                        if (event instanceof Holiday) {
                            holidayPanel.remove(eventRow);
                            holidayPanel.revalidate();
                            holidayPanel.repaint();
    
                            // Check if there are any holidays left, if not, show a "No holidays" label
                            if (eventManager.getAllholi().isEmpty()) {
                                holidayPanel.removeAll();
                                JLabel nothingLabel = new JLabel("No holidays to see here", SwingConstants.CENTER);
                                nothingLabel.setFont(new Font("Roboto", Font.PLAIN, 18));
                                nothingLabel.setForeground(new Color(0, 102, 102)); // Dark teal text color
                                holidayPanel.add(nothingLabel);
                                holidayPanel.revalidate();
                                holidayPanel.repaint();
                            }
                        } else if (event instanceof Appointment) {
                            appointmentPanel.remove(eventRow);
                            appointmentPanel.revalidate();
                            appointmentPanel.repaint();
    
                            // Check if there are any appointments left, if not, show a "No appointments" label
                            if (eventManager.getAllAppo().isEmpty()) {
                                appointmentPanel.removeAll();
                                JLabel nothingLabel = new JLabel("No appointments to see here", SwingConstants.CENTER);
                                nothingLabel.setFont(new Font("Roboto", Font.PLAIN, 18));
                                nothingLabel.setForeground(new Color(0, 102, 102)); // Dark teal text color
                                appointmentPanel.add(nothingLabel);
                                appointmentPanel.revalidate();
                                appointmentPanel.repaint();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to remove event. Event might not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    
        return deleteButton;
    }
    

       
    // Custom method to display event details
    private void showEventDetails(Event event) {
        JDialog dialog = new JDialog(this, "Event Details", true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.WHITE);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JLabel messageLabel = new JLabel("<html>" + getEventDetails(event).replace("\n", "<br>") + "</html>");
        messageLabel.setFont(new Font("Roboto", Font.PLAIN, 18));
        messageLabel.setForeground(new Color(0, 102, 102));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(messageLabel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        styleButton(okButton);
        okButton.addActionListener(e -> dialog.dispose());
        dialog.add(okButton, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // Custom method to display a confirmation dialog
    private int showConfirmDialog() {
        return JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this event?",
            "Delete Event",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
    }

    // Helper method to style buttons to match the theme
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
                button.setBackground(new Color(64, 135, 135));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(84, 155, 155));
            }
        });
    }

    // Method to format event details
    private String getEventDetails(Event event) {
        StringBuilder details = new StringBuilder();
        details.append("Name: ").append(event.getName()).append("\n")
               .append("Date: ").append(event.getDate()).append("\n")
               .append("Start Time: ").append(event.getStartTime()).append("\n")
               .append("End Time: ").append(event.getEndTime()).append("\n")
               .append("Description: ").append(event.getDescription());
        if (event instanceof Appointment) {
            details.append("\nLocation: ").append(((Appointment) event).getLocation());
        }
        if (event instanceof Holiday) {
        	 Holiday holiday = (Holiday) event;
             if (holiday.isPublicHoliday()) { // Assuming isPublicHoliday() is a method in Holiday class
                 details.append("\nPublic Holiday");
             }
        }
        return details.toString();
    }

    // Custom Scroll Bar UI for matching the theme
    private static class CustomScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(84, 155, 155);
        }
    }
}
