package es.ujaen.rlc00008.gnbwallet.ui.fragments.landing;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;

/**
 * Created by Ricardo on 30/5/16.
 */
public class LoginFragment extends BaseFragment {

	public interface LoginListener {

		void loginOk();
	}

	@BindView(R.id.login_user_edittext) EditText userEditText;
	@BindView(R.id.login_password_edittext) EditText passwordEditText;
	@BindView(R.id.login_access_button) Button accessButton;

	@Override
	protected int getContentView() {
		return R.layout.fragment_login;
	}

	@Override
	protected void prepareInterface(View mainView) {

	}

	private boolean localValidations(String login, String pass) {
		boolean validated = true;
		String loginPreFormatted = login != null ? login.trim() : null;
		String errorMessage = getString(R.string._generic_error_message);
		if (TextUtils.isEmpty(login)) {
			errorMessage = getString(R.string.login_num_doc_obligatorio);
			validated = false;
		} else if (TextUtils.isEmpty(pass)) {
			errorMessage = getString(R.string.login_clave_obligatorio);
			validated = false;
		} else if (pass.length() < SanConstants.MIN_PASSWORD_LENGTH) {
			errorMessage = getString(R.string.login_validate_clave_min_length);
			validated = false;
		} else {
			loginPreFormatted = loginPreFormatted.toUpperCase();
			loginPreFormatted = NumeroCuenta.formateaDocumento(loginPreFormatted);
			if (!NumeroCuenta.validaDocumento(loginPreFormatted)) {
				errorMessage = getString(R.string.login_validate_documento);
				validated = false;
			}
		}

		if (validated) {
			this.passwordFormatted = pass.trim();
			this.userDocFormatted = loginPreFormatted;
		} else {
			final MyAlertDialog errorDialog =
					MyAlertDialog.showGenericError(getSanActivity(), errorTitle, errorMessage);
		}
		return validated;
	}

}
