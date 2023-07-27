package de.petropia.spacelifeJobs.job;

import de.petropia.spacelifeJobs.Jobs;
import de.petropia.spacelifeJobs.job.listener.BreedListener;
import de.petropia.spacelifeJobs.job.listener.KillListener;
import de.petropia.spacelifeJobs.job.listener.MilkListener;
import de.petropia.spacelifeJobs.job.listener.ShearListener;

public class FarmerJob extends Job {
    private static final String NAME = "Landwirt";
    private static final String CONFIG_ID = "Landwirt";

    public FarmerJob(){
        new KillListener(this);
        new BreedListener(this);
        new ShearListener(this);
        new MilkListener(this);
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
