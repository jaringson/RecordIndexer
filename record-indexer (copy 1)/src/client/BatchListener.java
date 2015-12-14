package client;

import client.BatchState.Cell;
import shared.communication.DownloadBatch_Result;

public interface BatchListener {
	
	public void setSelectedCell(Cell cell);
	
	public void downloadBatch(DownloadBatch_Result result);
	
	public void submitBatch();
	
	public void toggleHighLight(boolean on);
	
	public void zoom(boolean zoomin);

	public void setCellData(String value, Cell cell);

	public void invertImage(boolean inverted);
}
