package de.petropia.spacelifeJobs.job.listener;

import de.petropia.spacelifeJobs.ConfigResolver;
import de.petropia.spacelifeJobs.Jobs;
import de.petropia.spacelifeJobs.job.Job;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.HashMap;

public class FishListener implements Listener {

    private final Job job;
    private final HashMap<Material, Double> revenue_map = new HashMap<>();

    public FishListener(Job job){
        this.job = job;
        for(ConfigResolver.EnumValueEntry<Material> entry : ConfigResolver.getEnumValueEntries(job, Material.class, "Fish")){
            revenue_map.put(entry.enumValue(), entry.value());
        }
        Bukkit.getServer().getPluginManager().registerEvents(this, Jobs.getInstance());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onFishEvent(PlayerFishEvent event){
        if(!(event.getCaught() instanceof Item item)){
            return;
        }
        Double amount = revenue_map.get(item.getItemStack().getType());
        if(amount == null){
            return;
        }
        job.reportMoneyBack(event.getPlayer(), amount);
    }

}
