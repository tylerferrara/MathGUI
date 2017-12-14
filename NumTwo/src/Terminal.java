import java.util.*;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Border;
import javafx.scene.Node;

public class Terminal implements Expression {

	private CompoundExpression parent;
	private String value;
	private Label pane;
	private int depth;
	
	/**
	 * @param value String representing Terminal's value
	 * @return Terminal
	 * public constructor for Terminal
	 */
	public Terminal(String value) {
		this.value = value;
		Label label = new Label(value);
//		pane = new HBox();
		pane = label;
//		pane.getChildren().add(label);
	}
	
	/**
	 * @return CompoundExpression
	 * returns the Terminal's parent
	 */
	@Override
	public CompoundExpression getParent() {
		return this.parent;
	}
	
	public LinkedList<Expression> getSubExpression(){
		return null;
	}
	public String getValue() {
		return this.value;
	}
	public Expression getMostSpecificFocus(double x, double y) {
		if(this.contains(x, y)) {
			return this;
		}
		else
			return null;
	}
	public boolean contains(double x, double y) {
		pane.localToScene(pane.getBoundsInLocal());
		return pane.contains(x, y);
	}
	/**
	 * @param parent
	 * sets Terminal's parent to parent
	 */
	@Override
	public void setParent(CompoundExpression parent) {
		this.parent = parent;

	}

	/**
	 * @return Expression
	 * returns the Terminal
	 */
	@Override
	public Expression deepCopy() {
		return this;
	}

	/**
	 * does nothing since Terminal is already flat
	 */
	@Override
	public void flatten() {
	}

	/**
	 * @param indentLevel number passed to recursiveConvertToString
	 * @return String
	 * returns the result of recursiveConvertToString
	 * called with the given indentLevel with a new line at the end
	 */
	@Override
	public String convertToString(int indentLevel) {
		return recursiveConvertToString(indentLevel);
	}
	
	/**
	 * @param indentLevel number of tabs to be added
	 * @return String
	 * indents the given indentLevel number of tabs then adds the
	 * toString value of the Terminal
	 */
	@Override
	public String recursiveConvertToString(int indentLevel) {
		String str = "";
		for(int i = 0; i < indentLevel; i++) {
			str += "\t";
		}
		str += this.value;
		return str;
	}
	@Override
	public Node getNode() {
//		final Label node = new Label(value);
//		return node;
		return this.pane;
	}

	@Override
	public String getString() {
		return this.value;
	}

}
