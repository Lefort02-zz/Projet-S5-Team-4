import java.sql.Date;

public class Doctor extends Person {
	
	private String speciality;
	
	 public Doctor(String name, String lastName, int insuranceN, int age, String passW, String spe)
	 {
		 super(name,lastName,insuranceN,age,passW);
		 this.speciality = spe;
	 }
	 
	 public void setSpeciality(String spe)
	 {
		 this.speciality = spe;
	 }
	 
	 public String getSpeciality()
	 {
		 return this.speciality;
	 }
}
