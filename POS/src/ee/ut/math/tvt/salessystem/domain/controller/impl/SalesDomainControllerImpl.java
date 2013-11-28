package ee.ut.math.tvt.salessystem.domain.controller.impl;

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
		HistoryItem e = new HistoryItem(goods);
		SalesSystemModel.getCurrentHistoryTableModel().addItem(e);
		session.getTransaction().begin();
		for (SoldItem soldItem : goods) {
			soldItem.setHistoryItem(e);
			changeStockItemQuantity(soldItem);
			session.save(soldItem);
			session.save(e);
		}
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

	@Override
	public void changeStockItemQuantity(StockItem stockItem, int quantity) {
		stockItem.setQuantity(stockItem.getQuantity() + quantity);
		session.getTransaction().begin();
		session.update(stockItem);
		session.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void changeStockItemQuantity(SoldItem soldItem) {
		long id_a = soldItem.getStockItem().getId();
		List<Integer> b = session.createQuery(
				"select quantity from StockItem where id ="
						+ Long.toString(id_a)).list();
		int difference = b.get(0) - soldItem.getQuantity();
		session.createQuery(
				"update StockItem set quantity = "
						+ Integer.toString(difference) + " where id = "
						+ Long.toString(id_a)).executeUpdate();
	}
}
