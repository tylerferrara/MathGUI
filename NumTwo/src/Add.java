import java.util.*;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.Node;

public class Add extends NonTerminal {
	public Add() {
		super.pane.getChildren().add(new Label("+"));
	}
	
	public String toString() {
		return "+";
	}
}
