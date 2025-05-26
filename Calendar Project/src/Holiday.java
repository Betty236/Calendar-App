import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Holiday extends Event 
{
	private LocalDate date;
    private boolean isPublicHoliday;
    private int duration;

    public Holiday(String name1, LocalDate date1, String Color1, LocalTime StartTime1, LocalTime EndTime1, String description1, int duration1, boolean isPublicHoliday1) throws Exception {
        super(name1, date1, Color1, StartTime1, EndTime1, description1);
        this.date = date1;
        this.duration = duration1;
        this.isPublicHoliday = isPublicHoliday1;
    }

    public LocalDate getDate() 
    {
        return date;
    }
    
    public void setDate(LocalDate date) 
    {
        this.date = date;
    }
    
    public boolean isPublicHoliday() {
        return isPublicHoliday;
    }

    public void setPublicHoliday(boolean isPublicHoliday) {
        this.isPublicHoliday = isPublicHoliday;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Override the equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Holiday holiday = (Holiday) o;
        return duration == holiday.duration &&
               isPublicHoliday == holiday.isPublicHoliday &&
               date == holiday.date;
    }

    // Override the hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isPublicHoliday, duration, date);
    }
}
