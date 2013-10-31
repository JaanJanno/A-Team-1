package ee.ut.math.tvt.salessystem.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Purhase confirmation dialog
 * 
 * @author T?nis
 * 
 */
public class PaymentConfirmation extends JDialog {

	private boolean accepted;
	private double payment = 0;
	private double sum;
	private static final long serialVersionUID = 1L;
	private JLabel sumPayment;
	private JTextField changePayment;
	private JTextField amountField;
	private JButton acceptPayment;
	private JButton cancelPayment;
	private NumberFormat amountFormat;
	private PropertyChangeListener lis;
	private JLabel message;

	public PaymentConfirmation(JFrame frame, boolean modal, double sum) {
		super(frame, modal);
		this.sum = sum;
		this.add(draw());
		setTitle("Confirm purchase");
		pack();

		int width = 300;
		int height = 200;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screen.width - width) / 2,
				(screen.height - height) / 2);
		this.setSize(width, height);
		setVisible(true);
	}

	private JPanel draw() {
		JPanel panel = new JPanel();
		setLayout(new GridLayout(6, 2));

		// Initialize the buttons
		acceptPayment = createAcceptPaymentButton();
		cancelPayment = createCancelPaymentButton();

		// Sum from purchase table
		// sumPayment.setText(Double.toString(model.getCurrentPurchaseTableModel().getSum()));
		sumPayment = new JLabel();
		changePayment = new JTextField();
		sumPayment.setText(Double.toString(sum));
		changePayment.setText("0");
		changePayment.setEnabled(false);

		// Warning message
		message = new JLabel("");

		// Necessary to only input numbers
//		amountFormat = NumberFormat.getNumberInstance();
		amountField = new JFormattedTextField();
		amountField.setText("0");
		amountField.setColumns(10);
		//		lis = new Lis();

		amountField.getDocument().addDocumentListener(new Dkuular());
		amountField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				warn();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				try {
					if (Double.parseDouble(amountField.getText()) < 0) {
						payment = ((Number) Double.parseDouble(amountField.getText()))
								.doubleValue();
						changePayment.setText(Double.toString(payment - sum));

					}
				} catch (NumberFormatException e) {

				}

			}
		});
		// Add the buttons to the panel, using GridBagConstraints we defined
		// above
		add(new JLabel("Total: "));
		add(sumPayment);

		add(new JLabel("Return amount: "));
		add(changePayment);

		add(new JLabel("Enter money here: "));
		add(amountField);

//		add(new JLabel("Please enter the amount of money you want to pay with"));
//		add(new JLabel("")); // Empty label to make the layout nicer

		add(acceptPayment);
		add(cancelPayment);

		add(message);
		return panel;

	}

	/**
	 * For checking if the purchase was accepted in the <code>PurchaseTab</code>
	 * 
	 * @return boolean
	 */
	public boolean isAccepted() {
		return accepted;
	}

	/**
	 * Creates the Accept payment button
	 * 
	 * @return button
	 * @author T?nis
	 */
	private JButton createAcceptPaymentButton() {
		JButton b = new JButton("Accept");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceptButtonClicked();
			}
		});

		return b;
	}

	/**
	 * Creates the Cancel payment button
	 * 
	 * @return button
	 * @author T?nis
	 */
	private JButton createCancelPaymentButton() {
		JButton b = new JButton("Cancel");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelButtonClicked();
			}
		});

		return b;
	}

	private void acceptButtonClicked() {
		
		//check if amount entered includes strings
		boolean allnumbers = true;
		if(amountField.getText().isEmpty()) {
			allnumbers = false;
			message.setText("Enter more money");
		}
	    for(int i = 0; i < amountField.getText().length(); i++) {
	        if(!Character.isDigit(amountField.getText().charAt(i))) {
	        	message.setText("Enter integers only");
	        	allnumbers = false;
	        }
	    }
	  
	    if (allnumbers == true) {
			// Checking if there is enough money entered
			payment = ((Number) Double.parseDouble(amountField.getText()))
					.doubleValue();
			if ((payment - sum) >= 0) {
				message.setText("Purchase accepted");
				accepted = true;
				setVisible(false);
			} else {
				message.setText("Enter more money");
			}
	    }

	}

	private void cancelButtonClicked() {
		amountField.setText("0");
		changePayment.setText("0");
		accepted = false;
		setVisible(false);

	}
	class Dkuular implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			try{
				payment = ((Number) Double.parseDouble(amountField.getText()))
						.doubleValue();
				changePayment.setText(Double.toString(Double.parseDouble(amountField.getText()) - sum));
			}catch(NumberFormatException ex){ // handle your exception
				changePayment.setText("0");
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			if (amountField.getText().isEmpty() == false) {
				try{
					payment = ((Number) Double.parseDouble(amountField.getText()))
							.doubleValue();
					changePayment.setText(Double.toString(Double.parseDouble(amountField.getText()) - sum));
				}catch(NumberFormatException ex){ // handle your exception
					changePayment.setText("0");
				}
			}
			else {
				changePayment.setText("0");
			}
		}





		@Override
		public void changedUpdate(DocumentEvent e) {
			try{
				payment = ((Number) Double.parseDouble(amountField.getText()))
						.doubleValue();
				changePayment.setText(Double.toString(Double.parseDouble(amountField.getText()) - sum));
			}catch(NumberFormatException ex){ // handle your exception
				changePayment.setText("0");
			}
		}


	}
	//	private class Lis implements PropertyChangeListener {
	//		/** Called when a field's "value" property changes. */
	//		public void propertyChange(PropertyChangeEvent e) {
	//			Object source = e.getSource();
	//			if (source == amountField) {
	//					payment = ((Number) Double.parseDouble(amountField.getText()))
	//							.doubleValue();
	//				changePayment.setText(Double.toString(payment - sum));
	//			}
	//		}
	//
	//	}

}
