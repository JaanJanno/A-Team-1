package ee.ut.math.tvt.salessystem.ui.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;

/**
 * Purchase history details model.
 */
public class HistoryTableModel extends SalesSystemTableModel<HistoryItem> {
	private static final long serialVersionUID = 1L;
	private final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd");
	private final DateFormat timeFormat = new SimpleDateFormat(
			"HH:mm:ss");

	private static final Logger log = Logger.getLogger(HistoryTableModel.class);

	public HistoryTableModel() {
		super(new String[] { "Date", "Time", "Sum" });
	}

	@Override
	protected Object getColumnValue(HistoryItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return dateFormat.format(item.getDate());
		case 1:
			return timeFormat.format(item.getDate());
		case 2:
			return item.getSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}


	 /**
	 * Add new HistoryItem to table.
	 */
	 public void addItem(final HistoryItem item) {
	 /**
	 * XXX In case such stockItem already exists increase the quantity of
	 * the existing stock.
	 */
	
	 rows.add(item);
	 log.debug("Purchase date: " + dateFormat.format(item.getDate()) + ", time: " + timeFormat.format(item.getDate()));
	 fireTableDataChanged();
	 }
}