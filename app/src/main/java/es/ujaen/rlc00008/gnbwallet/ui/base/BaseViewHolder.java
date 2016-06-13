package es.ujaen.rlc00008.gnbwallet.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Ricardo on 22/5/16.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

	protected BaseActivity context;

	public BaseViewHolder(BaseActivity context, View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
		this.context = context;
		context.component().inject(this);
	}
}
