package pt.ulisboa.tecnico.learnjava.sibs.operation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.ulisboa.tecnico.learnjava.sibs.domain.Operation;
import pt.ulisboa.tecnico.learnjava.sibs.domain.Registered;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.OperationException;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.SibsException;

public class OperationConstructorMethodTest {
	private static final String SOURCE_IBAN = "SourceIban";
	private static final String TARGET_IBAN = "TargetIban";
	private static final int VALUE = 100;
	private static final int ID = 1;

	@Test
	public void success() throws OperationException {
		Operation operation = new Operation(SOURCE_IBAN, TARGET_IBAN, VALUE, ID);

		assertEquals(VALUE, operation.getValue());
		assertEquals(SOURCE_IBAN, operation.getSourceIban());
		assertEquals(TARGET_IBAN, operation.getTargetIban());
		assertEquals(true, operation.getState() instanceof Registered);
		assertEquals(ID, operation.getId());
	}

	@Test(expected = OperationException.class)
	public void nullSourceIban() throws OperationException {
		new Operation(null, TARGET_IBAN, VALUE, ID);
	}

	@Test(expected = OperationException.class)
	public void emptyTargetIban() throws OperationException {
		new Operation(SOURCE_IBAN, "", VALUE, ID);
	}

	@Test(expected = OperationException.class)
	public void emptySourceIban() throws OperationException {
		new Operation("", TARGET_IBAN, VALUE, ID);
	}

	@Test(expected = OperationException.class)
	public void nullTargetIban() throws OperationException {
		new Operation(SOURCE_IBAN, null, VALUE, ID);
	}
	
	@Test(expected = OperationException.class)
	public void zeroValue() throws OperationException, SibsException {
		new Operation(SOURCE_IBAN, TARGET_IBAN, 0, ID);
	}

	@Test(expected = OperationException.class)
	public void negativeValue() throws OperationException, SibsException {
		new Operation(SOURCE_IBAN, TARGET_IBAN, -4, ID);
	}
	
}
