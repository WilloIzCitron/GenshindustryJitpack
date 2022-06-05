package mindustry.ui.dialogs;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.Strings;
import mindustry.ai.ControlPathfinder;
import mindustry.core.*;
import mindustry.gen.*;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import java.util.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class SysteminfoDialog extends Dialog{

    private Table prefs;
    private Table menu;
    private BaseDialog Systeminfo;
    private Seq<SettingsMenuDialog.SettingsCategory> categories = new Seq<>();

    //game version


    //detects what os and architecture it is
    private final String osget = System.getProperty("os.name") + " v" + System.getProperty("os.version") + " " + System.getProperty("os.arch");
    //total ram
    private long totalram = Runtime.getRuntime().totalMemory();

    private String javaType = "JDK";

    private Texture icon;

     /***get a gl version from gpu***/


    boolean gb = totalram >= 1024 * 1024 * 1024;

    public SysteminfoDialog(){
        super(bundle.get("systeminfo", "System Information"));
        Systeminfo = new BaseDialog(bundle.get("settings.systeminfo", "System Information"));
        Systeminfo.setFillParent(true);

        cont.table(t -> {
            t.margin(10f);
            t.pane(p -> {
                p.margin(10f);


                p.table(infotable ->{
                    icon = new Texture("icons/icon_64.png");
                    TextureRegion iconloaded = new TextureRegion(icon);
                    infotable.setBounds(0, 0, iconloaded.width + 12, iconloaded.height);
                    infotable.image(iconloaded).width(64f).height(64f).left();
                    infotable.add("Genshindustry\n\""+Version.codename+ "\" build "+Version.build+" v"+Version.versionNumber).left();
                });

                p.table(info -> {
                    //load icon

                    info.row();
                    if(!mobile){
                        if (javax.tools.ToolProvider.getSystemJavaCompiler() == null) {
                            javaType = "JRE";
                        }
                        info.row();
                        info.add(bundle.get("os", "OS:\n")+ osget).left();
                        //device info
                        info.row();
                        //ram usage with percentage and progressbar
                        info.row();
                        info.add("RAM:\nTotal:" + Strings.fixed(gb ? totalram / 1024f / 1024 / 1024f : totalram / 1024f / 1024f, 1) +(gb ? "GB" : "MB")).left();
                        info.row();
                        //java vendor and version
                        info.add(bundle.get("java", "Java:\n") + System.getProperty("java.vendor") + " " + javaType + " " + System.getProperty("java.runtime.version")).left();
                    }
                    info.row();
                    info.add(bundle.get("graphics", "Graphics:\n") + graphics.getGLVersion()).left();
                });

            });
            t.button(Icon.left, this::hide).center();
            //make ram updates every second
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    totalram = Runtime.getRuntime().totalMemory();
                }
            }, 0, 1000);
        });

    }
}
