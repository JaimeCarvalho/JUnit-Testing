package pt.ulisboa.tecnico.learnjava.sibs.sibs;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.learnjava.bank.services.Services;
import pt.ulisboa.tecnico.learnjava.sibs.domain.Sibs;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.OperationException;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.SibsException;

public class AddOperationMethodTest {

	private static final String TARGET_IBAN = "TargetIban";
	private static final String SOURCE_IBAN = "SourceIban";
	private static final int VALUE = 100;
	
	private Sibs sibs;

	@Before
	public void setUp() throws OperationException, SibsException {
		this.sibs = new Sibs(3, new Services());
	}
	
	@Test
	public void givenValidParametersAndEmptyOperationsListReturnIndex() throws OperationException, SibsException {
		int result = sibs.addOperation(SOURCE_IBAN, TARGET_IBAN, VALUE);
		assertEquals(0, result);
	}
	
	@Test
	public void givenValidParametersAndSomeOperationsListReturnIndex() throws OperationException, SibsException {
		sibs.addOperation(SOURCE_IBAN, TARGET_IBAN, VALUE);
		int result = sibs.addOperation(SOURCE_IBAN, TARGET_IBAN, VALUE);
		assertEquals(1, result);
	}

	@Test(expected = SibsException.class)
	public void givenValidParametersAndFullOperationsListReturnIndex() throws OperationException, SibsException {
		sibs.addOperation(SOURCE_IBAN, TARGET_IBAN, VALUE);
		sibs.addOperation(SOURCE_IBAN, TARGET_IBAN, VALUE);
		sibs.addOperation(SOURCE_IBAN, TARGET_IBAN, VALUE);
		sibs.addOperation(SOURCE_IBAN, TARGET_IBAN, VALUE);
	}
	
	
	@Test(expected = OperationException.class)
	public void nullTargetIban() throws OperationException, SibsException {
		sibs.addOperation(SOURCE_IBAN, null, VALUE);
	}

	@Test(expected = OperationException.class)
	public void nullSourceIban() throws OperationException, SibsException {
		sibs.addOperation(null, TARGET_IBAN, VALUE);
	}
	
	@Test(expected = OperationException.class)
	public void lengthZeroTargetIban() throws OperationException, SibsException {
		sibs.addOperation(SOURCE_IBAN, "", VALUE);
	}

	@Test(expected = OperationException.class)
	public void lengthZeroSourceIban() throws OperationException, SibsException {
		sibs.addOperation("", TARGET_IBAN, VALUE);
	}

	@Test(expected = OperationException.class)
	public void zeroValue() throws OperationException, SibsException {
		sibs.addOperation(SOURCE_IBAN, TARGET_IBAN, 0);
	}

	@Test(expected = OperationException.class)
	public void negativeValue() throws OperationException, SibsException {
		sibs.addOperation(SOURCE_IBAN, TARGET_IBAN, -4);
	}
	
}
