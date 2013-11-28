package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.OverLimitException;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class StockTableModelTest {
	private StockTableModel test;
	private StockItem temp;

	@Before
	public void setUp() {
		test = new StockTableModel();
		temp = new StockItem((long) 5, "Test", "Test", 15);

	}

	@Test
	public void testValidateNameUniqueness() {
		test.clear();

		test.addItem(temp);
		List<StockItem> control = test.getTableRows();
		test.addItem(temp);
		assertTrue(control.equals(test.getTableRows()));
	}

	@Test
	public void testHasEnoughInStock() {
		test.clear();
		temp = new StockItem((long) 5, "Test", "Test", 15,15);
		test.addItem(temp);
		try {
			test.hasEnoughInStock("Test", 20);
			assertTrue(false);
		} catch (OverLimitException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testGetItemByIdWhenItemExists() {
		test.clear();
		test.addItem(temp);
		StockItem result = test.getItemById((long) 5);
		assertTrue(temp.equals(result));
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetItemByIdWhenThrowsException() {
		test.clear();
		StockItem result = null;
		result = test.getItemById((long) 5);
		assertTrue(result == null);
	}
}
