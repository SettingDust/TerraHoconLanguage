package settingdust.terra.hoconlanguage;

import com.dfsek.tectonic.HoconConfiguration;
import com.dfsek.terra.addons.manifest.api.AddonInitializer;
import com.dfsek.terra.api.Platform;
import com.dfsek.terra.api.addon.BaseAddon;
import com.dfsek.terra.api.event.events.config.ConfigurationDiscoveryEvent;
import com.dfsek.terra.api.event.functional.FunctionalEventHandler;
import com.dfsek.terra.api.inject.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerraHoconLanguage implements AddonInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TerraHoconLanguage.class);

    @Inject private Platform platform;

    @Inject private BaseAddon addon;

    @Override
    public void initialize() {
        platform.getEventManager().getHandler(FunctionalEventHandler.class).register(addon,
            ConfigurationDiscoveryEvent.class
        ).then(event -> event.getLoader()
                             .open("", ".conf")
                             .thenEntries(entries -> entries.forEach(it -> {
                                 LOGGER.debug("Discovered config {}", it.getKey());
                                 event.register(
                                     it.getKey(),
                                     new HoconConfiguration(
                                         it.getValue(),
                                         it.getKey()
                                     )
                                 );
                             }))
                             .close()).failThrough();
    }
}
