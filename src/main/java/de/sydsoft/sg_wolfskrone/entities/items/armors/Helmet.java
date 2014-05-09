/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities.items.armors;

import de.sydsoft.sg_wolfskrone.entities.ItemBuilder;
import de.sydsoft.sg_wolfskrone.entities.items.Armor;
import de.sydsoft.sg_wolfskrone.entities.items.ArmorPrefix;
import de.sydsoft.sg_wolfskrone.entities.items.ItemRareNess;
import de.sydsoft.sg_wolfskrone.entities.items.ItemSuffix;

/**
 *
 * @author dbft111asc
 */
public class Helmet extends Armor {

    public Helmet(ItemSuffix suffix, ArmorPrefix prefix, ItemRareNess rareNess) {
        super(suffix, prefix, rareNess);
    }

    public Helmet(ItemBuilder builder) {
        super(builder);
    }
}
