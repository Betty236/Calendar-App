import java.time.LocalDate;
import java.util.*;

public class EventManger implements EventHandler 
{
	
	 public static ArrayList<Appointment> AppointmentList;
	 public static ArrayList<Holiday> HolidayList;
	 public static ArrayList<Reminder> ReminderList;
	
	 public EventManger() 
	 {
		AppointmentList = new ArrayList<Appointment>();
		HolidayList = new ArrayList<Holiday>() ;
		ReminderList = new ArrayList<Reminder>();
	 }
	 
	 //Add event to the right list by his type
	 public void addEvent(Event a) 
	{
		 if(a instanceof Appointment) 
		 {
			 Appointment b = (Appointment) a;
			 this.AppointmentList.add(b);
		 }
		 
		 if(a instanceof Holiday) 
		 {
			 Holiday b = (Holiday) a;
			 this.HolidayList.add(b);
		 }
		 
		 if(a instanceof Reminder) {
			 Reminder b = (Reminder) a;
			 this.ReminderList.add(b);
		 }
	 }
	 
	 
	 
	 //remove but we check if the event is equal by using IsEquals method we created in each class
	 //and we use casting after we checked the type of instance in order for it to work 
	 public boolean RemoveEvent(Event a) {    
		if (a instanceof Appointment) {
			Appointment b = (Appointment) a;
			for (int i = 0; i < this.AppointmentList.size(); i++) {
				if (this.AppointmentList.get(i).IsEquals(b)) {
					this.AppointmentList.remove(i);
					return true;
				}
			}
		}
	
		if (a instanceof Holiday) {
			Holiday b = (Holiday) a;
			for (int i = 0; i < this.HolidayList.size(); i++) {
				if (this.HolidayList.get(i).IsEquals(b)) {
					this.HolidayList.remove(i);
					return true;
				}
			}
		}
	
		if (a instanceof Reminder) {
			Reminder b = (Reminder) a;
			for (int i = 0; i < this.ReminderList.size(); i++) {
				if (this.ReminderList.get(i).IsEquals(b)) {
					this.ReminderList.remove(i);
					return true;
				}
			}
		}
	
		return false;
	}
	
	 
	 //list of events that has the same day as the day we get we iterate over all lists
	 public ArrayList<Event> getEventsByDay(Day day) {
		 ArrayList<Event> Events = new ArrayList<Event>();
		 for(int i = 0 ; i<= this.AppointmentList.size() ; i++) {
			 if (this.AppointmentList.get(i).getDate().isEqual(day.getDay())) {
				 Events.add(this.AppointmentList.get(i));
			 }
		 }
		 for(int i = 0 ; i<= this.ReminderList.size() ; i++) {
			 if (this.ReminderList.get(i).getDate().isEqual(day.getDay())) {
				 Events.add(this.ReminderList.get(i));
			 }
		 }
		 for(int i = 0 ; i<= this.HolidayList.size() ; i++) {
			 if (this.HolidayList.get(i).getDate().isEqual(day.getDay())) {
				 Events.add(this.HolidayList.get(i));
			 }
		 }
		 
		 return Events;
		 
	 }

	public ArrayList<Appointment> getAllAppo() {
		return AppointmentList;
	}


	public ArrayList<Holiday> getAllholi() {
		return HolidayList;
	}



	public ArrayList<Reminder> getAllRem() {
		return ReminderList;
	}

	public List<Event> getAllEventsForDay(LocalDate date) {
		List<Event> eventsForDay = new ArrayList<>();
	
		// Collect all appointments that match the given date
		for (Appointment appointment : AppointmentList) {
			if (appointment.getDate().equals(date)) {
				eventsForDay.add(appointment);
			}
		}
	
		// Collect all holidays that match the given date
		for (Holiday holiday : HolidayList) {
			if (holiday.getDate().equals(date)) {
				eventsForDay.add(holiday);
			}
		}
	
		// Collect all reminders that match the given date
		for (Reminder reminder : ReminderList) {
			if (reminder.getDate().equals(date)) {
				eventsForDay.add(reminder);
			}
		}
	
		return eventsForDay;
	}

	public List<Event> getAllEvents() {
        List<Event> allEvents = new ArrayList<>();
        allEvents.addAll(AppointmentList);
        allEvents.addAll(HolidayList);
        allEvents.addAll(ReminderList);
        return allEvents;
    }
	 
	 
	 
	 
}
