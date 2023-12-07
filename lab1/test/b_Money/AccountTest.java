package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		assertFalse(testAccount.timedPaymentExists("payment1"));

        testAccount.addTimedPayment("payment1", 2, 1, new Money(1000, SEK), SweBank, "Alice");
        assertTrue(testAccount.timedPaymentExists("payment1"));

        testAccount.removeTimedPayment("payment1");
        assertFalse(testAccount.timedPaymentExists("payment1"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		Money initialBalance = testAccount.getBalance();
		testAccount.addTimedPayment("payment1", 2, 1, new Money(5000, SEK), SweBank, "Alice");

        // Payment showuld be executed after 2 ticks
        testAccount.tick();
        assertEquals(1499250, testAccount.getBalance().getAmount().intValue());
        testAccount.tick();
        assertEquals(1498500, testAccount.getBalance().getAmount().intValue());
        // adjusted tick, withdrawal procedure - otherwise it only deposited on the 3rd tick
        }

	@Test
	public void testAddWithdraw() {
		Money initialBalance = testAccount.getBalance();
	    
	    testAccount.deposit(new Money(5000, SEK));
	    assertTrue(testAccount.getBalance().equals(initialBalance.add(new Money(5000, SEK))));

	    testAccount.withdraw(new Money(3000, SEK));
	    assertTrue(testAccount.getBalance().equals(initialBalance.add(new Money(2000, SEK))));
	}
	
	@Test
	public void testGetBalance() {
		assertEquals(new Money(1500000, SEK), testAccount.getBalance()); //java.lang.AssertionError: expected: b_Money.Money<15000.00 SEK> but was: b_Money.Money<15000.00 SEK>: what?
		// fixed by overriding equals in money.java

	}
}
