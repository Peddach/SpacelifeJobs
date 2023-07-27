package de.petropia.spacelifeJobs.job;

import de.petropia.spacelifeJobs.Jobs;
import de.petropia.spacelifeJobs.job.listener.KillListener;

public class KillerJob extends Job {
    private static final String NAME = "Krieger";
    private static final String CONFIG_ID = "Krieger";

    public KillerJob(){
        new KillListener(this);
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
