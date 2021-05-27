import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * class {@link ClubAbstractEntity} is an abstract class and is the backbone of
 * the entire app.<br>
 * it extends JFrame {@link javax.swing.JFrame} which makes it a JFrame by
 * itself.<br>
 * it holds all the instance variables needed for the entitiy to perform, and
 * its relevant methods eg: {@link #addToCenter}
 */
public abstract class ClubAbstractEntity extends JFrame {
    private final JPanel centerPanel;
    private ButtonHandler buttonsHandler;
    private final JButton okButton;
    private final JButton cancelButton;

    /**
     * ClubAbstractEntity parameterless constructor<br>
     * Initializes every window as a JFrame ({@link JFrame}).<br>
     * sets the window's attributes. eg: <br>
     * {@link #setSize} to 450*250, sets the layout, default colse operation
     * {@link #setDefaultCloseOperation}.<br>
     * creates the buttons bottom panel {@link javax.swing.JPanel} and holds the
     * buttons that are from class {@link java.awt.Button}<br>
     * moreover, it centralize the panels to the center of the frame
     * ({@link ClubAbstractEntity} is the frame).
     */
    public ClubAbstractEntity() {
        setSize(450, 250);// set window size
        setLayout(new BorderLayout());// set window layout
        setLocationRelativeTo(null);// set wondow location to center of the screen
        setResizable(false);// disallow window resizing
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);// disable close operation

        centerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        buttonsHandler = new ButtonHandler();
        okButton.addActionListener(buttonsHandler);
        cancelButton.addActionListener(buttonsHandler);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        add(buttonsPanel, BorderLayout.SOUTH); // ClubAbstractEntity is a JFrame so theres no need to explicitly tell it
                                               // to add. like ClubAbstractEntity.add(...)
        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * protected method cancel_Button controls the availability of the button,
     * according to each abstract entitiy instance and needs.<br>
     * the @param state is controlled from the inheritors of the abstract class.<br>
     * each instance of person/soldier/student can control the cancel button
     * according to its needs and state of operation.<br>
     * 
     * @param state true or false thats sets the button's availability.<br>
     *              if state==true, enable the button. if false, disable it.
     */
    protected void cancel_Button(boolean state) {
        if (state)
            cancelButton.setEnabled(true);
        else
            cancelButton.setEnabled(false);
    }

    /**
     * this method recievs a guicomponent (a panel- as given in this program)<br>
     * it than adds into the {@link #centerPanel} which has a
     * {@link java.awt.FlowLayout} layout - which means that every panel that will
     * be added will be added vertically.<br>
     * central panel will than be added into the ClubAbstractEntity panel that has a
     * {@link java.awt.BorderLayout} layout.
     * 
     * @param guiComponent center all the gui compnents
     */
    protected void addToCenter(Component guiComponent) {
        centerPanel.add(guiComponent);
    }

    /**
     * This abstract method meant to return true or false - dependends if the given
     * key matching the clubber's unique id.<br>
     * *this method will be overidden according to each entity validation needs.
     * 
     * @param key.
     * @return boolean
     */
    public abstract boolean match(String key);

    /**
     * This abstract method meant to returns true if the entire object content have
     * been validated. else, returns false
     * 
     * @return boolean
     */
    protected abstract boolean validateData();

    /**
     * This abstract method will save the clubber input data into the clubber's
     * instance fields.<br>
     * *this method will be overidden according to each Person entity needs.
     */
    protected abstract void commit();

    /**
     * This abstract method is meant discard any changes made<br>
     * *this method will be overidden according to each Person entity needs.
     */
    protected abstract void rollBack();

    /**
     * inner class ButtonsHandler implements java.awt.event ActionListener<br>
     * and java.io serializable<br>
     * the class controls the operations of each button (ok and cancel buttons) from
     * the buttonsPanel.
     */
    class ButtonHandler implements ActionListener, Serializable {
        /**
         * when the ok button is pushed the frame is hidden and {@link #commit} is
         * invoked.<br>
         * when the cancel button is pushed the frame is hidden and {@link #rollBack} is
         * invoked.
         * 
         * @param event the action event that happend in the mainFrame.
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == okButton) {
                if (validateData()) {
                    commit();
                    setVisible(false);
                } else
                    return;
            } else if (event.getSource() == cancelButton) {
                rollBack();
                setVisible(false);
            }
        }
    } // end class ButtonHandler
} // end class ClubAbAstractEntity