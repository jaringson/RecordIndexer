package client.table;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.sun.org.apache.regexp.internal.recompile;

import shared.communication.DownloadBatch_Result;
import shared.model.*;
import client.*;
import client.BatchState.Cell;

@SuppressWarnings("serial")
public class TableEntry extends JPanel implements BatchListener{
	private static TableModel tableModel;
	private static JTable table;
	private BatchState bState;
	private ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();
	
	public TableEntry(BatchState bState){
		bState.addListener(this);
		
		this.bState = bState;
	}
	
	

	@Override
	public void setCellData(String value, Cell cell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void downloadBatch(DownloadBatch_Result result) {
		table = new JTable();
		
		tableModel = new TableModel(bState, result);
		
		
		table.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
				  cells = bState.cells;

			      JTable target = (JTable)e.getSource();
			      int row = target.getSelectedRow();
			      int column = target.getSelectedColumn() -1;
			      for(ArrayList<Cell> cellRow:cells){
			    	  for(Cell cell : cellRow ){
			    		  if(cell.record == row && cell.field == column){
			    			  bState.setSelectedCell(cell);
			    		  }
			    		  
			    	  }
			      }

			  }
			});
		table.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				JTable target = (JTable)e.getSource();
				int row = target.getSelectedRow();
			    int column = target.getSelectedColumn() -1;
				if(e.getKeyCode() == KeyEvent.VK_TAB){
					for(ArrayList<Cell> cellRow:cells){
				    	  for(Cell cell : cellRow ){
				    		  if(cell.record == row && cell.field == column){
				    			  bState.setSelectedCell(cell);
				    		  }
				    		  
				    	  }
				     }
				}
			}
			
		});
		table.setModel(tableModel);
		table.setRowHeight(20);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		
		
		TableColumnModel columnModel = table.getColumnModel();		
		for (int i = 0; i < tableModel.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			column.setPreferredWidth(100);
		}		
		for (int i = 0; i < tableModel.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			column.setCellRenderer(new CellRenderer());
//			column.setCellEditor(new CellEditor());
		}
		
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(box);
		add(table.getTableHeader());
		add(table);

		revalidate();
	}

	@Override
	public void submitBatch() {
		removeAll();
		revalidate();
	}

	@Override
	public void toggleHighLight(boolean on) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void zoom(boolean zoomin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelectedCell(Cell cell) {
		CellRenderer.modifyColor(cell.record, cell.field+1);
		repaint();
	}


	@Override
	public void invertImage(boolean inverted) {
		// TODO Auto-generated method stub
		
	}

}

@SuppressWarnings("serial")
class CellRenderer extends JLabel implements TableCellRenderer {
	
	private static int selectRow =-1;
	private static int selectCol =-1;
	private Border unselectedBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
	private Border selectedBorder = BorderFactory.createLineBorder(Color.BLACK, 2);

	public CellRenderer() {
		
		setOpaque(true);
		setFont(getFont().deriveFont(16.0f));
	}

	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {

//		Color c = ColorUtils.fromString((String)value);
		Color c = Color.white;
		this.setBackground(c);
		
		if (isSelected) {
			this.setBorder(selectedBorder);
		}
		else {
			this.setBorder(unselectedBorder);
		}
		if(row == selectRow && column == selectCol){
			this.setBackground(Color.yellow);
		}
		
		this.setText((String)value);
		
		return this;
	}
	public static void modifyColor(int row, int column){
		selectRow = row;
		selectCol = column;
	}

}


//@SuppressWarnings("serial")
//class CellEditor extends AbstractCellEditor implements TableCellEditor {
//	
//	private JComboBox<String> comboBox;
//	private String currentValue;
//	private JTextField text;
//	
//	private Map<String, Color> colorMap;
//	
//	public CellEditor() {
//		text.addActionListener(actionListener);
//		
////		colorMap = new TreeMap<String, Color>();
////		colorMap.put("Black", Color.BLACK);
////		colorMap.put("Blue", Color.BLUE);
////		colorMap.put("Cyan", Color.CYAN);
////		colorMap.put("Dark Gray", Color.DARK_GRAY);
////		colorMap.put("Gray", Color.GRAY);
////		colorMap.put("Green", Color.GREEN);
////		colorMap.put("Yellow", Color.YELLOW);
////		colorMap.put("Light Gray", Color.LIGHT_GRAY);
////		colorMap.put("Magenta", Color.MAGENTA);
////		colorMap.put("Orange", Color.ORANGE);
////		colorMap.put("Pink", Color.PINK);
////		colorMap.put("Red", Color.RED);
////		colorMap.put("White", Color.WHITE);
////			
////		comboBox = new JComboBox<String>();
////		comboBox.setEditable(true);
////		comboBox.addActionListener(actionListener);
////		for (String colorName : colorMap.keySet()) {
////			comboBox.addItem(colorName);
////		}
//	}
//
//	@Override
//	public Object getCellEditorValue() {
//		return currentValue;
//	}
//
//	@Override
//	public Component getTableCellEditorComponent(JTable table, Object value,
//			boolean isSelected, int row, int column) {
//		
//		currentValue = (String)value;
//		
//		comboBox.setSelectedItem(currentValue);
//		
////		JLabel label = new JLabel();
//		
//		return comboBox;
//	}
//	
//	private ActionListener actionListener = new ActionListener() {
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			System.out.println("herdsdf");
//			System.out.println(e.getSource());
//		}	
//	};
//	
//}


