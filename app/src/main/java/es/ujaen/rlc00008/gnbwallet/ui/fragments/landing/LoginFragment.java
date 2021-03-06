package es.ujaen.rlc00008.gnbwallet.ui.fragments.landing;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import es.ujaen.rlc00008.gnbwallet.GNBConstants;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.LoginInteractor;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;
import es.ujaen.rlc00008.gnbwallet.ui.utils.Validator;

/**
 * Created by Ricardo on 30/5/16.
 */
public class LoginFragment extends BaseFragment {

	public interface LoginListener {

		void loginOk();
	}

	private LoginListener callback;

	private String loginFormatted;
	private String passwordFormatted;

	@BindView(R.id.login_user_edittext) EditText userEditText;
	@BindView(R.id.login_password_edittext) EditText passwordEditText;
	@BindView(R.id.login_access_button) Button accessButton;

	@Override
	public void onAttach(Context context) {
		try {
			callback = (LoginListener) context;
		} catch (ClassCastException e) {
			throw new RuntimeException(context + " must implement LoginListener!");
		}
		super.onAttach(context);
	}

	@Override
	protected int getContentView() {
		return R.layout.fragment_login;
	}

	@Override
	protected void prepareInterface(View mainView) {

	}

	@OnClick(R.id.login_access_button)
	void tryLogin() {
		hideKeyBoard(userEditText);
		if(localValidations(userEditText.getText().toString(), passwordEditText.getText().toString())){
			showLoading();
			loginInteractor.login(loginFormatted, passwordFormatted, new LoginInteractor.LoginCallback() {
				@Override
				public void loginOk() {
					hideLoading();
					userEditText.setText("");
					passwordEditText.setText("");
					callback.loginOk();
				}

				@Override
				public void operativeError(String message) {
					hideLoading();
					showErrorFragment(message);
				}
			});
		}
	}

	private boolean localValidations(String login, String pass) {
		boolean validated = true;
		String loginPreFormatted = login != null ? login.trim() : null;
		String errorMessage = getString(R.string._generic_error_message);
		if (TextUtils.isEmpty(login)) {
			errorMessage = getString(R.string.login_mandatory_user);
			validated = false;
		} else if (TextUtils.isEmpty(pass)) {
			errorMessage = getString(R.string.login_mandatory_password);
			validated = false;
		} else if (pass.length() < GNBConstants.MIN_PASSWORD_LENGTH) {
			errorMessage = getString(R.string.login_validate_password_min_length);
			validated = false;
		} else {
			loginPreFormatted = loginPreFormatted.toUpperCase();
			loginPreFormatted = Validator.formatDocument(loginPreFormatted);
			if (!Validator.checkDocument(loginPreFormatted)) {
				errorMessage = getString(R.string.login_validate_user);
				validated = false;
			}
		}

		if (validated) {
			this.loginFormatted = loginPreFormatted;
			this.passwordFormatted = pass.trim();
		} else {
			showErrorFragment(errorMessage);
		}
		return validated;
	}

}
