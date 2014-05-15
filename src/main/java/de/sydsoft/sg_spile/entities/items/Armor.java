/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.entities.items;

import de.sydsoft.sg_spile.entities.Item;
import de.sydsoft.sg_spile.entities.ItemBuilder;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author sythelux
 */
public abstract class Armor extends Item {

    protected ArmorPrefix prefix;

    public Armor(ItemSuffix suffix, ArmorPrefix prefix, ItemRareNess rareNess) {
        super(suffix, rareNess);
        this.prefix = prefix;
    }
    
    public Armor(ItemBuilder builder) {
        super(builder);
        this.prefix = builder.getArmorPrefix();
    }

    @Override
    public String toString() {
        try{
            return "ItemID#" + id + " "+ ((prefix!=null)?prefix.getName():"") + ResourceBundle.getBundle("ItemName").getString(name).toLowerCase() + " " + suffix.getName();
        } catch (MissingResourceException e) {
            return "!<Armor." + name +">!";
        }
    }

    public ArmorPrefix getPrefix() {
        return prefix;
    }
    
}
