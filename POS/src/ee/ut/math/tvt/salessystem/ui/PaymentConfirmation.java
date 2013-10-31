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
 * @author T�nis
 * 
 */
public class PaymentConfirmation extends JDialog {

	private boolean accepted;
	private double payment = 0;
	private double sum;
	private static final long serialVersionUID = 1L;
	private JLabel sumPayment;
	private JTextField changePayment;
	private JFormattedTextField amountField;
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
		amountFormat = NumberFormat.getNumberInstance();
		amountField = new JFormattedTextField(amountFormat);
		amountField.setValue(new Double(0));
		amountField.setColumns(10);
		lis = new Lis();
		amountField.addPropertyChangeListener("value", lis);

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
						payment = ((Number) amountField.getValue())
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

		add(new JLabel("Press enter to see change"));
		add(new JLabel("")); // Empty label to make the layout nicer

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
	 * @author T�nis
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
	 * @author T�nis
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
		// Checking if there is enough money entered
		payment = ((Number) amountField.getValue()).doubleValue();
		if ((payment - sum) > 0) {
			message.setText("Purchase accepted");
			accepted = true;
			setVisible(false);
		} else {
			message.setText("Enter more money");
		}

	}

	private void cancelButtonClicked() {
		accepted = false;
		setVisible(false);

	}

	private class Lis implements PropertyChangeListener {
		/** Called when a field's "value" property changes. */
		public void propertyChange(PropertyChangeEvent e) {
			Object source = e.getSource();
			if (source == amountField) {
				payment = ((Number) amountField.getValue()).doubleValue();
				changePayment.setText(Double.toString(payment - sum));
			}
		}

	}

}
