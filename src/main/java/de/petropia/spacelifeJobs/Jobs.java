package de.petropia.spacelifeJobs;

import de.petropia.turtleServer.api.PetropiaPlugin;
public class Jobs extends PetropiaPlugin {

    private static Jobs instance;

    @Override
    public void onEnable(){
        instance = this;
    }


    public static Jobs getInstance() {
        return instance;
    }
}
