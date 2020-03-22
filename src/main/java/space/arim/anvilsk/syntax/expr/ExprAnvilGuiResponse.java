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
package space.arim.anvilsk.syntax.expr;

import org.eclipse.jdt.annotation.Nullable;

import org.bukkit.event.Event;

import space.arim.anvilsk.event.AnvilGuiCompleteEvent;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.wesjd.anvilgui.AnvilGUI.Response;

public class ExprAnvilGuiResponse extends SimpleExpression<String> {
	
	static {
		Skript.registerExpression(ExprAnvilGuiResponse.class, String.class, ExpressionType.SIMPLE, "[anvilsk] completion response");
	}
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		if (!ScriptLoader.isCurrentEvent(AnvilGuiCompleteEvent.class)) {
			Skript.error("The expression 'anvilsk completion response' may only be used in anvil completion events");
			return false;
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "anvilsk completion response";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		if (e instanceof AnvilGuiCompleteEvent) {
			Response response = ((AnvilGuiCompleteEvent) e).getResponse();
			return response.equals(Response.close()) ? null : new String[] {response.getText()};
		}
		return null;
	}
	
	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET) {
			return new Class[] {String.class};
		}
		return null;
	}
	
	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		if (e instanceof AnvilGuiCompleteEvent) {
			String text = (mode == ChangeMode.SET) ? (String) delta[0] : null;
			((AnvilGuiCompleteEvent) e).setResponse(Response.text(text));
		}
	}
	
}
