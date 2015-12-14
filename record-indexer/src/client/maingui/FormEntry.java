package client.maingui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import shared.communication.DownloadBatch_Result;
import shared.model.Field;
import shared.model.Project;
import client.*;
import client.BatchState.Cell;

@SuppressWarnings("serial")
public class FormEntry extends JPanel implements BatchListener{
	private BatchState bState;
	private JPanel p1 = new JPanel();
	private JPanel p2= new JPanel();
	private int listindex =-1;
	ArrayList<JTextField> textfields;
	private Cell currentCell;
	
	@SuppressWarnings("rawtypes")
	private JList list = new JList<String>();
	public FormEntry(BatchState bState){
		bState.addListener(this);
		this.bState = bState;
		
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				int x = (int) ((2/3.0)*getWidth());
				int y = (int) ((1/3.0)*getWidth());
				p1.setPreferredSize(new Dimension(y, getHeight()));
				p2.setPreferredSize(new Dimension(x, getHeight()));
				list.setSize(p1.getSize());
			}
			@Override
			public void componentMoved(ComponentEvent e) {}

			@Override
			public void componentShown(ComponentEvent e) {
				bState.setSelectedCell(bState.currentCell);
			}

			@Override
			public void componentHidden(ComponentEvent e) {}

		});
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}

	@Override
	public void setCellData(String value, Cell cell) {}

	@Override
	public void downloadBatch(DownloadBatch_Result result) {

		List<Field> fields = result.getFields();
		Project project = result.getProject();
		
		p1.setBackground(Color.WHITE);
		p1.setLayout(new BorderLayout());

		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p2.add(Box.createRigidArea(new Dimension(0,5)));

		String listData[] = new String[project.getRecordsperimage()];
		for(int i = 0;i<project.getRecordsperimage();i++){
			StringBuilder sb = new StringBuilder();
			sb.append(i+1);
			listData[i] = sb.toString();
		}
	
		
		JList<String> newlist = new JList<String>(listData);
		list = newlist;
		ListSelectionModel listSelectionModel = list.getSelectionModel();
	    listSelectionModel.addListSelectionListener(new ListSelectionListener() {
	    	@Override
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel lsm = (ListSelectionModel)e.getSource();

		        if (!lsm.isSelectionEmpty())  {
		            int minIndex = lsm.getMinSelectionIndex();
		            int maxIndex = lsm.getMaxSelectionIndex();
		            for (int i = minIndex; i <= maxIndex; i++) {
		                if (lsm.isSelectedIndex(i)) {
		                	listindex = i;
		                	int currentField;
		                	if(bState.currentCell != null){
		                		currentField = bState.currentCell.field;
			                    for(int j =0;j<bState.cells.size();j++){
			                    	if(currentField == bState.cells.get(j).get(0).field){
			                    		for(int k= 0;k < bState.cells.get(j).size();k++){
			                    			if(bState.cells.get(j).get(k).record == listindex){
			                    				currentCell = bState.cells.get(j).get(k);
			                    			}
			                    		}
			                    	}
			                    }
		                	}
		                	else{
			                    for(int j =0;j<bState.cells.get(0).size();j++){
			                    	if(bState.cells.get(0).get(j).record == listindex){
	                    				currentCell = bState.cells.get(0).get(j);
	                    			}
			                    }
		                	}
			                bState.setSelectedCell(currentCell);		                    
		                }
		            }
		        }

			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setVisibleRowCount(-1);
		list.setBackground(Color.white);
		list.setBorder(BorderFactory.createLineBorder(Color.black));
		JScrollPane bar = new JScrollPane(list);
		p1.add(bar, BorderLayout.CENTER);
 		
		textfields = new ArrayList<JTextField>();

		for(Field field: fields){
			JPanel temp = new JPanel();
			temp.setLayout(new BoxLayout(temp, BoxLayout.X_AXIS));
			temp.add(Box.createRigidArea(new Dimension(10,0)));
			temp.add(new JLabel(field.getTitle()));
			JTextField text = new JTextField();
			text.setMaximumSize(new Dimension(100,20));
			text.setMinimumSize(new Dimension(50,20));
			text.addFocusListener(new FocusListener() {

	            @Override
	            public void focusGained(FocusEvent e) {
	            	for(int i =0 ; i < textfields.size();i++){
						
						if((JTextField)e.getSource() == textfields.get(i)){
							for(int j =0;j<bState.cells.size();j++){
		                    	
		                    		for(int k= 0;k < bState.cells.get(j).size();k++){
		                    			if(bState.cells.get(j).get(k).field ==i &&
		                    					bState.cells.get(j).get(k).record==listindex){
		                    				currentCell = bState.cells.get(j).get(k);
		                    			}
		                    		}
							}
							bState.setSelectedCell(currentCell);
						}
					}
	            }

	            @Override
	            public void focusLost(FocusEvent e) {
					for(int i =0 ; i < textfields.size();i++){
						
						if((JTextField)e.getSource() == textfields.get(i)){
							bState.cells.get(i).get(listindex).value = text.getText();
						}
					}
	            }

	        });
			temp.add(text);
			temp.add(Box.createRigidArea(new Dimension(10,0)));
			textfields.add(text);
			
			p2.add(temp);
			p2.add(Box.createGlue());

		}
		
		add(p1);
		add(p2);
	}

	@Override
	public void submitBatch() {
		removeAll();
		revalidate();
	}

	@Override
	public void toggleHighLight(boolean on) {}

	@Override
	public void zoom(boolean zoomin) {}

	@Override
	public void setSelectedCell(Cell cell) {

		list.setSelectedIndex(cell.record);
		ArrayList<Cell> row = new ArrayList<Cell>();
		for(int i=0;i<bState.cells.size();i++){
			row.add(bState.cells.get(i).get(cell.record));
		}

		for(int i = 0;i<textfields.size();i++){
			textfields.get(i).setText(row.get(i).value);
			if(i==cell.field){
				textfields.get(i).requestFocusInWindow();
			}
		}
	}
	@Override
	public void invertImage(boolean inverted) {}

}
