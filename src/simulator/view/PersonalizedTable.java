package simulator.view;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import java.awt.*;

@SuppressWarnings("serial")
public class PersonalizedTable extends JTable {
	PersonalizedTable (TableModel tm){
		super(tm);
//		setBorder(BorderFactory.createEmptyBorder());
//		setBackground(Color.red);
		setFillsViewportHeight(true);
	}
}
