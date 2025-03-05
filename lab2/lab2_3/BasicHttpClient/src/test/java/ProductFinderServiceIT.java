import org.junit.jupiter.api.Test;
import tqs.Product;
import tqs.TqsBasicHttpClient;
import tqs.ProductFinderService;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductFinderServiceIT {

    @Test
    public void testFindProductDetails_ValidProduct() throws IOException {
        TqsBasicHttpClient httpClient = new TqsBasicHttpClient();
        ProductFinderService productFinderService = new ProductFinderService(httpClient);

        Optional<Product> result = productFinderService.findProductDetails(3);

        assertTrue(result.isPresent());
        assertEquals(3, result.get().getId());
        assertEquals("Mens Cotton Jacket", result.get().getTitle());
    }

    @Test
    public void testFindProductDetails_InvalidProduct() throws IOException {
        TqsBasicHttpClient httpClient = new TqsBasicHttpClient();
        ProductFinderService productFinderService = new ProductFinderService(httpClient);

        Optional<Product> result = productFinderService.findProductDetails(300);

        assertFalse(result.isPresent());
    }
}