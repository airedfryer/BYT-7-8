package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

		// Ensure getAmount returns the correct amount for different types of money.
	@Test
	public void testGetAmount() {
        assertEquals(Integer.valueOf(10000), SEK100.getAmount());
        assertEquals(Integer.valueOf(1000), EUR10.getAmount());
	}

		// Ensure getCurrency returns the correct Currency for different types of money.
	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
        assertEquals(EUR, EUR10.getCurrency());
        
	}

	// Ensure toString returns the expected string representation for different types of money.
	@Test
	public void testToString() {
		assertEquals("100.00 SEK", SEK100.toString());
	    assertEquals("10.00 EUR", EUR10.toString());
	}
	
	// Ensure universalValue converts the amount to the universal currency correctly.
	@Test
	public void testGlobalValue() {
		assertEquals(Integer.valueOf(1500), SEK100.universalValue());
        assertEquals(Integer.valueOf(1500), EUR10.universalValue());
	}

	// Ensure equals correctly compares the universal values of different types of money.
	@Test
	public void testEqualsMoney() {
		Money SEK100Copy = new Money(10000, SEK);
        Money EUR10Copy = new Money(1000, EUR);

        assertTrue(SEK100.equals(SEK100Copy));
        assertTrue(EUR10.equals(EUR10Copy));
        assertFalse(SEK100.equals(EUR10));
	}

    // Ensure add correctly adds passed money of different currencies.
	@Test
	public void testAdd() {
        Money result = SEK100.add(EUR10);
        assertEquals(Integer.valueOf(11500), result.getAmount());
        assertEquals(SEK, result.getCurrency());
	}

	// Ensure sub correctly subtracts passed money of different currencies
	@Test
	public void testSub() {
		Money result = SEK100.sub(EUR10);
        assertEquals(Integer.valueOf(8500), result.getAmount());
        assertEquals(SEK, result.getCurrency());
	}

    // Ensure isZero correctly identifies types of money where the value is zero.
	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
        assertFalse(SEK100.isZero());
	}

	// Ensure negate correctly negates the amount of money passed.
	@Test
	public void testNegate() {
		Money negatedSEK100 = SEK100.negate();
        assertEquals(Integer.valueOf(-10000), negatedSEK100.getAmount());
        assertEquals(SEK, negatedSEK100.getCurrency());
	}

	// Ensure compareTo correctly compares passed money values based on universal value.
	@Test
	public void testCompareTo() {
		assertTrue(SEK100.compareTo(SEK200) < 0);
	    assertTrue(SEK200.compareTo(SEK100) > 0);
	    assertEquals(0, SEK100.compareTo(SEK100));
	    assertTrue(SEK100.compareTo(EUR10) < 0);
	    assertTrue(EUR10.compareTo(SEK100) > 0);
	}
}
