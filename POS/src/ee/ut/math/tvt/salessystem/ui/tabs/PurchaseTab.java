package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;
import ee.ut.math.tvt.salessystem.ui.PaymentConfirmation;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
	private PaymentConfirmation paymentConfirmation;

	public PurchaseTab(SalesDomainController controller, SalesSystemModel model) {
		this.domainController = controller;
		this.model = model;
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
			@Override
			public void actionPerformed(ActionEvent e) {
				newPurchaseButtonClicked();
			}
		});
		return b;
	}

	private JButton createConfirmButton() {
		JButton b = new JButton("Confirm");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);
		return b;
	}

	private JButton createCancelButton() {
		JButton b = new JButton("Cancel");
		b.addActionListener(new ActionListener() {
			@Override
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
		try {
			domainController.startNewPurchase();
			startNewSale();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	/** Event handler for the <code>cancel purchase</code> event. */
	protected void cancelPurchaseButtonClicked() {
		log.info("Sale cancelled");
		try {
			domainController.cancelCurrentPurchase();
			endSale();
			model.getCurrentPurchaseTableModel().clear();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	/** Event handler for the <code>submit purchase</code> event. */
	protected void submitPurchaseButtonClicked() {
		log.info("Sale started");
		try {
			paymentConfirmation = new PaymentConfirmation(null, true, model
					.getCurrentPurchaseTableModel().getSum());
			boolean accepted = paymentConfirmation.isAccepted();
			if (accepted) {
				log.info("Payment was accepted");
				log.debug("Contents of the current basket:\n"
						+ model.getCurrentPurchaseTableModel());
				List<SoldItem> currentPurchaseList = model
						.getCurrentPurchaseTableModel().getTableRows();
				domainController.submitCurrentPurchase(currentPurchaseList);
				for (SoldItem item : currentPurchaseList) {
					StockItem warehouseQuantity = model
							.getWarehouseTableModel().getItemByName(
									item.getName());
					warehouseQuantity.setQuantity(warehouseQuantity
							.getQuantity() - item.getQuantity());
				}
				endSale();
				model.getCurrentPurchaseTableModel().clear();
			} else {
				log.info("Payment cancelled");
			}
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
		log.info("Sale complete");
	}

	private void startNewSale() {
		purchasePane.reset();
		purchasePane.setEnabled(true);
		submitPurchase.setEnabled(true);
		cancelPurchase.setEnabled(true);
		newPurchase.setEnabled(false);
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
