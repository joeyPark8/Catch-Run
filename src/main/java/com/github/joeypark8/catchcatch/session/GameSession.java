package com.github.joeypark8.catchcatch.session;

import com.github.joeypark8.catchcatch.Catch_catch;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.Collections;
import java.util.List;

import static com.github.joeypark8.catchcatch.Catching.catcherCount;
import static com.github.joeypark8.catchcatch.Catching.players;

public class GameSession {
    private GameState currentState;
    private int timeRemaining; // In seconds


    Team catcherTeam = players.get(0).getScoreboard().registerNewTeam("Catcher");
    Team runnerTeam = players.get(0).getScoreboard().registerNewTeam("Runner");


    public GameSession() {
        this.currentState = GameState.WAITING;
        this.timeRemaining = 0;
    }

    public void startStep(GameState step, int duration) {
        this.currentState = step;
        this.timeRemaining = duration;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (timeRemaining <= 0) {
                    this.cancel();
                    nextStep();
                } else {
                    timeRemaining--;
                    // Optional: Broadcast time remaining
                }
            }
        }.runTaskTimer(new Catch_catch(), 0L, 20L); // Run every second
    }

    public void nextStep() {
        switch (currentState) {
            case STEP_ONE:
                startStep(GameState.STEP_TWO, 60); // Step 2 lasts 60 seconds
                handleStepTwo();
                break;
            case STEP_TWO:
                endGame();
                break;
            default: {


                break;
            }
        }
    }

    public void endGame() {
        currentState = GameState.FINISHED;

        // Handle game end logic

    }



    // actions to do
    public void handleStepOne() {
        // Logic for Step 1

        catcherTeam.color(NamedTextColor.DARK_PURPLE);
        runnerTeam.color(NamedTextColor.GREEN);

//        Random random = new Random();
        Collections.shuffle(players);
        List<Player> catchers = players.subList(0, catcherCount);

        for (Player player : players) {
            player.getInventory().clear();

            if (catchers.contains(player)) {
                catcherTeam.addPlayer(player);
            }
            else {
                runnerTeam.addPlayer(player);
            }
        }
    }

    public void handleStepTwo() {
        // Logic for Step 2
        // Bukkit.sendMessage("Step Two: Combat begins!");
        for (Player player : players) {
            player.getInventory().addItem(new ItemStack(Material.IRON_SWORD)); // Give sword
        }

        //TODO: check stability
        //consider using ->  getEntries()
        catcherTeam.getPlayers().forEach(p -> { //술래 맞음
            Player player = p.getPlayer();

            assert player != null;
            player.sendMessage(
                    Component.text("당신은 ")
                            .append(Component.text("술래")
                                            .color(NamedTextColor.DARK_PURPLE))
                            .append(Component.text("입니다!"))
            );


        });
        runnerTeam.getPlayers().forEach(p -> { //술래 아님: 러너
            Player player = p.getPlayer();

            assert player != null;
            player.sendMessage(
                    Component.text("당신은 ")
                            .append(Component.text("러너")
                                            .color(NamedTextColor.GREEN))
                            .append(Component.text("입니다!"))
            );


        });

    }

    public void handleStepThree() {
        // Logic for Step 3
//        Bukkit.broadcastMessage("Step Three: Final round!");
        // Add custom effects or spawn challenges
    }
}
