import javafx.scene.Node;
import javafx.scene.control.Label;

public class Multiply extends NonTerminal {
	public Multiply() {
		super.pane.getChildren().add(new Label("*"));
	}
	public String toString() {
		return "*";
	}

}
