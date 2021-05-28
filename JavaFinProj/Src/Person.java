import javax.swing.*;
import java.awt.*;

/**
 * public class Person extends ClubAbstractEntity which means each person
 * entitiy is also a JFrame by itself.<br>
 * this class holds several String instance variables id, name surname, tel that
 * are the Person's details.<br>
 * it also holds two arrays:<br>
 * 1.textFields which is a {@link javax.swing.JTextField} array. this array will
 * hold each variable user input for each field<br>
 * 2. errors which is a {@link javax.swing.JLabel} array. this array will hold
 * all the asterkis ('*') that indicated a validation error<br>
 * each array is needed for later use of its data. (hide\show relevant '*',
 * validate, etc)
 */
public class Person extends ClubAbstractEntity {
    // instance variables
    private String id, name, surname, tel;
    protected JLabel[] errors = new JLabel[4];
    protected JTextField[] textFields = new JTextField[4];

    /**
     * {@link Person} empty constructor - this constructor meant for searching and
     * updating clubber data. <br>
     * will invoke person's 4 parameter constructor with 'null' values, will disable
     * the cancel button {@link #cancel_Button}, and set the window to be visible.
     * {@link #setVisible}
     */
    public Person() {
        this(null, null, null, null);
        cancel_Button(false);
        setVisible(true);
    }

    /**
     * Person 4 parameters constructor - creates a Person with the given
     * information.<br>
     * <strong>each person will be created only after data validation.</strong><br>
     * The constructor creates a panel with the relevant text-fields and labels and
     * than adds this panel into a larger panel.<br>
     * Each small panel is made of 1 label (ID, Name, etc),a texfield,and an hidden
     * '*'.<br>
     * Than each small panel is being added to the main panel- Person (each person
     * is also a {@link javax.swing.JPanel} and a {@link ClubAbstractEntity} - as
     * mentioned)
     * 
     * @param id      Clubber's ID
     * @param name    Clubber's First name
     * @param surName Clubber's Surname
     * @param tel     Clubber's Phone number
     */
    public Person(String id, String name, String surName, String tel) {
        String[] labels = { "ID", "Name", "Surname", "Tel" };
        String[] fieldsContent = { id, name, surName, tel };
        JPanel[] textPanels = new JPanel[4];
        this.id = id;
        this.name = name;
        this.surname = surName;
        this.tel = tel;

        setTitle("Person Clubber's Data");// set title for the person panel

        for (int i = 0; i < labels.length; i++) {
            errors[i] = new JLabel("*");
            errors[i].setForeground(Color.red);
            errors[i].setVisible(false);

            textFields[i] = new JTextField(fieldsContent[i], 30);

            textPanels[i] = new JPanel();
            textPanels[i].add(new JLabel(labels[i]));
            textPanels[i].add(textFields[i]);
            textPanels[i].add(errors[i]);

            addToCenter(textPanels[i]);
        }
    }

    /**
     * This method overrides inherited abstract method {@link #match}<br>
     * this method check if the user id - entered in the relevant JTextfield
     * ({@link javax.swing.JTextField})
     * 
     * @param key this is the user input id. (to check for matches in the DB)
     * @return true if key matches the id field.
     * @return false if the doesnt match the id field.
     */
    @Override
    public boolean match(String key) {
        if (this.id != null && key.equals(this.id)) {
            return true;
        }
        return false;
    }

    /**
     * This method overrides inherited abstract method {@link #validateData}<br>
     * this method will validate the data (data is being treated as a regular
     * expression) entered by the user in the next sequence:<br>
     * ID field RE: '\\d-\\d{7}\\|[1-9]':<br>
     * digit, followed by '-', followed by 7 digits, followed by '|', followed by a
     * non-zero digit (e.g. 0-2423535|1)<br>
     * 
     * name field RE: '[A-Z][a-z]+':<br>
     * capital letter, followed by at least one lower-case letter (e.g. Zohar)<br>
     * 
     * surname field RE: '([A-Z][a-z]*('|-)?)+':<br>
     * capital letter, followed by 0 or more lowercase letters, followed by 0 or 1
     * occurrences of: "'" or '-' (e.g. Mc'Cormic)<br>
     * 
     * Tel field RE: '\\+\\([1-9]\\d{0,2}\\)[1-9]\\d{0,2}-[1-9]\\d{6}':<br>
     * international land/mobile-phone number (e.g. +(1)4-9520205)<br>
     * First digit after left-bracket must be non-zero<br>
     * 
     * Each time the user click the {@link #okButton}, {@link #validateData} is
     * being invoked and the validation proccess takes place once again.<br>
     * in each invokation, the loop goes through all the validPatterns and all the
     * textFields and check for a match.<br>
     * if a mach was not found in one of the textfields, an error asteriks becomes
     * visible and the loop breaks.<br>
     * if the mistake is fixed, but theres a mistake in another field (in the next
     * time we'll press the okButton),<br>
     * the previous askteriks will be hidden, and the relevant field asteriks will
     * be shown.
     * 
     * @return true if the content has been validated.
     * @return false if the content hasn't been validated
     */
    @Override
    protected boolean validateData() {
        String[] validPatterns = { "\\d-\\d{7}\\|[1-9]", "[A-Z][a-z]+", "([A-Z][a-z]*('|-)?)+",
                "\\+\\([1-9]\\d{0,2}\\)[1-9]\\d{0,2}-[1-9]\\d{6}" };
        for (int i = 0; i < textFields.length; i++) {
            if (!(textFields[i].getText().matches(validPatterns[i]))) {
                errors[i].setVisible(true);
                return false;
            } else
                errors[i].setVisible(false);
        }
        return true;
    }

    /**
     * this method overrides inherited abstract method {@link #commit}.<br>
     * this method will set the validated and relevant data to the correct instance
     * variable field,<br>
     * and invoke {@link #cancel_Button} which will enable the cancel button.
     * (according to "true" state sent to function)
     */
    @Override
    protected void commit() {
        this.id = textFields[0].getText();
        this.name = textFields[1].getText();
        this.surname = textFields[2].getText();
        this.tel = textFields[3].getText();
        cancel_Button(true);
    }

    /**
     * override method {@link #rollBack} - reverse the operation and setting each
     * text filed back to its original values.<br>
     * which means take the instance variables values, and enter them to the
     * textfields
     */
    @Override
    protected void rollBack() {
        textFields[0].setText(this.id);
        textFields[1].setText(this.name);
        textFields[2].setText(this.surname);
        textFields[3].setText(this.tel);
        for (int i = 0; i < errors.length; i++)
            errors[i].setVisible(false);
    }
}