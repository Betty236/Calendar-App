import java.awt.*;
import java.util.List;
import javax.swing.*;

public class ReminderListView extends JFrame {
    private EventManger eventManger;
    private List<Reminder> reminders;

    public ReminderListView(JFrame parentFrame, EventManger eventManger) {
        super("All Reminders");
        this.eventManger = eventManger;
        this.reminders = eventManger.getAllRem();

        setupForm();
        setSize(600, 400);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void setupForm() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        if (reminders.isEmpty()) {
            JLabel noRemindersLabel = new JLabel("Nothing to see here", JLabel.CENTER);
            noRemindersLabel.setFont(new Font("Roboto", Font.ITALIC, 18));
            mainPanel.add(noRemindersLabel);
        } else {
            for (Reminder reminder : reminders) {
                JPanel reminderPanel = createReminderPanel(reminder);
                mainPanel.add(reminderPanel);
            }
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createReminderPanel(Reminder reminder) {
        JButton reminderButton = new JButton(reminder.getName());
        reminderButton.setFont(new Font("Roboto", Font.PLAIN, 18));
        reminderButton.setBackground(new Color(229, 255, 254));
        reminderButton.setForeground(new Color(0, 102, 102));
        reminderButton.setBorder(BorderFactory.createLineBorder(Color.white, 2));

        reminderButton.addActionListener(e -> showReminderDetails(reminder));

        // Add a delete button next to each reminder
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Roboto", Font.PLAIN, 14));
        deleteButton.setBackground(new Color(255, 102, 102));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.red, 1));

        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this reminder?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                eventManger.RemoveEvent(reminder);
                refreshReminders();
            }
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(reminderButton, BorderLayout.CENTER);
        buttonPanel.add(deleteButton, BorderLayout.EAST);

        return buttonPanel;
    }

    private void showReminderDetails(Reminder reminder) {
        //JOptionPane.showMessageDialog(this, "Reminder Details:\n" + reminder, "Reminder Details", JOptionPane.INFORMATION_MESSAGE);
    	JDialog dialog = new JDialog(this, "Reminder Details", true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.WHITE);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JLabel messageLabel = new JLabel("<html>" + getReminderDetails(reminder).replace("\n", "<br>") + "</html>");
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
    
    // Method to format reminder details
    private String getReminderDetails(Reminder reminder) {
        return "Name: " + reminder.getName() + "\n"
                + "Date: " + reminder.getDate() + "\n"
                + "Time: " + reminder.getStartTime() + "\n"
                + "Description: " + reminder.getDescription();
    }
    
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

    private void refreshReminders() {
        this.reminders = eventManger.getAllRem();
        getContentPane().removeAll();
        setupForm();
        revalidate();
        repaint();
    }
}
