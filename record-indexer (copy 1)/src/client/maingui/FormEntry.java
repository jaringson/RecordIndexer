package client.maingui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

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
//				list.setSize(p1.getSize());
//				System.out.println(getWidth());


			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			
		});
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}

	@Override
	public void setCellData(String value, Cell cell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void downloadBatch(DownloadBatch_Result result) {

		List<Field> fields = result.getFields();
		Project project = result.getProject();
		
		p1.setBackground(Color.WHITE);
//		JPanel p1= new JPanel();
//		p1.setResizeWeight(.5);
//		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
//		p2.setPreferredSize(new Dimension((2/3)*this.getWidth(), this.getHeight()));
		
		String listData[] = new String[project.getRecordsperimage()];
		for(int i = 0;i<project.getRecordsperimage();i++){
			StringBuilder sb = new StringBuilder();
			sb.append(i+1);
			listData[i] = sb.toString();
		}
		
		JList<String> newlist = new JList<String>(listData);
		list = newlist;
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setVisibleRowCount(-1);
		list.setBackground(Color.white);
		
		p1.add(list);
 		
		ArrayList<JPanel> panels = new ArrayList<JPanel>();
		
		
		for(Field field: fields){
			JPanel temp = new JPanel();
			temp.setLayout(new BoxLayout(temp, BoxLayout.X_AXIS));
			temp.add(new JLabel(field.getTitle()));
//			temp.add(Box.createGlue());
			JTextField text = new JTextField();
			text.setSize(new Dimension(100, 20));
			temp.add(text);
			temp.add(Box.createRigidArea(new Dimension(50,0)));
			panels.add(temp);
			temp.setSize(new Dimension(p2.getWidth(),p2.getHeight()/fields.size()));
			p2.add(temp);
//			p2.add(Box.createRigidArea(new Dimension(getWidth(),10)));
		}
//		for(JPanel panel: panels){
//			
//		}
//		p2.add(new JButton("Jaron"));
		
		
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
	public void setSelectedCell(Cell cell) {}

	@Override
	public void invertImage(boolean inverted) {}

}
