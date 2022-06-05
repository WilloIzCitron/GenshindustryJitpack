package mindustry.ui.dialogs;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.core.Version;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.ui.Links.*;

import static mindustry.Vars.*;

public class AboutDialog extends BaseDialog{
    Seq<String> contributors = new Seq<>();
    static ObjectSet<String> bannedItems = ObjectSet.with("google-play", "itch.io", "dev-builds", "f-droid");

    public AboutDialog(){
        super("@about.button");

        shown(() -> {
            contributors = Seq.with(Core.files.internal("contributors").readString("UTF-8").split("\n"));
            Core.app.post(this::setup);
        });

        shown(this::setup);
        onResize(this::setup);
    }

    void setup(){
        cont.clear();
        buttons.clear();

        float h = Core.graphics.isPortrait() ? 90f : 80f;
        float w = Core.graphics.isPortrait() ? 330f : 600f;

        Table in = new Table();
        ScrollPane pane = new ScrollPane(in);

        for(LinkEntry link : Links.getLinks()){
            if((ios || OS.isMac || steam) && bannedItems.contains(link.name)){
                continue;
            }

            Table table = new Table(Tex.underline);
            table.margin(0);
            table.table(img -> {
                img.image().height(h - 5).width(40f).color(link.color);
                img.row();
                img.image().height(5).width(40f).color(link.color.cpy().mul(0.8f, 0.8f, 0.8f, 1f));
            }).expandY();

            table.table(i -> {
                i.background(Tex.buttonEdge3);
                i.image(link.icon);
            }).size(h - 5, h);

            table.table(inset -> {
                inset.add("[accent]" + link.title).growX().left();
                inset.row();
                inset.labelWrap(link.description).width(w - 100f).color(Color.lightGray).growX();
            }).padLeft(8);

            table.button(Icon.link, () -> {
                if(link.name.equals("wiki")) Events.fire(Trigger.openWiki);

                if(!Core.app.openURI(link.link)){
                    ui.showErrorMessage("@linkfail");
                    Core.app.setClipboardText(link.link);
                }
            }).size(h - 5, h);

            in.add(table).size(w, h).padTop(5).row();
        }

        shown(() -> Time.run(1f, () -> Core.scene.setScrollFocus(pane)));

        cont.add(pane).growX();

        addCloseButton();

        buttons.button("@credits", this::showCredits).size(200f, 64f);

        if(Core.graphics.isPortrait()){
            for(Cell<?> cell : buttons.getCells()){
                cell.width(140f);
            }
        }

    }

    public void showCredits(){
        BaseDialog dialog = new BaseDialog("@credits");
        dialog.addCloseButton();
        Texture icon = new Texture("icons/icon_64.png");
        TextureRegion iconloaded = new TextureRegion(icon);
        dialog.cont.image(iconloaded).width(64f).height(64f).center().setBounds(0, 0, iconloaded.width + 12, iconloaded.height);
        dialog.cont.row();
        dialog.cont.add("Genshindustry\n\""+ Version.codename+ "\" build "+Version.build+" v"+Version.versionNumber).center();
        dialog.cont.row();
        dialog.cont.add("@credits.text").fillX().wrap().get().setAlignment(Align.center);
        dialog.cont.row();
        if(!contributors.isEmpty()){
            dialog.cont.image().color(Pal.accent).fillX().height(3f).pad(3f);
            dialog.cont.row();
            dialog.cont.add("@contributors");
            dialog.cont.row();
            dialog.cont.pane(new Table(){{
                int i = 0;
                left();
                for(String c : contributors){
                    add("[lightgray]" + c).left().pad(3).padLeft(6).padRight(6);
                    if(++i % 3 == 0){
                        row();
                    }
                }
            }});
        }
        dialog.show();
    }
}
