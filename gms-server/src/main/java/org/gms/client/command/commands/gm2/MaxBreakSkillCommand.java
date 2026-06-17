package org.gms.client.command.commands.gm2;

import org.gms.client.*;
import org.gms.client.Character;
import org.gms.client.command.Command;
import org.gms.provider.Data;
import org.gms.provider.DataProviderFactory;
import org.gms.provider.wz.WZFiles;
import org.gms.util.I18nUtil;

public class MaxBreakSkillCommand extends Command {
    {
        setDescription(I18nUtil.getMessage("MaxSkillCommand.message1"));
    }

    @Override
    public void execute(Client c, String[] params) {
        Character player = c.getPlayer();
        for (Data skill_ : DataProviderFactory.getDataProvider(WZFiles.STRING).getData("Skill.img").getChildren()) {
            try {
                Skill skill = SkillFactory.getSkill(Integer.parseInt(skill_.getName()));
                if (player.isMyJobSkill(skill.getId())) player.changeSkillLevel(skill, (byte) skill.getBreakMaxLevel(), skill.getMaxLevel(), -1);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                break;
            } catch (NullPointerException npe) {
            }
        }

        if (player.getJob().isA(Job.ARAN1) || player.getJob().isA(Job.LEGEND)) {
            Skill skill = SkillFactory.getSkill(5001005);
            player.changeSkillLevel(skill, (byte) -1, -1, -1);
        } else {
            Skill skill = SkillFactory.getSkill(21001001);
            player.changeSkillLevel(skill, (byte) -1, -1, -1);
        }

        player.yellowMessage(I18nUtil.getMessage("MaxSkillCommand.message2"));
    }
}
