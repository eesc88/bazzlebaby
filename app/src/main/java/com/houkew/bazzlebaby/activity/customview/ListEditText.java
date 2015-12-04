package com.houkew.bazzlebaby.activity.customview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
/**
 * �Զ����б������
 * @author Administrator
 *
 */
/**
 * 自定义列表输入框
 * @author Administrator
 *
 */
public class ListEditText {
	private Context context;
	private LinearLayout fatherView;
	private int etId=0;
	private Map<String, EditText> meditText;
	private List<Integer> childrenViewIdexs;

	public ListEditText(Context context, LinearLayout fatherView) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.fatherView = fatherView;
		meditText=new HashMap<String, EditText>();
		childrenViewIdexs=new ArrayList<Integer>();
	}

	/**
	 * 添加医生姓名
	 */
	public void addDoctorName() {
		final LinearLayout ly = new LinearLayout(context);
		ly.setId(etId);
		ly.setOrientation(LinearLayout.HORIZONTAL);
		final Button bn = new Button(context);
		bn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int lyId = ly.getId();
				deleteChidrenView(lyId);
			}
		});
		bn.setText("联系人:");
		bn.setId(etId);
		EditText et = new EditText(context);
		et.setInputType(InputType.TYPE_CLASS_NUMBER);
		et.setHint("请输入联系人姓名");
		et.setId(etId);
		et.setFocusableInTouchMode(true);
		ly.addView(bn);
		ly.addView(et);
		fatherView.addView(ly);
		meditText.put(etId + "", et);
		childrenViewIdexs.add(etId++);
		Log.i("sys", "添加到的位置：" + fatherView.getChildCount());
		Log.i("sys", "该位置的布局ID值:" + ly.getId());
		Log.i("sys",
				"该位置的集合ID值:"
						+ childrenViewIdexs.get(fatherView.getChildCount() - 1));
	}

	/**
	 * 删除子view
	 *
	 * @param lyId
	 */
	private void deleteChidrenView(int lyId) {
		Log.i("sys", "删除的ID:" + lyId);
		meditText.remove("" + lyId);
		int index = 0;

		for (int i = 0; i < childrenViewIdexs.size(); i++) {
			Log.i("sys", "正在查找..--ID:" + childrenViewIdexs.get(i) + "--lyId="
					+ lyId + "--i:" + i);
			if (lyId == childrenViewIdexs.get(i)) {
				index = i;
				Log.i("sys", "已获取到值--index=" + index);
				break;
			}
		}

		int cont = fatherView.getChildCount();
		Log.i("sys", "View的个数:--cont=" + cont);
		if (cont >= index) {
			Log.i("sys", "将要删除个位置--index:" + index);
			childrenViewIdexs.remove(index);
			fatherView.removeViewAt(index);
		} else {
			Log.e("sys", "cont<index");
		}
	}

	/**
	 * 获取输入的医生姓名
	 */
	public String getEditText() {
		String value="";
		Set<String> keySet = meditText.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			String editValue=meditText.get(string).getText().toString();
			Log.i("sys", "EditText.getValue()-->"+ editValue+"||EditText.getId()-->" + meditText.get(string).getId());
			value=value+editValue+",";
			Log.i("sys", "value-->" + value);
		}
		return value;
	}
}

