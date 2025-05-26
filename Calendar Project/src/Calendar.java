import java.util.*;

public class Calendar 
{
	public static ArrayList<month> months;

    // Constructor for Calendar2024
    public Calendar() {
        months = new ArrayList<>();
        // List of all month names
        String[] monthNames ={
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        //adding all months to the list
        for (String monthName : monthNames) 
        {
            months.add(new month(monthName));
        }
    }
    
    //getting the month back by comparing the name we get to names on the list and returning the right month
    public month getMonth(String name) 
    {
    	Iterator<month> it= this.months.iterator();
        while (it.hasNext()) {
        	month Month1 = it.next(); 
            if(Month1.getName().equalsIgnoreCase(name)) {
            	return Month1;
            }
        }
        
        throw new IllegalArgumentException("Invalid month name: " + name);
    }
    
    
    
}
