/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.entities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.sydsoft.libst.util.Console;
import de.sydsoft.sg_spile.entities.items.ArmorPrefix;
import de.sydsoft.sg_spile.entities.items.ItemRareNess;
import de.sydsoft.sg_spile.entities.items.ItemSuffix;
import de.sydsoft.sg_spile.entities.items.WeaponPrefix;

/**
 *
 * @author sythelux
 */
public class XMLParser {

    String fileName = "./assets/Elements/Items/Items.xml";
    Document dom;
    ArrayList<ItemBuilder> itemBuilders = new ArrayList<>();
    ArrayList<String> itemTypes = new ArrayList<>();

    public void parse() {
        parseXmlFile(fileName);
        parseDocument();
    }

    private void parseXmlFile(String fileName) {
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            dom = db.parse(fileName);


        } catch (ParserConfigurationException pce) {
            Console.errMsg(pce);
        } catch (SAXException se) {
            Console.errMsg(se);
        } catch (IOException ioe) {
            Console.errMsg(ioe);
        }
    }

    private void parseDocument() {
        //get the root element
        Element docEle = dom.getDocumentElement();

        //get a nodelist of  elements
        NodeList nl = docEle.getElementsByTagName("Item");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                //get the item element
                Element el = (Element) nl.item(i);
                
                ItemBuilder iB = new ItemBuilder();
                iB.setId(getIntValue(el, "ID"));
                itemTypes.add(iB.getId(), getTextValue(el, "type") );
                iB.setRareNess(ItemRareNess.getByNumber(getIntValue(el, "ItemRareNess")));
                if (itemTypes.get(iB.getId()).contains("armor")) {
                    iB.setArmorPrefix(ArmorPrefix.getByName(getTextValue(el, "prefix")));
                } else{
                    iB.setWeaponPrefix(WeaponPrefix.getByName("prefix"));
                }
                iB.setName(getTextValue(el, "Name"));
                iB.setSuffix(ItemSuffix.getByName(getTextValue(el, "ItemSuffix")));
                Attributes attrs = new Attributes();
                EnumSet<Attribute> attrEnumSet = EnumSet.allOf(Attribute.class);
                String attr= "";
                for (int j = 0; j < attrEnumSet.size(); j++) {
                    attr = Attribute.getByNumber(j).name();
//                    NodeList nl2 = el.getElementsByTagName(attr.toLowerCase());
//                    if (nl2 != null && nl2.getLength() > 0) {
                    if (getTextValue(el, attr.toLowerCase())!= null) {
                        float attrF = getFloatValue(el, attr.toLowerCase());
                        attrs.add(Attribute.getByName(attr), attrF);
                    }
                }
                iB.setAttributs(attrs);
                itemBuilders.add(iB.getId(), iB);
            }
        }
        nl = docEle.getElementsByTagName("Attack");
        //TODO: Angriffe
        if (nl != null && nl.getLength() > 0) {
        }
    }

    private String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }
        return textVal;
    }

    private int getIntValue(Element ele, String tagName) {
        //in production application you would catch the exception
        return Integer.parseInt(getTextValue(ele, tagName));
    }

    private float getFloatValue(Element ele, String tagName) {
        //in production application you would catch the exception
        return Float.parseFloat(getTextValue(ele, tagName));
    }

    public ItemBuilder getItemBuilderAt(int id) {
        return itemBuilders.get(id);
    }

    public ArrayList<ItemBuilder> getItemBuilders() {
        return itemBuilders;
    }
    
    public String getItemTypeAt(int id) {
        return itemTypes.get(id);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public void addItemBuilder(ItemBuilder itemBuilder){
        
    }    
}
