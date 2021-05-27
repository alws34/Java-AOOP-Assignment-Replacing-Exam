import javax.swing.*;
import java.awt.*;

/**
 * Public class {@link Soldier} which extends {@link Person} (which makes it a
 * {@link ClubAbstractEntity} and also a {@link javax.swing.JFrame}).
 * 
 */
public class Soldier extends Person {
    private String personalNumber;
    private JLabel error = new JLabel("*");
    private JTextField personalNumtextField;

    /**
     * Soldier empty constructor - this constructor meant for searching and updating
     * clubber's data - Soldier type<br>
     * will invoke Soldier's 5 parameter constructor with 'null' values, will
     * disable the cancel button {@link #cancel_Button}, and set the window to be
     * visible. {@link #setVisible}
     */
    public Soldier() {
        this(null, null, null, null, null);
        cancel_Button(false);
        setVisible(true);
    }

    /**
     * Soldier constructor this constructor meant for creating ind initializing new
     * Soldier type clubber.<br>
     * this constructor will invoke {@link Person}<br>
     * this constuctor will color the "*" red, and hide it.<br>
     * it will create a new 'small panel' made of 1 label (ID, Name, etc), a
     * texfield, and an hidden '*'.<br>
     * it than invokes {@link #addToCenter} and sends to it the newly created
     * additional panel this entity needs.
     * 
     * @param id             Clubber's ID
     * 
     * @param name           Clubber's First name
     * 
     * @param surname        Clubber's Surname
     * 
     * @param tel            Clubber's Phone number
     * 
     * @param personalNumber Clubber's Personal Number
     */
    public Soldier(String id, String name, String surname, String tel, String personalNumber) {
        super(id, name, surname, tel);
        this.personalNumber = personalNumber;
        setTitle("Soldier Clubber's Data");

        error.setForeground(Color.red);
        error.setVisible(false);

        personalNumtextField = new JTextField(personalNumber, 30);

        JPanel personalNumberPanel = new JPanel();
        personalNumberPanel.add(new JLabel("Personal No."));
        personalNumberPanel.add(personalNumtextField);
        personalNumberPanel.add(error);

        addToCenter(personalNumberPanel);

    }

    /**
     * this method overrides inherited method {@link #match}<br>
     * the method onvokes {@link Person} match function and if it returns true,
     * than @return true if super match method returned false,<br>
     * another check is being performed , {@link java.lang.String#regionMatches} is
     * being performed to check if the key matches the student's id from the 5th
     * digit<br>
     * 
     * @param key user input key to check.<br>
     * @return true if the given key matches the Person's id OR if the key matches
     *         the student's id from the 5th digit<br>
     * @return false if the two conditions above does not apply to the given key.
     * 
     */
    @Override
    public boolean match(String key) {
        if (this.personalNumber == null)
            return false;
        if (super.match(key) || key.equals(this.personalNumber)) {
            return true;
        }
        return false;
    }

    /**
     * this method overrides inherited method {@link Person#validateData}<br>
     * [(R|O|C)]/[1-9]\\d{6} checks for one capital letter of either of the 3
     * letters - R, O, or C (shortcut for: Rank, Officer or Chief <br>
     * (can also be a Non-commissioned officer),<br>
     * followed by a '/', followed by 7 digits (first digit must be non-zero). E.g.
     * R/4684509
     * 
     * @return true if validation completed successfuly.
     * 
     * @return false and sets the '*' to visible once validation fails
     */
    @Override
    protected boolean validateData() {
        String pattern = "[(R|O|C)]/[1-9]\\d{6}";
        if (super.validateData()) {
            if (!(personalNumtextField.getText().matches(pattern))) {
                error.setVisible(true);
                return false;
            }
        } else
            return false;
        return true;
    }

    /**
     * this method overrides inherited abstract method {@link #commit}.<br>
     * this method will set the validated and relevant data to the correct instance
     * variable field,<br>
     * and invokes {@link Person#commit} which will enable the cancel button.
     * (according to "true" state sent to function)
     */
    @Override
    protected void commit() {
        this.personalNumber = personalNumtextField.getText();
        super.commit();
    }

    /**
     * override method {@link #rollBack} - reverse the operation and setting each
     * text filed back to its original values.<br>
     * which means take the instance variables values, and enter them to the
     * textfields<br>
     * invokes {@link Person#rollBack} to roll back changes, returns the textfiled
     * state to its previous state,<br>
     * and re-hides the '*' (if one is present. if not, it will just stay hidden)
     */
    @Override
    protected void rollBack() {
        super.rollBack();
        personalNumtextField.setText(this.personalNumber);
        error.setVisible(false);
    }
}