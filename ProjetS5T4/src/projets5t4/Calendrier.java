
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Calendrier {

	private Date date;
	private Calendar c;
	public Calendrier()
	{
		this.date = new Date(System.currentTimeMillis());
		this.c = Calendar.getInstance(); 
		c.setTime(date); 
	}
	
	
	public Object[] dateValue(int i)
	{
		
		SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/YYYY");
		
		c.add(Calendar.WEEK_OF_MONTH, i);
		date = c.getTime();
		
		
		String mon =" null ",tue = " null ",wed = " null ",thu = " null ",fri = " null ";
		
		
		
		if(date.getDay()==1)
		{
			mon = formatter.format(date);
			
			c.add(Calendar.DATE, 1);
			tue = formatter.format(c.getTime());
			
			c.add(Calendar.DATE, 1);
			wed = formatter.format(c.getTime());
			
			c.add(Calendar.DATE, 1);
			thu = formatter.format(c.getTime());
			
			c.add(Calendar.DATE, 1);
			fri = formatter.format(c.getTime());
			
		}
		
		else if(date.getDay()==2)
		{

			c.add(Calendar.DATE, -1);
			mon = formatter.format(c.getTime());
			
			
			tue = formatter.format(date);
			
			c.add(Calendar.DATE, 2);
			wed = formatter.format(c.getTime());
			
			c.add(Calendar.DATE, 1);
			thu = formatter.format(c.getTime());
			
			c.add(Calendar.DATE, 1);
			fri = formatter.format(c.getTime());
		}
		
		else if(date.getDay()==3)
		{
			c.add(Calendar.DATE, -2);
			mon = formatter.format(c.getTime());
			
			c.add(Calendar.DATE, 1);
			tue = formatter.format(c.getTime());
			
			wed = formatter.format(date);
			
			c.add(Calendar.DATE, 2);
			thu = formatter.format(c.getTime());
			
			c.add(Calendar.DATE, 1);
			fri = formatter.format(c.getTime());
		}
		
		else if(date.getDay()==4)
		{
			c.add(Calendar.DATE, -3);
			mon = formatter.format(c.getTime());
			
			c.add(Calendar.DATE, 1);
			tue = formatter.format(c.getTime());
			

			c.add(Calendar.DATE, 1);
			wed = formatter.format(c.getTime());
			
			thu = formatter.format(date);
			
			c.add(Calendar.DATE, 2);
			fri = formatter.format(c.getTime());
		}
		
		else if(date.getDay()==5)
		{
			c.add(Calendar.DATE, -4);
			mon = formatter.format(c.getTime());
			
			c.add(Calendar.DATE, 1);
			tue = formatter.format(c.getTime());
			

			c.add(Calendar.DATE, 1);
			wed = formatter.format(c.getTime());
			
			
			c.add(Calendar.DATE, 1);
			thu = formatter.format(c.getTime());
			
			fri = formatter.format(date);
		}
		
		Object[]object ={" ","Monday "+ mon,"Tuesday "+ tue,"Wednesday "+ wed,"Thursday "+thu,"Friday "+fri};
		return object;
	}
	
	public Date addDay(Date date, int number)
	{
		c.setTime(date);
		c.add(Calendar.DATE, number);
		
		return c.getTime();
	}

}
