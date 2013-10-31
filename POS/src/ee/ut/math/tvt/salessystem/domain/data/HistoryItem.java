package ee.ut.math.tvt.salessystem.domain.data;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ee.ut.math.tvt.salessystem.ui.model.PurchaseHistoryTableModel;

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
	private double sum = 0;
	private Date date;
	private PurchaseHistoryTableModel cart;
	private List<SoldItem> goods;

	public HistoryItem(List<SoldItem> goods) {
		this.goods = goods;
		this.date = new Date();
		for (SoldItem soldItem : goods) {
			sum += soldItem.getSum();
		}
	}

	public double getSum() {
		return sum;
	}

	public Date getDate() {
		return date;
	}

	public PurchaseHistoryTableModel getCart() {
		return cart;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "HistoryItem [id=" + id + ", sum=" + sum + ", date=" + date
				+ ", cart=" + cart + "]";
	}

	public List<SoldItem> getGoods() {
		return goods;
	}

	public void setGoods(List<SoldItem> goods) {
		this.goods = goods;
	}

}
