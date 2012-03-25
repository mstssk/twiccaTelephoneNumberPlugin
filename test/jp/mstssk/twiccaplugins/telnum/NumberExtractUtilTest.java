package jp.mstssk.twiccaplugins.telnum;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

public class NumberExtractUtilTest {

	Logger logger = Logger.getAnonymousLogger();

	@Test
	public void test() throws Exception {

		// 数値が含まれないテキスト
		assertNotExtract("");
		assertNotExtract("陽の光を借りて照る大いなる月たらんよりも、自ら光を放つ小さき灯たれ。");
		assertNotExtract("All your base belong to us.");
		assertNotExtract("くぁｗせｄｒｆｔｇｙふじこｌｐ；＠：「」");

		// 9桁未満の番号
		assertNotExtract("110"); // 緊急通報番号とかはツイートから抽出する必要ないよね
		assertNotExtract("1");
		assertNotExtract("12345678");
		assertNotExtract("2011-03-11");

		// 番号単体
		assertExtract("090-1234-5678");
		assertExtract("0120-123-45678");
		assertExtract("０９０ー１２３４ー５６７８");
		assertExtract("０９０ー５６７８-１２３４");

		// 考慮外の数値フォーマット
		assertNotExtract("090=1234=5678");
		assertNotExtract("090=1234-5678");
		assertNotExtract("090-1234--5678");
		assertNotExtract("090  1234  5678");
		assertNotExtract("０９０ーー１２３４ー５６７８");
		assertNotExtract("〇九〇一二三四五六七八九"); // 漢数字まで面倒見きれん

		// なんか文章
		assertExtract("ほげほあふぁｋｌ090-1234-5678ｊｆｋ０９０ー９８７５-３５ｌさｊ1,23,456,789ふぁ");
		assertExtract("一括0円つまり基本料金無料なので月々ユニバーサルサービス料金5円のみ！TEL044-123-4567お見逃しなく！");
		assertExtract("ギャラクシーネクサスFOMAからFOMAでなんと一括0円の超大特価！！台数は4台のみになります♪03－0123－4567取り置き申し込みはお早めに");

		// ぬるぽ
		try {
			TelephoneNumberExtractUtil.extract(null);
		} catch (NullPointerException e) {
			assertNotNull(e);
		}

	}

	private void assertExtract(String text) {
		List<TelephoneNumber> list = TelephoneNumberExtractUtil.extract(text);
		assertListNotEmpy(list);
		log("'" + text + "' has " + list.size() + " numbers.");
		for (TelephoneNumber telephoneNumber : list) {
			assertNotNull(telephoneNumber);
			log(telephoneNumber.toString());
		}
	}

	private void assertListNotEmpy(List<?> list) {
		if (list == null || list.isEmpty()) {
			fail();
		} else {
			assertFalse(list.isEmpty());
		}
	}

	private void assertNotExtract(String text) {
		List<TelephoneNumber> list = TelephoneNumberExtractUtil.extract(text);
		if (!list.isEmpty()) {
			String msg = "'" + text + "' should be has no number, "
					+ "But below numbers are extracted!";
			log(msg);
			for (TelephoneNumber telephoneNumber : list) {
				log(telephoneNumber.toString());
			}
			fail();
		} else {
			assertTrue(list.isEmpty());
		}
	}

	private void log(String msg) {
		logger.log(Level.INFO, msg);
	}
}
