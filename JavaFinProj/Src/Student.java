import javax.swing.*;
import java.awt.*;

/**
 * Public class {@link Student} which extends {@link Person} (which makes it a
 * {@link ClubAbstractEntity} and also a javax.swing.JFrame).
 * 
 */
public class Student extends Person {
    private String studentId;
    private JLabel error = new JLabel("*");
    private JTextField idTextfield;
    /**
     * Student empty constructor - this constructor meant for searching and updating
     * clubber's data - Student type<br>
     * will invoke Student's 5 parameter constructor with 'null' values, will
     * disable the cancel button {@link #cancel_Button}, and set the window to be
     * visible. {@link #setVisible}
     */
    public Student() {
        this(null, null, null, null, null);
        cancel_Button(false);
        setVisible(true);
    }

    /**
     * Student constructor this constructor meant for creating ind initializing new
     * Student type clubber.<br>
     * this constructor will invoke {@link Person}<br>
     * this constuctor will color the "*" red, and hide it.<br>
     * it will create a new 'small panel' made of 1 label (ID, Name, etc), a
     * texfield, and an hidden '*'.<br>
     * it than invokes {@link #addToCenter} and sends to it the newly created
     * additional panel this entity needs.
     * 
     * @param id        Clubber's ID
     * 
     * @param name      Clubber's First name
     * 
     * @param surName   Clubber's Surname
     * 
     * @param tel       Clubber's Phone number
     * 
     * @param studentId Clubber's student ID
     */
    public Student(String id, String name, String surName, String tel, String studentId) {
        super(id, name, surName, tel);
        this.studentId = studentId;
        setTitle("Student Clubber's Data");

        error.setForeground(Color.red);
        error.setVisible(false);

        idTextfield = new JTextField(studentId, 30);

        JPanel studentIdPanel = new JPanel();
        studentIdPanel.add(new JLabel("Student ID"));
        studentIdPanel.add(idTextfield);
        studentIdPanel.add(error);

        addToCenter(studentIdPanel);
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
        if (this.studentId == null)
            return false;
        else if (super.match(key))
            return true;
        else if (key.regionMatches(0, this.studentId, 4, 5))
            return true;
        else
            return false;
    }

    /**
     * this method overrides inherited method {@link Person#validateData}<br>
     * [A-Z]{3}/[1-9]\\d{4} checks for A-Z chars for the first 3 characters,
     * searches for a '/' in the 4th character,<br>
     * than checks for numbers only
     * 
     * @return true if validation completed successfuly.
     * 
     * @return false and sets the '*' to visible once validation fails
     */
    @Override
    protected boolean validateData() {
        String StudenIdPattern = "[A-Z]{3}/[1-9]\\d{4}";
        if (super.validateData()) {
            if (!(idTextfield.getText().matches(StudenIdPattern))) {
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
        this.studentId = idTextfield.getText();
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
        idTextfield.setText(this.studentId);
        error.setVisible(false);
    }
}