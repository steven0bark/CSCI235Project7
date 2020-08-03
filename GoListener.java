/**
 * GoListener.java
 *
 * Action Listener class for the Go button
 *
 * @author Steven Barker
 * CSCI 235, Wheaton College, Spring 2019
 * Project 7
 * Date?
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GoListener implements ActionListener, Painter{
	
	/**
	*The reference to the panel that will be painted
	*/
	private PaintPanel graphPanel;

	/**
	*the reference to the window that is displayed
	*/
	private GraphCalc theWindow;

	
	/**
	*Constructor
	*/

	public GoListener(PaintPanel graphPanel, GraphCalc theWindow){
		this.graphPanel = graphPanel;
		this.theWindow = theWindow;
		graphPanel.setPainter(this);
	}
	
	/**
	*actionPerformed
	*The action performed when the Go button is pushed
	*Repaints the panel
	*/
	public void actionPerformed(ActionEvent e){	
		graphPanel.repaint();
	}
	

	/**
     * Update the display using the given graphics
     * object.
     * Fills an ArrayList with all the pixels that need to be evaluated
     * Sets a random color for the line
     * Finds and paints the x and Y axis
     * Tests for the y=x exception
     * If true, sets the y point equal to the x point and paints at each corresponding pixel row and column
     * If false: finds the x value corresponding to current pixel column
     * Finds corresponding f(x) value
     * Finds pixel row corresponding to f(x)
     * Paints a 1,1 rectangle and the corresponding column and pixel
     * Catches NumberFormat Excpetions and StringIndexOutofBound Exceptions
     * PRECONDITION: xmin< xmax, ymin < ymax, all min and max values must be ints, f(x) must be in proper syntax
     * POSTCODNTION: The Paint panel will have the axies(if they are in range) and f(x) painted on it
     * @param g The graphics object to manipulate
     */
	public void paint(Graphics g){
		try{		
			
			if(theWindow.getXmin() >= theWindow.getXmax() || theWindow.getYmin() >= theWindow.getYmax()){
				throw new IllegalArgumentException();
			}

			ArrayList<Integer> xP = theWindow.getXPList();
			Color random = new Color((float)Math.random(),(float)Math.random(),(float)Math.random());

			//draws the x axis
			if(theWindow.getYmax() <= 0){

			}else{
			int xaxispixel = theWindow.yToPix(0);
			g.drawLine(0, xaxispixel, graphPanel.width(), xaxispixel);
			}

			//draws the y axis
			int yaxispixel = theWindow.findYAxis();
			g.drawLine(yaxispixel, 0, yaxispixel, graphPanel.height());

			boolean xexceptionFlag = theWindow.xExceptionTest();
			if(xexceptionFlag){
				for(Integer xpixel: xP){
					double xpoint = theWindow.pixToX((double)xpixel);
					double ypoint = xpoint;
					int ypixel = theWindow.yToPix((double)ypoint);
					if(ypoint >= theWindow.getYmin() && ypoint <= theWindow.getYmax()){
						g.setColor(random);
						g.fillRect(xpixel, ypixel, 1,1);
					}
				}
			}else{
				for(Integer xpixel: xP){
					double xpoint = theWindow.pixToX((double)xpixel);
					double ypoint = theWindow.evaluateAt(xpoint);				
					int ypixel = theWindow.yToPix(ypoint);
					if(ypoint >= theWindow.getYmin() && ypoint <= theWindow.getYmax()){
						g.setColor(random);
						g.fillRect(xpixel, ypixel, 1,1);
					}
				}
			}
		}catch(NumberFormatException e){
			theWindow.badInput(0);
			System.out.println("Bad Input, could not complete graph");
		}catch(StringIndexOutOfBoundsException e){
			theWindow.badInput(1);
			System.out.println("Bad Input");
		}catch(IllegalArgumentException e){
			theWindow.badInput(2);
			System.out.println("Bad Input");
		}	
	}
}
