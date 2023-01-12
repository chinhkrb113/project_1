package hust.pms.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public interface LabelHelper {
	public void setLabel(Label label, Pos pos, Color color, String text);
	public void clearLabel(Label label);
}
