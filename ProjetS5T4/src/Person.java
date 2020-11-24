import java.sql.Date;

public abstract class Person {

	protected String Name;
	protected String lastName;
	protected int insuranceNumber;
	protected int born;
	protected String password;
	
	public Person(String name, String lastName, int insuranceN, int born, String passW)
	{
		this.Name = name;
		this.lastName = lastName;
		this.insuranceNumber = insuranceN;
		this.born = born;
		this.password = passW;
	}
	
	
	
	
	
	public String getName()
	{
		return this.Name;
	}
	
		public void setName(String Name)
		{
			this.Name = Name;
		}
	
	public int getInsuranceNumber()
	{
		return this.insuranceNumber;
	}
	
	public int getBorn()
	{
		return this.born;
	}
	public String getPassWord()
	{
		return this.password;
	}
	
		public void setPassWord(String passW)
		{
			this.password = passW;
		}
	
	
}
