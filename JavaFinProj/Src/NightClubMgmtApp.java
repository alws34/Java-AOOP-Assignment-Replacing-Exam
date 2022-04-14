import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;

/**
 * This class implements the main menu for the program and initializes all the
 * different components ,including reading clubbers's data from a binary file,
 * and writing it back into the file before the program closes.<br>
 * This Class extends JFrame {@link javax.swing.JFrame}, which makes the entire
 * NightClubMgmtApp a JFRAME.<br>
 * It holds the clubbers ArrayList {@link ClubAbstractEntity}.<br>
 * 
 * This class also implements an ActionListener
 * 
 */
public class NightClubMgmtApp extends JFrame implements ActionListener {
    // Instance Variables
    private ArrayList<ClubAbstractEntity> clubbers = new ArrayList<>();
    private JButton[] buttons_array = new JButton[4];

    /**
     * Parameterless constructor NightClubMgmtApp() initializes the main window (the
     * main menu) reads all objects from a file to the clubbers array. <br>
     * sets all windows parameters and attributes :<br>
     * size, title,resizing, default close operation , all the controls (button
     * panel), setting up While closing the window (by the red X- wich its action is
     * disabled with DO_NOTHING_ON_CLOSE ),<br>
     * a window listener is added (wich is a EventListener (interface by itself)
     * interface ) windowClosing.<br>
     * this listener will invoke writeClubbersDBtoFile() which will write the
     * clubbers data into a binary file, and than will terminate the program and
     * kill the JVM - telling the OS to clean everything up.
     * 
     * The buttons are instances of {@link javax.swing.JButton}.<br>
     * 
     * Both button are going into a Jpanel {@link javax.swing.JPanel}
     * mainMenuButtonsPanel.
     */
    public NightClubMgmtApp() {
        loadClubbersDBFromFile();
        String[] buttons = { "Search", "Add Person", "Add Soldier", "Add Student" };
        JPanel mainMenuButtonsPanel = new JPanel();

        mainMenuButtonsPanel.setLayout(new GridLayout(buttons.length, 0));

        // add the initialization buttons to the panel
        for (int i = 0; i < buttons.length; i++) {
            buttons_array[i] = new JButton(buttons[i]);
            buttons_array[i].addActionListener(this);
            mainMenuButtonsPanel.add(buttons_array[i]);
        }
        setSize(450, 250);
        setTitle("Main Menu");
        add(mainMenuButtonsPanel);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                writeClubbersDBtoFile(); // write the clubbers array to the file
                System.exit(0); // this will terminate the JVM with all the windows and objects ,and tells the
                                // OS// to clean everything up
            }
        });
        setVisible(true);
    }

    /**
     * This method invokes an input dialog @param input that asks for a clubber's ID
     * (key).<br>
     * Than the method iterates through the clubbers arraylist and checks if the
     * input (the key given by the user) matches any clubbers's ID, if theres a
     * match it displays the requested clubber's window along with all it's
     * data.<br>
     * if no clubber was found, display a message dialog
     * {@link javax.swing.JOptionPane#showMessageDialog} with an appropriate
     * message.
     */
    private void manipulateDB() {
        String input;
        boolean found = false;
        input = JOptionPane.showInputDialog(this, "Please Enter The Clubber's Key", "Notice",
                JOptionPane.INFORMATION_MESSAGE);
        for (ClubAbstractEntity clubber : clubbers)
            if (clubber.match(input)) {
                found = true;
                clubber.setVisible(true);
                break;
            }
        if (!found)
            display_Error_Message(this, "Clubber does not exist in DataBase");
        else
            found = true;
    }

    /**
     * This method will creat a FileInputStream {@link java.io.FileInputStream} and
     * a ObjectInputStream {@link java.io.ObjectInputStream} to read the clubber's
     * data from the binary file. after the initialization of the clubers arraylist,
     * both input streams closes.<br>
     * the method catches the following exceptions: 1.java.io.FileNotFoundException
     * {@link java.io.FileNotFoundException} - when no file to read from is found -
     * <strong>this exception will be cought at the first startup of the
     * program</strong>.<br>
     * 2.IOException {@link java.io.IOException} - will be cought if the streamer
     * cannot write the data to the file from some reason.<br>
     * 3.ClassNotFoundException {@link java.lang.ClassNotFoundException} will be
     * cought when theres no clubbers array (something went wrong at the
     * initialization)
     */
    private void loadClubbersDBFromFile() {
        // Read data from file, create the corresponding objects and put them
        // into clubbers ArrayList. For example:
        // clubbers.add(new Person("0-2423535|1", "Mark", "Mc'Cormic",
        // "+(1)4-9520205"));
        // clubbers.add(new Soldier("0-2223335|1", "Zohar", "Couper-Berg",
        // "+(44)206-8208167", "O/4684109"));
        // clubbers.add(new Student("2-5554445|3", "Avi", "Avrahami-O'Mally",
        // "+(972)50-6663210", "SCE/12345"));
        try {
            FileInputStream fis = new FileInputStream("BKCustomers.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            clubbers = (ArrayList<ClubAbstractEntity>) ois.readObject();
            ois.close();
            fis.close();
        } catch (java.io.FileNotFoundException fileNotFoundException) {
            display_Error_Message(null, "Error! File Not Found!");
        } catch (IOException ioException) {
            display_Error_Message(null, "Error! Can't write to file!");
        } catch (ClassNotFoundException classNotFoundException) {
            display_Error_Message(null, "Error!");
        }
    }

    /**
     * This method writes the clubbers array into the file every time the program
     * closes (by pressing the close button(red X)).<br>
     * this method is invoked only when the close button(red X) is pressed.<br>
     * Otherwise, the program will not save any changes nor new clubbers.<br>
     * this method created two Output Streams (File and Object) than it writes the
     * clubbers array into the Object output stream and than both stream closes.<br>
     * this method catches two exceptions: <br>
     * 1.FileNotFounException {@link java.io.FileNotFoundException} - will be cought
     * when no file was found.<br>
     * 2.IOException {@link java.io.IOException} - will be cought if the streamer
     * cann't write to the file. (if the file is corrupt for instance).
     */
    private void writeClubbersDBtoFile() {
        // Write all the objects’ data in clubbers ArrayList into the file
        try {
            FileOutputStream fos = new FileOutputStream("BKCustomers.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(clubbers);
            oos.close();
            fos.close();
        } catch (FileNotFoundException fileNotFoundException) {
            display_Error_Message(null, "Error! File Not Found!");
        } catch (IOException iOException) {
            display_Error_Message(null, "Error! Can't write to file!");
        }
    }

    /**
     * This method gets a click event and checks its origin. <br>
     * it wires the pressed button to the wanted operation and invokes the relevant
     * constructor. if you want to add a person, than buttons_array[1] will lead to
     * Person empty constructor invokation etc. {@link Person} <br>
     * buttons_array[0] will invoke {@link manipulateDB} - which is basically the
     * search operation in the clubbers DB.
     * 
     * @param event event given to the function in order to chech which button was
     *              pressed
     */
    @Override
    // buttons_array[0] == search
    // buttons_array[1] == add person
    // buttons_array[2] == add soldier
    // buttons_array[3] == add student
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == buttons_array[0]) {
            manipulateDB();
        } else if (event.getSource() == buttons_array[1])
            clubbers.add(new Person());
        else if (event.getSource() == buttons_array[2])
            clubbers.add(new Soldier());
        else if (event.getSource() == buttons_array[3])
            clubbers.add(new Student());
    }

    /**
     * This method will display an error message a specified message.<br>
     * 
     * @param message  is the String message given to the method in order to display
     *                 it to the user.<br>
     * @param par_comp is the parentComponent
     *                 {@link javax.swing.JOptionPane#showMessageDialog } -
     *                 determines the Frame in which the dialog is displayed
     */
    private void display_Error_Message(Component par_comp, String message) {
        JOptionPane.showMessageDialog(par_comp, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * This method is the main - the program's entry point. <br>
     * creates the application and invokes the NightClubMgmtApp() empty constructor.
     * 
     * @param args String array of arguments. default main variable.
     */
    public static void main(String[] args) {
        NightClubMgmtApp application = new NightClubMgmtApp();
    }
}// End of class NightClubMgmtApp
