package jp.mstssk.twiccaplugins.telnum;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文字列中から電話番号っぽいのを抽出
 * 
 * @author mstssk
 */
public class TelephoneNumberExtractUtil {

	/** 最小桁数 */
	private static final int MIN_LENGTH = 9;

	/** 電話番号っぽい文字列の正規表現（最小9桁） */
	private static final Pattern PATTERN_TEL_NUM = Pattern
			.compile("[0-9０-９](((?<![-−‐―一━ー─－　\\s])[-−‐―一━ー─－　\\s])|[0-9０-９]){8,}");

	/** 単純な数字の正規表現 */
	private static final Pattern PATTERN_NUM_CHAR = Pattern.compile("[0-9０-９]");

	/**
	 * テキストから電話番号っぽい文字列を抽出・整形
	 * 
	 * @param src テキスト (NotNull)
	 * @return 抽出した電話番号の一覧 (NotNull)
	 */
	public static List<TelephoneNumber> extract(final String src) {
		final List<TelephoneNumber> list = new ArrayList<TelephoneNumber>();
		final Matcher matcher = PATTERN_TEL_NUM.matcher(src);
		while (matcher.find()) {
			String matched = matcher.group();
			String formated = format(matched);
			// 区切り文字を取り除いた数字だけでの桁数が9以上でなければならない
			if (formated.length() >= MIN_LENGTH) {
				TelephoneNumber telephoneNumber = new TelephoneNumber(matched,
						formated);
				list.add(telephoneNumber);
			}
		}
		return list;
	}

	/**
	 * 半角数字に整形
	 * 
	 * @param str
	 * @return
	 */
	protected static String format(final String str) {
		StringBuilder sb = new StringBuilder();
		Matcher matcher = PATTERN_NUM_CHAR.matcher(str);
		while (matcher.find()) {
			String matched = matcher.group();
			String num = multiWidth2halfWidth(matched);
			sb.append(num);
		}
		return sb.toString();
	}

	/**
	 * 全角数字→半角数字 <br>
	 * 関係ない文字はそのまま
	 * 
	 * @param str
	 * @return
	 */
	protected static String multiWidth2halfWidth(String str) {
		StringBuilder sb = new StringBuilder();
		char[] charArray = str.toCharArray();
		for (char c : charArray) {
			if (c >= '０' && c <= '９') {
				char encoded = (char) (c - '０' + '0');
				sb.append(encoded);
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
