package jp.mstssk.twiccaplugins.telnum;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

public class NumberExtractUtilTest {

	Logger logger = Logger.getLogger(NumberExtractUtilTest.class
			.getSimpleName());

	@Test
	public void test() throws Exception {

		List<TelephoneNumber> list = TelephoneNumberExtractUtil
				.extract("ほげほあふぁｋｌ090-1234-5678ｊｆｋ０９０ー９８７５-３５ｌさｊ1,23,456,789ふぁ");

		boolean extracted = false;
		assertNotNull(list);
		for (TelephoneNumber telephoneNumber : list) {
			assertNotNull(telephoneNumber);
			logger.log(Level.INFO, telephoneNumber.toString());
			extracted = true;
		}

		assertTrue(extracted);

	}

}
