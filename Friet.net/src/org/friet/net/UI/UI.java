package org.friet.net.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.InsetsUIResource;
import org.friet.net.UI.border.BBorder;
import org.friet.net.UI.border.ButtonBorder;
import org.friet.net.UI.border.MenuBorder;
import org.friet.net.UI.border.NoBorder;
import org.friet.net.UI.border.TableHeaderBorder;

/**
 *
 * @author arne
 */
public class UI {
    public static final Color geel = new Color(255, 229, 0);
    public static final Color orangje = new Color(81, 70, 0);
    public static final Color grijs = new Color(0, 62, 107);
    public static final Color grijs2 = new Color(190, 190, 190);

    public static void setUIFont (FontUIResource f, ColorUIResource c){
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            
            if (value != null && value instanceof FontUIResource){
                UIManager.put (key, f);
            }
            if (value != null && key.toString().contains("foreground")){
                UIManager.put (key, c);
            }
            if (key.toString().startsWith("Table.") && value != null) {
                //System.out.println(key + " --- " + value.toString());
            }
        }
    } 
    
    public static List list(ColorUIResource c){
        List arr = new ArrayList();
        arr.add(1);arr.add(1);arr.add(c);arr.add(c);arr.add(c);
        return arr;
    }
    
    public static void setLAF(){
        //font
        setUIFont(new FontUIResource("calibri", Font.PLAIN, 12), new ColorUIResource(Color.white));

        //menubar
        UIManager.put("MenuBar.background", new ColorUIResource(new Color(45, 45, 45)));
        UIManager.put("MenuBar.gradient", list(new ColorUIResource(grijs)));
        UIManager.put("MenuBar.borderColor", new ColorUIResource(geel));
        UIManager.put("MenuBar.border", new BBorder());
        
        //menu
        UIManager.put("Menu.border", new MenuBorder());
        UIManager.put("Menu.selectionBackground", new ColorUIResource(grijs));
        UIManager.put("Menu.selectionForeground", new ColorUIResource(Color.white));
        UIManager.put("Menu.borderColor", new ColorUIResource(geel));
        UIManager.put("Menu.font", new FontUIResource("calibri", Font.BOLD, 18));
        UIManager.put("Menu.preserveTopLevelSelection", false);
        
        //paneel
        UIManager.put("Panel.background", new ColorUIResource(new Color(255, 255, 255)));
        
        //Option pane
        UIManager.put("OptionPane.background", new ColorUIResource(new Color(255, 255, 255)));
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(Color.black));
        
        //Button
        UIManager.put("Button.gradient", list(new ColorUIResource(grijs)));
        UIManager.put("Button.border", new ButtonBorder());
        UIManager.put("Button.borderColor", new ColorUIResource(geel));
        UIManager.put("Button.enabledBorderColor", new ColorUIResource(orangje));
        UIManager.put("Button.disabledText", new ColorUIResource(new Color(10, 10, 10)));
        UIManager.put("Button.font", new FontUIResource("calibri", Font.PLAIN, 16));
        UIManager.put("Button.-background", new ColorUIResource(grijs));
        UIManager.put("Button.hoverBackground", new ColorUIResource(grijs2));

        
        //tabbedPane
        UIManager.put("TabbedPane.borderHightlightColor",new ColorUIResource(orangje));
        UIManager.put("TabbedPane.darkShadow", new ColorUIResource(new Color(10,10,10)));
        UIManager.put("TabbedPane.focus", new ColorUIResource(new Color(10,10,10)));
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        UIManager.put("TabbedPane.unselectedBackground", new ColorUIResource(grijs2));
        UIManager.put("TabbedPane.selectedTabPadInsets", new Insets(0, 0, 0, 0));
        UIManager.put("TabbedPane.contentAreaColor", new ColorUIResource(grijs));
        UIManager.put("TabbedPane.selected", new ColorUIResource(grijs));
        UIManager.put("TabbedPane.tabInsets", new Insets(4,4,4,4));
        
        //ScrollPane
        UIManager.put("ScrollPane.border", new NoBorder());
        UIManager.put("ScrollPane.background", new ColorUIResource(grijs));
        UIManager.put("ScrollPane.foreground", new ColorUIResource(grijs));
        UIManager.put("ScrollPane.borderColor", new ColorUIResource(orangje));
        
        //TableHeader
        UIManager.put("TableHeader.cellBorder", new TableHeaderBorder());
        UIManager.put("TableHeader.background", new ColorUIResource(grijs));
        UIManager.put("TableHeader.borderColor", new ColorUIResource(geel));

        //Table
        UIManager.put("Table.background", new ColorUIResource(grijs2));
        UIManager.put("Table.scrollPaneBorder", new NoBorder());
        UIManager.put("Table.foreground", new ColorUIResource(Color.black));
        UIManager.put("Table.font", new FontUIResource("calibri", Font.BOLD, 14));

        //TextField
        UIManager.put("TextField.foreground", new ColorUIResource(new Color(0, 0, 0)));
        UIManager.put("TextField.margin", new InsetsUIResource(0, 10, 0, 0));

        //PasswordField
        UIManager.put("PasswordField.foreground", new ColorUIResource(new Color(0, 0, 0)));
        UIManager.put("PasswordField.margin", new InsetsUIResource(0, 10, 0, 0));

        //paswordField
        UIManager.put("PaswordField.foreground", new ColorUIResource(new Color(0, 0, 0)));

        //combobox 
        UIManager.put("ComboBox.foreground", new ColorUIResource(new Color(0, 0, 0)));

    }
    
    
}