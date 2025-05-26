import java.time.*;
import java.util.ArrayList;


public class month 
{

	
	private String monthName;
	private ArrayList<Day> days;	
		
    // Constructor for Month that takes a month name as a string
    public month(String monthName) 
    {
        this.monthName = monthName;
        this.days = new ArrayList<>();

        // Convert the month name string to Month 
        Month month1 = getMonthFromName(monthName);

        // check how many days supposed to be in a month days
        if (month1 != null) {
            int length = month1.length(true); //length of month
            for (int i = 1; i <= length; i++) {
            	LocalDate Date1= LocalDate.of(2024, month1, i);
                days.add(new Day(Date1));
            }
        } else {
            throw new IllegalArgumentException("Invalid month name: " + monthName);
        }
    }
    
    
    //converts the month name to an actual month it's private because we don't want anyone to call or use it
    private Month getMonthFromName(String monthName) {
        switch (monthName.toLowerCase()) {
            case "january": return Month.JANUARY;
            case "february": return Month.FEBRUARY;
            case "march": return Month.MARCH;
            case "april": return Month.APRIL;
            case "may": return Month.MAY;
            case "june": return Month.JUNE;
            case "july": return Month.JULY;
            case "august": return Month.AUGUST;
            case "september": return Month.SEPTEMBER;
            case "october": return Month.OCTOBER;
            case "november": return Month.NOVEMBER;
            case "december": return Month.DECEMBER;
            default: return null;
        }
    }
    
    public String getName() {
    	return this.monthName;
    }
	
	
}
