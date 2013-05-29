import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;

public class DrawingPanel extends JPanel{
	private Color lineColor, fillColor;
	public DrawingPanel(){
		super();
		this.setForeground(Color.BLACK);
		this.setBackground(Color.WHITE);
	}
}
