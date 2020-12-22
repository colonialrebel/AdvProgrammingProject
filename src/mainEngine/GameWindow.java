package mainEngine;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GameWindow {
	JFrame _frame;
	JMenuBar _menubar;
	JMenu _menu;
	JPanel _center;
	JTextArea _textArea;
	JPanel _south;
	JButton _choice1;
	JButton _choice2;
	JButton _choice3;
	ThePandemicGame _pg;
	public GameWindow() {//constructor
		buildFrame();
		buildTextArea();
		buildChoiceArea();
		//buildMenu();
		_frame.setVisible(true);
	}
	public void setGame(ThePandemicGame pg) {
		_pg=pg;
	}


	private void buildFrame() {
		_frame = new JFrame("A Covid Life");
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setLayout(new BorderLayout());
        _frame.setSize(800,640);
	}
	/*
	private void buildMenu() {//Im keeping this method here even Though I am not using it, just incase I want to use it in the future
		_menubar = new JMenuBar();
		_menu = new JMenu("test menu");
		_menubar.add(_menu);
		
		JMenuItem testitem = new JMenuItem("test item");
		_menu.add(testitem);
		_frame.getContentPane().add(BorderLayout.NORTH,_menubar);
	}
	*/
	private void buildTextArea() {
        _center = new JPanel();
        _center.setLayout(new BoxLayout(_center,BoxLayout.PAGE_AXIS));
		_textArea = new JTextArea(10,30);
        _textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(_textArea);
        _center.add(scrollPane);
        _frame.getContentPane().add(BorderLayout.CENTER,_center);
	}
	private void buildChoiceArea() {
		_south = new JPanel();
		_choice1 = new JButton("choice 1");
		_choice2 = new JButton("choice 2");
		_choice3 = new JButton("choice 3");
		
		_choice1.setSize(200,100);
		_choice2.setSize(200,100);
		_choice3.setSize(200,100);
		
		_choice1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				_pg.choiceMade(1);
			}
		});
		
		_choice2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				_pg.choiceMade(2);
			}
		});
		
		_choice3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				_pg.choiceMade(3);
			}
		});
		_south.setLayout(new GridLayout(1,3));
		_south.add(_choice1);
		_south.add(_choice2);
		_south.add(_choice3);
		
        _frame.getContentPane().add(BorderLayout.SOUTH,_south);
	}
	public void pushText(String str) {
		_textArea.append(str+"\n");
	}
	public void pushChoices(String c1, String c2, String c3) {
		_choice1.setText(c1);
		_choice2.setText(c2);
		_choice3.setText(c3);
	}
}
