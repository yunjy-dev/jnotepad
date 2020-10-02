import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;

public class JNotePad extends JFrame {
	
	private JTextPane _textPane;
	
	public JNotePad() {
		super("JNotePad");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_textPane = new JTextPane();
		add(_textPane);
		setJMenuBar(createMenuBar());
		add(createToolBar(), BorderLayout.NORTH);
		
		JScrollPane p = new JScrollPane(_textPane);
		add(p);
	}
	private JMenuBar createMenuBar() {
		
		JMenuBar menubar = new JMenuBar();

		JMenu m;
		// File
		m = new JMenu("File");
		m.add(new JMenuItem("New"));
		m.add(new JMenuItem("Open..."));
		m.add(new JMenuItem("Save"));
		m.add(new JMenuItem("Save As..."));
		m.addSeparator();
		m.add(new JMenuItem("Exit"));
		menubar.add(m);
		
		// Edit
		m = new JMenu("Edit");
		m.add(new JMenuItem("Cut"));
		m.add(new JMenuItem("Copy"));
		m.add(new JMenuItem("Paste"));
		menubar.add(m);
		
		//Help
		m = new JMenu("Help");
		m.add(new JMenuItem("Help"));
		m.add(new JMenuItem("About"));
		menubar.add(m);
		
		JMenuItem mi = new JMenuItem("About");
		mi.addActionListener(new AboutActionListener());
		m.add(mi);
		
		return menubar;
	}
	private JToolBar createToolBar() {

		JToolBar toolbar = new JToolBar();

		toolbar.add(new JButton("New"));
		toolbar.add(new JButton("Open"));
		toolbar.add(new JButton("Save"));
		toolbar.add(new JButton("Save As"));
		toolbar.addSeparator();
		
		toolbar.add(new JButton("Copy"));
		toolbar.add(new JButton("Cut"));
		toolbar.add(new JButton("Paste"));
		toolbar.addSeparator();
		
		toolbar.add(new JButton("Help"));
		toolbar.add(new JButton("About"));
		toolbar.addSeparator();
		
		return toolbar;
	}
	public void start() {
		setSize(600,400);
		setLocation(100,100);
		setVisible(true);
	}

	public static void main(String[] args) {
		
		new JNotePad().start();
	}
	
	class AboutActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String[] mesg = {
					"JNotePad v 0.1",
					"Author: June"
			};
			JOptionPane.showMessageDialog(JNotePad.this,  mesg, "About JNotePad", JOptionPane.INFORMATION_MESSAGE);
			
		}
	}

}



