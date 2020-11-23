package ru.ardeon.additionalmechanics.mechanics.builds;

import java.util.HashMap;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class BuildManager {
	HashMap<String, Build> builds = new HashMap<String, Build>();
	
	public BuildManager() {
		builds = Build.loadAll();
		AdditionalMechanics.getPlugin().getLogger().info(builds.keySet().toString());
	}
}
