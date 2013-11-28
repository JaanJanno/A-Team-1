package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Client;
import ee.ut.math.tvt.salessystem.domain.data.Sale;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;
import ee.ut.math.tvt.salessystem.ui.windows.PayingWindow;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab labeled
 * "Point-of-sale" in the menu).
 */
public class PurchaseTab {
	private static final Logger log = Logger.getLogger(PurchaseTab.class);
	private final SalesDomainController domainController;
	private JButton newPurchase;
	private JButton submitPurchase;
	private JButton cancelPurchase;
	private PurchaseItemPanel purchasePane;
	private SalesSystemModel model;
	private JFrame parent;

	public PurchaseTab(SalesDomainController controller,
			SalesSystemModel model, JFrame parent) {
		this.domainController = controller;
		this.model = model;
		this.parent = parent;
	}

	/**
	 * The purchase tab. Consists of the purchase menu, current purchase dialog
	 * and shopping cart table.
	 */
	public Component draw() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());
		panel.add(getPurchaseMenuPane(), getConstraintsForPurchaseMenu());
		purchasePane = new PurchaseItemPanel(model);
		panel.add(purchasePane, getConstraintsForPurchasePanel());
		return panel;
	}

	private Component getPurchaseMenuPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = getConstraintsForMenuButtons();
		newPurchase = createNewPurchaseButton();
		submitPurchase = createConfirmButton();
		cancelPurchase = createCancelButton();
		panel.add(newPurchase, gc);
		panel.add(submitPurchase, gc);
		panel.add(cancelPurchase, gc);
		return panel;
	}

	private JButton createNewPurchaseButton() {
		JButton b = new JButton("New purchase");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newPurchaseButtonClicked();
			}
		});
		return b;
	}

	private JButton createConfirmButton() {
		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startPayingPurchase();
			}
		});
		confirmButton.setEnabled(false);
		return confirmButton;
	}

	private JButton createCancelButton() {
		JButton b = new JButton("Cancel");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);
		return b;
	}

	/** Event handler for the <code>new purchase</code> event. */
	protected void newPurchaseButtonClicked() {
		log.info("New sale process started");
		startNewSale();
	}

	/** Event handler for the <code>cancel purchase</code> event. */
	protected void cancelPurchaseButtonClicked() {
		log.info("Sale cancelled");
		endSale();
		model.getCurrentPurchaseTableModel().clear();

	}

	/** Event handler for the <code>submit purchase</code> event. */
	protected void startPayingPurchase() {
		double price = model.getCurrentPurchaseTableModel().getTotalPrice();
		PayingWindow.show(price, this);
	}

	public void endPurchaseAfterPaying() {
		log.info("Sale complete");
		try {
			model.getSale().setSellingTime(new Date());
			log.debug("Contents of the current basket:\n"
					+ model.getCurrentPurchaseTableModel());
			domainController.registerSale(model.getSale());
			endSale();
			model.getCurrentPurchaseTableModel().clear();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	public void cancelPaying() {
		submitPurchase.setEnabled(true);
		cancelPurchase.setEnabled(true);
		purchasePane.setEnabled(true);
	}

	private void startNewSale() {
		purchasePane.reset();
		model.setSale(new Sale());
		model.getSale().setSoldItems(new ArrayList<SoldItem>());
		showSelectClientDialog();
		purchasePane.setEnabled(true);
		submitPurchase.setEnabled(true);
		cancelPurchase.setEnabled(true);
		newPurchase.setEnabled(false);
	}

	private void showSelectClientDialog() {
		List<Client> clients = domainController.getAllClients();
		Client currentClient = (Client) JOptionPane.showInputDialog(parent,
				"Choose client", "Choose client", JOptionPane.OK_CANCEL_OPTION,
				(Icon) null, clients.toArray(), null);
		if (currentClient != null) {
			log.info("Client " + currentClient.getFirstName() + " with ID="
					+ currentClient.getId() + " got selected.");
		} else {
			log.info("No client selected");
		}
		model.getSale().setClient(currentClient);
	}

	private void endSale() {
		purchasePane.reset();
		cancelPurchase.setEnabled(false);
		submitPurchase.setEnabled(false);
		newPurchase.setEnabled(true);
		purchasePane.setEnabled(false);
	}

	private GridBagConstraints getConstraintsForPurchaseMenu() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;
		return gc;
	}

	private GridBagConstraints getConstraintsForPurchasePanel() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 1.0;
		return gc;
	}

	private GridBagConstraints getConstraintsForMenuButtons() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridwidth = GridBagConstraints.RELATIVE;
		return gc;
	}
}
