package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Client;
import ee.ut.math.tvt.salessystem.domain.data.Sale;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import java.util.List;

/**
 * Main model. Holds all the other models.
 */
public class SalesSystemModel {
	private StockTableModel warehouseTableModel;
	private PurchaseInfoTableModel currentPurchaseTableModel;
	private PurchaseHistoryTableModel purchaseHistoryTableModel;
	private ClientTableModel clientTableModel;
	private Sale sale;

	/**
	 * Construct application model.
	 * 
	 * @param domainController
	 *            Sales domain controller.
	 */
	public SalesSystemModel(SalesDomainController domainController) {
		warehouseTableModel = new StockTableModel();
		currentPurchaseTableModel = new PurchaseInfoTableModel(this);
		purchaseHistoryTableModel = new PurchaseHistoryTableModel();
		clientTableModel = new ClientTableModel();
		List<StockItem> stockItems = domainController.getAllStockItems();
		warehouseTableModel.populateWithData(stockItems);
		List<Client> clients = domainController.getAllClients();
		clientTableModel.populateWithData(clients);
		List<Sale> sales = domainController.getAllSales();
		purchaseHistoryTableModel.populateWithData(sales);
	}

	public StockTableModel getWarehouseTableModel() {
		return warehouseTableModel;
	}

	public PurchaseInfoTableModel getCurrentPurchaseTableModel() {
		return currentPurchaseTableModel;
	}

	public PurchaseHistoryTableModel getPurchaseHistoryTableModel() {
		return purchaseHistoryTableModel;
	}

	public ClientTableModel getClientTableModel() {
		return clientTableModel;
	}

	public void setPurchaseHistoryTableModel(
			PurchaseHistoryTableModel purchaseHistoryTableModel) {
		this.purchaseHistoryTableModel = purchaseHistoryTableModel;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}
}
