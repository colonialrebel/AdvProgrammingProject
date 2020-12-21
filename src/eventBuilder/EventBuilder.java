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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

/* Updated Event Building tool for the game. Can create, edit, or delete events 
 * Events are saved in text file as a single line (for each event)
 * On the line, all the parameters will be separated by commas. Booleans can also be stored (ex. "Is player wearing mask")
 * Files can be loaded or saved using the menu bar. New files can be created by adding events
 * The current file can be viewed on the left. Modify/delete any event (line) 
 * 		by pressing the corresponding button and selecting an event with the dragdown JComboBox
 * Requires SpringUtilities.java (https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/SpringGridProject/src/layout/SpringUtilities.java)
 */

public class EventBuilder extends JFrame
{
	private static final long serialVersionUID = 3794059922116115530L;
	
	public JTextArea aTextArea = new JTextArea();
	private JLabel eventArea = new JLabel("", SwingConstants.CENTER);
	private JPanel p = new JPanel();
	private JPanel p2 = new JPanel();
	
	public static final String[] LABEL_LIST = { "Event Name", "Description",
			"Population", "Covid Rate","Density","Is player wearing mask"," "};
	public static final int NUM_COLS = 7;
	private final JTextField[] textField = new JTextField[LABEL_LIST.length];

	private static JCheckBox checkBox1 = new JCheckBox();
    private static JButton submitButton = new JButton("Submit");
	private static JComboBox<String> itemChooser = new JComboBox<String>();
	private static JButton modifyButton = new JButton ("Modify selected event");
	private static JButton deleteButton = new JButton("Delete selected Event");
	private int selectedEvent;
	private String selectedEventString = "";
	private boolean isModifyState = false;
	
	private JPanel getBottomPanel2()
	{
		JButton addMainButton = new JButton("Add event");
		JButton deleteMainButton = new JButton("Delete event");
		JButton modifyMainButton = new JButton("Modify Event");

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,3));
		
		// Add/save event as a text file, currently only reads for event name and subsequent text
		addMainButton.setMnemonic(KeyEvent.VK_ENTER);
		addMainButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				{
					for (int i = 0 ; i < LABEL_LIST.length-2; i++)
			    	{
//			    		System.out.println(button1.isSelected());
			    			
			    		textField[i].setText("");
			    		checkBox1.setSelected(false);
			    			
			    	}
		    		eventArea.setText("Add a new event");
					p.setVisible(true);
					p2.setVisible(false);
					isModifyState = false;
				}
			}
		});

		deleteMainButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				p.setVisible(false);
				p2.setVisible(true);
				deleteButton.setVisible(true);
				modifyButton.setVisible(false);

				eventArea.setText("Choose an event to delete");
				for (String line : aTextArea.getText().split("\n")) {
					String line_trimmed = line.split(",")[0];

					itemChooser.addItem(line_trimmed);
				}

			}
		});
		
		// Modify event text
		modifyMainButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				p.setVisible(false);
				p2.setVisible(true);
				deleteButton.setVisible(false);
				modifyButton.setVisible(true);
				
				eventArea.setText("Choose an event to modify");
				for (String line : aTextArea.getText().split("\n")) {
					String line_trimmed = line.split(",")[0];

					itemChooser.addItem(line_trimmed);
				}
				
				
			}
		});
		panel.add(addMainButton);
		panel.add(modifyMainButton);
		panel.add(deleteMainButton);
		return panel;
	}

	private JPanel getEditPanel()
	{
//    	final JTextField[] textField = new JTextField[LABEL_LIST.length];
	    final JPanel mainPanel = new JPanel(new SpringLayout());
	    for (int i = 0; i<NUM_COLS;i++) {
	    	JLabel labelName = new JLabel(LABEL_LIST[i], JLabel.TRAILING);
	    	mainPanel.add(labelName);
	    	textField[i] = new JTextField(10);
	    	labelName.setLabelFor(textField[i]);

	    	if (LABEL_LIST[i].equals("Is player wearing mask")) {
				mainPanel.add(checkBox1);
	    	} else if (LABEL_LIST[i].equals(" ")){
	    		mainPanel.add(submitButton);
	    	} else {
	    		mainPanel.add(textField[i]);
	    	}
	    	
	    }
	
	    //Lay out the panel.
	    SpringUtilities.makeCompactGrid(mainPanel,
                NUM_COLS, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

	     submitButton.addActionListener(new ActionListener() {

	    	public void actionPerformed(ActionEvent e)
	    	{
	    		System.out.println(isModifyState);
    			String event_str = "";
    			String tempTextArea = aTextArea.getText();
		    	for (int i = 0 ; i < LABEL_LIST.length-1; i++)
		    	{
		    			
		    		if (i==LABEL_LIST.length-2) {
		    			event_str = event_str+Boolean.toString(checkBox1.isSelected())+"\n";
		    		} else {
		    			event_str = event_str+textField[i].getText()+",";
		    		}
		    			
		    	}
		    	
	    		if (!isModifyState) {
			    	eventArea.setText("Event Added");
					aTextArea.append(event_str);
	    		} else {
	    			aTextArea.setText("");
			    	eventArea.setText("Event Modified");
			    	int counter = 0;
			    	
			    	for (String line : tempTextArea.split("\n")) {
						if (counter == selectedEvent) {
							aTextArea.append(event_str);
						} else {
							aTextArea.append(line+"\n");
						}
						counter = counter+1;
					}
	    		}
	    		
	    		p.setVisible(false);	   
	    	}
	    	
	    });
	     mainPanel.setOpaque(true);
	     return mainPanel;
	}

	private JPanel getComboPanel()
	{
		final JPanel comboPanel = new JPanel();

		comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.PAGE_AXIS));

		
		comboPanel.add(Box.createRigidArea(new Dimension(0,150)));
		comboPanel.add(itemChooser);
		comboPanel.add(Box.createRigidArea(new Dimension(0,150)));
		comboPanel.add(modifyButton);
		comboPanel.add(deleteButton);
		
		modifyButton.addActionListener(new ActionListener() {

	    	public void actionPerformed(ActionEvent e)
	    	{
	    		eventArea.setText("Modify Event");
	    		p2.setVisible(false);
	    		p.setVisible(true);
	    		selectedEvent = itemChooser.getSelectedIndex();
	    		
	    		int counter = 0;
	    		for (String line : aTextArea.getText().split("\n")) {
					if (counter == selectedEvent) {
						selectedEventString = line;
					}
					counter = counter+1;
				}
	    		
	    		String [] splitEvent = selectedEventString.split(",");

	    		for (int i = 0 ; i < splitEvent.length; i++)
		    	{
	    			if (i != splitEvent.length-1) {
			    		textField[i].setText(splitEvent[i]);
	    			} else {
	    				checkBox1.setSelected(Boolean.valueOf(splitEvent[i]));
	    			}
		    		
		    			
		    	}
	    		
	    		isModifyState = true;
				itemChooser.removeAllItems();


	    	}
	    	
	    });
		
		deleteButton.addActionListener(new ActionListener() {

	    	public void actionPerformed(ActionEvent e)
	    	{
	    		eventArea.setText("Event Deleted");
	    		p2.setVisible(false);
	    		selectedEvent = itemChooser.getSelectedIndex();
	    		int counter = 0;
	    		String temp = aTextArea.getText();
	    		aTextArea.setText("");
	    		
				for (String line : temp.split("\n")) {
					if (counter != selectedEvent) {
						aTextArea.append(line+"\n");
					}
					counter = counter+1;
				}
				itemChooser.removeAllItems();
	    	}
	    	
	    });
		
		return comboPanel;

	}
	

	
	public EventBuilder() 
	{
		super("Event Manager");
		
		setLocationRelativeTo(null);
		setSize(1200,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getBottomPanel2(), BorderLayout.SOUTH);
		
		p = getEditPanel();
		getContentPane().add(p, BorderLayout.CENTER);
		p.setVisible(false);
		
		getContentPane().add(eventArea, BorderLayout.NORTH);
		getContentPane().add(aTextArea, BorderLayout.LINE_START);
		
		p2 = getComboPanel();
		getContentPane().add(p2, BorderLayout.LINE_END);
		p2.setVisible(false);
		setJMenuBar(getMyMenuBar());
		
		aTextArea.setText("");
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
			String line;
			StringBuilder sb=  new StringBuilder();
	        while ((line = reader.readLine()) != null) 
	        {
	               sb.append(line+"\n");
	        }
	        line = sb.toString();
			aTextArea.setText(line);
			eventArea.setText("FILE OPENED");
			p.setVisible(false);
			p2.setVisible(false);
			

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
			writer.write(aTextArea.getText());
			writer.flush();  
			writer.close();
			eventArea.setText("FILE SAVED");

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
		
		JMenuItem saveItem = new JMenuItem("Save File As");
		fileMenu.add(saveItem);
		
		openItem.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("OPENED");
					loadFromFile();
				}
			}	
				);
		saveItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				saveToFile();
				System.out.println("SAVED");
			}
		});
		
		return jmenuBar;
	}

	   
	public static void main(String[] args)
	{
		new EventBuilder();
	
	}
	
	
}