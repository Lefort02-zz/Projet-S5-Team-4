import java.sql.Date;

public class Patient extends Person {
 private String antecedent;
 
 public Patient(String name, String lastName, int insuranceN, int born, String passW, String ant)
 {
	 super(name,lastName,insuranceN,born,passW);
	 this.antecedent = ant;
 }
 
 public void setAntecedent(String ant)
 {
	 this.antecedent += "\n" + ant ;
 }
 
 public String getAntecedent()
 {
	 return this.antecedent;
 }
 
}
