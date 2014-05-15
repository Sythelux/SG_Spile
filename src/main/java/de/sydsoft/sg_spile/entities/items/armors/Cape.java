/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.entities.items.armors;

import de.sydsoft.sg_spile.entities.ItemBuilder;
import de.sydsoft.sg_spile.entities.items.Armor;
import de.sydsoft.sg_spile.entities.items.ArmorPrefix;
import de.sydsoft.sg_spile.entities.items.ItemRareNess;
import de.sydsoft.sg_spile.entities.items.ItemSuffix;

/**
 *
 * @author dbft111asc
 */
public class Cape extends Armor{
    public Cape(ItemSuffix suffix, ArmorPrefix prefix, ItemRareNess rareNess) {
        super(suffix, prefix, rareNess);
    }
    public Cape(ItemBuilder builder) {
        super(builder);
    }

}
