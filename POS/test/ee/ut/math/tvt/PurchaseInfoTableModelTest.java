package ee.ut.math.tvt;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class PurchaseInfoTableModelTest {
	private PurchaseInfoTableModel test;
	private StockItem temp;
	private SoldItem temp2;

	@Before
	public void setUp() {
		test = new PurchaseInfoTableModel();
		temp = new StockItem((long) 5, "Test", "Test", 15);
		temp2 = new SoldItem(temp, 10);
	}

	@Test
	public void testValidateNameUniqueness() {
		test.clear();
		test.addItem(temp2);
		List<SoldItem> control = test.getTableRows();
		test.addItem(temp2);
		assertTrue(control.equals(test.getTableRows()));
	}

	@Test
	public void testGetQuantity() {
		test.clear();
		test.addItem(temp2);
		int a = test.getQuantity(temp);
		assertTrue(a == 10);
	}

	@Test
	public void testGetSum() {
		test.clear();
		test.addItem(temp2);
		assertTrue(test.getSum() == 150);
	}

}
