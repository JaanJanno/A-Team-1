package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.JTableHeader;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labeled "History" in the menu).
 * 
 * @author TKasekamp
 */
public class HistoryTab {
	private SalesSystemModel model;

	public HistoryTab(SalesSystemModel model) {
		this.model = model;
	}

	public Component draw() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		panel.setLayout(gb);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;
		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(drawHistoryMainPane(), gc);
		panel.add(drawHistorySecondPane(), gc);
		return panel;
	}

	private Component drawHistoryMainPane() {
		JPanel panel = new JPanel();
		JTable table = new JTable(
				SalesSystemModel.getCurrentHistoryTableModel());
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			// Checks when a row on the history table has been selected.//
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				JTable table = (JTable) e.getComponent();
				int row = table.rowAtPoint(e.getPoint());
				updateTable(row);
			}
		});
		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(table);
		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		panel.setLayout(gb);
		panel.add(scrollPane, gc);
		panel.setBorder(BorderFactory.createTitledBorder("Purchase history"));
		return panel;
	}

	private Component drawHistorySecondPane() {
		JPanel panel = new JPanel();
		JTable table = new JTable(model.getCurrentPurchaseHistoryTableModel());
		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(table);
		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		panel.setLayout(gb);
		panel.add(scrollPane, gc);
		panel.setBorder(BorderFactory
				.createTitledBorder("Purchase history details"));
		return panel;
	}

	private void updateTable(int row) {
		HistoryItem h = SalesSystemModel.getCurrentHistoryTableModel()
				.getTableRows().get(row);
		model.getCurrentPurchaseHistoryTableModel().populateWithData(
				h.getGoods());
		model.getCurrentPurchaseHistoryTableModel().fireTableDataChanged();
	}
}
