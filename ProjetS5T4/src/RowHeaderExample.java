import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * @version 1.0 11/09/98
 */

class RowHeaderRenderer extends JLabel implements ListCellRenderer {

  RowHeaderRenderer(JTable table) {
    JTableHeader header = table.getTableHeader();
    setOpaque(true);
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    setHorizontalAlignment(CENTER);
    setForeground(header.getForeground());
    setBackground(header.getBackground());
    setFont(header.getFont());
  }

  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {
    setText((value == null) ? "" : value.toString());
    return this;
  }
}

public class RowHeaderExample extends JFrame {
	JScrollPane sc;
	JTable table;
	
  public RowHeaderExample() {
    super("Row Header Example");
    setSize(300, 150);

    ListModel lm = new AbstractListModel() {
      String headers[] = {"8h00","9h00","10h00","11h00","12h00","13h00","14h00","15h00","16h00","17h00","18h00","19h00" };

      public int getSize() {
        return headers.length;
      }

      public Object getElementAt(int index) {
        return headers[index];
      }
    };
    Object[][]stockageExample = {
			{"1","2","3","4","5"},
			{"1","2","3","4","5"},
			{"1","2","3","4","5"},
			{"1","2","3","4","5"},
			{"1","2","3","4","5"},
			{"1","2","3","4","5"},
			{"1","2","3","4","5"},
			{"1","2","3","4","5"},
			{"1","2","3","4","5"},
			{"1","2","3","4","5"},
			{"1","2","3","4","5"},
			{"1","2","3","4","5"},
			{"1","2","3","4","5"}};
    Object[]headerExample = {"Monday","Tuesday","Wednesday","Thursday","Friday"};

   table = new JTable(stockageExample,headerExample);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    JList rowHeader = new JList(lm);
    rowHeader.setFixedCellWidth(50);

    rowHeader.setFixedCellHeight(table.getRowHeight()
        + table.getRowMargin());
    //                           + table.getIntercellSpacing().height);
    rowHeader.setCellRenderer(new RowHeaderRenderer(table));

     this.sc = new JScrollPane(table);
    this.sc.setRowHeaderView(rowHeader);
  }
  
  public JScrollPane getSc()
  {
	  return this.sc;
  }
  
  public JTable getTable()
  {
	  return this.table;
  }
  
  
}