/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.logic;

import com.jme3.network.AbstractMessage;

import de.sydsoft.sg_spile.entities.Player;

/**
 *  Klasse zum holen des aktuellen Programmstatus (siehe SteamDev)
 * @author sythelux
 */
public class AppStateFetcher extends AbstractMessage{
    Player player;
}
