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

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.wesjd.anvilgui.AnvilGUI.Response;

public class ExprCloseResponse extends SimpleExpression<Response> {
	
	static {
		Skript.registerExpression(ExprTextResponse.class, Response.class, ExpressionType.SIMPLE, "[anvilsk] anvil close response");
	}
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Response> getReturnType() {
		return Response.class;
	}
	
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "anvilsk anvil close response";
	}
	
	@Override
	@Nullable
	protected Response[] get(Event e) {
		return new Response[] {Response.close()};
	}
	
}
