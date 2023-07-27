package de.petropia.spacelifeJobs;

import de.petropia.spacelifeJobs.command.JobCommand;
import de.petropia.spacelifeJobs.job.*;
import de.petropia.turtleServer.api.PetropiaPlugin;
public class Jobs extends PetropiaPlugin {

    private static Jobs instance;

    @Override
    public void onEnable(){
        instance = this;
        saveDefaultConfig();
        saveConfig();
        reloadConfig();

        //TODO enable jobs based on server
        new CaveJob();
        new FarmerJob();
        new KillerJob();
        new NetherJob();
        new SeaJob();
        new WoodJob();
        getCommand("jobs").setExecutor(new JobCommand());
        getCommand("jobs").setTabCompleter(new JobCommand());
    }


    public static Jobs getInstance() {
        return instance;
    }
}
