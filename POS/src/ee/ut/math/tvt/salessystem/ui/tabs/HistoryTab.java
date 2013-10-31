package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {

	public static ArrayList<SoldItem> SoldItems = new ArrayList<SoldItem>();
	private static SalesSystemModel model;
	private JScrollPane scrollpane;

	// TODO - implement!

	public HistoryTab(SalesSystemModel model) {
		this.model = model;
		JTable table = new JTable(model.getCurrentHistoryTableModel());
		scrollpane = new JScrollPane(table);
	}

	public static void addToHistory(SoldItem soldItem) {
		SoldItems.add(soldItem);
	}

	public static void saveToHistory() {
		for (int i = 0; i < SoldItems.size(); i++) {
			model.getCurrentHistoryTableModel().addItem(SoldItems.get(i));
		}
	}

	public static void DeleteTempHistory() {
		SoldItems.clear();
	}

	private GridBagConstraints getScrollPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		return gc;
	}

	public Component draw() {
		JPanel panel = new JPanel();

		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder("History"));

		panel.add(scrollpane, getScrollPaneConstraints());

		return panel;
	}
}