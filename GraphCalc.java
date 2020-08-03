/**
 * GraphCalc.java
 *
 * Graphing calculator program (class with main method)
 *
 * @author Steven Barker
 * CSCI 235, Wheaton College, Spring 2019
 * Project 7
 * 5/3/19
 */


import javax.swing.*;  
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GraphCalc {


    /**
    *The texts fields for the input of the function, x and y min value, x and y value
    */
    private JTextField funcField, xminField, yminField, xmaxField, ymaxField;
    
    /**
    *The panel that the graph will be painted on
    */
    private PaintPanel graphPanel;
    

    /**
    *Constructor
    *Constucts and displays the window and all neccecary GUI components
    */

    public GraphCalc(){
        JFrame window = new JFrame("Graphing calculator");
        window.setLayout(new FlowLayout());
        window.setSize(450, 800);
        
        graphPanel = new PaintPanel(400, 500);



        funcField = new JTextField(25);
        xminField = new JTextField(5);
        yminField = new JTextField(5);
        xmaxField = new JTextField(5);
        ymaxField = new JTextField(5);


        funcField.setText("(x)");
        xminField.setText("-10");
        yminField.setText("-10");
        xmaxField.setText("10");
        ymaxField.setText("10");

        JButton go = new JButton("Go");
        go.addActionListener(new GoListener(graphPanel, this));
        

        JLabel func = new JLabel("f(x): ");
        JLabel xmin = new JLabel("x min: ");
        JLabel ymin = new JLabel("y min: ");
        JLabel xmax = new JLabel("x max: ");
        JLabel ymax = new JLabel("y max: "); 

    window.add(graphPanel);
    JPanel panel2 = new JPanel();
    panel2.setLayout(new FlowLayout());

    JPanel panel3 = new JPanel();
    panel3.setLayout(new GridLayout(2,2));

    window.add(panel2);
    window.add(panel3);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel2.add(func);
    panel2.add(funcField);
    
    panel3.add(xmin);
    panel3.add(xminField);
    
    panel3.add(ymin);
    panel3.add(yminField);
    
    panel3.add(xmax);
    panel3.add(xmaxField);
    
    panel3.add(ymax);
    panel3.add(ymaxField);

    panel2.add(go);
     
    window.setVisible(true);
    }

    public static void main(String[] args) {
        GraphCalc theWindow = new GraphCalc();
    }

    /**
    *xRange
    *Finds the range of x to be graphed
    *takes the absolute value of the min and max value and adds them
    *@return an integer with the range of x to be graphed
    */
    public int xRange(){
        int absoluteXmin = Math.abs(this.getXmin());
        int absoluteXmax = Math.abs(this.getXmax()); 
        return absoluteXmin + absoluteXmax;

    }
    
    /**
    *yRange
    *Finds the range of y to be graphed
    *takes the absolute value of the min and max value and adds them
    *@return an integer with the range of y to be graphed
    */
    public int yRange(){
        int absoluteYmin = Math.abs(this.getYmin());
        int absoluteYmax = Math.abs(this.getYmax());
        return absoluteYmin + absoluteYmax;
    }

    /**
    *pixToX
    *Finds the corresponding x value at a pixel column
    *Uses the range and panel width to find the number of x values per pixel length
    *Finds the amount of x values to move over to reach the specified pixel value
    *Adds that many x values to the x minimum
    *PRECONDITION: the pixel value must be typecasted to a double
    *@param pixel is the pixel that you want to find the corresponding x value of
    *@return will be the x value of the specified pixel
    */
    public double pixToX(double pixel){
        double xfactor = (double)this.xRange()/(double)graphPanel.width();//x values per pixel
        double xdistance = xfactor * pixel;
        return xdistance+this.getXmin(); 
    }

    /**
    *ytoPix
    *Finds the corresponding pixel row of a y value
    *Uses the graph hieght and y range to find the number of pixels per y value
    *Finds the distance needed to move from the Y min to get to the current y value
    *Multiplies the distance moved in y values to the distance moved in pixel rows
    *Moves that many pixel rows up from the bottom of the graph
    *PRECONDITION: y must be double
    *@param y is the specified y value 
    *@return the int of the corresponding pixel row
    */
    public int yToPix(double y){
        double yfactor = (double)graphPanel.height()/(double)this.yRange();//pixels per y value
        double ydistance = y - this.getYmin();
        double numofpixels = yfactor * ydistance;
        double yPix = graphPanel.height() - numofpixels;
        return (int) yPix;
    }

    /**
    *getXPList
    *Gets an array list with every pixel column value needed to be graphed
    *Starting at 0 adds every integer to the list until the last column of the graph panel is added
    *@return An ArrayList with every pixel column that must be graphed
    */
    public ArrayList<Integer> getXPList(){
        ArrayList<Integer> newList = new ArrayList<Integer>();

        for(int i = 0; i < graphPanel.width();i++){
            newList.add(i);//puts all of the pixel values in an arrayList
        }
        return newList;
    }

    /**
    *findYAxis
    *Finds where to draw the Y axis
    *Finds the x values per pixel
    *Finds the number of pixel values in the x max
    *Subtracts that many pixels from the graph width to find which coloumn where x=0
    *@return the integer of the coloumn where x = 0
    */
    public int findYAxis(){
        double xfactor = (double)graphPanel.width()/(double)this.xRange();
        double distance = (double)this.getXmin()*xfactor;
        double pixel = 0 - distance;
        return (int)pixel;
    }

    public int findXAxis(){
        double yfactor = (double)graphPanel.height()/(double)this.yRange();
        double distance = (double)this.getYmin()*yfactor;
        System.out.println("pixel distance " + distance);
        double ypixel = graphPanel.height() + distance;
        return (int)(graphPanel.height()-ypixel);
    }

    /**
    *xExceptionText
    *Tests if the function is y=x
    *If the text in the function field is x, returns true
    *Else is returns false
    *@return a boolean variable indicating what the function is
    */

    public boolean xExceptionTest(){
        if(funcField.getText().equals("(x)") || funcField.getText().equals("x")){
            return true;
        }else return false;
    }

    /**
    *badInput
    *The error message is put if the calculator hits any exception
    *Puts an error message in the text fields when called
    *@param error indicates what the error is
    *POSTCONDITION: the corresponding texts field will now contain an error message
    */
    public void badInput(int error){
      if(error == 0){
        xminField.setText("Put int");
        yminField.setText("Put int");
        xmaxField.setText("Put int");
        ymaxField.setText("Put int");
        }
      if(error == 1){
        funcField.setText("Cannot complete graph, Check f(x) Syntax");
      }
      if(error == 2){
        xminField.setText("min<max");
        yminField.setText("min<max");
        xmaxField.setText("min<max");
        ymaxField.setText("min<max");
      }
    }

    /**
    *evaluateAt
    *find the F(x) value corresponding to the given x value
    *creates an ExprNode object to hold the parsing tree from the 
    *string given in the function text field
    *evaluates the function given at the given x value
    *PRECONDITION: xvalue must be a double
    *PRECONDITION: the string in the function field must be in the proper
    *syntax for the Interpreter class to work properly
    *@param xvalue is the value being evaluated at
    *@return the y value corresponding to the x value
    */
    public double evaluateAt(double xvalue){
        ExprNode tree = Interpreter.parse(funcField.getText());
        return tree.evaluate(xvalue);
    
    }


    /**
    *getXmin
    *Accessor method to the input in the X min field
    *@return the integer of the x min value
    */
    public int getXmin(){
            return Integer.parseInt(xminField.getText());
    } 

     /**
    *getXmax
    *Accessor method to the input in the X max field
    *@return the integer of the x max value
    */
    public int getXmax(){
        return Integer.parseInt(xmaxField.getText());
    }

    /**
    *getYmin
    *Accessor method to the input in the Y min field
    *@return the integer of the Y min value
    */
    public int getYmin(){
        return Integer.parseInt(yminField.getText());
        
    }


    /**
    *getXmin
    *Accessor method to the input in the Y max field
    *@return the integer of the Y max value
    */
    public int getYmax(){
        return Integer.parseInt(ymaxField.getText());
    }   
}
