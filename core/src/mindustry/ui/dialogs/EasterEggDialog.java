package mindustry.ui.dialogs;

import arc.audio.Music;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.content.UnitTypes;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.audio.SoundControl;

import java.util.logging.ConsoleHandler;

import static arc.Core.*;

public class EasterEggDialog extends Dialog{
    private Table prefs;
    private Table menu;

private BaseDialog EasterEgg;

     public EasterEggDialog() {
        super(bundle.get("easterEgg", "Easter Egg"));
        EasterEgg = new BaseDialog(bundle.get("settings.easterEgg", "Easter Egg"));


        cont.table(t -> {
            t.margin(10f);
            t.pane(p -> {
                p.margin(10f);
                //text input for secrets
                p.add("Secrets, please enter the text here").padBottom(10f).center().row();
                p.field("enter pls", s -> {
                        if (s.equals("mindustry")) {
                            Menus.infoToast("anuk anuk anook!", 10f);
                        }
                        if (s.equals("France World Cup 2018 Russia")) {
                            Musics.menu.setVolume(0f);
                            Musics.francewc2018.play();
                        }
                        if (s.equals("Kobo Kanaeru")) {
                            Musics.menu.setVolume(0f);
                            Musics.mantrahujan.play();
                        }
                        if(s.equals("god dagger")) {
                            UnitTypes.dagger.speed = 2f;
                            UnitTypes.dagger.weapons.remove(0);
                            UnitTypes.dagger.weapons.add(UnitTypes.reign.weapons.get(0));
                            UnitTypes.dagger.weapons.remove(1);
                            UnitTypes.dagger.weapons.add(UnitTypes.reign.weapons.get(1));
                        }
                }).width(300f).height(40f).padBottom(10f).center();
            });
            t.row();
            t.button(Icon.left, this::hide).center();
        });
    }
}
