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
package space.arim.anvilsk.syntax.evt;

import org.eclipse.jdt.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import space.arim.anvilsk.event.AnvilGuiCloseEvent;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;

public class EvtAnvilGuiClose extends SkriptEvent {

	private String identifier;
	
	static {
		Skript.registerEvent("Anvil Gui Close", EvtAnvilGuiClose.class, AnvilGuiCloseEvent.class, "[anvilsk] anvil gui close for [identifier] %string%");
		EventValues.registerEventValue(AnvilGuiCloseEvent.class, Player.class, new Getter<Player, AnvilGuiCloseEvent>() {
			@Override
			@Nullable
			public Player get(AnvilGuiCloseEvent arg) {
				return arg.getPlayer();
			}
		}, 0);
	}
	
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "anvilsk anvil gui close for identifier " + identifier;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Literal<?>[] args, int matchedPattern, ParseResult parseResult) {
		identifier = ((Literal<String>) args[0]).getSingle();
		return true;
	}
	
	@Override
	public boolean check(Event e) {
		return (e instanceof AnvilGuiCloseEvent) && ((AnvilGuiCloseEvent) e).getIdentifier().equalsIgnoreCase(identifier);
	}
	
}
