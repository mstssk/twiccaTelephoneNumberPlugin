package jp.mstssk.twiccaplugins.telnum;

import android.content.Intent;
import android.net.Uri;
import android.provider.Contacts;

/**
 * 各種Intentの生成Util
 * 
 * @author mstssk
 */
public class IntentUtil {

	/** Intent.ACTION_CALL_PRIVILEGED の代替 */
	private static final String INTENT_ACTION_CALL_PRIVILEGED = "android.intent.action.CALL_PRIVILEGED";

	/**
	 * 通話Intent<br>
	 * Skypeなどにも引っかかる。<br>
	 * API Level 7 未満では動かない筈…
	 * 
	 * @param number
	 */
	public static Intent createPriviledgedCallIntent(
			final TelephoneNumber number) {
		Uri phoneNumberUri = createTelephoneNumberUri(number);
		Intent intent = new Intent(INTENT_ACTION_CALL_PRIVILEGED,
				phoneNumberUri);
		return intent;
	}

	/**
	 * ダイアル画面を表示するIntent
	 * 
	 * @param number
	 * @return
	 */
	public static Intent createDialIntent(final TelephoneNumber number) {
		Uri phoneNumberUri = createTelephoneNumberUri(number);
		Intent intent = new Intent(Intent.ACTION_DIAL, phoneNumberUri);
		return intent;
	}

	/**
	 * 電話番号をテキストとして共有するIntent
	 * 
	 * @param number
	 */
	public static Intent createTextShareIntent(final TelephoneNumber number) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, number.getFormatedNumber());
		return Intent.createChooser(intent, number.getFormatedNumber());
	}

	/**
	 * 電話番号を電話帳に登録するIntent
	 * 
	 * @param number
	 */
	public static Intent createContactInsertIntnet(final TelephoneNumber number) {
		Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
		intent.setType(Contacts.People.CONTENT_ITEM_TYPE);
		intent.putExtra(Contacts.Intents.Insert.PHONE,
				number.getFormatedNumber());
		return intent;
	}

	/**
	 * tel:12345678 を生成
	 * 
	 * @param number
	 * @return
	 */
	public static Uri createTelephoneNumberUri(final TelephoneNumber number) {
		return Uri.parse("tel:" + number.getFormatedNumber());
	}
}
