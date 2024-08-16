package net.coma112.ctoken.listeners;

import net.coma112.ctoken.menu.PaginatedMenu;
import net.coma112.ctoken.menu.menus.SettingsMenu;
import net.coma112.ctoken.utils.MenuUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {
    @EventHandler
    public void onClick(final InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof PaginatedMenu menu) {
            event.setCancelled(true);
            menu.handleMenu(event);
        }
    }

    @EventHandler
    public void onClose(final InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = new SettingsMenu(MenuUtils.getMenuUtils(player)).getInventory();

        if (event.getInventory().equals(inventory)) inventory.close();
    }
}
