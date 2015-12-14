package client.maingui;


import java.io.IOException;

import javax.swing.*;

import shared.communication.DownloadBatch_Result;
import shared.model.Field;
import client.BatchListener;
import client.BatchState;
import client.ClientCommunicator;
import client.Controller;
import client.BatchState.Cell;

@SuppressWarnings("serial")
public class FieldHelp extends JPanel implements BatchListener{
	BatchState bState;
	private JEditorPane editorPane  = new JEditorPane();
	public FieldHelp(BatchState bState){
		bState.addListener(this);
		this.bState = bState;
	}

	@Override
	public void setSelectedCell(Cell cell) {
		removeAll();
		for(Field field:Controller.getFields()){
			if(field.getColumnnumber() == cell.field){
				editorPane.setContentType("text/html");
				try {
					editorPane.setPage(ClientCommunicator.getURLPrefix()+"/Records/"+field.getHelphtml());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		add(editorPane);
		revalidate();
	}

	@Override
	public void downloadBatch(DownloadBatch_Result result) {}

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
	public void setCellData(String value, Cell cell) {}

	@Override
	public void invertImage(boolean inverted) {}
}
