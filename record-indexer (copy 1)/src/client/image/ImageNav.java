package client.image;

import javax.swing.*;

import shared.communication.DownloadBatch_Result;
import client.*;
import client.BatchState.Cell;

@SuppressWarnings("serial")
public class ImageNav extends JPanel implements BatchListener{
	public ImageNav(BatchState bState){
		add(new ImageComp(bState));
	}

	

	@Override
	public void setCellData(String value, Cell cell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void downloadBatch(DownloadBatch_Result result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitBatch() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}



	@Override
	public void invertImage(boolean inverted) {
		// TODO Auto-generated method stub
		
	}
}
