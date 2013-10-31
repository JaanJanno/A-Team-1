package ee.ut.math.tvt.salessystem.domain.data;

import java.util.Date;
import java.util.List;

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

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "HistoryItem [id=" + id + ", sum=" + sum + ", date=" + date
				+ "]";
	}

	public List<SoldItem> getGoods() {
		return goods;
	}

	public void setGoods(List<SoldItem> goods) {
		this.goods = goods;
	}

}
