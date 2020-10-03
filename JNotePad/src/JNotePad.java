import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class JNotePad extends JFrame {
	
	private JTextPane _textPane;
	private ActionMap _actionMap;
	private boolean _isSaved;
	private JFileChooser _fc;
	private File _file;
	
	public JNotePad() {
		super("JNotePad");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_textPane = new JTextPane();
		_actionMap = createActionMap();
		_isSaved = true;
		_fc = new JFileChooser(".");
		_file = null;
		_textPane.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				_isSaved = false;
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				_isSaved = false;
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				_isSaved = false;
				
			}
		});
		
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
		am.put("cut", new CutAction());
		am.put("exit", new ExitAction());
		am.put("new", new NewAction());
		am.put("open", new OpenAction());
		am.put("save", new SaveAction());
		am.put("saveas", new SaveAsAction());



		return am;
	}
	private JMenuBar createMenuBar() {
		
		JMenuBar menubar = new JMenuBar();

		JMenu m;
		// File
		m = new JMenu("File");
		m.add(new JMenuItem(_actionMap.get("new")));
		m.add(new JMenuItem(_actionMap.get("open")));
		m.add(new JMenuItem(_actionMap.get("save")));
		m.add(new JMenuItem(_actionMap.get("saveas")));
		m.addSeparator();
		m.add(new JMenuItem(_actionMap.get("exit")));
		menubar.add(m);
		
		// Edit
		m = new JMenu("Edit");
		m.add(new JMenuItem(_actionMap.get("cut")));
		m.add(new JMenuItem("Copy"));
		m.add(new JMenuItem("Paste"));
		menubar.add(m);
		
		//Help
		m = new JMenu("Help");
		m.add(new JMenuItem(_actionMap.get("help")));
		m.add(new JMenuItem(_actionMap.get("about")));
		
		menubar.add(m);
		return menubar;
	}
	private JToolBar createToolBar() {

		JToolBar toolbar = new JToolBar();

//		toolbar.add(new JButton("New"));
		toolbar.add(new JButton(_actionMap.get("new")));
//		toolbar.add(new JButton("Open"));
		toolbar.add(new JButton(_actionMap.get("open")));
//		toolbar.add(new JButton("Save"));
		toolbar.add(new JButton(_actionMap.get("save")));
//		toolbar.add(new JButton("Save As"));
		toolbar.add(new JButton(_actionMap.get("saveas")));

		toolbar.addSeparator();
		
		toolbar.add(new JButton("Copy"));
//		toolbar.add(new JButton("Cut"));
		toolbar.add(new JButton(_actionMap.get("cut")));
		toolbar.add(new JButton("Paste"));
		toolbar.addSeparator();
		
//		toolbar.add(new JButton("Help"));
		toolbar.add(new JButton(_actionMap.get("help")));
//		toolbar.add(new JButton("About"));
		toolbar.add(new JButton(_actionMap.get("about")));
		toolbar.addSeparator();
		
		return toolbar;
	}
	
	private void start() {
		setSize(600,400);
		setLocation(100,100);
		setVisible(true);
	}
	
	private boolean confirmSave() {
		if(_isSaved)
			return true;
		int ret = JOptionPane.showConfirmDialog(this, "Content has been modified. Save Changes?","JNotePad", JOptionPane.YES_NO_CANCEL_OPTION);
		switch(ret) {
		case JOptionPane.YES_OPTION:
			save();
			return true;
			
		case JOptionPane.NO_OPTION:
			return true;
		
		default:
			return false;
		}
	}

	private boolean open() {
		if(_fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
			File file = _fc.getSelectedFile();
			try {
				open(file);
				setTitle(file.getName() + "  - JNotePad");
				_file = file;
				return true;
			} catch(IOException e) {
				JOptionPane.showMessageDialog(this, "Cannot open file" + file, "JNotePad", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return false;
	}
	
	private void open(File file) throws IOException{
		BufferedReader r = new BufferedReader(new FileReader(file));
		StringBuffer sbuf = new StringBuffer();
		char[] buf = new char[1024];
		int nread;
		
		while ((nread = r.read(buf))!= -1) {
			sbuf.append(buf, 0, nread);
		}
		r.close();
		_textPane.setText(sbuf.toString());
	}
	
	
	private boolean save() {
		if(_file==null)
			return saveAs();
		else
			try {
				save(_file);
				return true;
			} catch (IOException e) {
				showSaveErrorMessage();
				e.printStackTrace();
			}
		return false;
	}
	private void showSaveErrorMessage() {
		String[] mesg = {
				"Cannot save file: " + _file, "Access denied"
		};
		JOptionPane.showMessageDialog(this, mesg, "JNotePad", JOptionPane.ERROR_MESSAGE);
	}
	//Save 인 경우 미리 연결된 file parameter 
	//Save As 인 경우 사용자가 선택한 file parameter 
	private void save(File file) throws IOException{
		BufferedWriter w = new BufferedWriter(new FileWriter(file));
		w.write(_textPane.getText());
		w.close();
	}
	private boolean saveAs() {
		if(_fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION) {
			File file = _fc.getSelectedFile();
			try {
				save(file);
				_file = file;
				setTitle(_file.getName() + " - JNotePad");
				return true;
			} catch (Exception e) {
				showSaveErrorMessage();
				return false;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		
		new JNotePad().start();
		
		System.out.println(Math.max(10, 5));
		
	}
	
	private class AboutAction extends AbstractAction{
		public AboutAction() {
			super("About");//About text가 보이게
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
	
	private class CutAction extends AbstractAction{
		public CutAction() {
			super("Cut");
		}
		public void actionPerformed(ActionEvent e) {
			System.out.println(getValue(Action.NAME));
			System.out.println(getValue(_textPane.getText().toString()));//왜 null???????
			_textPane.cut();
		}
	}
	
	private class ExitAction extends AbstractAction{
		public ExitAction() {
			super("Exit");
		}
		
		public void actionPerformed(ActionEvent e) {
			System.out.println(getValue(Action.NAME));
			if(!confirmSave())
				return;
			System.exit(0);
		}
	}
	
	private class NewAction extends AbstractAction{
		public NewAction() {
			super("New");
		}
		
		public void actionPerformed(ActionEvent e) {
			System.out.println(getValue(Action.NAME));
			//TODO: 사용자에게 저장할 것인지 여부 물어보는 로직 추가
			//추가완료
			if(!confirmSave())
				return;
			_textPane.setText("");
			_isSaved = true;
		}
	}
	
	private class OpenAction extends AbstractAction{
		public OpenAction() {
			super("Open...");
		}

		public void actionPerformed(ActionEvent e) {
			System.out.println(getValue(Action.NAME));
			if(!confirmSave())
				return;
			//open();
			_isSaved = open();

		}
	}
	
	private class SaveAction extends AbstractAction{
		public SaveAction() {
			super("Save");
		}
		public void actionPerformed(ActionEvent e) {
			System.out.println(getValue(Action.NAME));
			//save();
			_isSaved = save();
		}
	}
	
	private class SaveAsAction extends AbstractAction{
		public SaveAsAction() {
			super("Save As...");
		}
		public void actionPerformed(ActionEvent e) {
			System.out.println(getValue(Action.NAME));
			//saveAs();
			_isSaved = saveAs();
		}
	}

}



