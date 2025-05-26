import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment extends Event {
	
	private String location;
	private String note;
	private LocalDate date;
	
	public Appointment(String name1, LocalDate date1, String Color1 , LocalTime StartTime1,LocalTime EndTime1,String description1 , String location1 , String note1)throws Exception {
		
		super(name1,  date1,Color1 ,  StartTime1, EndTime1, description1);
		this.note = note1;
		this.location = location1;
		this.date = date1;	
	}

	public LocalDate getDate() {
        return date;
    }
	
	public void setDate(LocalDate date) {
        this.date = date;
    }

	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}
	
	
	//method that help us compare if two Appointment object are the same we use the method we created in event and check
	//if event parameters equal first and the rest of the parameters
    public boolean IsEquals( Appointment appo) {
    	
    	Event event = (Event) appo;
        if(super.IsEquals(event) == false) {
        	return false;
        }
        
        return (this.location.equals(appo.location)) && (this.note.equals(appo.note) && (this.date.equals(appo.date)));
    }

	
	
	
}
