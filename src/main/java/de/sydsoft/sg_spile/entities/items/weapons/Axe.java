/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.entities.items.weapons;

import de.sydsoft.sg_spile.entities.ItemBuilder;
import de.sydsoft.sg_spile.entities.items.ItemRareNess;
import de.sydsoft.sg_spile.entities.items.ItemSuffix;
import de.sydsoft.sg_spile.entities.items.Weapon;
import de.sydsoft.sg_spile.entities.items.WeaponPrefix;

/**
 *
 * @author sythelux
 */
public class Axe extends Weapon {

    public Axe(ItemSuffix suffix, WeaponPrefix prefix, ItemRareNess rareNess) {
        super(suffix, prefix, rareNess);
    }

    public Axe(ItemBuilder builder) {
        super(builder);
    }
}
