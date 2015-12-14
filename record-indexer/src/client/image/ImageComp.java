package client.image;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import shared.communication.DownloadBatch_Result;
import shared.model.*;
import client.*;
import client.BatchState.Cell;
import client.image.ImageNav.DrawingRect;

@SuppressWarnings("serial")
public class ImageComp extends JComponent implements BatchListener{
	private Image image;
	
	private static int w_translateX;
	private static int w_translateY;
	private static double scale;
	
	private boolean dragging;
	private int w_dragStartX;
	private int w_dragStartY;
	private int w_dragStartTranslateX;
	private int w_dragStartTranslateY;
	private AffineTransform dragTransform;

	private ArrayList<DrawingShape> shapes;
	private Font font;
	private BasicStroke stroke;
	
	private BatchState bState;
	private ArrayList<Field> fields;
	private ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();
	
	private Cell cell;
	DownloadBatch_Result result = new DownloadBatch_Result();
	private boolean on;
	private boolean inverted = false;
	private ImageNav imagenav;
	
	
	public ImageComp(BatchState bState) {
		this.bState = bState;
		
		bState.addListener(this);
		
		inverted = bState.inverted;
		on = bState.highlightOn;

		w_translateX = bState.w_translateX;
		w_translateY = bState.w_translateY;
		scale = bState.scale;
		
		initDrag();

		shapes = new ArrayList<DrawingShape>();
		
		font = new Font("SansSerif", Font.PLAIN, 72);
		stroke = new BasicStroke(5);
		
		this.setBackground(Color.GRAY);
		this.setPreferredSize(new Dimension(700, 700));
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(1000, 1000));
		
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);
		this.addComponentListener(componentAdapter);
		
	}
	
	public void setImageNav(ImageNav imagenav){
		this.imagenav = imagenav;
	}
	
	public static int getW_translateX() {
		return w_translateX;
	}
	public static int getW_translateY() {
		return w_translateY;
	}
	public static double getScale() {
		return scale;
	}
	
	private void setCells(){
		Project project  = Controller.getProject();
		
		double recordHeight =  project.getRecordheight();
		fields = Controller.getFields();
		double startingY = project.getFirstycoordinate();
		
		int j = 0;
		for(Field field: fields){
			double startingX = field.getXcoord();
			double width =field.getWidth();

			double y = startingY;
			ArrayList<Cell> columncells = new ArrayList<Cell>();
			for(int i = 0; i < project.getRecordsperimage(); i++){

				Cell temp = bState.new Cell(startingX,y, startingX + width ,y+recordHeight);
				temp.setRecordField(i, j);
				columncells.add(temp);
				y = y + recordHeight;
			}
			cells.add(columncells);
			j++;
		}
		if(bState.cells == null){
			bState.cells = cells;
		}
		
		repaint();
	}
	
	
	
	private void initDrag() {
		dragging = false;
		w_dragStartX = 0;
		w_dragStartY = 0;
		w_dragStartTranslateX = 0;
		w_dragStartTranslateY = 0;
		dragTransform = null;
	}
	
	private void initShapes(){
		shapes.clear();
		Batch batch = result.getBatch();
		String url = ClientCommunicator.getURLPrefix();
		try {
			image = ImageIO.read(new URL(url+"/Records/"+batch.getFile()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(inverted){
			RescaleOp op = new RescaleOp(-1.0f, 255f, null);
			image = op.filter((BufferedImage) image, null);
		}
		shapes.add(new DrawingImage(image, new Rectangle2D.Double(0, 0, image.getWidth(null), image.getHeight(null))));
		this.repaint();
	}
	

	
	public void setScale(double newScale) {
		scale = newScale;
		this.repaint();
	}
	
	public void setTranslation(int w_newTranslateX, int w_newTranslateY) {
		w_translateX = w_newTranslateX;
		w_translateY = w_newTranslateY;
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);
		
		g2.translate(this.getWidth()/2.0,this.getHeight()/2.0);
		g2.scale(scale, scale);
		g2.translate(w_translateX, w_translateY);
		g2.translate(-this.getWidth()/2.0,-this.getHeight()/2.0);

		drawShapes(g2);
	}
	
	private void drawBackground(Graphics2D g2) {
		g2.setColor(getBackground());
		g2.fillRect(0,  0, getWidth(), getHeight());
	}

	private void drawShapes(Graphics2D g2) {
		
		for (DrawingShape shape : shapes) {
			shape.draw(g2);
		}
	}
	
	private Cell getCellSellected(int w_X, int w_Y){
		for(int i = 0; i < cells.size();i++){
			for(int j = 0; j < cells.get(i).size();j++){
				Cell cell = cells.get(i).get(j);
				if(w_X > cell.x && w_X < cell.x2){
					if(w_Y > cell.y && w_Y <cell.y2 ){
						return cell;
					}
				}
			}
		}
		return null;
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {
			
			int d_X = e.getX();
			int d_Y = e.getY();
			
			AffineTransform transform = new AffineTransform();
			transform.translate(getWidth()/2.0,getHeight()/2.0);
			transform.scale(scale, scale);
			transform.translate(w_translateX, w_translateY);
			transform.translate(-getWidth()/2.0,-getHeight()/2.0);
			
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			
			
			cell = getCellSellected(w_X, w_Y);
			if(cell != null){
				bState.setSelectedCell(cell);
			}
			
			boolean hitShape = false;
			
			Graphics2D g2 = (Graphics2D)getGraphics();
			for (DrawingShape shape : shapes) {
				if (shape.contains(g2, w_X, w_Y)) {
					hitShape = true;
					break;
				}
			}
			
			if (hitShape) {
				dragging = true;		
				w_dragStartX = w_X;
				w_dragStartY = w_Y;		
				w_dragStartTranslateX = w_translateX;
				w_dragStartTranslateY = w_translateY;
				dragTransform = transform;
			}
			
			if(imagenav!= null){
				imagenav.initShapes();
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {	
			if (dragging) {
				int d_X = e.getX();
				int d_Y = e.getY();
				
				Point2D d_Pt = new Point2D.Double(d_X, d_Y);
				Point2D w_Pt = new Point2D.Double();
				try
				{
					dragTransform.inverseTransform(d_Pt, w_Pt);
				}
				catch (NoninvertibleTransformException ex) {
					return;
				}
				int w_X = (int)w_Pt.getX();
				int w_Y = (int)w_Pt.getY();
				
				int w_deltaX = w_X - w_dragStartX;
				int w_deltaY = w_Y - w_dragStartY;
				
				w_translateX = w_dragStartTranslateX + w_deltaX;
				w_translateY = w_dragStartTranslateY + w_deltaY;
				
				repaint();
			}
			if(imagenav!= null){
				imagenav.initShapes();
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			initDrag();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(e.getWheelRotation() == -1){
				scale = scale + .05;
				setScale(scale);
			}
			else{
				scale = scale - .05;
				if(scale < 0){
					scale = 0;
				}
				setScale(scale);

			}

			
			if(imagenav!= null){
				imagenav.initShapes();
			}
			return;
		}	
	};
	
	private ComponentAdapter componentAdapter = new ComponentAdapter() {

		@Override
		public void componentHidden(ComponentEvent e) {
			return;
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			return;
		}

		@Override
		public void componentResized(ComponentEvent e) {
		}

		@Override
		public void componentShown(ComponentEvent e) {
			return;
		}	
	};

	
	/////////////////
	// Drawing Shape
	/////////////////
	
	
	interface DrawingShape {
		boolean contains(Graphics2D g2, double x, double y);
		void draw(Graphics2D g2);
		Rectangle2D getBounds(Graphics2D g2);
	}


	class DrawingRect implements DrawingShape {

		private Rectangle2D rect;
		private Color color;
		
		public DrawingRect(Rectangle2D rect, Color color) {
			this.rect = rect;
			this.color = color;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.setColor(color);
			g2.fill(rect);
		}
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
	}


	class DrawingLine implements DrawingShape {

		private Line2D line;
		private Color color;
		
		public DrawingLine(Line2D rect, Color color) {
			this.line = rect;
			this.color = color;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {

			final double TOLERANCE = 5.0;
			
			Point2D p1 = line.getP1();
			Point2D p2 = line.getP2();
			Point2D p3 = new Point2D.Double(x, y);
			
			double numerator = (p3.getX() - p1.getX()) * (p2.getX() - p1.getX()) + (p3.getY() - p1.getY()) * (p2.getY() - p1.getY());
			double denominator =  p2.distance(p1) * p2.distance(p1);
			double u = numerator / denominator;
			
			if (u >= 0.0 && u <= 1.0) {
				Point2D pIntersection = new Point2D.Double(	p1.getX() + u * (p2.getX() - p1.getX()),
															p1.getY() + u * (p2.getY() - p1.getY()));
				
				double distance = pIntersection.distance(p3);
				
				return (distance <= TOLERANCE);
			}
			
			return false;
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.setColor(color);
			g2.setStroke(stroke);
			g2.draw(line);
		}	
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return line.getBounds2D();
		}
	}


	class DrawingImage implements DrawingShape {

		private Image image;
		private Rectangle2D rect;
		
		public DrawingImage(Image image, Rectangle2D rect) {
			this.image = image;
			this.rect = rect;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.drawImage(image, (int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getMaxX(), (int)rect.getMaxY(),
							0, 0, image.getWidth(null), image.getHeight(null), null);
		}	
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
	}


	class DrawingText implements DrawingShape {

		private String text;
		private Color color;
		private Point2D location;
		
		public DrawingText(String text, Color color, Point2D location) {
			this.text = text;
			this.color = color;
			this.location = location;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			Rectangle2D bounds = getBounds(g2);
			return bounds.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.setColor(color);
			g2.setFont(font);
			g2.drawString(text, (int)location.getX(), (int)location.getY());
		}
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			FontRenderContext context = g2.getFontRenderContext();
			Rectangle2D bounds = font.getStringBounds(text, context);
			bounds.setRect(location.getX(), location.getY() + bounds.getY(), 
							bounds.getWidth(), bounds.getHeight());
			return bounds;
		}
		
		public String getText() {
			return text;
		}
		
		public void setText(String value) {
			text = value;
		}
	}


	@Override
	public void setCellData(String value, Cell cell) {}

	@Override
	public void downloadBatch(DownloadBatch_Result result) {
		this.result = result;
		initShapes();
		setCells();
	}

	@Override
	public void submitBatch() {
		shapes.clear();
		cells.clear();
		repaint();
	}

	@Override
	public void toggleHighLight(boolean on) {
		
		this.on = on;
		if(!on){
			initShapes();
		}
		else{
			setSelectedCell(cell);
		}
	}

	@Override
	public void zoom(boolean zoomin) {
		if(zoomin){
			if(scale < .5){
				scale =scale +.05;
			}
			else{
				scale = scale + .15;
			}
			setScale(scale);
		}
		else{
			if(scale < .5){
				scale =scale -.05;
			}
			else{
				scale = scale - .15;
			}
			if(scale < 0){
				scale = 0;
			}
			setScale(scale);
		}
	}

	@Override
	public void setSelectedCell(Cell cell) {

		if(cell != null && on){
			this.cell = cell;
			initShapes();
			Rectangle2D rect = new Rectangle2D.Double(cell.x,cell.y, cell.x2-cell.x,cell.y2-cell.y);
			Color c = new Color(176,224,230,100);
			DrawingRect temp = new DrawingRect(rect, c);
			shapes.add(temp);
		}
	}

	@Override
	public void invertImage(boolean inverted) {
		this.inverted = inverted;
		initShapes();
		setSelectedCell(cell);
	}
}