package mappers;

import model.Reaction;

/**
 * Created by podsh on 08.05.2017.
 */
public class ReactionMapper {
    public static domain.Reaction map(Reaction reaction) {
        domain.Reaction serverReaction = new domain.Reaction();
        serverReaction.setId(reaction.getId());
        serverReaction.setAngle(reaction.getAngle());
        serverReaction.setName(reaction.getName());
        return serverReaction;
    }

    public static Reaction map(domain.Reaction serverReaction) {
        Reaction clientReaction = new Reaction();
        clientReaction.setId(serverReaction.getId());
        clientReaction.setAngle(serverReaction.getAngle());
        clientReaction.setName(serverReaction.getName());
        return clientReaction;
    }
}
