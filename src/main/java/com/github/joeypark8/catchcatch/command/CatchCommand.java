package com.github.joeypark8.catchcatch.command;

import com.github.joeypark8.catchcatch.session.GameSession;
import com.github.joeypark8.catchcatch.session.GameState;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import static com.github.joeypark8.catchcatch.Catching.catcherCount;
import static com.github.joeypark8.catchcatch.Catching.players;

public class CatchCommand {

    static GameSession session = new GameSession();

    public static void register() {
        CommandAPICommand command = new CommandAPICommand("catchup")
                .withAliases("ctu")
                .withSubcommand(
                        new CommandAPICommand("start")
                                .withArguments(new IntegerArgument("catcher counts"))
                                .executes(CatchCommand::start)
                )
                .withSubcommand(
                        new CommandAPICommand("end")
                                .executes(CatchCommand::end)
                );

        command.register();
    }

    private static void start(CommandSender sender, CommandArguments args) {

        session.startStep(GameState.STEP_ONE, 120);

        int wantedCatcherCounts = (int) args.get(0);
        if (wantedCatcherCounts >= players.size()) {
            catcherCount = wantedCatcherCounts;

            session.handleStepOne();
        }
        else sender.sendMessage(Component.text("술래 인원 초과!!"));


    }

    private static void end(CommandSender sender, CommandArguments args) {
        session.endGame();
    }
}
