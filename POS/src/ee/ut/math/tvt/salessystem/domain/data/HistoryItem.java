package ee.ut.math.tvt.salessystem.domain.data;

import java.util.Calendar;
import java.util.Date;

import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

/**
 * Holds date, time and sum of purchase along with the
 * <code> PurchaseInfoTableModel</code> instance that holds all the items
 * bought.
 * 
 * @author TKasekamp
 * 
 */
public class HistoryItem implements DisplayableItem {

	private Long id;
	private double sum;
	private Date date;
	private Calendar cal;
	private PurchaseInfoTableModel cart;

	public HistoryItem(double sum, PurchaseInfoTableModel cart) {
		this.sum = sum;
		this.cart = cart;
		this.date = new Date();
		this.cal = Calendar.getInstance();
	}

	public double getSum() {
		return sum;
	}

	public Date getDate() {
		return date;
	}

	public Calendar getCal() {
		return cal;
	}

	public PurchaseInfoTableModel getCart() {
		return cart;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "HistoryItem [id=" + id + ", sum=" + sum + ", date=" + date
				+ ", cal=" + cal + ", cart=" + cart + "]";
	}

}
