package tqs;
import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    private final IStockmarketService stockMarket;
    private final List<Stock> stocks;

    public StocksPortfolio(IStockmarketService stockMarket) {
        this.stockMarket = stockMarket;
        this.stocks = new ArrayList<>();
    }

    public void addStock(Stock s) {
        this.stocks.add(s);
    }

    public double totalValue() {
        if (stocks.size() == 0) {
            return 0.0;
        }
        double total = 0.0;
        for (Stock s : stocks) {
            total += stockMarket.lookUpPrice(s.getLabel()) * s.getQuantity();
        }
        return total;
    }

    public List<Stock> mostValuableStocks(int topN) {
        stocks.sort((stock1, stock2) -> {
            double value1 = stock1.getQuantity() * stockMarket.lookUpPrice(stock1.getLabel());
            double value2 = stock2.getQuantity() * stockMarket.lookUpPrice(stock2.getLabel());
            return Double.compare(value2, value1);
        });

        return stocks.subList(0, Math.min(topN, stocks.size()));
    }
}
