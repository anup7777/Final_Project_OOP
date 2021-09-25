package Currency;

import org.junit.Test;

import static org.junit.Assert.*;

public class CurrencyyTest {

    @Test
    public void getCurrencies() {
        Currencyy curen = new Currencyy();
        String[] actual = curen.getCurrencies();
        String [] expected = {"Japanese yen (JPY)", "Euro (EUR)", "US Dollars (USD)", "Australian Dollars (AUD)",
                "Canadian Dollars (CAD)", "South Korean Won (KRW)", "Thai Baht (THB)",
                "United Arab Emirates Dirham (AED)"};
        assertEquals(expected,actual);
    }

    @Test
    public void getSymbols() {
        Currencyy curren = new Currencyy();
        String[] actual = curren.getSymbols();
        String[] expected = {"¥", "€", "$", "A$", "C$", "₩", "฿", "د.إ"};
        assertEquals(expected,actual);
    }

    @Test
    public void getFactors() {
        Currencyy curenn = new Currencyy();
        double[] actual =curenn.getFactors();
        double[] expected = {137.52, 1.09, 1.29, 1.78, 1.70, 1537.75, 40.52, 4.75};
        assertEquals(expected,actual);
    }
}