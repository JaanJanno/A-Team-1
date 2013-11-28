package ee.ut.math.tvt.salessystem.ui.panels;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Purchase pane + shopping cart tabel UI.
 */
public class PurchaseItemPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JComboBox<String> itemNameBox;
	private JTextField barCodeField;
	private JTextField quantityField;
	private JTextField priceField;
	private JButton addItemButton;
	private static SalesSystemModel model;

	/**
	 * Constructs new purchase item panel.
	 * 
	 * @param model
	 *            composite model of the warehouse and the shopping cart.
	 */
	public PurchaseItemPanel(SalesSystemModel model) {
		PurchaseItemPanel.model = model;
		setLayout(new GridBagLayout());
		add(drawDialogPane(), getDialogPaneConstraints());
		add(drawBasketPane(), getBasketPaneConstraints());
		setEnabled(false);
	}

	// Shopping cart pane
	private JComponent drawBasketPane() {
		JPanel basketPane = new JPanel();
		basketPane.setLayout(new GridBagLayout());
		basketPane.setBorder(BorderFactory.createTitledBorder("Shopping cart"));
		JTable table = new JTable(model.getCurrentPurchaseTableModel());
		JScrollPane scrollPane = new JScrollPane(table);
		basketPane.add(scrollPane, getBacketScrollPaneConstraints());
		return basketPane;
	}

	// Purchase dialog
	private JComponent drawDialogPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Product"));
		barCodeField = new JTextField();
		quantityField = new JTextField("1");
		priceField = new JTextField();
		itemNameBox = new JComboBox<String>();
		fillItemNameBox();
		itemNameBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				fillDialogFields();
			}
		});
		priceField.setEditable(false);
		panel.add(new JLabel("Bar code:"));
		panel.add(barCodeField);
		panel.add(new JLabel("Amount:"));
		panel.add(quantityField);
		panel.add(new JLabel("Name:"));
		panel.add(itemNameBox);
		panel.add(new JLabel("Price:"));
		panel.add(priceField);
		addItemButton = new JButton("Add to cart");
		addItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addItemEventHandler();
			}
		});
		panel.add(addItemButton);
		return panel;
	}

	/**
	 * Fill dialog with data from the "database".
	 * 
	 * @author Tõnis
	 */
	//
	public void fillDialogFields() {
		StockItem stockItem = getStockItemByName();
		if (stockItem != null) {
			barCodeField.setText(String.valueOf(stockItem.getId()));
			String priceString = String.valueOf(stockItem.getPrice());
			priceField.setText(priceString);
		} else {
			reset();
		}
	}

	/**
	 * Search the warehouse for a StockItem with the name selected in the
	 * itemNameBox JComboBox to the barCode textfield.
	 * 
	 * @author Tõnis
	 * @return item
	 */
	private StockItem getStockItemByName() {
		try {
			String name = (String) itemNameBox.getSelectedItem();
			return model.getWarehouseTableModel().getItemByName(name);
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
		StockItem stockItem = getStockItemByName();
		if (stockItem != null) {
			int quantity;
			try {
				quantity = Integer.parseInt(quantityField.getText());
			} catch (NumberFormatException ex) {
				quantity = 1;
			}
			int currentQuantity = model.getCurrentPurchaseTableModel()
					.getQuantity(stockItem);
			if (quantity > stockItem.getQuantity()
					|| currentQuantity + quantity > stockItem.getQuantity()) {
				overQuantityLimitError(stockItem, quantity);
			} else {
				model.getCurrentPurchaseTableModel().addItem(
						new SoldItem(stockItem, quantity));
			}
		}
	}

	public void overQuantityLimitError(StockItem stockItem, int quantity) {
		JOptionPane.showMessageDialog(
				this.getRootPane(),
				"It seems you want too much from our little shop. We only have "
						+ Integer.toString(stockItem.getQuantity()) + " of "
						+ stockItem.getName() + ", while you wanted "
						+ Integer.toString(quantity) + ".",
				"Warehouse out of stock", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Sets whether or not this component is enabled.
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.addItemButton.setEnabled(enabled);
		this.barCodeField.setEnabled(enabled);
		this.quantityField.setEnabled(enabled);
		itemNameBox.setEnabled(enabled);
		fillDialogFields();
	}

	/**
	 * Reset dialog fields.
	 */
	public void reset() {
		barCodeField.setText("");
		quantityField.setText("1");
		priceField.setText("");
	}

	/**
	 * Fills the productNameBox with items from warehouse.
	 * 
	 * @author Tõnis
	 */
	public static void fillItemNameBox() {
		System.out.println(model.getWarehouseTableModel().getTableRows());
		itemNameBox.removeAllItems();
		for (StockItem item : model.getWarehouseTableModel().getTableRows()) {
			itemNameBox.addItem(item.getName());
		}
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
