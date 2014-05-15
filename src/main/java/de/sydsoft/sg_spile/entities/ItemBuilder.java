/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.entities;

import java.lang.reflect.InvocationTargetException;

import de.sydsoft.libst.util.Console;
import de.sydsoft.sg_spile.entities.items.ArmorPrefix;
import de.sydsoft.sg_spile.entities.items.ItemRareNess;
import de.sydsoft.sg_spile.entities.items.ItemSuffix;
import de.sydsoft.sg_spile.entities.items.WeaponPrefix;
import de.sydsoft.sg_spile.util.DBConnector;
import de.sydsoft.sg_spile.util.Dice;

/**
 *
 * @author sythelux
 */
public class ItemBuilder {

    private static Class<ItemBuilder> TYPE = ItemBuilder.class;
    private String name;
    private Attributes attrs;
    private int id;
    private ItemSuffix suffix;
    private ItemRareNess rareNess;
    private ArmorPrefix aPrefix;
    private WeaponPrefix wPrefix;
    private int handling = 0;
    private String[] descriptions;
    private Effect effect;

    public Item buildRandomGreen(String className, int level) {
        rareNess = ItemRareNess.GREEN;
        return buildRandom(className, level);
    }

    public Item buildRandomBlue(String className, int level) {
        rareNess = ItemRareNess.BLUE;
        return buildRandom(className, level);
    }

    private Item buildRandom(String className, int level) {
        id = DBConnector.getNextID();
        suffix = ItemSuffix.Random();
        attrs = new Attributes();
        for (Attribute attribut : suffix.getAttributKeys()) {
            attrs.put(attribut, (float) (Dice.nextInt(10) + (4 * Math.sqrt(level) + 1) - 5f));
        }
        Item helpI = null;
        if (className.toLowerCase().contains("weapon")) {
            name = className.substring(8);
            wPrefix = WeaponPrefix.Random();
            handling = Dice.nextInt(3);
            try {
                helpI = (Item) (Class.forName("de.sydsoft.sg_wolfskrone.entities.items." + className).getConstructor(ItemBuilder.TYPE).newInstance(this));
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException ex) {
                Console.errMsg(ex);
            }
        } else {
            name = className.substring(7);
            aPrefix = ArmorPrefix.Random();
            try {
                helpI = (Item) (Class.forName("de.sydsoft.sg_wolfskrone.entities.items." + className).getConstructor(ItemBuilder.TYPE).newInstance(this));
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException ex) {
                Console.errMsg(ex);
            }
        }
        return helpI;
    }

    public Item build(String className) {
        id = DBConnector.getNextID();
        Item i = null;
        try {
            i = (Item) Class.forName(className).getConstructor(ItemBuilder.TYPE).newInstance(this);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException ex) {
            Console.errMsg(ex);
        }
        return i;
    }

    /* Item von XML Datei aus erstellen
     * @param FileName Name der Datei exklusive Endung/Pfad
     */
    public Item buildFromXML(int ID){
        XMLParser xmlP = new XMLParser();
        xmlP.parse();
        Item i = null;
        try {
            i = (Item) Class.forName("de.sydsoft.sg_wolfskrone.entities.items." + xmlP.getItemTypeAt(ID)).getConstructor(ItemBuilder.TYPE).newInstance(xmlP.getItemBuilderAt(ID));
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException ex) {
            Console.errMsg(ex);
        }
        return i;
    }
    
    public ItemBuilder setAttributs(Attributes attributs) {
        this.attrs = attributs;
        return this;
    }

    public ItemBuilder setRareNess(ItemRareNess rareNess) {
        this.rareNess = rareNess;
        return this;
    }

    public ItemBuilder setSuffix(ItemSuffix suffix) {
        this.suffix = suffix;
        return this;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArmorPrefix(ArmorPrefix aPrefix) {
        this.aPrefix = aPrefix;
    }

    public void setWeaponPrefix(WeaponPrefix wPrefix) {
        this.wPrefix = wPrefix;
    }

    public Attributes getAttributs() {
        return attrs;
    }

    public int getId() {
        return id;
    }

    public ItemRareNess getRareNess() {
        return rareNess;
    }

    public ItemSuffix getSuffix() {
        return suffix;
    }

    public ArmorPrefix getArmorPrefix() {
        return aPrefix;
    }

    public WeaponPrefix getWeaponPrefix() {
        return wPrefix;
    }

    public int getHandling() {
        return handling;
    }

    public String getName() {
        return name;
    }

    public String[] getDescriptions() {
        return descriptions;
    }

    public Effect getEffect() {
        return effect;
    }
    
    @Override
    public String toString() {
        return id + ": " + aPrefix + "/" + wPrefix + " " + name + " "+suffix;
    }
}
