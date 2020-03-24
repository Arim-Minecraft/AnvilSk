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
package space.arim.anvilsk.syntax.eff;

import org.eclipse.jdt.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import space.arim.anvilsk.AnvilSkPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffCloseAnvilGui extends Effect {
	
	private Expression<Player> player;
	
	static {
		Skript.registerEffect(EffCloseAnvilGui.class, "[anvilsk] close anvil gui for %player%");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		player = (Expression<Player>) exprs[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "anvilsk close anvil gui for " + player.toString(e, debug);
	}
	
	@Override
	protected void execute(Event e) {
		AnvilSkPlugin.inst().closeGui(player.getSingle(e));
	}
	
}
