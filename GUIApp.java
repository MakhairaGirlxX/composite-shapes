package ShapesTwo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIApp {

	public static void main(String[] args) {

		JFrame myFrame = new AppFrame();
	}

}

class AppFrame extends JFrame
{
	public AppFrame()
	{
		this.setLayout(new GridLayout(1, 1));
		this.add(new MainPanel());
		this.setSize(600, 600);
		this.setLocation(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}

class MainPanel extends JPanel
{
	private final DrawPanel drawPnl = new DrawPanel();
	
	JPanel pnlContainer;
	
	JButton create;
	JButton erase;	
	
	MainPanel()
	{
		super();
		this.setBorder(BorderFactory.createTitledBorder("Shapes"));
		pnlContainer = new JPanel();
		drawPnl.setBackground(Color.white);
		
		this.setLayout(new BorderLayout());
		
		GridLayout layout = new GridLayout(1, 2);
		layout.setHgap(20);
		pnlContainer.setLayout(layout);
		
		create = new JButton("Create");
		erase = new JButton("Erase");
		
		pnlContainer.add(create);
		pnlContainer.add(erase);
		
		this.add(pnlContainer, "South");
		this.add(drawPnl, "Center");
		
		shapeListeners();
	}
	
	private void shapeListeners()
	{
		create.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				 
				int randomCoor = (int)Math.floor(Math.random()*(600-20+1)+20);
				int randomSize = (int)Math.floor(Math.random()*(300-20+1)+20);
				  
				Shapes circle = new Circle(randomCoor, randomCoor, randomSize, randomSize, Color.red);
				Shapes circle1 = new Circle(randomCoor, randomCoor, randomSize, randomSize, Color.CYAN);
				
				Shapes rectangle = new Rectangle(randomCoor, randomCoor, randomSize, randomSize, Color.black);
				Shapes rectangle1 = new Rectangle(randomCoor, randomCoor, randomSize, randomSize, Color.GREEN);
				Shapes rectangle2 = new Rectangle(randomCoor, randomCoor, randomSize, randomSize, Color.PINK);
				
				Shapes triangle = new Triangle(randomCoor, randomCoor, randomSize, randomSize, Color.BLUE);
				
				Random rnd = new Random();
				int selection = 1 + rnd.nextInt(6);
				Shapes shape = null;
				
				switch(selection)
				{
				
				case 1: 
					shape = new Circle(50, 50, 250, 250, Color.red);; 
				break;
				
				case 2: 
					shape = new Rectangle(50, 50, 250, 250, Color.black);
				break;
				
				case 3: 
					shape = new Triangle(50, 50, 250, 250, Color.black);
				break;
				
				case 4: 
					shape = new ShapeArr(50, 50, 250, 250, Color.black, new Shapes[] {new ShapeArr(50, 50, 250, 250, Color.black, new Shapes[] {circle}), circle, rectangle, triangle});
				break;
				
				case 5:
					shape = new ShapeArr(50, 50, 250, 250, Color.black, new Shapes[] {circle1, circle1, circle1, circle1});
				break;
				
				case 6:
					shape = new ShapeArr(50, 50, 250, 250, Color.black, new Shapes[] {rectangle, rectangle1, rectangle2, rectangle});
				}
				
				drawPnl.addShape(shape);				
			}
		});
		
		erase.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				drawPnl.clearPanel();				
			}
		});
	}

}

class DrawPanel extends JPanel
{
	public ArrayList<IShape> shapes = new ArrayList<>();
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);	
		
		for(IShape s : shapes)
		{
			Graphics2D g2 = (Graphics2D) g.create();
			s.draw(g2);
			System.out.println("Shape drawn");
		}			
	}
	
	public void addShape(Shapes shape)
	{		
		shapes.add(shape);	
		this.repaint();
	}
	
	public void clearPanel()
	{
		if(!shapes.isEmpty())
		{
			shapes.remove(shapes.size() - 1);
			this.repaint();
	
		}
	}
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }
}


abstract class Shapes implements IShape
{	
	private int x, y;
	private int width, height;
	private Color c;
	
	public Shapes(int x, int y, int width, int height, Color c)
	{
		this.x = x;
		this.y = y;
		this.c = c;
		this.width = width;
		this.height = height;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public Color getColor()
	{
		return c;
	}
	
	public abstract void draw(Graphics g);
}

class Circle extends Shapes
{	
	
	public Circle(int x, int y, int width, int height, Color c)
	{
		super(x, y, width, height, c);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setColor(getColor());	
		g2.drawOval(getX(), getY(), getWidth(), getHeight());		
		
	}
	
}

class ShapeArr extends Shapes
{	
	ArrayList<Shapes> shapeArr;
	
	public ShapeArr(int x, int y, int width, int height, Color c, Shapes[] s)
	{
		super(x, y, width, height, c);
		shapeArr = new ArrayList<Shapes>();
		
		for(int i = 0; i < s.length; i++)
		{
			shapeArr.add(s[i]);
		}
	}
	
	public void draw(Graphics g) {
		for(int i = 0; i < shapeArr.size(); i++){
			shapeArr.get(i).draw(g);		
		}		
	}
	
}

class Rectangle extends Shapes
{	
	public Rectangle(int x, int y, int width, int height, Color c)
	{
		super(x, y, width, height, c);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setColor(getColor());	
		g2.drawRect(getX(), getY(), getWidth(), getHeight());		
		
	}
	
}

class Triangle extends Shapes
{	
	public Triangle(int x, int y, int width, int height, Color c)
	{
		super(x, y, width, height, c);
	}
	
	public void draw(Graphics g) {

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setColor(getColor());	
		g2.drawPolygon(new int[] {10, 20, 30} , new int[] {100, 20, 100}, 3);		
		
	}
	
}

interface IShape
{
	void draw(Graphics g);
}

