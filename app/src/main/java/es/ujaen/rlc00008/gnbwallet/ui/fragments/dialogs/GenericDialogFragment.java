package es.ujaen.rlc00008.gnbwallet.ui.fragments.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseDialogFragment;

/**
 * Created by Ricardo on 31/5/16.
 */
public class GenericDialogFragment extends BaseDialogFragment {

	public interface GenericDialogListener {

		void genericDialogCancel();

		void genericDialogLeftClick();

		void genericDialogRightClick();
	}

	private GenericDialogListener mCallback;

	private int iconResId;
	private String message;
	private String leftButtonText;
	private String rightButtonText;

	@BindView(R.id.generic_icon_imageview) ImageView iconImageView;
	@BindView(R.id.generic_message_textview) TextView messageTextView;
	@BindView(R.id.generic_left_button) Button leftButton;
	@BindView(R.id.generic_right_button) Button rightButton;

	public static GenericDialogFragment newInstance(int iconResId, String message) {
		GenericDialogFragment fragment = new GenericDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("iconResId", iconResId);
		bundle.putString("message", message);
		fragment.setArguments(bundle);
		return fragment;
	}

	public static GenericDialogFragment newInstance(int iconResId, String message, String buttonText) {
		GenericDialogFragment fragment = new GenericDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("iconResId", iconResId);
		bundle.putString("message", message);
		bundle.putString("rightButtonText", buttonText);
		fragment.setArguments(bundle);
		return fragment;
	}

	public static GenericDialogFragment newInstance(int iconResId, String message, String leftButtonText, String rightButtonText) {
		GenericDialogFragment fragment = new GenericDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("iconResId", iconResId);
		bundle.putString("message", message);
		bundle.putString("leftButtonText", leftButtonText);
		bundle.putString("rightButtonText", rightButtonText);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onAttach(Context context) {
		try {
			if(getParentFragment() instanceof GenericDialogListener) {
				mCallback = (GenericDialogListener) getParentFragment();
			} else {
				mCallback = (GenericDialogListener) context;
			}
		} catch (ClassCastException e) {
			throw new RuntimeException(getParentFragment() + " must implement GenericDialogListener!");
		}
		super.onAttach(context);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		iconResId = getArguments().getInt("iconResId");
		message = getArguments().getString("message");
		leftButtonText = getArguments().getString("leftButtonText");
		rightButtonText = getArguments().getString("rightButtonText");
	}

	@Override
	public boolean backPressed() {
		mCallback.genericDialogCancel();
		return true;
	}

	@Override
	protected int getContentView() {
		return R.layout.fragment_dialog_generic;
	}

	@Override
	protected void prepareInterface(View mainView) {

		if (iconResId > 0) {
			iconImageView.setImageResource(iconResId);
			iconImageView.setVisibility(View.VISIBLE);
		} else {
			iconImageView.setVisibility(View.GONE);
		}

		if (leftButtonText != null) {
			leftButton.setText(leftButtonText);
			leftButton.setVisibility(View.VISIBLE);
		} else {
			leftButton.setVisibility(View.GONE);
		}

		if (rightButtonText != null) {
			rightButton.setText(rightButtonText);
		}

		messageTextView.setText(message);
	}

	@OnClick(R.id.generic_left_button)
	void leftButtonClick() {
		mCallback.genericDialogLeftClick();
	}

	@OnClick(R.id.generic_right_button)
	void rightButtonClick() {
		mCallback.genericDialogRightClick();
	}
}
