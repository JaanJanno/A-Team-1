package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {

	private static final Logger log = Logger.getLogger(HistoryTab.class);
	private SalesSystemModel model;
	
	
//	public static ArrayList<SoldItem> SoldItems = new ArrayList<SoldItem>();
//	private static SalesSystemModel model;
//	private JScrollPane scrollpane;
//
//
	public HistoryTab(SalesSystemModel model) {
		this.model = model;
	}
	
	// warehouse stock tab - consists of a menu and a table
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

//		panel.add(drawStockMenuPane(), gc);

		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(drawHistoryMainPane(), gc);
		return panel;
	}
	
	// table of the wareshouse stock
	private Component drawHistoryMainPane() {
		JPanel panel = new JPanel();

		JTable table = new JTable(model.getCurrentHistoryTableModel());
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			
			//Checks when a row on the history table has been selected.//
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		    	
		        JTable table = (JTable)e.getComponent();
		        int row = table.rowAtPoint(e.getPoint());
		        
		        System.out.println(row);
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

		panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));
		return panel;
	}
//
//	public static void addToHistory(SoldItem soldItem) {
//		SoldItems.add(soldItem);
//	}
//
//	public static void saveToHistory() {
//		for (int i = 0; i < SoldItems.size(); i++) {
//			model.getCurrentHistoryTableModel().addItem(SoldItems.get(i));
//		}
//	}
//
//	public static void DeleteTempHistory() {
//		SoldItems.clear();
//	}
//
//	private GridBagConstraints getScrollPaneConstraints() {
//		GridBagConstraints gc = new GridBagConstraints();
//
//		gc.fill = GridBagConstraints.BOTH;
//		gc.weightx = 1.0;
//		gc.weighty = 1.0;
//
//		return gc;
//	}
//
//	public Component draw() {
//		JPanel panel = new JPanel();
//
//		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//		panel.setLayout(new GridBagLayout());
//		panel.setBorder(BorderFactory.createTitledBorder("History"));
//
//		panel.add(scrollpane, getScrollPaneConstraints());
//
//		return panel;
//	}
}