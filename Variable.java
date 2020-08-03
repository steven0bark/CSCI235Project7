/**
 * Variable.java
 *
 * Creates an operation node in the parsing tree
 *
 * @author Steven Barker
 * CSCI 235, Wheaton College, Spring 2019
 * Project 7
 * 4/19/17
 */

public class Variable implements ExprNode{

	/**
	*Will be the constant subsituted for x
	*/
	private double var;

	/**
	*The left operand
	*/
	private ExprNode left;

	/**
	*The right operand
	*/
	private ExprNode right;

	/**
	*Constructor
	*Contsructs the variable node in the tree
	*/
	public Variable(){
		var = 0.0;//probably will change later
		left = null;
		right = null;
	}

	public double evaluate(double x){
		var = x;
		return var;
	}

}