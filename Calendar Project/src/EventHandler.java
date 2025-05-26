import java.util.*;


public interface EventHandler {
	public void addEvent(Event a);
	public boolean RemoveEvent(Event a);
	public ArrayList<Event> getEventsByDay(Day a);
	public ArrayList<Appointment> getAllAppo();
	public ArrayList<Holiday> getAllholi();
	public ArrayList<Reminder> getAllRem();
}
