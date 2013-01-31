
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.bot.Context;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author PimGame
 */
public class Smelting extends Node {

    public static final Area area = new Area(new Tile[]{
                new Tile(3267, 3191, 0), new Tile(3268, 3182, 0),
                new Tile(3281, 3182, 0), new Tile(3280, 3190, 0)
            });

    @Override
    public String toString() {
        return "Smelting...";
    }

    @Override
    public boolean activate() {
        return playerAtFurnace() && Inventory.getCount(Variables.INV_FILTER) > 0 && Players.getLocal().getInteracting() == null;
    }

    @Override
    public void execute() {
        final SceneObject furnace = SceneEntities.getNearest(Variables.FURNACE);
        if (furnace.isOnScreen() && !Widgets.get(1251).validate()) {
            if (!Widgets.get(1371).validate()) {
                Camera.turnTo(furnace);
                furnace.interact("Smelt", "Furnace");
                sleep(Random.nextInt(1000, 2000));
            }
        }
        System.out.println(Widgets.get(1370).getChild(56).getText());
        if (!Widgets.get(1370).getChild(56).getText().contains(Variables.bar) && Widgets.get(1370).getChild(56).validate()) {

            switch (Variables.bar) {
                case "Bronze":
                    Widgets.get(1371).getChild(44).getChild(Variables.BAR_WIDGETS[0]).interact("Select");
                    break;
                case "Iron":
                    Widgets.get(1371).getChild(44).getChild(Variables.BAR_WIDGETS[1]).interact("Select");
                    break;
                case "Steel":
                    Widgets.get(1371).getChild(44).getChild(Variables.BAR_WIDGETS[2]).interact("Select");
                    break;
                case "Mithril":
                    Widgets.get(1371).getChild(44).getChild(Variables.BAR_WIDGETS[3]).interact("Select");
                    break;
                case "Adamant":
                    Widgets.get(1371).getChild(44).getChild(Variables.BAR_WIDGETS[4]).interact("Select");
                    break;
                case "Rune":
                    Widgets.get(1371).getChild(44).getChild(Variables.BAR_WIDGETS[5]).interact("Select");
                    break;
                case "Gold":
                    Widgets.get(1371).getChild(44).getChild(Variables.BAR_WIDGETS[6]).interact("Select");
                    break;
                case "Silver":
                    Widgets.get(1371).getChild(44).getChild(Variables.BAR_WIDGETS[7]).interact("Select");
                    break;
            }
            sleep(Random.nextInt(750, 1500));
        }
        while (Widgets.get(1371).validate()) {
            Widgets.get(1370).getChild(38).click(true);
            Task.sleep(1000);
        }

        while (Inventory.getCount(Variables.INV_FILTER) > 0 && Widgets.get(1251).validate()) {
            if (Smelter.bars >= Variables.oresAmount && Variables.oresAmount != 0) {
                System.out.println("Reached the amount of ores to smelt.");
                Widgets.get(1251).getChild(48).click(true);
                Task.sleep(1000);
                Context.get().getScriptHandler().stop();
            } else {
                sleep(500);
            }
        }
    }

    public static boolean playerAtFurnace() {
        return area.contains(Players.getLocal().getLocation());
    }
}
