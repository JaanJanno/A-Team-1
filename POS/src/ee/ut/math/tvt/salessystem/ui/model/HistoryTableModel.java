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
	private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	


	private static final Logger log = Logger
			.getLogger(PurchaseInfoTableModel.class);

	public HistoryTableModel() {
		super(new String[] { "Date", "Time", "Sum" });
	}


	@Override
	protected Object getColumnValue(HistoryItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return dateFormat.format(item.getDate());
		case 1:
			return dateFormat.format(item.getCal().getTime());
		case 2:
			return item.getSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

//	@Override
//	public String toString() {
//		final StringBuffer buffer = new StringBuffer();
//
//		for (int i = 0; i < headers.length; i++)
//			buffer.append(headers[i] + "\t");
//		buffer.append("\n");
//
//		for (final  item : rows) {
//			buffer.append(item.getId() + "\t");
//			buffer.append(item.getName() + "\t");
//			buffer.append(item.getPrice() + "\t");
//			buffer.append(item.getQuantity() + "\t");
//			buffer.append(item.getSum() + "\t");
//			buffer.append("\n");
//		}
//
//		return buffer.toString();
//	}

//	/**
//	 * Add new StockItem to table.
//	 */
//	public void addItem(final SoldItem item) {
//		/**
//		 * XXX In case such stockItem already exists increase the quantity of
//		 * the existing stock.
//		 */
//
//		rows.add(item);
//		log.debug("Added " + item.getName() + " quantity of "
//				+ item.getQuantity());
//		fireTableDataChanged();
//	}
}