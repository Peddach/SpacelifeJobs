package de.petropia.spacelifeJobs.job;

import de.petropia.spacelifeJobs.Jobs;
import de.petropia.spacelifeJobs.job.listener.BreakListener;
import de.petropia.spacelifeJobs.job.listener.KillListener;

public class WoodJob extends Job {
    private static final String NAME = "Förster";
    private static final String CONFIG_ID = "Forster";

    public WoodJob(){
        new KillListener(this);
        new BreakListener(this);
        Jobs.getInstance().getLogger().info("Enabled Job: " + NAME);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getConfigID() {
        return CONFIG_ID;
    }
}
