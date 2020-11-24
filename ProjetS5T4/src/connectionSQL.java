import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.*;


public class connectionSQL {

	
	private static String url, user, password;
	
	
	public connectionSQL()
	{
		
		this.url = "jdbc:mysql://localhost:3306/ProjetTeam4";
		this.user = "root";
		this.password = "Augustin01";
                       
	}
	
	public void AjoutDocteur()
	{
		try 
		{
			Connection connection = DriverManager.getConnection(url, user, password);
			System.out.println("DataBase connected");
			
			String sql = "INSERT INTO docteur (numSécuDocteur, nom, prénom, age, spécialité, mdp) VALUES (?,?,?,?,?,?)";
			PreparedStatement statementDoctor = connection.prepareStatement(sql);
			statementDoctor.setString(1,"222");
			statementDoctor.setString(2,"test3");
			statementDoctor.setString(3,"test3");
			statementDoctor.setInt(4,28);
			statementDoctor.setString(5,"mains");
			statementDoctor.setString(6,"15642");
			
			int row = statementDoctor.executeUpdate();
			if(row > 0)
			{
				System.out.println("command executed");
			}
			
			statementDoctor.close();
			connection.close();
			
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) 
		{
            e.printStackTrace();
            System.out.println("DataBase connected");
        }
	}

	public void AjoutRDV()
	{
		try 
		{
			Connection connection = DriverManager.getConnection(url, user, password);
			System.out.println("DataBase connected");
			
			String sql = "INSERT INTO rdv (NuméroRDV, numSécuDocteur, numSécuPatient, Date, Horaire, raison) VALUES (?,?,?,?,?,?)";
			PreparedStatement statementRDV = connection.prepareStatement(sql);
			statementRDV.setString(1,"RDV3");
			statementRDV.setString(2,"555");
			statementRDV.setString(3,"666");
			statementRDV.setDate(4, java.sql.Date.valueOf("2020-11-26"));
			statementRDV.setTime(5,java.sql.Time.valueOf("08:00:00"));
			statementRDV.setString(6,"mal au pied");
			
			int row = statementRDV.executeUpdate();
			if(row > 0)
			{
				System.out.println("command executed");
			}
			
			statementRDV.close();
			connection.close();
			
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) 
		{
            e.printStackTrace();
            System.out.println("DataBase connection error");
        }
	}
	
	public void AjoutPatient()
	{
		try 
		{
			Connection connection = DriverManager.getConnection(url, user, password);
			System.out.println("DataBase connected");
			
			String sql = "INSERT INTO patient (numSécuPatient, nom, prénom, age, antécedent, mdp) VALUES (?,?,?,?,?,?)";
			PreparedStatement statementPatient = connection.prepareStatement(sql);
			statementPatient.setString(1,"666");
			statementPatient.setString(2,"test1");
			statementPatient.setString(3,"test2");
			statementPatient.setInt(4,35);
			statementPatient.setString(5,"");
			statementPatient.setString(6,"99999");
			
			int row = statementPatient.executeUpdate();
			if(row > 0)
			{
				System.out.println("command executed");
			}
			
			statementPatient.close();
			connection.close();
			
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) 
		{
            e.printStackTrace();
            System.out.println("DataBase connected");
        }
	}
	
	public static List<Doctor> getListDoctor()
	{
		List<Doctor> DoctorList = new ArrayList<Doctor>();
		
		try {
			Connection connection = DriverManager.getConnection(url, user, password);
			System.out.println("DataBase Doctor connected");
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM docteur");
			while(rs.next())
			{
				Doctor doc = new Doctor(rs.getString(2),rs.getString(3),rs.getInt(1),rs.getInt(4),rs.getString(6),rs.getString(5));
				DoctorList.add(doc);
			}
			
			stmt.close();
			connection.close();
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return DoctorList;
		
	}
	
	public static List<Patient> getListPatient()
	{
		List<Patient> PatientList = new ArrayList<Patient>();
		
		try {
			Connection connection = DriverManager.getConnection(url, user, password);
			System.out.println("DataBase Patient connected");
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM patient");
			while(rs.next())
			{
				Patient pat = new Patient(rs.getString(2),rs.getString(3),rs.getInt(1),rs.getInt(4),rs.getString(6),rs.getString(5));
				PatientList.add(pat);
			}
			
			stmt.close();
			connection.close();
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return PatientList;
		
	}
	
	public static List<RDV> getListRDV()
	{
		List<RDV> RDVList = new ArrayList<RDV>();
		
		try {
			
			List<Doctor> DoctorList = getListDoctor();
			List<Patient> PatientList = getListPatient();
			
			Connection connection = DriverManager.getConnection(url, user, password);
			System.out.println("DataBase RDV connected");
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM rdv");
			String Text = "";
			Doctor doc = null;
			Patient pat = null;
			while(rs.next())
			{
				for(int i = 0; i < DoctorList.size(); i ++)
				{
					if(rs.getInt(2) == DoctorList.get(i).getInsuranceNumber())
					doc = DoctorList.get(i);
				}
				for(int i = 0; i < PatientList.size(); i ++)
				{
					if(rs.getInt(3) == PatientList.get(i).getInsuranceNumber())
					pat = PatientList.get(i);
				}
				
				//java.util.Date uDate = rs.getDate(4);
				RDV rdv = new RDV(doc,pat,rs.getDate(4),rs.getTime(5),rs.getString(6),rs.getString(1));
				RDVList.add(rdv);
			}
			
			stmt.close();
			connection.close();
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return RDVList;
		
	}
	
	public static void removeDoctor(int numSecu)
	{
		try {
			Connection connection = DriverManager.getConnection(url, user, password);
			System.out.println("DataBase Doctor connected");
			Statement stmt = connection.createStatement();
			stmt.execute("DELETE FROM docteur WHERE numSécuDocteur =" + numSecu);
			
			stmt.close();
			connection.close();
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
	
	}
	
	public static void removePatient(int numSecu)
	{
		try {
			Connection connection = DriverManager.getConnection(url, user, password);
			System.out.println("DataBase patient connected");
			Statement stmt = connection.createStatement();
			stmt.execute("DELETE FROM patient WHERE numSécuPatient =" + numSecu);
			
			stmt.close();
			connection.close();
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
	
	}
	
	public static void removeRDV(String num)
	{
		try {
			Connection connection = DriverManager.getConnection(url, user, password);
			System.out.println("DataBase rdv connected");
			Statement stmt = connection.createStatement();
			stmt.execute("DELETE FROM rdv WHERE numéroRDV = \"" + num + "\"");
			
			stmt.close();
			connection.close();
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}	
		
	@SuppressWarnings({ "null", "deprecation" })
	public Object[][] TableContains(Date begining)
	{
		List<RDV> rdvList = getListRDV();
		Object[][]recipList = null;
		
		Calendrier calend = new Calendrier();
		
		for(int correctDate =0; correctDate < rdvList.size();correctDate++)//Read all RDV saved in the DB
		{
			if(rdvList.get(correctDate).getDate() == begining)//React when the date of a rdv is equal to the first day of the week
			{
				int row = 0;
				for(int count = 8; count < 19; count++)
				{
					if(rdvList.get(correctDate).getTime().getHours() == count)
						recipList[1][row] = rdvList.get(correctDate).getNumberRDV();
					row++;
					
				}
				
			}
			else if(rdvList.get(correctDate).getDate() == calend.addDay(begining,1))//React when the date of a rdv is equal to the 2nd day of the week
			{
				int row = 0;
				for(int count = 8; count < 19; count++)
				{
					if(rdvList.get(correctDate).getTime().getHours() == count)
						recipList[2][row] = rdvList.get(correctDate).getNumberRDV();
					row++;
					
				}
				
			}
			else if(rdvList.get(correctDate).getDate() == calend.addDay(begining,2))//React when the date of a rdv is equal to the 3 day of the week
			{
				int row = 0;
				for(int count = 8; count < 19; count++)
				{
					if(rdvList.get(correctDate).getTime().getHours() == count)
						recipList[3][row] = rdvList.get(correctDate).getNumberRDV();
					row++;
					
				}
				
			}
			else if(rdvList.get(correctDate).getDate() == calend.addDay(begining,3))//React when the date of a rdv is equal to the 4 day of the week
			{
				int row = 0;
				for(int count = 8; count < 19; count++)
				{
					if(rdvList.get(correctDate).getTime().getHours() == count)
						recipList[4][row] = rdvList.get(correctDate).getNumberRDV();
					row++;
					
				}
				
			}
			else if(rdvList.get(correctDate).getDate() == calend.addDay(begining,4))//React when the date of a rdv is equal to the 5 day of the week
			{
				int row = 0;
				for(int count = 8; count < 19; count++)
				{
					if(rdvList.get(correctDate).getTime().getHours() == count)
						recipList[5][row] = rdvList.get(correctDate).getNumberRDV();
					row++;
					
				}
				
			}
			
			
			
		}
		return recipList;
	}
		
}
