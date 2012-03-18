package jp.mstssk.twiccaplugins.telnum;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 電話番号プラグイン for twicca
 * 
 * @author mstssk
 */
public class TelephoneNumberActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();

		if (!isValidIntent(intent)) {
			Toast.makeText(this, R.string.empty_text, Toast.LENGTH_SHORT)
					.show();
			finish();
		}

		String text = intent.getStringExtra(Intent.EXTRA_TEXT);
		List<TelephoneNumber> list = TelephoneNumberExtractUtil.extract(text);
		if (list.isEmpty()) {
			Toast.makeText(this, android.R.string.emptyPhoneNumber,
					Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		setTitle(R.string.choice);
		TelephoneNumberListAdapter adapter = new TelephoneNumberListAdapter(
				this, list);
		setListAdapter(adapter);
	}

	/**
	 * {@inheritDoc} 長押し選択リスナの設定のためのオーバライド
	 */
	@Override
	public void onContentChanged() {
		super.onContentChanged();
		getListView().setOnItemLongClickListener(onLongClickListener);
	}

	/**
	 * 適切な呼び出しかどうか判定
	 * 
	 * @param intent
	 * @return
	 */
	private boolean isValidIntent(Intent intent) {
		if (intent == null) {
			return false;
		}
		String text = intent.getStringExtra(Intent.EXTRA_TEXT);
		if (text == null || text.length() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 選択。抽出した電話番号へのダイアル画面を表示する。
	 * 
	 * @param l
	 * @param v
	 * @param position
	 * @param id
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		TelephoneNumber number = getItem(position);
		throwDialIntent(number);
		finish();
	}

	/**
	 * 長押し選択。抽出した電話番号をテキストとして共有する。
	 * 
	 * @param l
	 * @param v
	 * @param position
	 * @param id
	 * @return
	 */
	protected boolean onListItemLongClick(ListView l, View v, int position,
			long id) {
		TelephoneNumber number = getItem(position);
		throwTextShareIntent(number);
		finish();
		return true;
	}

	/**
	 * ダイアル画面を表示
	 * 
	 * @param number
	 */
	protected void throwDialIntent(TelephoneNumber number) {
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
				+ number.getFormatedNumber()));
		startActivity(intent);
	}

	/**
	 * 電話番号をテキストとして共有
	 * 
	 * @param number
	 */
	protected void throwTextShareIntent(TelephoneNumber number) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, number.getFormatedNumber());
		startActivity(intent);
	}

	/**
	 * 選択した{@link TelephoneNumber}を取得
	 * 
	 * @param position
	 * @return
	 */
	protected TelephoneNumber getItem(int position) {
		return (TelephoneNumber) getListAdapter().getItem(position);
	}

	/** 経由用長押しリスナ */
	private AdapterView.OnItemLongClickListener onLongClickListener = new AdapterView.OnItemLongClickListener() {
		public boolean onItemLongClick(AdapterView<?> parent, View v,
				int position, long id) {
			return onListItemLongClick((ListView) parent, v, position, id);
		}
	};
}