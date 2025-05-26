import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reminder extends Event {

    private LocalTime RemindTime;

    public Reminder(String name1, LocalDate date1, String Color1, LocalTime StartTime1, LocalTime EndTime1, String description1, LocalTime RemindTime1) throws Exception {
        super(name1, date1, Color1, StartTime1, EndTime1, description1);
        this.RemindTime = RemindTime1;
    }

    public LocalTime getRemindTime() {
        return RemindTime;
    }

    public void setRemindTime(LocalTime remindTime) {
        RemindTime = remindTime;
    }

    // Override equals method to compare Reminder objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Reminder reminder = (Reminder) o;
        return RemindTime.equals(reminder.RemindTime);
    }

    // Override hashCode method to generate a hash code based on Reminder fields
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), RemindTime);
    }

    // Deprecated method - Use equals instead
    @Deprecated
    public boolean IsEquals(Reminder R) {
        return this.equals(R);
    }
}
