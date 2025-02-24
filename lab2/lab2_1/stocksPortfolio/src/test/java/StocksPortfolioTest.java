import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import tqs.Stock;
import tqs.StocksPortfolio;
import tqs.IStockmarketService;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StocksPortfolioTest {

    @InjectMocks
    StocksPortfolio portfolio;

    @Mock
    IStockmarketService market;

    @Test
    void getTotalValue(){
        when(market.lookUpPrice("EBAY")).thenReturn(4.0);
        when(market.lookUpPrice("MSFT")).thenReturn(1.5);

        StocksPortfolio portfolio = new StocksPortfolio(market);
        portfolio.addStock(new Stock("EBAY", 2));
        portfolio.addStock(new Stock("MSFT", 4));

        double result = portfolio.totalValue();

        assertEquals(14.0, result);
        assertThat(result, is(14.0));
        verify(market, times(2)).lookUpPrice(anyString());
    }

    @Test
    void mostValuableStocks() {
        when(market.lookUpPrice("EBAY")).thenReturn(4.0);
        when(market.lookUpPrice("MSFT")).thenReturn(1.5);

        StocksPortfolio portfolio = new StocksPortfolio(market);
        portfolio.addStock(new Stock("EBAY", 2));
        portfolio.addStock(new Stock("MSFT", 4));

        assertEquals(2, portfolio.mostValuableStocks(2).size());
        assertThat(portfolio.mostValuableStocks(2).size(), is(2));
        assertThat(portfolio.mostValuableStocks(2).get(0).getLabel(), is("EBAY"));
        assertThat(portfolio.mostValuableStocks(2).get(1).getLabel(), is("MSFT"));
//        verify(market, times(2)).lookUpPrice(anyString());
    }
}
