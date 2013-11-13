package ee.ut.math.tvt.salessystem.domain.data;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



/**
 * Holds date, time and sum of purchase along with a List of SoldItems connected
 * to this purchase.
 * 
 * @author TKasekamp
 */
@Entity
@Table(name = "HISTORYITEM")
public class HistoryItem implements Cloneable,DisplayableItem {

	private final Format DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	private final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
    @Column(name = "total")
	private double sum;
    @Column(name = "date")
	private Date date;
//    private Timestamp date;
    @OneToMany(mappedBy = "historyItem")
	private List<SoldItem> goods;

	public HistoryItem(List<SoldItem> goods) {
		this.goods = goods;
		this.date = new Date();
		sum = 0;
		for (SoldItem soldItem : goods) {
			sum += soldItem.getSum();
		}
	}

	public double getSum() {
		return sum;
	}

	public String getDate() {
		return date == null ? "?" : DATE_FORMAT.format(date);
	}
	public String getTime() {
//		return date == null ? "?" : TIME_FORMAT.format(date);
		return "help";
	}	

	@Override
	public Long getId() {
		return id;
	}

	public List<SoldItem> getGoods() {
		return goods;
	}

	public void setGoods(List<SoldItem> goods) {
		this.goods = goods;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "HistoryItem [id=" + id + ", sum=" + sum + ", date=" + date
				+ ", goods=" + goods + "]";
	}

}
