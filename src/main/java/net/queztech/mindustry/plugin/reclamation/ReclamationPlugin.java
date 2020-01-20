package net.queztech.mindustry.plugin.reclamation;

import arc.*;
import arc.util.*;
import mindustry.gen.Call;
import mindustry.plugin.Plugin;
import mindustry.game.EventType.*;

import static arc.util.Log.*;
import static mindustry.Vars.*;

public class ReclamationPlugin extends Plugin{

    public ReclamationPlugin(){
        Core.settings.defaults(
        "reclamation-increment", 8,
        "reclamation-modulus", 5
        );

        //listen for a block selection event
        Events.on(WaveEvent.class, event -> {
            if((state.wave % Core.settings.getInt("reclamation-modulus")) != 0) return;
            info("Reclamation changed: &lc{0} -> {1}", state.rules.dropZoneRadius, state.rules.dropZoneRadius + Core.settings.getInt("reclamation-increment"));
            state.rules.dropZoneRadius += Core.settings.getInt("reclamation-increment");
            Call.onSetRules(state.rules);
        });
    }

    @Override
    public void registerServerCommands(CommandHandler handler){
        handler.register("reclamation-increment", "[somenumber]", "Set the reclamation increment.", arg -> {
            if(arg.length == 0){
                info("Reclamation increment is currently &lc{0}.", Core.settings.getInt("reclamation-increment"));
                return;
            }

            if(Strings.canParseInt(arg[0]) && Strings.parseInt(arg[0]) >= 0){
                int lim = Strings.parseInt(arg[0]);
                Core.settings.put("reclamation-increment", lim);
                info("Reclamation increment is now &lc{0}.", lim);
            }else{
                err("Invalid number.");
            }
        });

        handler.register("reclamation-modulus", "[somenumber]", "Set the reclamation modulus.", arg -> {
            if(arg.length == 0){
                info("Reclamation modulus is currently &lc{0}.", Core.settings.getFloat("reclamation-modulus"));
                return;
            }

            if(Strings.canParseInt(arg[0]) && Strings.parseInt(arg[0]) > 0){
                int lim = Strings.parseInt(arg[0]);
                Core.settings.put("reclamation-modulus", lim);
                info("Reclamation modulus is now &lc{0}.", lim);
            }else{
                err("Invalid number.");
            }
        });
    }
}
