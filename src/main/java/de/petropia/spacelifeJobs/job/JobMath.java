package de.petropia.spacelifeJobs.job;

import de.petropia.spacelifeJobs.ConfigResolver;

public class JobMath {

    public static double computeRevenue(int level, double amount){
        double levelDoubled = level*2;
        double bonus = amount * (levelDoubled/100);
        return amount + bonus;
    }

    public static boolean isLevelUp(double allTimeAmount, double requiredAmount){
        return (allTimeAmount >= requiredAmount);
    }

    public static double getRequiredAmountForNextLevel(int currentLevel, Job job){
        double baseValue = ConfigResolver.getJobBaseValue();
        double jobMultiplier = ConfigResolver.getJobMultiplier(job);
        int nextLevel = currentLevel + 1;
        return (Math.pow(baseValue, nextLevel) + nextLevel * nextLevel) * jobMultiplier;
    }

    public static double calculateAddedBuff(double revenue){
        double maxBuffMoney = ConfigResolver.getMaxMoneyBuff();
        double buff = (revenue / maxBuffMoney);
        if(buff >= 1){
            return 1;
        }
        return buff;
    }

    public static double removeBuffFromRevenue(double buff, double revenue){
        return revenue * (1-buff);
    }
}
