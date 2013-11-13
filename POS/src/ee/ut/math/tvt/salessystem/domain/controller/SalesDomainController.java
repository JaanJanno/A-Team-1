package ee.ut.math.tvt.salessystem.domain.controller;

import java.util.List;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;

/**
 * Sales domain controller is responsible for the domain specific business
 * processes.
 */
public interface SalesDomainController {

	/**
	 * Load the current state of the warehouse.
	 * 
	 * @return List of ${link
	 *         ee.ut.math.tvt.salessystem.domain.data.StockItem}s.
	 */
	public List<StockItem> loadWarehouseState();

	// business processes
	/**
	 * Initiate new business transaction - purchase of the goods.
	 * 
	 * @throws VerificationFailedException
	 */
	public void startNewPurchase() throws VerificationFailedException;

	/**
	 * Rollback business transaction - purchase of goods.
	 * 
	 * @throws VerificationFailedException
	 */
	public void cancelCurrentPurchase() throws VerificationFailedException;

	/**
	 * Commit business transaction - purchase of goods.
	 * 
	 * @param goods
	 *            Goods that the buyer has chosen to buy.
	 * @throws VerificationFailedException
	 */
	public void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException;
	
	/**
	 * Closes database connection.
	 */
	public void endSession();
	
	/**
	 * Load the purchases saved in the database.
	 * @author TKasekamp
	 * @return List of ${link
	 *         ee.ut.math.tvt.salessystem.domain.data.HistoryItem}s.
	 */
	public List<HistoryItem> loadHistory();
	
	/**
	 * Load the purchase details in the database.
	 * @author TKasekamp
	 * @return List of ${link
	 *         ee.ut.math.tvt.salessystem.domain.data.SoldItem}s.
	 */	
	public List<SoldItem> loadHistoryDetails();

	/**
	 * Adds the item to warehouse database.
	 * @author TKasekamp
	 */	
	public void addToWarehouse(StockItem stockItem);
	
	/**
	 * Decreases or increases the quantity of this StockItem in the warehouse.
	 * @param stockItem 
	 * @param change true increases, false decreases the value
	 * @author TKasekamp
	 */
	public void changeStockItemQuantity(StockItem stockItem, boolean change);
	public void changeStockItemQuantity(SoldItem soldItem);
}
