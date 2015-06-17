package com.scmspain.workshop.core;


import com.google.inject.Singleton;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.governator.annotations.Modules;
import com.scmspain.workshop.core.health.HealthCheck;
import com.scmspain.workshop.security.AuthenticationManager;
import com.scmspain.workshop.security.AuthenticationManagerInterface;
import netflix.karyon.KaryonBootstrap;
import netflix.karyon.archaius.ArchaiusBootstrap;
import scmspain.karyon.restrouter.KaryonRestRouterModule;

@ArchaiusBootstrap()
@KaryonBootstrap(name = "AppServer", healthcheck = HealthCheck.class)
@Singleton
@Modules(include = {
    //ShutdownModule.class,
    //KaryonWebAdminModule.class,
    AppServer.KaryonRestRouterModuleImpl.class,
    //KaryonEurekaModule.class,
})
public interface AppServer {
    class KaryonRestRouterModuleImpl extends KaryonRestRouterModule{

        private DynamicPropertyFactory properties = DynamicPropertyFactory.getInstance();

        @Override
        protected void configureServer() {

            interceptorSupport().forUri("/*").intercept(LoggingInterceptor.class);
            int port = properties.getIntProperty("server.port", 8080).get();
            int threads = properties.getIntProperty("server.threads",50).get();
            server().port(port).threadPoolSize(threads);

        }
        @Override
        public void configure()
        {
            bind(AuthenticationManagerInterface.class).to(AuthenticationManager.class);
            //bind(ForlayoEndpoint.class).asEagerSingleton();
            //bind(IClientConfig.class).to(DefaultClientConfigImpl.class);

            super.configure();
        }
    }
}