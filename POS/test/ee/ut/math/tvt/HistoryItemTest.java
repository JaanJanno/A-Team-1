package ee.ut.math.tvt;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class HistoryItemTest {
	private ArrayList<SoldItem> goods;
	private StockItem temp;
	private StockItem temp2;
	private HistoryItem test;

	@Before
	public void setUp() {
		temp = new StockItem((long) 5, "Test", "Test", 15);
		temp2 = new StockItem((long) 6, "Test2", "Test2", 15);

	}

	@Test
	public void testAddSoldItem() {
		goods = new ArrayList<SoldItem>();
		goods.add(new SoldItem(temp, 10));
		goods.add(new SoldItem(temp2, 10));
		test = new HistoryItem(goods);
		ArrayList<SoldItem> goods2 = new ArrayList<SoldItem>();
		goods2 = (ArrayList<SoldItem>) test.getGoods();

		assertTrue(goods.equals(goods2));
	}

	@Test
	public void testGetSumWithNoItems() {
		goods = new ArrayList<SoldItem>();
		test = new HistoryItem(goods);
		assertTrue(test.getSum() == 0);
	}

	@Test
	public void testGetSumWithOneItem() {
		goods = new ArrayList<SoldItem>();
		goods.add(new SoldItem(temp, 10));
		test = new HistoryItem(goods);

		assertTrue(test.getSum() == 150);
	}

	@Test
	public void testGetSumWithMultipleItems() {
		goods = new ArrayList<SoldItem>();
		goods.add(new SoldItem(temp, 10));
		goods.add(new SoldItem(temp2, 10));
		test = new HistoryItem(goods);
		assertTrue(test.getSum() == 300);
	}
}
