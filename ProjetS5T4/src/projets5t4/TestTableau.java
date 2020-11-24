import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;



public class TestTableau extends JFrame{

	//Instantiations

	JPanel display;
	JPanel displayTime;
	JButton nextWeek;
	JButton lastWeek;
	JTable stockage;
	JScrollPane sp;
	
	JList history;
	JList docStorage;
	JList time;
	
	JLabel user;
	JLabel messageError;
	
	JFrame frame = new JFrame();
	int Week =0;
	
	
	
	public void initialize()
	{
		
		//examples
		String[]historyExample = {"RDV 18/11/20","RDV 15/08/18"};
		String[]docExample = {"Martin K.","Joseph M."};
		Object[][]stockageExample = {
				{"8h00","1","2","3","4","5"},
				{"9h00","1","2","3","4","5"},
				{"10h00","1","2","3","4","5"},
				{"11h00","1","2","3","4","5"},
				{"12h00","1","2","3","4","5"},
				{"13h00","1","2","3","4","5"},
				{"14h00","1","2","3","4","5"},
				{"15h00","1","2","3","4","5"},
				{"16h00","1","2","3","4","5"},
				{"17h00","1","2","3","4","5"},
				{"18h00","1","2","3","4","5"},
				{"19h00","1","2","3","4","5"}};
		
		Object[]headerExample = new Calendrier().dateValue(Week);

		//Initilizations
		history = new JList(historyExample);
		docStorage = new JList(docExample);
		
		nextWeek= new JButton("Next Week");
		lastWeek = new JButton("Last Week");
		
		user = new JLabel("Informations of the user. Example Name + MatriculNumber");
		messageError = new JLabel("Message Logs : ");
		stockage = new JTable(stockageExample,headerExample)//lock the editable function of the table by the user
		{
			   public boolean isCellEditable(int row, int column)
			   {
			        return false;
			   }
		};
		stockage.setRowHeight(40);
		sp = new JScrollPane(stockage);

		//Display Intialization & Components
		display = new JPanel(new BorderLayout(100,100));
		displayTime = new JPanel(new BorderLayout(0,0));

		displayTime.add(sp,BorderLayout.CENTER);
		displayTime.add(nextWeek,BorderLayout.EAST);
		displayTime.add(lastWeek,BorderLayout.WEST);
		display.add(displayTime,BorderLayout.CENTER);
		display.add(history,BorderLayout.WEST);
		display.add(docStorage,BorderLayout.EAST);
		display.add(user,BorderLayout.NORTH);
		display.add(messageError,BorderLayout.SOUTH);
		display.setSize(100, 100);
		displayTime.setSize(100,100);
		
		
		stockage.getColumn(" ").setCellRenderer(new DefaultTableCellRenderer() //change row headers' color
		        {
		            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
		            {
		                setText(value.toString());
		                setBackground(isSelected ? Color.blue : Color.lightGray);
		                return this;
		            }
		        });
		stockage.getColumn(" ").setMaxWidth(50);
		
		//Frame Initialization
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.getContentPane().add(display);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		
		
	}
	
	public void Run()
	{
		
		stockage.addMouseListener(new java.awt.event.MouseAdapter() 
		{
			public void mouseReleased(java.awt.event.MouseEvent evt) //React with the selected case (only stockage contents will react)
			{
				int caseColumnIndex = stockage.columnAtPoint(evt.getPoint());
				int caseRowIndex = stockage.rowAtPoint(evt.getPoint());
				messageError.setText("Message : " + stockage.getValueAt(caseRowIndex, caseColumnIndex));
				stockage.setCellSelectionEnabled(true);
				if(stockage.isCellSelected(caseRowIndex, caseColumnIndex))
				{
					stockage.setSelectionBackground(Color.CYAN);
				}
			}
		});
		
		

	}
	
	public void WeeKGestion()
	{
		nextWeek.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){  
		    	messageError.setText("Next");   
		    	
		    			stockage.repaint();
		    	
		    }  
		    });  
		
		lastWeek.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){  
		            messageError.setText("Last");  
		           
			    			revalidate();
		            
		    }  
		    });
	}
	
	public static void main(String[]args)
	{
		TestTableau test = new TestTableau();//Instantiation
		test.initialize();
		test.Run();
		test.WeeKGestion();
		
		
		
	}
}
