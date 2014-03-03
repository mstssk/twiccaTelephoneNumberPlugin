package jp.mstssk.twiccaplugins.telnum;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

	/**
	 * 初期化
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();

		if (!isValidIntent(intent)) {
			Toast.makeText(this, R.string.empty_text, Toast.LENGTH_SHORT)
					.show();
			finish();
		}

		List<TelephoneNumber> list = TelephoneNumberExtractUtil.extract(intent.getStringExtra("user_name"));
		list.addAll(TelephoneNumberExtractUtil.extract(intent.getStringExtra(Intent.EXTRA_TEXT)));
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
	 * 適切な呼び出しかどうか判定
	 * 
	 * @param intent
	 * @return
	 */
	private boolean isValidIntent(final Intent intent) {
		if (intent == null) {
			return false;
		}
		final String text = intent.getStringExtra(Intent.EXTRA_TEXT);
		if (TextUtils.isEmpty(text)) {
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
		final TelephoneNumber number = getItem(position);
		callPriviledged(number);
		finish();
	}

	/**
	 * 発信
	 * 
	 * @param number
	 */
	private void callPriviledged(final TelephoneNumber number) {
		try {
			final Intent intent = IntentUtil
					.createCallActionViewIntent(number);
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			// もしエラーの場合は普通のACTION_DIAL
			Intent intent = IntentUtil.createDialIntent(number);
			startActivity(intent);
		}
	}

	/**
	 * 長押し選択。抽出した電話番号をどうするかサブメニュー表示
	 * 
	 * @param l
	 * @param v
	 * @param position
	 * @param id
	 * @return
	 */
	protected boolean onListItemLongClick(ListView l, View v, int position,
			long id) {
		final TelephoneNumber number = getItem(position);
		showNumberShareMenu(number);
		return true;
	}

	/**
	 * 共有メニューを表示
	 * 
	 * @param number
	 */
	private void showNumberShareMenu(final TelephoneNumber number) {
		new AlertDialog.Builder(this).setItems(R.array.share_menu,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case 0:
								// 普通に発信
								Intent dialIntent = IntentUtil
										.createDialIntent(number);
								startActivity(dialIntent);
								break;
							case 1:
								// CALL_PRIVILEGED
								callPriviledged(number);
								break;
							case 2:
								// 共有
								Intent shareIntent = IntentUtil
										.createTextShareIntent(number);
								startActivity(shareIntent);
								break;
							case 3:
								// 電話帳へ
								Intent contactInsertIntnet = IntentUtil
										.createContactInsertIntnet(number);
								startActivity(contactInsertIntnet);
								break;
							default:
								break;
						}
						finish();
					}
				}).show();
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