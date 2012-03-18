package jp.mstssk.twiccaplugins.telnum;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * {@link TelephoneNumber}用の{@link android.widget.Adapter Adapter}
 * 
 * @author mstssk
 */
public class TelephoneNumberListAdapter extends BaseAdapter {

	private LayoutInflater inflater;

	private List<TelephoneNumber> list;

	public TelephoneNumberListAdapter(Context context,
			List<TelephoneNumber> list) {
		this.list = list;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public TelephoneNumber getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = initView(convertView, parent);

		TelephoneNumber item = getItem(position);

		TextView text1 = extractViewById(view, android.R.id.text1);
		TextView text2 = extractViewById(view, android.R.id.text2);
		text1.setText(item.getFormatedNumber());
		text2.setText(item.getSrcString());

		return view;
	}

	/**
	 * viewを初期化 <br>
	 * 初期化済みなら何もしない
	 * 
	 * @param convertView
	 * @param parent
	 * @return
	 */
	private View initView(View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = inflater.inflate(android.R.layout.simple_list_item_2,
					parent, false);
		} else {
			view = convertView;
		}
		return view;
	}

	/**
	 * 型キャスト記述を省略できるだけのfindViewById
	 * 
	 * @param view
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T extends View> T extractViewById(final View view, final int id) {
		return (T) view.findViewById(id);
	}

}
