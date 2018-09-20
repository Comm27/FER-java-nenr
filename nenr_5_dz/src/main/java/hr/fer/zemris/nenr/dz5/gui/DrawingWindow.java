package hr.fer.zemris.nenr.dz5.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawingWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String WINDOW_NAME = "ANN GUI";
	private static final int WINDOW_SIZE = 600;
	
	JPanel drawingPanel;
	private volatile boolean finishedDrawing;
	private List<Coordinate> coordinates;
	
	public DrawingWindow() {
		setTitle(WINDOW_NAME);
		setSize(WINDOW_SIZE, WINDOW_SIZE);
		setLocation(400, 200);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		
		drawingPanel = new JPanel();
		cp.add(drawingPanel);
		initDrawingPanelListeners(drawingPanel);
	}

	private void initDrawingPanelListeners(JPanel drawingPanel) {
		DrawingUtil drawingUtil = new DrawingUtil();
		
		drawingPanel.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (!drawingUtil.isStarted()) {
					return;
				}
				
				drawingUtil.addCoordinate(e.getPoint());
				drawPoint(drawingPanel, e.getPoint());
			}

			private void drawPoint(JPanel drawingPanel, Point point) {
				Graphics2D g2d = (Graphics2D) drawingPanel.getGraphics();
				g2d.setColor(Color.BLACK);
				g2d.drawLine((int) point.getX(), (int) point.getY(), (int) point.getX(), (int) point.getY());
				g2d.dispose();
			}
		});
		
		drawingPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				drawingUtil.setStarted();
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (!drawingUtil.isStarted()) {
					return;
				}
				
				coordinates = drawingUtil.getCoordinates();
				DrawingWindow.this.dispose();
				finishedDrawing = true;
			}
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				initDrawingPanelBackground(drawingPanel);
			}
		});
	}

	private void initDrawingPanelBackground(JPanel drawingPanel) {
		drawingPanel.setOpaque(true);
		Graphics2D g2d = (Graphics2D) drawingPanel.getGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, drawingPanel.getWidth() - 1, drawingPanel.getHeight() - 1);
		g2d.dispose();
	}
	
	public boolean isFinishedDrawing() {
		return finishedDrawing;
	}
	
	public List<Coordinate> getCoordinates() {
		return coordinates;
	}
	
	private static class DrawingUtil {
		
		private boolean started;
		private List<Coordinate> points;

		public DrawingUtil() {
			started = false;
			points = new ArrayList<>();
		}
		
		public void setStarted() {
			this.started = true;
		}
		
		public boolean isStarted() {
			return started;
		}
		
		public void addCoordinate(Point point) {
			points.add(new Coordinate(point.getX(), point.getY()));
		}
		
		public List<Coordinate> getCoordinates() {
			return points;
		}
	}
}