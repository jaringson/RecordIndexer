package client.table;


import java.awt.Color;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import shared.communication.DownloadBatch_Result;
import shared.model.Field;
import shared.model.Project;
import client.BatchState;

@SuppressWarnings("serial")
public class TableModel extends AbstractTableModel {

	private BatchState bState;
	private int columncount;
	private int rowcount;
	private String [] columnNames;
	private Object[][] data;
	
	public TableModel(BatchState bState, DownloadBatch_Result result){
		this.bState = bState;
		
		List<Field> fields = result.getFields();
		Project project = result.getProject();
		
		columnNames = new String[fields.size()+1];
		data = new Object[project.getRecordsperimage()][fields.size()+1];
		
		columnNames[0] = "Record";
		for(int i = 0;i<project.getRecordsperimage();i++){
			StringBuilder sb = new StringBuilder();
			sb.append(i+1);
			data[i][0] = sb.toString();
			for(int j = 1;j< fields.size()+1;j++){
				columnNames[j] = fields.get(j-1).getTitle();
				data[i][j] = "";
			}
		}
		if(bState.cells != null){
			for(int i = 0;i<bState.cells.size();i++){
				for(int j =0;j<bState.cells.get(i).size();j++){
					data[j][i+1] = bState.cells.get(i).get(j).value;
				}
			}
		}
		
		setColumnsCount(result.getFields().size()+1);
		setRowCount(project.getRecordsperimage());
	}
	
	public void setColumnsCount(int num){
		columncount = num;
	}
	public void setRowCount(int num){
		rowcount = num;
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (rowIndex >= 0 && rowIndex < getRowCount() && columnIndex >= 0
				&& columnIndex < getColumnCount()) {
			
			data[rowIndex][columnIndex] = aValue;
			bState.cells.get(columnIndex -1).get(rowIndex).value = (String) aValue;
			
		} else {
			throw new IndexOutOfBoundsException();
		}		
	};
	
	@Override
	public int getRowCount() {
		return rowcount;
	}

	@Override
	public int getColumnCount() {
		return columncount;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object result = null;
		
		for(int i = 0;i<bState.cells.size();i++){
			for(int j =0;j<bState.cells.get(i).size();j++){
				data[j][i+1] = bState.cells.get(i).get(j).value;
			}
		}
		
		if (rowIndex >= 0 && rowIndex < getRowCount() && columnIndex >= 0
				&& columnIndex < getColumnCount()) {

			result = data[rowIndex][columnIndex];

		} else {
			throw new IndexOutOfBoundsException();
		}
		for(int i=0;i<bState.cells.size();i++){
			for(int j=0;j<bState.cells.get(i).size();j++){
				if(bState.cells.get(i).get(j).record == rowIndex && 
						bState.cells.get(i).get(j).field+1 == columnIndex){
					bState.cells.get(i).get(j).value = (String) result;
					break;
				}
			}
		}

		return result;
	}
	
	@Override
	public String getColumnName(int column) {
		String result = null;

		if (column >= 0 && column < getColumnCount()) {
			result = columnNames[column];
		} else {
			throw new IndexOutOfBoundsException();
		}
		return result;
	}
	@Override
	public boolean isCellEditable(int row, int column) {
		if(column == 0){
			return false;
		}
		return true;
	}

}