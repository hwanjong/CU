import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {
	private static MainFrame uniqueMainFrame =
			new MainFrame("Catch Mind");
	private DrawingPanel drawingpanel;
	private info info;
	private MainFrame(String title){
		super(title);
		setLayout(new BorderLayout());
		chatHandler chat = new chatHandler();
		add(BorderLayout.SOUTH,chat);
		info = new info();
		add(BorderLayout.EAST,info);
		drawingpanel = new DrawingPanel();
		add(BorderLayout.CENTER,drawingpanel);
	}
	public static MainFrame getInstance(){
		return uniqueMainFrame;
	}
	
	public void init(){
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(1000,700);
		setVisible(true);
	}
}

