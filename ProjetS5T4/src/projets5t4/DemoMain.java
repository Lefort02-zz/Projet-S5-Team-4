import java.sql.Date;

public class DemoMain {

	public static void main(String[] args) {
		connectionSQL liaison = new connectionSQL();
		
		System.out.println(liaison.TableContains(new Date(2020,11,24)));

	}

}
