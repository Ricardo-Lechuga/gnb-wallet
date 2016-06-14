package es.ujaen.rlc00008.gnbwallet.ui.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.model.CardTransaction;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseActivity;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseViewHolder;
import es.ujaen.rlc00008.gnbwallet.ui.utils.MyCalendar;

/**
 * Created by Ricardo on 14/6/16.
 */
public class CardTransactionViewHolder extends BaseViewHolder {

	@BindView(R.id.transaction_amount_textview) TextView amountTextView;
	@BindView(R.id.transaction_description_textview) TextView descriptionTextView;
	@BindView(R.id.transaction_operation_date_textview) TextView operationDateTextView;

	public CardTransactionViewHolder(BaseActivity context, View itemView) {
		super(context, itemView);
	}

	public void build(CardTransaction cardTransaction) {

		amountTextView.setText(cardTransaction.getAmount().getAmountFormatted());
		descriptionTextView.setText(cardTransaction.getDescription());
		operationDateTextView.setText(MyCalendar.getCalendarFormatted(cardTransaction.getOperationCalendar()));
	}
}
