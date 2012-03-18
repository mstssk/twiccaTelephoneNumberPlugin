package jp.mstssk.twiccaplugins.telnum;

/**
 * 電話番号
 * 
 * @author mstssk
 */
public class TelephoneNumber {

	/** 元文字列 */
	private String srcString;

	/** 整形済み電話番号 */
	private String formatedNumber;

	/**
	 * @param src 元文字列
	 * @param formated 整形済み電話番号 記号を含まない半角数字
	 */
	public TelephoneNumber(String src, String formated) {
		this.srcString = src;
		this.formatedNumber = formated;
	}

	/**
	 * 元文字列を取得します。
	 * 
	 * @return 元文字列
	 */
	public String getSrcString() {
		return srcString;
	}

	/**
	 * 元文字列を設定します。
	 * 
	 * @param srcString 元文字列
	 */
	public void setSrcString(String srcString) {
		this.srcString = srcString;
	}

	/**
	 * 整形済み電話番号を取得します。
	 * 
	 * @return 整形済み電話番号
	 */
	public String getFormatedNumber() {
		return formatedNumber;
	}

	/**
	 * 整形済み電話番号を設定します。
	 * 
	 * @param formatedNumber 整形済み電話番号
	 */
	public void setFormatedNumber(String formatedNumber) {
		this.formatedNumber = formatedNumber;
	}

	/**
	 * 出力例：
	 * 
	 * <pre>
	 * { extracted : ０９０ー１２３４ー５６７８, formated : 09012345678 }
	 * </pre>
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ extracted : ");
		sb.append(srcString);
		sb.append(", formated : ");
		sb.append(formatedNumber);
		sb.append(" }");
		return sb.toString();
	}

}
