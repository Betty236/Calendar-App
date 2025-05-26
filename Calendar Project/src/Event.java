import java.time.*;

public abstract class Event {
	
	
	private String name;
	private LocalDate date;
	private String Color;
	private LocalTime StartTime;
	private LocalTime EndTime;
	private String description;
	
	
	public Event(String name1, LocalDate date1, String Color1 , LocalTime StartTime1,LocalTime EndTime1,String description1)throws Exception {
		if(date1.getYear()!=2024) {
			throw new Exception ("Year must be 2024");
		}
		this.name = name1;
		this.date = date1;
		this.Color = Color1;
		this.StartTime = StartTime1;
		this.EndTime = EndTime1;
		this.description = description1;
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String x) {
		this.name = x;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getColor() {
		return Color;
	}

	public void setColor(String color) {
		Color = color;
	}

	public LocalTime getStartTime() {
		return StartTime;
	}

	public void setStartTime(LocalTime startTime) {
		StartTime = startTime;
	}

	public LocalTime getEndTime() {
		return EndTime;
	}

	public void setEndTime(LocalTime endTime) {
		EndTime = endTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	//check if two events have equals parameters
    public boolean IsEquals(Event event) {

        return 	this.name.equals(event.name) &&
        		this.date.isEqual(event.date) &&
        		this.Color.equals(event.Color)&&
        		this.StartTime.equals(event.StartTime) &&
        		this.EndTime.equals(event.EndTime) &&
        		this.description.equals(event.description);
    }

    Object getTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
	
}
