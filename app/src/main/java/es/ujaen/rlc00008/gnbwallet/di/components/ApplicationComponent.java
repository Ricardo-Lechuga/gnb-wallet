package es.ujaen.rlc00008.gnbwallet.di.components;

import javax.inject.Singleton;

import dagger.Component;
import es.ujaen.rlc00008.gnbwallet.GNBApplication;
import es.ujaen.rlc00008.gnbwallet.data.GNBRepository;
import es.ujaen.rlc00008.gnbwallet.data.source.net.GNBServices;
import es.ujaen.rlc00008.gnbwallet.di.modules.ApplicationModule;
import es.ujaen.rlc00008.gnbwallet.di.modules.GNBRepositoryModule;
import es.ujaen.rlc00008.gnbwallet.di.modules.MemoryModule;
import es.ujaen.rlc00008.gnbwallet.di.modules.ServiceModule;

/**
 * Created by Ricardo Lechuga on 18/1/16.
 */
@Singleton
@Component(
		modules = {
				ApplicationModule.class,
				GNBRepositoryModule.class,
				ServiceModule.class,
				MemoryModule.class
		})
public interface ApplicationComponent {

	// Exported to child components
	GNBApplication application();

	GNBRepository gnbRepository();

	GNBServices gnbServices();
}
