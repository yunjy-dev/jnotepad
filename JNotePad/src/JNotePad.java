import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
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
	private ActionMap _actionMap;
	
	public JNotePad() {
		super("JNotePad");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_textPane = new JTextPane();
		_actionMap = createActionMap();

		add(_textPane);
		setJMenuBar(createMenuBar());
		add(createToolBar(), BorderLayout.NORTH);
		
		JScrollPane p = new JScrollPane(_textPane);
		add(p);
		
	}
	private ActionMap createActionMap() {
		ActionMap am = new ActionMap();
		am.put("about", new AboutAction());
		am.put("help", new HelpAction());

		return am;
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
//		m.add(new JMenuItem("Help"));
		m.add(new JMenuItem(_actionMap.get("help")));
//		m.add(new JMenuItem("About"));
		
//		JMenuItem mi = new JMenuItem("About");
//		mi.addActionListener(new AboutAction());
//		m.add(mi);
		m.add(new JMenuItem(_actionMap.get("about")));
		
		menubar.add(m);
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
		
//		toolbar.add(new JButton("Help"));
		toolbar.add(new JButton(_actionMap.get("help")));
//		toolbar.add(new JButton("About"));
		toolbar.add(new JButton(_actionMap.get("about")));
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
	
	private class AboutAction extends AbstractAction{
		public AboutAction() {
			super("About");//About text�� ���̰�
		}
		public void actionPerformed(ActionEvent e) {
			String[] mesg = {
					"JNotePad v 0.1",
					"Author: June"
			};
			JOptionPane.showMessageDialog(JNotePad.this,  mesg, "About JNotePad", JOptionPane.INFORMATION_MESSAGE);
			
		}
	}
	
	private class HelpAction extends AbstractAction{
		public HelpAction() {
			super("Help");
		}
		public void actionPerformed(ActionEvent e) {
			String[] mesg = {
					"Sorry",
					"Help contents are not supported yet."
			};
			JOptionPane.showMessageDialog(JNotePad.this, mesg, "Help JNotePad", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}



