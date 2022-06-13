package mindustry.ui.dialogs;

import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.ui.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

import static arc.Core.*;

public class EasterEggDialog extends Dialog{
    private Table prefs;
    private Table menu;
    AtomicBoolean IsEntered = new AtomicBoolean(false);

    Runnable enter = new Runnable() {
        @Override
        public void run() {
            IsEntered.set(false);
        }
    };
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
                    if(IsEntered.get()) {
                        if (s.equals("mindustry")) {
                            Menus.infoToast("anuk anuk anook!", 10f);
                        }
                        IsEntered.set(false);
                    }
                }).width(300f).height(40f).padBottom(10f).center();
                p.button(Icon.left, enter).center();
            });
            t.row();
            t.button(Icon.left, this::hide).center();
        });
    }
}
