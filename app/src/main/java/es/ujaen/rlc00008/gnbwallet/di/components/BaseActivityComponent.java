package es.ujaen.rlc00008.gnbwallet.di.components;

import dagger.Component;
import es.ujaen.rlc00008.gnbwallet.di.modules.ActivityModule;
import es.ujaen.rlc00008.gnbwallet.di.scopes.ActivityScope;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseActivity;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseDialogFragment;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseViewHolder;

/**
 * Created by Ricardo Lechuga on 20/1/16.
 */
@ActivityScope
@Component(
		dependencies = {
				ApplicationComponent.class
		},
		modules = {
				ActivityModule.class
		})
public interface BaseActivityComponent {

	// Activity and setting fragment can get injected through this component
	void inject(BaseActivity activity);

	void inject(BaseFragment baseFragment);

	void inject(BaseDialogFragment baseFragment);

	void inject(BaseViewHolder baseViewHolder);
}
