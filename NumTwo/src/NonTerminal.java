import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.*;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;


public class NonTerminal implements CompoundExpression {
	
	private CompoundExpression parent;
	protected LinkedList<Expression> children;
	protected HBox pane;
	private int depth;
	protected ArrayList<HBox> selectOptions;
	
	/**
	 * @return NonTerminal
	 * public constructor for NonTerminal
	 */
	public NonTerminal() {
		selectOptions = new ArrayList<HBox>();
		this.children = new LinkedList<Expression>();
		pane = new HBox();
	}
	
	public boolean contains(double x, double y) {
		pane.localToScene(pane.getBoundsInLocal());
		return pane.contains(x, y);
	}
	
	@Override
	public Expression getMostSpecificFocus(double x, double y) {
		for(int i = 0; i < this.children.size(); i++) {
			Expression child = this.children.get(i);
			if(child.contains(x, y)){
				return child;
			}
		}
		return null;
	}
	/**
	 * @return CompoundExpression
	 * returns the parent of the NonTerminal
	 */
	@Override
	public CompoundExpression getParent() {
		return this.parent;
	}

	/**
	 * @param parent is CompoundExpression
	 * sets the parent of the NonTerminal to parent
	 */
	@Override
	public void setParent(CompoundExpression parent) {
		this.parent = parent;
	}

	/**
	 * @return Expression
	 * creates a copy of the entire Expression with the NonTerminal at its root
	 */
	@Override
	public Expression deepCopy() {
		NonTerminal copy;
		if(this.toString().equals("+")) {
			copy = new Add();
		} else if(this.toString().equals("*")) {
			copy = new Multiply();
		} else {
			copy = new Parentheses();
		}
		
		for(int i = 0; i < this.children.size(); i++) {
			copy.addSubexpression((Expression) this.children.get(i).deepCopy());
			copy.getSubExpression().get(i).setParent(copy);
		}
		return copy;
	}

	/**
	 * if one of the children of the NonTerminal is the same type
	 * it will make the child's children the children of the
	 * NonTerminal and set the NonTerminal as their parent,
	 * then removes the first child from the NonTerminal's children
	 */
	@Override
	public void flatten() {
		for(int i = 0; i < this.children.size(); i++) {
			Expression child = this.children.get(i);
			child.flatten();
			if(this.toString().equals(child.toString())) {
				// TIME TO OPTIMIZE
				// set child's children to have this object as the parent
				LinkedList<Expression> childChildren = ((NonTerminal) child).getSubExpression();
				for(int k = childChildren.size()-1; k >= 0; k--) {
					childChildren.get(k).setParent(this);
					// add child's children to this.children
					children.add(i, childChildren.get(k));
				}
			
				LinkedList<Expression> children = this.children;		
				children.remove(i+childChildren.size());
				setSubExpression(children);
				// remove child from this.children
				
			}
			
		}
		
	}

	/**
	 * @param indentLevel number to be passed to recursiveConvertToString
	 * @return String
	 * returns the result of recursiveConvertToString
	 * called with the given indentLevel with a new line at the end
	 */
	@Override
	public String convertToString(int indentLevel) {
		return recursiveConvertToString(indentLevel)+"\n";
	}
	
	/**
	 * @param indentLevel number of tabs to be added
	 * @return String
	 * indents the given indentLevel number of tabs then adds the
	 * toString value of the NonTerminal and then calls
	 * itself on the children with the original indentLevel + 1
	 */
	@Override
	public String recursiveConvertToString(int indentLevel) {
		String str = "";
		for(int i = 0; i < indentLevel; i++) {
			str += "\t";
		}
		str += this.toString();
		for(int i = 0; i < this.children.size(); i++) {
			str += "\n";
			str += this.children.get(i).recursiveConvertToString(indentLevel+1);
		}
		return str;
		
	}

	/**
	 * @param subexpression
	 * adds the given Expression to the NonTerminal's children
	 */
	@Override
	public void addSubexpression(Expression subexpression) {
//		pane.getChildren().add(subexpression.getHbox());
		this.children.add(subexpression);
	}
	
	/**
	 * @param subExpressionList
	 * sets the NonTerminal's children to subExpressionList
	 */
	public void setSubExpression(LinkedList<Expression> subExpressionList) {
		for(int i = 0; i < subExpressionList.size(); i++) {
//			pane.getChildren().add(subExpressionList.get(i).getHbox());
		}
		this.children = subExpressionList;
	}
	
	/**
	 * @return the NonTerminal's children
	 */
	public LinkedList<Expression> getSubExpression() {
		return this.children;
	}

	public Node getNode() {
		HBox hbox = new HBox();
		for(int i = 0; i < this.children.size(); i++) {
			if(this.children.get(i) instanceof NonTerminal) {
				hbox.getChildren().add(this.children.get(i).getNode());
			} else {
				hbox.getChildren().add(new Label(this.children.get(i).getString()));
			}
			if(i+1 != this.children.size()) {
				hbox.getChildren().add(new Label(this.toString()));
			}
		}
		return hbox;
	}
	
	
//	@Override
//	public Node getNode() {
//		HBox hbox = new HBox();
//		if(this.children.size() > 0) {
//			for(int i = 1; i < this.children.size(); i++) {
//				hbox.getChildren().add(this.pane);
//				hbox.getChildren().add(this.children.get(i).getNode());
//			}
//		}
//		return hbox;
//	}
	
	@Override
	public String getString() {
		String str = "";
		for(Expression child : children)
		{
			str+= child.getString();
		}
		return str;
	}
	
}
