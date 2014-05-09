/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities.items.weapons;

import de.sydsoft.sg_wolfskrone.entities.items.ItemRareNess;
import de.sydsoft.sg_wolfskrone.entities.items.ItemSuffix;
import de.sydsoft.sg_wolfskrone.entities.items.WeaponPrefix;

/**
 *
 * @author dbft111asc
 */
public class TwoHandSword extends Sword {

    public TwoHandSword(ItemSuffix suffix, WeaponPrefix prefix, ItemRareNess rareNess) {
        super(suffix, prefix, rareNess);
        handling = 1;
    }
}
