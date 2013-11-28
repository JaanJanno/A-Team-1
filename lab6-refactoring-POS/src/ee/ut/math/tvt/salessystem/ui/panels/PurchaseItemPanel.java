package ee.ut.math.tvt.salessystem.ui.panels;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.SalesSystemException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.NoSuchElementException;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.apache.log4j.Logger;

/**
 * Purchase pane + shopping cart tabel UI.
 */
public class PurchaseItemPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(PurchaseItemPanel.class);
	private JComboBox stockItemSelector;
	private JTextField barCodeField;
	private JTextField quantityField;
	private JTextField priceField;
	private JButton addItemButton;
	private SalesSystemModel model;

	/**
	 * Constructs new purchase item panel.
	 * 
	 * @param model
	 *            composite model of the warehouse and the shopping cart.
	 */
	public PurchaseItemPanel(SalesSystemModel model) {
		this.model = model;
		setLayout(new GridBagLayout());
		add(drawDialogPane(), getDialogPaneConstraints());
		add(drawBasketPane(), getBasketPaneConstraints());
		setEnabled(false);
	}

	private JComponent drawBasketPane() {
		JPanel basketPane = new JPanel();
		basketPane.setLayout(new GridBagLayout());
		basketPane.setBorder(BorderFactory.createTitledBorder("Shopping cart"));
		JTable table = new JTable(model.getCurrentPurchaseTableModel());
		JScrollPane scrollPane = new JScrollPane(table);
		basketPane.add(scrollPane, getBacketScrollPaneConstraints());
		return basketPane;
	}

	private JComponent drawDialogPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Product"));
		stockItemSelector = new JComboBox();
		barCodeField = new JTextField();
		quantityField = new JTextField("1");
		priceField = new JTextField();
		stockItemSelector.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					fillDialogFields();
				}
			}
		});
		barCodeField.setEditable(false);
		priceField.setEditable(false);
		panel.add(new JLabel("Item:"));
		panel.add(stockItemSelector);
		panel.add(new JLabel("Bar code:"));
		panel.add(barCodeField);
		panel.add(new JLabel("Amount:"));
		panel.add(quantityField);
		panel.add(new JLabel("Price:"));
		panel.add(priceField);
		addItemButton = new JButton("Add to cart");
		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItemEventHandler();
			}
		});
		panel.add(addItemButton);
		return panel;
	}

	public void fillDialogFields() {
		StockItem stockItem = (StockItem) stockItemSelector.getSelectedItem();
		if (stockItem != null) {
			String barCodeAsString = String.valueOf(stockItem.getId());
			String priceAsString = String.valueOf(stockItem.getPrice());
			barCodeField.setText(barCodeAsString);
			priceField.setText(priceAsString);
		} else {
			reset();
		}
	}

	private StockItem getStockItemByBarcode() {
		try {
			int code = Integer.parseInt(barCodeField.getText());
			return model.getWarehouseTableModel().getItemById(code);
		} catch (NumberFormatException ex) {
			return null;
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	/**
	 * Add new item to the cart.
	 */
	public void addItemEventHandler() {
		StockItem stockItem = getStockItemByBarcode();
		if (stockItem != null) {
			int quantity;
			try {
				quantity = Integer.parseInt(quantityField.getText());
			} catch (NumberFormatException ex) {
				quantity = 1;
			}
			try {
				SoldItem item = new SoldItem(stockItem, quantity);
				model.getCurrentPurchaseTableModel().addItem(item);
				model.getSale().addSoldItem(item);
			} catch (SalesSystemException e) {
				showNotEnoughInStockWarning();
			}
		}
	}

	private void showNotEnoughInStockWarning() {
		JOptionPane.showMessageDialog(this,
				"Not enough stock, decrease amount", "Attention",
				JOptionPane.WARNING_MESSAGE);
		logger.debug("  -- there was not enough cargo in warehouse to add item");
	}

	/**
	 * Sets whether or not this component is enabled.
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.stockItemSelector.setEnabled(enabled);
		this.barCodeField.setEnabled(enabled);
		this.quantityField.setEnabled(enabled);
		this.addItemButton.setEnabled(enabled);
		fillDialogFields();
	}

	/**
	 * Reset dialog fields.
	 */
	public void reset() {
		((DefaultComboBoxModel) stockItemSelector.getModel())
				.removeAllElements();
		for (StockItem stockItem : model.getWarehouseTableModel()
				.getTableRows()) {
			((DefaultComboBoxModel) stockItemSelector.getModel())
					.addElement(stockItem);
		}
		barCodeField.setText("");
		quantityField.setText("1");
		priceField.setText("");
	}

	private GridBagConstraints getDialogPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 0d;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.NONE;
		return gc;
	}

	private GridBagConstraints getBasketPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 1.0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.BOTH;
		return gc;
	}

	private GridBagConstraints getBacketScrollPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		return gc;
	}
}
