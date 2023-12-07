package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


// openaccount fix: this issue was solved by fixing openAccount so that it creates an account and registers it.

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
        assertEquals("SweBank", SweBank.getName());
        assertEquals("Nordea", Nordea.getName());
        assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
        assertEquals(SEK, SweBank.getCurrency());
        assertEquals(SEK, Nordea.getCurrency());
        assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
        SweBank.openAccount("Bob");
        Nordea.openAccount("Alice"); //this one SHOULD throw an error: Bob has a swebank account, alice doesn't exist
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		 Money SEK100 = new Money(10000, SEK);
	     SweBank.deposit("Ulrika", SEK100); // <- account.deposit(money);
	     assertEquals(1500, SweBank.getBalance("Ulrika").intValue()); // <- throw new AccountDoesNotExistException(); openaccount fix
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
        Money SEK50 = new Money(5000, SEK);
        SweBank.withdraw("Alice", SEK50); // <- AccountDoesNotExistException(). SHOULD be thrown.
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals(0, SweBank.getBalance("Ulrika").intValue()); // <- AccountDoesNotExistException()
        assertEquals(0, Nordea.getBalance("Bob").intValue()); // <- same fail. fixed by updating openaccount so it actually opens an account
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException, InsufficientFundsException {
		Money SEK2000 = new Money(200000, SEK);
	    SweBank.deposit("Ulrika", SEK2000);
		Money SEK50 = new Money(5000, SEK);
		// if the snippet above is removed, throws InsuffcientFunds
        SweBank.transfer("Ulrika", Nordea, "Bob", SEK50); // <- AccountDoesNotExistException. updated to InsufficientFundsException (correct) if not preceded by the aforementioned snippet
        assertEquals(29250, SweBank.getBalance("Ulrika").intValue());
        assertEquals(750, Nordea.getBalance("Bob").intValue());
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		 Money SEK20 = new Money(2000, SEK);
	     SweBank.addTimedPayment("Bob", "payment1", 2, 1, SEK20, Nordea, "Bob"); // <- account.addTimedPayment(payid, interval, next, amount, tobank, toaccount);
	     SweBank.tick();
	     assertEquals(300, Nordea.getBalance("Bob").intValue());
	}
}
