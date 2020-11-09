package pandemicGame;

/* A text-writing gui meant to facilitate the creation of "events" for our covid-simulation game. 
 * Events are the backbone of the gameplay loop and the player will be presented with many of them each game
 * An event could be anything from walking outside or going to a store
 * Currently only the event name (ie "Dine at a restaurant") and the info are saved
 * with the name being the first line of the file and the info being subsequent lines (similar to how fasta files are read)
 * More functionality (quick stats, dropdowns, checkboxes) will be added. However, events will always be saved as individual text files to be parsed by the main game code
 * Save and Load functions to access files on pc. The left side of the GUI shows loaded events for reference when editing. New events are saved via "Add event" button.
 * 
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EventBuilder extends JFrame
{
	private static final long serialVersionUID = 3794059922116115530L;
	
	private JTextArea aTextArea = new JTextArea();
	private String eventTitle = "";
	private JTextField eventTitleField = new JTextField("Enter event title here",2);
	private String eventDesc = "";
	private JLabel currentFile = new JLabel("Current File");
	private JLabel savedFile = new JLabel("");
	private JButton addEventButton = new JButton("Add event");
	private String savedFileName = "";
	JLabel openLabel = new JLabel();
	
	private JPanel getBottomPanel2()
	{
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		
		// Add/save event as a text file, currently only reads for event name and subsequent text
		addEventButton.setMnemonic(KeyEvent.VK_ENTER);
		addEventButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				{
					eventTitle = eventTitleField.getText();
					eventDesc = aTextArea.getText();
					saveToFile();
					eventTitleField.setText("");
					aTextArea.setText("");
					updateTextField();
					savedFile.setText("File saved as: "+savedFileName+". Event name: "+eventTitle);
					System.out.println(eventDesc);
					System.out.println(eventTitle);
				}
			}
		});
		
		// Clear event text
		JButton deleteEventButton = new JButton("Delete Event");
		deleteEventButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				updateTextField();
			}
		});
		panel.add(addEventButton);
		panel.add(deleteEventButton);
		return panel;
	}

	private void updateTextField()
	{
		aTextArea.setText("");
		validate();
	}
	
	public EventBuilder() 
	{
		super("Event Builder");
		
		setLocationRelativeTo(null);
		setSize(800,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getBottomPanel2(), BorderLayout.SOUTH);
		getContentPane().add(aTextArea, BorderLayout.CENTER);
		getContentPane().add(eventTitleField, BorderLayout.NORTH);
		getContentPane().add(openLabel, BorderLayout.LINE_START);
		getContentPane().add(savedFile, BorderLayout.LINE_END);
		setJMenuBar(getMyMenuBar());
		
		updateTextField();
		setVisible(true);
	}
	
	private void loadFromFile() 
	{
		JFileChooser jfc = new JFileChooser();
		
		if( jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			return;
		
		if( jfc.getSelectedFile() == null)
			return;
			
		File chosenFile = jfc.getSelectedFile();
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(chosenFile));
			String line = reader.readLine();
			this.eventTitle = line;
			eventTitleField.setText(this.eventTitle);
			this.eventDesc="";

			while (line != null) {
				line=reader.readLine();
				if (line != null) {
					aTextArea.append(line+"\n");
					this.eventDesc = this.eventDesc + "<br>" + line;
				}
			}
			
			openLabel.setText("<html>"+"NAME OF EVENT: "+this.eventTitle+"<br>"+this.eventDesc+"</html>");

			reader.close();

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Could not read file", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void saveToFile()
	{
		JFileChooser jfc = new JFileChooser();
			
		if( jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
			return;
		
		if( jfc.getSelectedFile() == null)
			return;
			
		File chosenFile = jfc.getSelectedFile();
			
		if( jfc.getSelectedFile().exists())
		{
			String message = "File " + jfc.getSelectedFile().getName() + " exists.  Overwrite?";
				
			if( JOptionPane.showConfirmDialog(this, message) != 
					JOptionPane.YES_OPTION)
					return;			
		}
		
		try
		{
			BufferedWriter writer= new BufferedWriter(new FileWriter(chosenFile));
			writer.write(this.eventTitle+ "\n");
			writer.write(this.eventDesc);
			writer.flush();  
			writer.close();
			savedFileName = jfc.getSelectedFile().getName();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Could not write file", JOptionPane.ERROR_MESSAGE);
		}
	}
	private JMenuBar getMyMenuBar()
	{
		JMenuBar jmenuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		jmenuBar.add(fileMenu);
		
		JMenuItem openItem = new JMenuItem("Open File");
		fileMenu.add(openItem);
		
		openItem.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("OPENED");
					currentFile.setText("FILE CONTENTS HERE");
					loadFromFile();
				}
			}	
				);
		
		return jmenuBar;
	}
	
	public static void main(String[] args)
	{
		new EventBuilder();
	}
	
}