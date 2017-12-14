import java.util.*;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.Node;

public class Parentheses extends NonTerminal {
	public Parentheses() {
		super.pane.getChildren().add(new Label("()"));
	}
	public String toString() {
		return "()";
	}
	public Node getNode() {
		HBox hbox = new HBox();
		for(int i = 0; i < super.children.size(); i++) {
			if(super.children.get(i) instanceof NonTerminal) {
				hbox.getChildren().add(new Label("("));
				hbox.getChildren().add(super.children.get(i).getNode());
				hbox.getChildren().add(new Label(")"));
			} else {
				hbox.getChildren().add(new Label("("+super.children.get(i).toString()+")"));
			}
			
		}
		return hbox;
	}

}
