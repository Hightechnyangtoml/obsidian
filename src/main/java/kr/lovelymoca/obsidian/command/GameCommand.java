package kr.lovelymoca.obsidian.command;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

public class GameCommand implements CommandExecutor {
    // 필요한 명령어 - 시작, 멈춤, 팀가입 npc 소환
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] strings) {
        if (!sender.isOp()) {
            sender.sendMessage("§cop가 없습니다.");
            return true;
        }
        if (strings.length == 0) {
            sender.sendMessage("§c사용법: /game <start|stop|reset|npc>");
            return true;
        }
        if (strings[0].equalsIgnoreCase("start")) {
            return true;
        }
        if (strings[0].equalsIgnoreCase("stop")) {
            return true;
        }
        if (strings[0].equalsIgnoreCase("reset")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team empty A");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team empty B");
            // Todo 상점이나 인벤, 버프 등등 초기화하기
            Bukkit.broadcast(Component.text("§e모든 팀이 초기화 되었습니다!"));
            return true;
        }
        if (strings[0].equalsIgnoreCase("npc")) {
            if (strings.length != 2) {
                sender.sendMessage("§c사용법: /game npc <A|B>");
                return true;
            }
            if (!(strings[1].equalsIgnoreCase("A") || strings[1].equalsIgnoreCase("B"))) {
                sender.sendMessage("§c사용법: /game npc <A|B>");
                return true;
            }
            // 해당 플레이어가 서있는 블럭좌표에 팀 색상 발광을 가진 팀 이름의 움직이지 않는 라마를 소환
            Player player = (Player) sender;
            player.getWorld().spawn(player.getLocation(), Llama.class, llama -> {
                llama.setAI(false);
                llama.setInvulnerable(true);
                llama.setSilent(true);
                llama.setCustomNameVisible(true);
                llama.setDomestication(llama.getMaxDomestication());
                llama.setOwner(null);
                llama.setGlowing(true);

               if (strings[1].equalsIgnoreCase("A")) {
                   llama.customName(Component.text("§c[A팀] 성화 인더스트리"));
                   Team a = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("A");
                   a.addEntry(llama.getUniqueId().toString());
               } else {
                   llama.customName(Component.text("§c[B팀] 하이 카논"));
                   Team a = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("B");
                   a.addEntry(llama.getUniqueId().toString());
               }
            });
            sender.sendMessage("§a당신의 위치에 해당 엔티티를 스폰했습니다!");
            return true;
        }
        return false;
    }
}
