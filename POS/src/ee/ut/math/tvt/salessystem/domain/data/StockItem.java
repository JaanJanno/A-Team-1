package ee.ut.math.tvt.salessystem.domain.data;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



/**
 * Stock item. Corresponds to the Data Transfer Object design pattern.
 */
@Entity
@Table(name = "STOCKITEM")
public class StockItem implements Cloneable, DisplayableItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Don't know if necessary. TK
	private Long id;

    @Column(name = "name")
	private String name;

    @Column(name = "price")
	private double price;

    @Column(name = "description")
	private String description;

    @Column(name = "quantity")
	private int quantity;

    @OneToMany(mappedBy = "stockItem")
    private Set<SoldItem> soldItems;
	/**
	 * Constucts new <code>StockItem</code> with the specified values.
	 * 
	 * @param id
	 *            barcode id
	 * @param name
	 *            name of the product
	 * @param desc
	 *            description of the product
	 * @param price
	 *            price of the product
	 */
	public StockItem(Long id, String name, String desc, double price) {
		this.id = id;
		this.name = name;
		this.description = desc;
		this.price = price;
	}

	public StockItem(Long id, String name, String desc, double price,
			int quantity) {
		this.id = id;
		this.name = name;
		this.description = desc;
		this.price = price;
		this.quantity = quantity;
	}

	/**
	 * Constructs new <code>StockItem</code>.
	 */
	public StockItem() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return id + " " + name + " " + description + " " + price;
	}

	/**
	 * Method for querying the value of a certain column when StockItems are
	 * shown as table rows in the SalesSstemTableModel. The order of the columns
	 * is: id, name, price, quantity.
	 */
	public Object getColumn(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return id;
		case 1:
			return name;
		case 2:
			return new Double(price);
		case 3:
			return new Integer(quantity);
		default:
			throw new RuntimeException("invalid column!");
		}
	}

	@Override
	public Object clone() {
		StockItem item = new StockItem(getId(), getName(), getDescription(),
				getPrice(), getQuantity());
		return item;
	}

	public Set<SoldItem> getSoldItems() {
		return soldItems;
	}

	public void setSoldItems(Set<SoldItem> soldItems) {
		this.soldItems = soldItems;
	}

}
