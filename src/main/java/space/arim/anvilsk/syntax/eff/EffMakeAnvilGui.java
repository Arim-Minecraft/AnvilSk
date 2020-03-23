/* 
 * ArimLib
 * Copyright © 2020 Anand Beh <https://www.arim.space>
 * 
 * ArimLib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ArimLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ArimLib. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU General Public License.
 */
package space.arim.anvilsk.syntax.eff;

import org.eclipse.jdt.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import space.arim.anvilsk.AnvilSkPlugin;
import space.arim.anvilsk.event.AnvilGuiCloseEvent;
import space.arim.anvilsk.event.AnvilGuiCompleteEvent;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.wesjd.anvilgui.AnvilGUI;

public class EffMakeAnvilGui extends Effect {
	
	private Expression<String> identifier;
	private Expression<Player> player;
	private Expression<String> title;
	private Expression<ItemStack> item;
	private Expression<String> text;
	private Expression<Boolean> preventClose;
	
	static {
		Skript.registerEffect(EffMakeAnvilGui.class, "[anvilsk] make anvil gui %string% for %player% named %string% with item %itemstack% text %string% and prevent close %boolean%");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		identifier = (Expression<String>) exprs[0];
		player = (Expression<Player>) exprs[1];
		title = (Expression<String>) exprs[2];
		item = (Expression<ItemStack>) exprs[3];
		text = (Expression<String>) exprs[4];
		preventClose = (Expression<Boolean>) exprs[5];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "anvilsk make anvil gui " + identifier.toString(e, debug) + " for " + player.toString(e, debug) + " named " + title.toString(e, debug) + " with item " + item.toString(e, debug) + " text " + text.toString(e, debug) + " and prevent close " + preventClose.toString(e, debug);
	}
	
	@Override
	protected void execute(Event e) {
		String id = identifier.getSingle(e);
		AnvilGUI.Builder builder = new AnvilGUI.Builder();
		
		builder.plugin(AnvilSkPlugin.inst()).text(text.getSingle(e)).title(title.getSingle(e)).item(item.getSingle(e)).onComplete((player, text) -> {
			AnvilGuiCompleteEvent evt = new AnvilGuiCompleteEvent(id, player, text);
			Bukkit.getServer().getPluginManager().callEvent(evt);
			return evt.getResponse();
		});
		if (preventClose.getSingle(e)) {
			builder.onClose((player) -> {
				AnvilSkPlugin.inst().cleanup(player, id);
			}).preventClose();
		} else {
			builder.onClose((player) -> {
				AnvilSkPlugin.inst().cleanup(player, id);
				Bukkit.getServer().getPluginManager().callEvent(new AnvilGuiCloseEvent(id, player));
			});
		}
		AnvilSkPlugin.inst().openGui(player.getSingle(e), id, builder);
	}

}
