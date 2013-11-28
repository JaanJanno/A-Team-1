package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class SoldItemTest {
	private StockItem test;

	@Before
	public void setUp() {
		test = new StockItem((long) 5, "Test", "Test", 15);

	}

	@Test
	public void testGetSum() {
		SoldItem test2 = new SoldItem(test, 10);
		assertTrue(test2.getSum() == 150);
	}

	@Test
	public void testGetSumWithZeroQuantity() {
		SoldItem test2 = new SoldItem(test, 0);
		assertTrue(test2.getSum() == 0);
	}
}
