package pt.ulisboa.tecnico.learnjava.sibs.sibs;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.learnjava.bank.services.Services;
import pt.ulisboa.tecnico.learnjava.sibs.domain.Sibs;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.OperationException;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.SibsException;

public class GetNumberOfOperationsMethodTest {

	private static final String TARGET_IBAN = "TargetIban";
	private static final String SOURCE_IBAN = "SourceIban";
	private static final int VALUE = 10;
	
	private Sibs sibs;
	
	@Before // == BeforeEach
	public void setUp() {
		sibs = new Sibs(3, new Services());
	}
	
	@Test
	public void GivenEmptyOperationsListReturnZero() {
		int result = sibs.getNumberOfOperations();
		assertEquals(0, result);
	}
	
	@Test
	public void GivenFullOperationsListReturnMaxSize() throws OperationException, SibsException {
		sibs.addOperation(TARGET_IBAN, SOURCE_IBAN, VALUE);
		sibs.addOperation(TARGET_IBAN, SOURCE_IBAN, VALUE);
		sibs.addOperation(TARGET_IBAN, SOURCE_IBAN, VALUE);		
		int result = sibs.getNumberOfOperations();
		assertEquals(3, result);
	}
	
	@Test
	public void GivenSomeOperationListReturnSize() throws OperationException, SibsException {
		sibs.addOperation(TARGET_IBAN, SOURCE_IBAN, VALUE);
		sibs.addOperation(TARGET_IBAN, SOURCE_IBAN, VALUE);
		int result = sibs.getNumberOfOperations();
		assertEquals(2, result);
	}
	
}