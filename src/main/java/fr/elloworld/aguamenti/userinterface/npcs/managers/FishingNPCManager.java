package fr.elloworld.aguamenti.userinterface.npcs.managers;

import fr.elloworld.aguamenti.Aguamenti;
import fr.elloworld.aguamenti.userinterface.npcs.CavernNPC;
import fr.elloworld.aguamenti.userinterface.npcs.ForgottenSourceNPC;
import fr.elloworld.aguamenti.userinterface.npcs.MainLakeNPC;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.trait.LookClose;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class FishingNPCManager implements Listener {

    private final Aguamenti main;

    public FishingNPCManager(Aguamenti main) {
        this.main = main;
    }

    public void createNPCs() {
        MainLakeNPC mLake = main.getMainLakeNPC();
        CavernNPC cavern = main.getCavernNPC();
        ForgottenSourceNPC fSource = main.getForgottenSourceNPC();

        main.getFishingNPCs().add(mLake);
        main.getFishingNPCs().add(cavern);
        main.getFishingNPCs().add(fSource);

        if (!main.getFishingNPCs().isEmpty()) {

            for (FishingNPC npc : main.getFishingNPCs()) {

                npc.createNPC();
                String signature = "xl6kXIVuUo0C7JQGMjx+fin6TzGBx8HTULhIxJPHqw9MUv26dF9JFnls4uGQBGhv6NAukQpqrLnOkKfrb8mCAV9tB7qcMzE6s0FaefAIcbva0P7qlwhD1pryUW3cBTH5E+AeUml90l5MwjnhV0KvHImjS3pbEeAGANg2sQFIxFzdPnRpQl3Z6G+4k7loHdiWadYQDp+h0HMg53R6dTfrQZy8pBqFQ/G2RRCfU7QnKq9eu30Ca9cTLd/pByO9YsWJ54kXZm2zL7yMs0V0icagrgVPIjlER9uynQ7Y65Di72UPsttVMXl83SE2SKNpzJ8KVP+sf8uCLRClde++XvkT05P/C3TGEvApKjP7MQP7FVjmnQWbrBtIJOZdutfhC4bNMr/r8vdLYV9VjxLYo41FwTqxC2tRPYrP+tQI+PIhyUMevyvrr+FHRx/Fj0yTylwqQs62lwDPQi+NUscMMttABh1Yk/uwuRruDkW/lNH8tNLrGqlJoIMfg3b8YTGdrQg1+1rzrpbi3CZvFfOlUMyo6aw1UBnS4Gn7pAIJo2xYXX7wOEi6E3euEClbE66Cx5LGrjCTk/El+WM64z3+VTJUyUhyjwyohFn4Hr/f8SkTlV8aEs/XKjrZDsdpmakWns+Nz+ccGqWz/b1PuVfHG1phi2eq1IY8eoiNqmYiuBn//uY=";
                String value = "ewogICJ0aW1lc3RhbXAiIDogMTY0MTE0NjI4ODkwOSwKICAicHJvZmlsZUlkIiA6ICJhYTRlOTU2YTMyYzU0ODlhYTFiY2Q3NDVlZTVjNjllYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJOZW1vQXVyYSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8zZTQyODY5OTk0MWU0Y2NlMzNiNmI1ODc1YzRkN2NkNjNlZjI2NzJjOGViZWFjNWZjZmU0YTM2YmEzNWFjNmNhIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
                npc.getNpc().getOrAddTrait(SkinTrait.class).setSkinPersistent("fisher", signature, value);
                npc.getNpc().getOrAddTrait(LookClose.class).lookClose(true);

                npc.getNpc().getOrAddTrait(LookClose.class).useRealisticLooking();
                npc.getNpc().getOrAddTrait(LookClose.class).setRealisticLooking(true);

                npc.getNpc().getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.HAND, new ItemStack(Material.FISHING_ROD, 1));
                npc.getNpc().spawn(npc.getLocation());
            }
        }
    }

    public void removeAllNPCs() {

        if (!main.getFishingNPCs().isEmpty()) {

            for (FishingNPC npc : main.getFishingNPCs())
                npc.getNpc().destroy();

            main.getFishingNPCs().clear();
        }
    }

    @EventHandler
    public void onNPCLeftClick(NPCLeftClickEvent e) {
        main.getStartFishing().startFishing(e.getClicker(), e.getNPC());
    }

    @EventHandler
    public void onNPCRightClick(NPCRightClickEvent e) {
        main.getStartFishing().startFishing(e.getClicker(), e.getNPC());
    }
}

