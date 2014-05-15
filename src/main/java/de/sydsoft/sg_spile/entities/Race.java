/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.entities;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author sythelux
 */
public class Race extends Attributes {
	private static final long	serialVersionUID	= -5314913043270248596L;

	public enum RaceType {

        HUMAN, HALFGIANT, UNKNOWN
    };
    public RaceType raceType = RaceType.UNKNOWN;

    public static Race deSerialize(String serializedString) {//TODO
        Race r = new Race();
        serializedString = serializedString.replaceFirst(";", "");
        return r;
    }

    public String serialize() {//TODO
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    @Override
    public String toString() {
        try {
            return ResourceBundle.getBundle("Race").getString("RaceType." + raceType.name());
        } catch (MissingResourceException e) {
            return "!<RaceType." + raceType.name() + ">!";
        }
    }
}
