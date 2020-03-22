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
package space.arim.anvilsk.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import net.wesjd.anvilgui.AnvilGUI;

public class AnvilGuiCompleteEvent extends AnvilGuiEvent {

	private static final HandlerList handlers = new HandlerList();
	
	private final String text;
	private AnvilGUI.Response response;
	
	public AnvilGuiCompleteEvent(String identifier, Player player, String text) {
		super(identifier, player);
		this.text = text;
		response = AnvilGUI.Response.close();
	}
	
	public String getText() {
		return text;
	}
	
	public AnvilGUI.Response getResponse() {
		return response;
	}
	
	public void setResponse(AnvilGUI.Response response) {
		this.response = response;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
