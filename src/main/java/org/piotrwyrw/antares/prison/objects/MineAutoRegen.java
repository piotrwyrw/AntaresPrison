package org.piotrwyrw.antares.prison.objects;

import org.bukkit.scheduler.BukkitRunnable;
import org.piotrwyrw.antares.prison.AntaresPrison;

public class MineAutoRegen {

    public void startBoth(int minutesInteli, int minutesDefault) {
        startInteligent(minutesInteli);
        startDefault(minutesDefault);
    }

    public void startInteligent(int minutes) {
        new BukkitRunnable() {
            @Override
            public void run() {
                AntaresPrison.getInstance().mines.inteligentRegenAllMines();
            }
        }.runTaskTimer(AntaresPrison.getInstance(), (20 * 60) * minutes, (20 * 60) * minutes);
    }

    public void startDefault(int minutes) {
        new BukkitRunnable() {
            @Override
            public void run() {
                AntaresPrison.getInstance().mines.regenAllMines();
            }
        }.runTaskTimer(AntaresPrison.getInstance(), (20 * 60) * minutes, (20 * 60) * minutes);
    }

}
