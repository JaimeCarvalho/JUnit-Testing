package pt.ulisboa.tecnico.learnjava.sibs.operation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.learnjava.sibs.domain.Operation;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.OperationException;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.SibsException;

public class ComissionMethodTest {
	private static final String SOURCE_IBAN = "SourceIban";
	private static final String TARGET_IBAN = "TargetIban";
	private static final int VALUE = 100;
	private static final int ID = 1;
	
	private Operation op;
	
	@Before
	public void setUp() throws OperationException, SibsException {
		op = new Operation(SOURCE_IBAN, TARGET_IBAN, VALUE, ID);
	}
	
	@Test
	public void success() {
		int result = op.commission();
		assertEquals(2, result);
	}
	
}
