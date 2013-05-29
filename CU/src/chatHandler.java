import java.awt.*;
import java.net.*;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class chatHandler extends JPanel implements ActionListener {
	
	TextField tf;
	TextArea ta;
	Button b;
	String msg;
	public chatHandler(){
		super();
		this.setLayout(new BorderLayout());
		ta = new TextArea();
		ta.setEditable(false);
		add(BorderLayout.CENTER,ta);
		tf = new TextField();
		tf.addActionListener(this);
		add(BorderLayout.SOUTH,tf);
	}
	
	public void actionPerformed(ActionEvent e){
		ta.append(tf.getText());
		ta.append("\n");
		tf.setText("");
		tf.requestFocus();
	}
	
	public void sender(){
		
	}
	
	public void receive(){
		ta.append(msg);
		ta.append("\n");
	}
}
