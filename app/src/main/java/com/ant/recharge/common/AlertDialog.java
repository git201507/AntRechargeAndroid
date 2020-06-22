package com.ant.recharge.common;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.ant.recharge.R;


public class AlertDialog extends Dialog {
	public static final int BUTTON_POSITIVE = 1;
	public static final int BUTTON_NEGATIVE = 2;
	public static final int BUTTON_SINGLE_NEUTRAL = 4;

	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private LinearLayout mMainView;
	private LayoutParams mMainViewlp;

	private LinearLayout mTitleLayout;
	private ImageView mMiddleLine;
	private FrameLayout mBodyLayout;
	private LinearLayout mBtnLayout;
	private LinearLayout mSingleBtnLayout;

	private TextView mTitleTxt;
	private TextView mMessageTxt;
	private Button mConfirmBtn;
	private Button mCancelBtn;
	private Button mNeutralSingleBtn;

	private int mButtonNum = 2;

	private CancelAction cancelAction;
	private boolean touchable = true;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(mMainView, mMainViewlp);
		DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.dimAmount = 0.25f;
		lp.alpha = 1.0f;
		lp.width = (int) (dm.widthPixels * 0.8);
		getWindow().setAttributes(lp);
		Window window = this.getWindow();
		window.setGravity(Gravity.CENTER);
		setCanceledOnTouchOutside(touchable);

	}

	public AlertDialog(Context context) {
		super(context);
		mContext = context;
		initData();
		initView();
	}

	public AlertDialog(Context context, int buttonNum) {
		super(context);
		mContext = context;
		mButtonNum = buttonNum;
		initData();
		initView();
	}

	public AlertDialog(Context context, int buttonNum, boolean touchable) {
		super(context);
		this.touchable = touchable;
		mContext = context;
		mButtonNum = buttonNum;
		initData();
		initView();
	}

	public void initData() {
		mLayoutInflater = LayoutInflater.from(mContext);
		mMainViewlp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
	}

	private void initView() {
		mMainView = (LinearLayout) mLayoutInflater.inflate(
				R.layout.view_alertdialog, null);
		mTitleLayout = (LinearLayout) mMainView.findViewById(R.id.title_layout);
		mMiddleLine = (ImageView) mMainView.findViewById(R.id.line_layout);
		mBodyLayout = (FrameLayout) mMainView.findViewById(R.id.content_layout);
		mBtnLayout = (LinearLayout) mMainView.findViewById(R.id.buttons_layout);
		mSingleBtnLayout = (LinearLayout) mMainView
				.findViewById(R.id.singlebtn_layout);
		mMessageTxt = (TextView) mMainView.findViewById(R.id.message);
		mConfirmBtn = (Button) mMainView.findViewById(R.id.button_ok);
		mCancelBtn = (Button) mMainView.findViewById(R.id.button_cancel);
		mTitleTxt = (TextView) mMainView.findViewById(R.id.title);
		mNeutralSingleBtn = (Button) mMainView.findViewById(R.id.single_btn_ok);

		mTitleLayout.setVisibility(View.GONE);
		mTitleTxt.setVisibility(View.GONE);
		mMiddleLine.setVisibility(View.GONE);
		mMessageTxt.setVisibility(View.GONE);
		mBodyLayout.setVisibility(View.GONE);
		mConfirmBtn.setVisibility(View.GONE);
		mCancelBtn.setVisibility(View.GONE);
		mSingleBtnLayout.setVisibility(View.GONE);
	}

	public AlertDialog setCustomTitle(View view) {
		mTitleLayout.setVisibility(View.VISIBLE);
		mTitleLayout.addView(view, 0, mMainViewlp);
		return this;
	}

	public AlertDialog setTitleViewPadding(int left, int top, int right,
			int bottom) {
		mTitleLayout.setPadding(left, top, right, bottom);
		return this;
	}

	public AlertDialog setTitleTxt(int textId) {
		mTitleLayout.setVisibility(View.VISIBLE);
		mTitleTxt.setVisibility(View.VISIBLE);
		mTitleTxt.setText(textId);
		return this;
	}

	public AlertDialog setTitleTxtColor(int textId) {
		mTitleTxt.setTextColor(mContext.getResources().getColor(
				R.color.black_overlay));
		mTitleLayout.setGravity(Gravity.LEFT);
		mTitleTxt.setPadding(0, 18, 0, 18);
		return this;
	}

	public AlertDialog setTitleTxt(String text) {
		mTitleLayout.setVisibility(View.VISIBLE);
		mTitleTxt.setVisibility(View.VISIBLE);
		mTitleTxt.setText(text);
		return this;
	}

	public AlertDialog setView(View pView) {
		mBodyLayout.setVisibility(View.VISIBLE);
		mBodyLayout.addView(pView);
		return this;
	}

	public AlertDialog setCustomMessagePadding(int left, int top,
			int right, int bottom) {
		mMessageTxt.setPadding(left, top, right, bottom);
		return this;
	}

	public AlertDialog setCustomMessage(String mess) {
		mMessageTxt.setVisibility(View.VISIBLE);
		mMessageTxt.setText(mess);
		return this;
	}

	public AlertDialog setCustomMessage(String mess, int height) {
		mMessageTxt.setVisibility(View.VISIBLE);
		mMessageTxt.setText(mess);
		mMessageTxt.setGravity(Gravity.CENTER_VERTICAL);
		mMessageTxt.setLayoutParams(new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT, height));
		return this;
	}

	public AlertDialog setCustomMessage(int mess) {
		mMessageTxt.setVisibility(View.VISIBLE);
		mMessageTxt.setText(mess);
		return this;
	}

	public AlertDialog setCustomMessageLineSpace(float add, float mult) {
		mMessageTxt.setLineSpacing(add, mult);
		return this;
	}

	public AlertDialog setCustomMessage(int mess, int textId) {
		mMessageTxt.setTextColor(mContext.getResources().getColor(textId));
		mMessageTxt.setVisibility(View.VISIBLE);
		mMessageTxt.setText(mess);
		return this;
	}

	public void setContentMarginTop(int top) {
		LayoutParams lp = (LayoutParams) mBodyLayout.getLayoutParams();
		lp.setMargins(0, top, 0, 0);
		mBodyLayout.setLayoutParams(lp);
	}

	public void setButtonMarginTop(int top) {
		LayoutParams lp = (LayoutParams) mBtnLayout.getLayoutParams();
		lp.setMargins(0, top, 0, 0);
		mBtnLayout.setLayoutParams(lp);
	}

	public void setButtonMarginBottom(int bottom) {
		LayoutParams lp = (LayoutParams) mBtnLayout.getLayoutParams();
		lp.setMargins(0, 15, 0, bottom);
		mBtnLayout.setLayoutParams(lp);
	}

	public AlertDialog setPositiveButton(int textId,
			View.OnClickListener listener) {
		return setPositiveButton(getContext().getString(textId), listener);
	}

	public AlertDialog setPositiveButton(String text,
			View.OnClickListener listener) {
		if (text == null || "".equals(text))
			text = mContext.getResources().getString(R.string.action_confirm);
		mBtnLayout.setVisibility(View.VISIBLE);
		switch (mButtonNum) {
		case 1:
			mSingleBtnLayout.setVisibility(View.VISIBLE);
			mNeutralSingleBtn.setText(text);
			mNeutralSingleBtn.setOnClickListener(listener);
			mBtnLayout.setVisibility(View.GONE);
			break;
		case 2:
			mConfirmBtn.setVisibility(View.VISIBLE);
			mConfirmBtn.setText(text);
			mConfirmBtn.setOnClickListener(listener);
			mSingleBtnLayout.setVisibility(View.GONE);
			mCancelBtn.setVisibility(View.VISIBLE);
			break;
		}
		return this;
	}

	public AlertDialog setNegativeButton(int textId,
			View.OnClickListener listener) {
		return setNegativeButton(getContext().getString(textId), listener);
	}

	public AlertDialog setNegativeButton(String text,
			View.OnClickListener listener) {
		if (text == null || "".equals(text))
			text = mContext.getResources().getString(R.string.action_cancel);
		mBtnLayout.setVisibility(View.VISIBLE);
		switch (mButtonNum) {
		case 1:
			mSingleBtnLayout.setVisibility(View.VISIBLE);
			mNeutralSingleBtn.setText(text);
			mNeutralSingleBtn.setOnClickListener(listener);
			mBtnLayout.setVisibility(View.GONE);
			break;
		case 2:
			mCancelBtn.setVisibility(View.VISIBLE);
			mCancelBtn.setText(text);
			mCancelBtn.setOnClickListener(listener);
			mSingleBtnLayout.setVisibility(View.GONE);
			mConfirmBtn.setVisibility(View.VISIBLE);
			break;
		}
		return this;
	}

	public AlertDialog setNeutralButton(int textId,
			View.OnClickListener listener) {
		mSingleBtnLayout.setVisibility(View.GONE);
		mBtnLayout.setVisibility(View.VISIBLE);
		mConfirmBtn.setVisibility(View.VISIBLE);
		mCancelBtn.setVisibility(View.VISIBLE);
		return this;
	}

	public Button getButton(int buttonId) {
		switch (buttonId) {
		case BUTTON_POSITIVE:
			return mConfirmBtn;
		case BUTTON_NEGATIVE:
			return mCancelBtn;
		case BUTTON_SINGLE_NEUTRAL:
			return mNeutralSingleBtn;
		}
		return null;
	}

	public void setButton(int buttonId, String text,
			View.OnClickListener listener) {
		switch (buttonId) {
		case BUTTON_POSITIVE:
			mBtnLayout.setVisibility(View.VISIBLE);
			mConfirmBtn.setVisibility(View.VISIBLE);
			mConfirmBtn.setText(text);
			mConfirmBtn.setOnClickListener(listener);
			break;
		case BUTTON_NEGATIVE:
			mBtnLayout.setVisibility(View.VISIBLE);
			mCancelBtn.setVisibility(View.VISIBLE);
			mCancelBtn.setText(text);
			mCancelBtn.setOnClickListener(listener);
			break;
		case BUTTON_SINGLE_NEUTRAL:
			mSingleBtnLayout.setVisibility(View.VISIBLE);
			mNeutralSingleBtn.setText(text);
			mNeutralSingleBtn.setOnClickListener(listener);
			break;
		}
	}

	private int getScreenWidth() {
		DisplayMetrics dm = mContext.getApplicationContext().getResources()
				.getDisplayMetrics();
		if (dm.widthPixels >= dm.heightPixels) {
			return dm.heightPixels;
		} else {
			return dm.widthPixels;
		}
	}

	public void setLayoutParams(int width) {
		mMainViewlp.width = width;
	}

	public void show() {
		try {
			if (mTitleLayout.getVisibility() == View.VISIBLE
					&& (mMessageTxt.getVisibility() == View.VISIBLE || mBodyLayout
							.getVisibility() == View.VISIBLE)) {
				mMiddleLine.setVisibility(View.VISIBLE);
			}
			super.show();
		} catch (Exception e) {
			e.printStackTrace();
			dismiss();
		}

	}

	public void cleanData() {
		mMainView = null;
		mMainViewlp = null;
		mTitleLayout = null;
		mTitleTxt = null;
		mMiddleLine = null;
		mMessageTxt = null;
		mBodyLayout = null;
		mBtnLayout = null;
		mConfirmBtn = null;
		mCancelBtn = null;
		mSingleBtnLayout = null;
		mNeutralSingleBtn = null;
	}

	public void clear() {
		mContext = null;
		mLayoutInflater = null;
		touchable = true;
		if (mMainView != null) {
			mMainView.removeAllViews();
			mMainView.setBackgroundDrawable(null);
			mMainView = null;
		}
		mMainViewlp = null;
		if (mTitleLayout != null) {
			mTitleLayout.removeAllViews();
			mTitleLayout.setBackgroundDrawable(null);
			mTitleLayout = null;
		}
		if (mMiddleLine != null) {
			mMiddleLine.setBackgroundDrawable(null);
			mMiddleLine.setImageDrawable(null);
			mMiddleLine = null;
		}
		if (mBodyLayout != null) {
			mBodyLayout.removeAllViews();
			mBodyLayout.setBackgroundDrawable(null);
			mBodyLayout = null;
		}
		if (mBtnLayout != null) {
			mBtnLayout.setBackgroundDrawable(null);
			mBtnLayout.removeAllViews();
			mBtnLayout = null;
		}
		if (mSingleBtnLayout != null) {
			mSingleBtnLayout.removeAllViews();
			mSingleBtnLayout.setBackgroundDrawable(null);
			mSingleBtnLayout = null;
		}
		mTitleTxt = null;
		mMessageTxt = null;
		if (mConfirmBtn != null) {
			mConfirmBtn.setBackgroundDrawable(null);
			mConfirmBtn = null;
		}
		if (mCancelBtn != null) {
			mCancelBtn.setBackgroundDrawable(null);
			mCancelBtn = null;
		}
		if (mNeutralSingleBtn != null) {
			mNeutralSingleBtn.setBackgroundDrawable(null);
			mNeutralSingleBtn = null;
		}
	}

	public void setCancelAction(CancelAction cancelAction) {
		this.cancelAction = cancelAction;
	}

	public interface CancelAction {
		public void cancel();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (null != cancelAction) {
				cancelAction.cancel();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN
				&& isOutOfBounds(event)) {
			if (null != cancelAction) {
				cancelAction.cancel();
			}
		}
		return super.onTouchEvent(event);
	}

	private boolean isOutOfBounds(MotionEvent event) {
		final int x = (int) event.getX();
		final int y = (int) event.getY();
		final int slop = ViewConfiguration.get(mContext)
				.getScaledWindowTouchSlop();
		final View decorView = getWindow().getDecorView();
		return (x < -slop) || (y < -slop)
				|| (x > (decorView.getWidth() + slop))
				|| (y > (decorView.getHeight() + slop));
	}
}