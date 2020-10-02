import javax.swing.JFrame;

public class JNotePad extends JFrame {
	
	
	public JNotePad() {
		super("JNotePad");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void start() {
		setSize(600,400);
		setLocation(100,100);
		setVisible(true);
	}

	public static void main(String[] args) {
		
		new JNotePad().start();
	}

}
