package org.ifs.userStore;

import org.keycloak.Config;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;

import java.util.List;

public class UserStoreProviderFactory implements UserStorageProviderFactory<UserStoreProvider> {

    public void init(Config.Scope config) {

        String someProperty = config.get("someProperty");
//        log.infov("Configured {0} with someProperty: {1}", this, someProperty);
    }

    @Override
    public UserStoreProvider create(KeycloakSession session, ComponentModel model){
        UserRepository repository = new UserRepository();

        return new UserStoreProvider(session, model, repository);
    }

    @Override
    public String getId() {
        return "ifs-user-provider";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {

        // this configuration is configurable in the admin-console
        return ProviderConfigurationBuilder.create()
                .property()
                .name("myParam")
                .label("My Param")
                .helpText("Some Description")
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("some value")
                .add()
                // more properties
                // .property()
                // .add()
                .build();
    }

}
