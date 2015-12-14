package client.qualitycheck;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shared.communication.DownloadBatch_Result;
import shared.model.Field;
import shared.model.Project;
import client.BatchListener;
import client.BatchState;
import client.ClientCommunicator;
import client.Controller;
import client.BatchState.Cell;
import client.qualitycheck.ISpellCorrector.NoSimilarWordFoundException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class QualityCheck extends JDialog implements BatchListener {
	
	private JEditorPane editorPane = new JEditorPane();
	private JPanel panel = new JPanel();
	
	public static String url;
	public static String inputWord;
	public ISpellCorrector corrector;
	
	public static void main(String args[]){
		url = "http://localhost:44443/Records/knowndata/ethnicities.txt";
		inputWord = "wit";
		runQualityCheck();
	}
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 * @return 
	 */
	public static String runQualityCheck()  {
		
		/**
		 * Create an instance of your corrector here
		 */
		String suggestion = null;
		ISpellCorrector corrector = new SpellCorrector();
		
		try {
			corrector.useDictionary(url);
			suggestion = corrector.suggestSimilarWord(inputWord);
		} catch (IOException | NoSimilarWordFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("Suggestion is: " + suggestion);
		return suggestion;
	}
	
	public QualityCheck(BatchState bState) {
		super();
		bState.addListener(this);
		this.setTitle("Indexer");
		this.setLocation(100, 54);
		this.setSize(120, 110);
		this.add(panel);
	}

	@Override
	public void setSelectedCell(Cell cell) {
		removeAll();
		for(Field field:Controller.getFields()){
			if(field.getColumnnumber() == cell.field){
				
				url = ClientCommunicator.getURLPrefix()+"/Records/"+field.getKnowndata();
	
			}
		}

		panel.add(editorPane);
		panel.revalidate();
		this.setVisible(true);
	}

	@Override
	public void downloadBatch(DownloadBatch_Result result) {}

	@Override
	public void submitBatch() {}

	@Override
	public void toggleHighLight(boolean on) {}

	@Override
	public void zoom(boolean zoomin) {}

	@Override
	public void setCellData(String value, Cell cell) {}

	@Override
	public void invertImage(boolean inverted) {}
}
