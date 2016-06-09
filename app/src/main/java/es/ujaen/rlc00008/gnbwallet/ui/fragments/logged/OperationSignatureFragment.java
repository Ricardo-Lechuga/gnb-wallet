package es.ujaen.rlc00008.gnbwallet.ui.fragments.logged;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;

/**
 * Created by Ricardo on 7/6/16.
 */
public class OperationSignatureFragment extends BaseFragment {

	public interface OperationSignatureListener {
		void operationSignatureEntered(String operationSignature);
	}

	private OperationSignatureListener callback;

	private String question;

	@BindView(R.id.operation_signature_question_textview) TextView questionTextView;
	@BindView(R.id.operation_signature_password_textview) TextView passwordTextView;
	@BindView(R.id.operation_signature_ok_button) Button okButton;
	@BindView(R.id.operation_signature_back_button) ImageButton backImageButton;

	@BindViews({
			R.id.operation_signature_button_0,
			R.id.operation_signature_button_1,
			R.id.operation_signature_button_2,
			R.id.operation_signature_button_3,
			R.id.operation_signature_button_4,
			R.id.operation_signature_button_5,
			R.id.operation_signature_button_6,
			R.id.operation_signature_button_7,
			R.id.operation_signature_button_8,
			R.id.operation_signature_button_9
	})
	List<Button> keypadButtons;

	public static OperationSignatureFragment newInstance(String question) {
		OperationSignatureFragment fragment = new OperationSignatureFragment();
		Bundle bundle = new Bundle();
		bundle.putString("question", question);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onAttach(Context context) {
		try {
			callback = (OperationSignatureListener) context;
		} catch (ClassCastException e) {
			throw new RuntimeException(context + " must implement OperationSignatureListener!");
		}
		super.onAttach(context);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		question = getArguments().getString("question");
	}

	@Override
	protected int getContentView() {
		return R.layout.fragment_operation_signature;
	}

	@Override
	protected void prepareInterface(View mainView) {

		questionTextView.setText(getString(R.string.operation_signature_enter, question));

		for (int i = 0; i < keypadButtons.size(); i++) {
			Button keyPadButton = keypadButtons.get(i);
			final int position = i;
			keyPadButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					buttonClick(String.valueOf(position));
				}
			});
		}
		refreshButtons();
	}

	@OnClick(R.id.operation_signature_back_button)
	void backClick() {
		if (passwordTextView.getText().length() > 0) {
			String password = passwordTextView.getText().toString();
			String newPassword = password.substring(0, password.length() - 1);
			passwordTextView.setText(newPassword);
		}
		refreshButtons();
	}

	@OnClick(R.id.operation_signature_ok_button)
	void okClick() {
		callback.operationSignatureEntered(passwordTextView.getText().toString());
	}

	private void buttonClick(String value) {
		if (passwordTextView.getText().length() < 8) {
			passwordTextView.setText(passwordTextView.getText() + value);
		}

		refreshButtons();
	}

	private void refreshButtons() {

		if (passwordTextView.length() > 0) {
			passwordTextView.setVisibility(View.VISIBLE);
		} else {
			passwordTextView.setVisibility(View.INVISIBLE);
		}

		if (passwordTextView.getText().length() >= 1) {
			animateButton(true, backImageButton);
		} else {
			animateButton(false, backImageButton);
		}

		if (passwordTextView.getText().length() >= 4 && passwordTextView.getText().length() <= 8) {
			animateButton(true, okButton);
		} else {
			animateButton(false, okButton);
		}
	}

	private void animateButton(final Boolean visible, final View button) {
		ObjectAnimator animator;
		if (visible) {
			if (button.getVisibility() == View.VISIBLE) {
				return;
			}
			animator = ObjectAnimator.ofFloat(button, View.ALPHA, 0f, 1f);
		} else {
			if (button.getVisibility() != View.VISIBLE) {
				return;
			}
			animator = ObjectAnimator.ofFloat(button, View.ALPHA, 1f, 0f);
		}
		animator.setDuration(250); //ms
		animator.start();
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				if (visible) {
					button.setVisibility(View.VISIBLE);
				} else {
					button.setVisibility(View.INVISIBLE);
				}
			}
		});
	}
}
