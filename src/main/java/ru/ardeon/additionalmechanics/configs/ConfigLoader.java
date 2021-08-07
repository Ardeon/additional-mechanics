package ru.ardeon.additionalmechanics.configs;

import ru.ardeon.additionalmechanics.Reloadable;
import ru.ardeon.additionalmechanics.configs.settings.SettingsLoaderMain;
import ru.ardeon.additionalmechanics.configs.settings.SettingsLoaderPortals;
import ru.ardeon.additionalmechanics.configs.settings.SettingsLoaderUseableItems;
import ru.ardeon.additionalmechanics.configs.settings.SettingsLoaderVars;

import java.util.ArrayList;
import java.util.List;

public class ConfigLoader implements Reloadable {
	private final Configuration main, vars, portals, items;
    private final List<Configuration> all = new ArrayList<Configuration>();

    public Configuration getMain() {
        return main;
    }

    public Configuration getVars() {
        return vars;
    }

    public Configuration getPortals() {
        return portals;
    }

    public ConfigLoader() {
        main = new Configuration(new SettingsLoaderMain());
        all.add(main);
        vars = new Configuration(new SettingsLoaderVars());
        all.add(vars);
        portals = new Configuration(new SettingsLoaderPortals());
        all.add(portals);
        items = new Configuration(new SettingsLoaderUseableItems());
        all.add(items);
    }


    @Override
    public void reload() {
        for (Configuration configuration : all) {
            configuration.reload();
        }
    }
}
