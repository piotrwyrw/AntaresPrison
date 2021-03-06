package org.piotrwyrw.antares.prison.events;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.piotrwyrw.antares.prison.AntaresPrison;
import org.piotrwyrw.antares.prison.PrisonsUser;
import org.piotrwyrw.antares.prison.PrisonsUsers;
import org.piotrwyrw.antares.prison.WorthManager;
import org.piotrwyrw.antares.prison.constants.MessageConstants;

public class ChestCloseEvent implements Listener {

    @EventHandler
    public void onEvent(InventoryCloseEvent evt) {
        if (evt.getPlayer().getWorld() != AntaresPrison.getInstance().world)
            return;
        if (evt.getInventory().getType() != InventoryType.CHEST)
            return;
        if (!evt.getView().getTitle().equals(MessageConstants.NAME_SELL_CHEST))
            return;

        PrisonsUsers users = AntaresPrison.getInstance().users;

        Player p = (Player) evt.getPlayer();
        double total = 0.0d;
        double own = 0.0d;

        for (ItemStack stack : evt.getInventory().getContents()) {
            if (stack == null)
                continue;
            Material m = stack.getType();
            WorthManager worthManager = AntaresPrison.getInstance().worthManager;

            if (!worthManager.hasWorth(m)) {
                p.getWorld().dropItem(evt.getPlayer().getLocation(), stack);
                continue;
            }

            total += (worthManager.worthOf(stack.getType()) * stack.getAmount());
            own += (worthManager.worthOf(stack.getType()) * stack.getAmount());
        }
        PrisonsUser user = users.getUser(evt.getPlayer().getUniqueId());
        user.setBalance(user.getBalance() + total);
        users.updateUser(user);
        if (own > 0.0d) {
            p.sendTitle("§c§lSOLD!", "§7" + total);
            p.spawnParticle(Particle.EXPLOSION_LARGE, p.getLocation(), 5);
            p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 100, 1.5f);
        }
        evt.getInventory().clear();
    }

}
