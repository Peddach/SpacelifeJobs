package de.petropia.spacelifeJobs.job;

import de.petropia.spacelifeJobs.Jobs;
import de.petropia.spacelifeJobs.job.listener.BreakListener;

public class CaveJob extends Job {

    private static final String NAME = "Höhlenforscher";
    private static final String CONFIG_ID = "Caveforscher";

    public CaveJob(){
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
