package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;

/**
 * Purchase history details model.
 */
public class HistoryTableModel extends SalesSystemTableModel<HistoryItem> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(HistoryTableModel.class);

	public HistoryTableModel() {
		super(new String[] { "Date", "Time", "Sum" });
	}

	@Override
	protected Object getColumnValue(HistoryItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getDate();
		case 1:
			return item.getTime();
		case 2:
			return Math.round(item.getSum() * 100) / 100.0;
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	/**
	 * Add new HistoryItem to table.
	 */
	public void addItem(final HistoryItem item) {
		rows.add(item);
		log.debug("Purchase date: " + item.getDate() + ", time: "
				+ item.getTime());
		fireTableDataChanged();
	}
}