package client;

import java.util.ArrayList;
import java.util.List;

public class BatchState {
	
	private String[][] values;
	private Cell selectedCell;
	private List<BatchStateListener> listeners;
	
	public BatchState(int records, int fields) {
		values = new String[records][fields];
		selectedCell = null;
		listeners = new ArrayList<BatchStateListener>();
	}
	
	public void addListener(BatchStateListener l) {
		listeners.add(l);
	}
	
	public void setValue(Cell cell, String value) {
		
		String oldValue = values[cell.record][cell.field];
        
		values[cell.record][cell.field] = value;
		
		if (!value.equals(oldValue)) {
        
			for (BatchStateListener l : listeners) {
				l.valueChanged(cell, value);
			}
		}
	}
	
	public String getValue(Cell cell) {
		return values[cell.record][cell.field];
	}
	
	public void setSelectedCell(Cell selCell) {
		
		Cell oldValue = selectedCell;
        
		selectedCell = selCell;
		
		if (selCell.record != oldValue.record || 
			selCell.field != oldValue.field) {
        
			for (BatchStateListener l : listeners) {
				l.selectedCellChanged(selCell);
			}
		}
	}
	
	public Cell getSelectedCell() {
		return selectedCell;
	}
	
	
	public interface BatchStateListener {
		
		public void valueChanged(Cell cell, String newValue);
		
		public void selectedCellChanged(Cell newSelectedCell);
	}
	
	public class Cell {
		int record;
		int field;
	}
}
