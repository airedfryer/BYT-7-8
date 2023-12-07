package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	// Ensure getName returns the correct name for different currencies.
	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
        assertEquals("EUR", EUR.getName());
	}
	
	// Ensure getRate returns the correct rate for different currencies.
	@Test
	public void testGetRate() {
		assertEquals(Double.valueOf(0.15), SEK.getRate());
        assertEquals(Double.valueOf(1.5), EUR.getRate());
	}
	
	// Ensure setRate sets the rate for different currencies correctly.
	@Test
	public void testSetRate() {
		SEK.setRate(0.18);
        assertEquals(Double.valueOf(0.18), SEK.getRate());
	}
	
	// Ensure universalValue sets the universal currency rate correctly.
	@Test
	public void testGlobalValue() {
		assertEquals(Integer.valueOf(1500), SEK.universalValue(10000));
        assertEquals(Integer.valueOf(1500), EUR.universalValue(1000));
	}
	
	// Ensure valueInThisCurrency converts an amount from another currency to this currency correctly.
	@Test
	public void testValueInThisCurrency() {
		assertEquals(Integer.valueOf(1500), SEK.valueInThisCurrency(10000, EUR));
	    assertEquals(Integer.valueOf(1000), DKK.valueInThisCurrency(200, SEK));
	}

}
