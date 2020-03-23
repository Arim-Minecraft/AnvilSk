/* 
 * AnvilSk
 * Copyright © 2020 Anand Beh <https://www.arim.space>
 * 
 * AnvilSk is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * AnvilSk is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with AnvilSk. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU General Public License.
 */
package space.arim.anvilsk;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import net.wesjd.anvilgui.AnvilGUI;

public class AnvilSkPlugin extends JavaPlugin {

	private static AnvilSkPlugin inst;
	
	private Map<String, Map<UUID, AnvilGUI>> guis = new HashMap<String, Map<UUID, AnvilGUI>>();
	
	private void error(String reason, Exception cause) {
		getLogger().severe("**ERROR**: Unable to load NPCSk's features! Reason: " + reason + ". Shutting down...");
		getServer().getPluginManager().disablePlugin(this);
		if (cause != null) {
			cause.printStackTrace();
		}
	}
	
	private boolean tryLoad() throws IOException, ClassNotFoundException {
		Class.forName("ch.njol.skript.Skript");
		if (!Skript.isAcceptRegistrations()) {
			error("Skript is not accepting syntax registrations", null);
			return false;
		}
		SkriptAddon addon = Skript.registerAddon(this);
		addon.loadClasses("space.arim.anvilsk.syntax", "eff", "evt", "expr");
		return true;
	}
	
	@Override
	public void onEnable() {
		inst = this;
		try {
			if (tryLoad()) {
				getLogger().info("Everything ready. Starting...");
			}
		} catch (IOException ex) {
			error("Could not load syntax classes", ex);
		} catch (ClassNotFoundException ex) {
			error("Skript not found", ex);
		}
	}
	
	public void openGui(Player player, String id, AnvilGUI.Builder builder) {
		guis.computeIfAbsent(id, (i) -> new HashMap<UUID, AnvilGUI>()).put(player.getUniqueId(),
				builder.open(player));
	}
	
	public void closeGui(Player player, String id) {
		Map<UUID, AnvilGUI> subMap = guis.get(id);
		if (subMap != null) {
			AnvilGUI gui = subMap.get(player.getUniqueId());
			if (gui != null) {
				gui.closeInventory();
			}
		}
	}
	
	public void cleanup(Player player, String id) {
		Map<UUID, AnvilGUI> subMap = guis.get(id);
		if (subMap != null) {
			subMap.remove(player.getUniqueId());
		}
	}
	
	public static AnvilSkPlugin inst() {
		return inst;
	}
	
}
