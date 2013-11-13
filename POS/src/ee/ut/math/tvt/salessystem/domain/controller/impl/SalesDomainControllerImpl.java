package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {

	private Session session = HibernateUtil.currentSession();

	@Override
	public void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException {
		// Let's assume we have checked and found out that the buyer is
		// underaged and
		// cannot buy chupa-chups
		// throw new VerificationFailedException("Underaged!");
		// XXX - Save purchase

		// Had to change historytablemodel to static to access it from here
		HistoryItem e = new HistoryItem(goods);
		SalesSystemModel.getCurrentHistoryTableModel().addItem(e);

		// After updating the model, the purchase and goods are put in the
		// database. Warehouse stock is decreased
		session.getTransaction().begin();

		for (SoldItem soldItem : goods) {
			soldItem.setHistoryItem(e);
//			changeStockItemQuantity(soldItem);
			session.save(soldItem);
		}

		session.save(e);
		session.getTransaction().commit();

	}

	@Override
	public void cancelCurrentPurchase() throws VerificationFailedException {
		// XXX - Cancel current purchase
	}

	@Override
	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockItem> loadWarehouseState() {
		// XXX mock implementation
		// List<StockItem> dataset = new ArrayList<StockItem>();
		//
		// StockItem chips = new StockItem(1l, "Lays chips", "Potato chips",
		// 11.0,
		// 5);
		// StockItem chips = new StockItem();
		// chips.setName("Lays chips");
		// chips.setDescription("Potato chips");
		// chips.setPrice(11.0);
		// chips.setQuantity(5);
		// HibernateUtil.currentSession().getTransaction().begin();
		// HibernateUtil.currentSession().save(chips);
		// HibernateUtil.currentSession().getTransaction().commit();
		// StockItem chupaChups = new StockItem(2l, "Chupa-chups", "Sweets",
		// 8.0,
		// 8);
		// StockItem frankfurters = new StockItem(3l, "Frankfurters",
		// "Beer sauseges", 15.0, 12);
		// StockItem beer = new StockItem(4l, "Free Beer", "Student's delight",
		// 0.0, 100);
		//
		// dataset.add(chips);
		// dataset.add(chupaChups);
		// dataset.add(frankfurters);
		// dataset.add(beer);
		List<StockItem> dataset = session.createQuery("from StockItem").list();
		return dataset;
	}

	@Override
	public void endSession() {
		HibernateUtil.closeSession();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HistoryItem> loadHistory() {
		List<HistoryItem> dataset = session.createQuery("from HistoryItem")
				.list();
		return dataset;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SoldItem> loadHistoryDetails() {
		List<SoldItem> dataset = session.createQuery("from SoldItem").list();
		return dataset;
	}

	@Override
	public void addToWarehouse(StockItem stockItem) {
		session.getTransaction().begin();
		session.save(stockItem);
		session.getTransaction().commit();

	}

	@SuppressWarnings("unchecked")
	@Override
	public void changeStockItemQuantity(StockItem stockItem, boolean change) {
		// CURRENTLY UNUSED
		int a;
		if (change) {
			a = 1;
		} else {
			a = -1;
		}
		// I know it will only return one quantity
		List<Integer> b = session.createQuery(
				"select quantity from StockItem where id ="
						+ Long.toString(stockItem.getId())).list();
		stockItem.setQuantity(stockItem.getQuantity() + a * b.get(0));

		session.getTransaction().begin();
		session.update(stockItem);
		session.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void changeStockItemQuantity(SoldItem soldItem) {
		// I know it will only return one quantity
		long id_a = soldItem.getStockItem().getId();
		List<Integer> b = session.createQuery(
				"select quantity from StockItem where id ="
						+ Long.toString(id_a)).list();
		int difference = b.get(0) - soldItem.getQuantity();
		session.createQuery("update StockItem set quantity = "+ Integer.toString(difference) + " where id = " + Long.toString(id_a)).executeUpdate();
		
	}
}
