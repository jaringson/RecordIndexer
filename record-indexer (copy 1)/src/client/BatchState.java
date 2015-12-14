package client;

import java.awt.Point;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import client.image.ImageComp;
import client.maingui.IndexerFrame;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.DownloadBatch_Result;
import shared.model.*;

public class BatchState  implements BatchListener{

	XStream xmlStream = new XStream(new DomDriver());
	
	public BatchState(){
		
		toggleHighLight(true);
		xLocation = 300;
		yLocation = 100;
		width =1200;
		height =800;
		verSlide = 500;
		horSlide = 400;
	}

	
	public transient static List<BatchListener> listeners = new ArrayList<BatchListener>();
	public transient static IndexerFrame frame;
	
	//all the data variables
	//zoom level, 2D array[][]String, highlights, frame size/location
	public int xLocation;
	public int yLocation;
	public int width;
	public int height;
	public int verSlide;
	public int horSlide;
	public Cell currentCell;
	public Batch batch;
	public User user;
	public Project project;

	public ArrayList<Field> fields;
	public ArrayList<ArrayList<Cell>> cells;
	
	public int zoom;
	public Boolean highlightOn = true;
	public Boolean inverted = false;
	public int w_translateX =0;
	public int w_translateY=0;
	public double scale=1.0;
	
	public  void reinitialize(){
		listeners = new ArrayList<BatchListener>();
		
	}
	public void setAll(){
		if(batch != null){
			DownloadBatch_Result result = new DownloadBatch_Result();
			result.setBatch(batch);
			result.setProject(project);
			result.setFields(fields);

			downloadBatch(result);
			setSelectedCell(currentCell);
		}
	}

	public void addListener(BatchListener listen){
		listeners.add(listen);
	}
	
	public void setSaveData(){
		xLocation = frame.getX();
		yLocation = frame.getY();
		width = frame.getWidth();
		height = frame.getHeight();
		verSlide = frame.getVerSlide();
		horSlide = frame.getHorSlide();
		batch = Controller.getBatch();
		user = Controller.getUser();
		project = Controller.getProject();
		w_translateX = ImageComp.getW_translateX();
		w_translateY = ImageComp.getW_translateY();
		scale = ImageComp.getScale();
	}

	@Override
	public void setSelectedCell(Cell cell) {
		currentCell = cell;
		if(currentCell != null){
			for(BatchListener current: listeners){
				current.setSelectedCell(currentCell);
			}
		}
		
	}

	
	@Override
	public void setCellData(String value, Cell cell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void downloadBatch(DownloadBatch_Result result) {
		fields = (ArrayList<Field>) result.getFields();
		batch = result.getBatch();
		project = result.getProject();
		for(BatchListener current: listeners){
			current.downloadBatch(result);
		}
	}

	@Override
	public void submitBatch() {
		for(BatchListener current: listeners){
			current.submitBatch();
		}
	}

	@Override
	public void toggleHighLight(boolean on) {
		highlightOn = on;
		for(BatchListener current: listeners){
			current.toggleHighLight(this.highlightOn);
		}
		
	}

	@Override
	public void zoom(boolean zoomin) {
		for(BatchListener current: listeners){
			current.zoom(zoomin);
		}
	}

	@Override
	public void invertImage(boolean inverted) {
		this.inverted = inverted;
		for(BatchListener current: listeners){
			current.invertImage(inverted);
		}
	}
	
	
	

	public class Cell{
		public String value;
		public double x;
		public double x2;
		public double y;
		public double y2;
		public int record;
		public int field;
		public Cell(double x, double y,double x2,double y2){
			this.x = x;
			this.y = y;
			this.x2 = x2;
			this.y2 = y2;
		}
		public void setRecordField(int record, int field){
			this.record = record;
			this.field = field;
		}
	}
	
}
