package de.petropia.spacelifeJobs.job;

import de.petropia.spacelifeCore.player.JobStats;
import de.petropia.spacelifeCore.player.SpacelifeDatabase;
import de.petropia.spacelifeCore.player.SpacelifePlayer;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.time.Duration;

public abstract class Job {

    public void reportMoneyBack(Player player, double amount){
        SpacelifePlayer spacelifePlayer = SpacelifeDatabase.getInstance().getCachedPlayer(player.getUniqueId());
        JobStats stats = spacelifePlayer.getJobStats().computeIfAbsent(getConfigID(), id -> {
            JobStats jobStats = new JobStats(id);
            jobStats.levelUp(); //Set level to at least 1
            return jobStats;
        });
        amount = JobMath.computeRevenue(stats.getLevel(), amount);
        double addedBuff = JobMath.calculateAddedBuff(amount);
        double allBuff = stats.getBuff() + addedBuff;
        if(allBuff >= 1){
            allBuff = 1;
        }
        stats.setBuff(allBuff);
        amount = JobMath.removeBuffFromRevenue(allBuff, amount);
        for(JobStats otherStats : spacelifePlayer.getJobStats().values()){
            if(otherStats.equals(stats)){
                continue;
            }
            double otherStatsBuff = otherStats.getBuff() - addedBuff;
            if(otherStatsBuff < 0){
                otherStatsBuff = 0;
            }
            otherStats.setBuff(otherStatsBuff);
        }
        stats.addMoney(amount);
        double moneyToNextLevel = JobMath.getRequiredAmountForNextLevel(stats.getLevel(), this);
        if(JobMath.isLevelUp(stats.getAllTimeMoney(), moneyToNextLevel)){
            stats.levelUp();
            player.showTitle(Title.title(
                    Component.text("Neues Joblevel", NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD),
                    Component.text(getName() + " - Level " + stats.getLevel(), NamedTextColor.GRAY),
                    Title.Times.times(
                            Duration.ofMillis(300),
                            Duration.ofMillis(3200),
                            Duration.ofSeconds(1)
                    )
            ));
            player.playSound(Sound.sound(org.bukkit.Sound.UI_TOAST_CHALLENGE_COMPLETE, Sound.Source.MASTER, 1.5F, 1.4F));
        }
        DecimalFormat formatter = new DecimalFormat("0.00");
        player.sendActionBar(Component.text(getName() + ": ", NamedTextColor.GRAY)
                .append(Component.text("+", TextColor.fromCSSHexString("#6b9466")).decorate(TextDecoration.BOLD))
                .append(Component.text(formatter.format(amount) + "$", NamedTextColor.GOLD))
                .append(Component.text(" (", NamedTextColor.GRAY))
                .append(Component.text("-" + formatter.format(allBuff*100) + "%", TextColor.fromCSSHexString("#a14848")).decorate(TextDecoration.ITALIC))
                .append(Component.text(")",NamedTextColor.GRAY))
                .append(Component.text(" - ", NamedTextColor.DARK_GRAY))
                .append(Component.text(formatter.format(stats.getAllTimeMoney()) + "$/" + formatter.format(moneyToNextLevel) + "$", NamedTextColor.GRAY)));
    }

    public abstract String getName();
    public abstract String getConfigID();
}
