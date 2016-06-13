package es.ujaen.rlc00008.gnbwallet.domain.interactors;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.MyLog;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.data.RepositoryCallback;
import es.ujaen.rlc00008.gnbwallet.data.entities.CardTransactionDTO;
import es.ujaen.rlc00008.gnbwallet.data.source.net.Meta;
import es.ujaen.rlc00008.gnbwallet.domain.base.BaseInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.domain.model.CardTransaction;

/**
 * Created by Ricardo on 22/5/16.
 */
public class GetTransactionsInteractor extends BaseInteractor {

	public interface GetTransactionsCallback extends BaseInteractorCallback {

		void transactionsResponse(List<CardTransaction> cardTransactions);
	}

	@Inject
	public GetTransactionsInteractor() {
	}

	public void getTransactions(final Card card, final GetTransactionsCallback callback) {
		new Thread() {
			@Override
			public void run() {
				repository.getCardTransactions(card.getCardDTO(), new RepositoryCallback<List<CardTransactionDTO>>() {
					@Override
					public void resultOk(final List<CardTransactionDTO> response) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (callback != null) {
									List<CardTransaction> transactionList = new ArrayList<>();
									for (CardTransactionDTO cardTransactionDTO : response) {
										transactionList.add(new CardTransaction(cardTransactionDTO));
									}
									callback.transactionsResponse(transactionList);
								}
							}
						});
					}

					@Override
					public void resultError(final Meta meta) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (callback != null) {
									callback.operativeError(meta.getErrorMessage());
								}
							}
						});
					}

					@Override
					public void genericException(Throwable t) {
						MyLog.printStackTrace(t);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (callback != null) {
									callback.operativeError(context.getString(R.string._generic_error_message));
								}
							}
						});
					}
				});
			}
		}.start();
	}
}
