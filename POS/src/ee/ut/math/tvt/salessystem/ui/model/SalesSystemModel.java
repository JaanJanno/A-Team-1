package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;

/**
 * Main model. Holds all the other models.
 */
public class SalesSystemModel {

	private static final Logger log = Logger.getLogger(SalesSystemModel.class);

	// Warehouse model
	private StockTableModel warehouseTableModel;

	// Current shopping cart model
	private PurchaseInfoTableModel currentPurchaseTableModel;

	private static HistoryTableModel currentHistoryTableModel;

	private final SalesDomainController domainController;
	private PurchaseHistoryTableModel currentPurchaseHistoryTableModel;

	/**
	 * Construct application model.
	 * 
	 * @param domainController
	 *            Sales domain controller.
	 */
	public SalesSystemModel(SalesDomainController domainController) {
		this.domainController = domainController;

		warehouseTableModel = new StockTableModel();
		currentPurchaseTableModel = new PurchaseInfoTableModel();
		currentHistoryTableModel = new HistoryTableModel();
		currentPurchaseHistoryTableModel = new PurchaseHistoryTableModel();

		// populate stock model with data from the warehouse
		warehouseTableModel.populateWithData(domainController
				.loadWarehouseState());
		currentHistoryTableModel.populateWithData(domainController.loadHistory());
//		domainController.loadHistoryDetails();
	}

	public StockTableModel getWarehouseTableModel() {
		return warehouseTableModel;
	}

	public PurchaseInfoTableModel getCurrentPurchaseTableModel() {
		return currentPurchaseTableModel;
	}

	public static HistoryTableModel getCurrentHistoryTableModel() {
		return currentHistoryTableModel;
	}

	public PurchaseHistoryTableModel getCurrentPurchaseHistoryTableModel() {
		return currentPurchaseHistoryTableModel;
	}

}
