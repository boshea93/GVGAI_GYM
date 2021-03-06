package qmul.gvgai.engine.ontology.effects.binary;

import lombok.extern.slf4j.Slf4j;
import qmul.gvgai.engine.core.content.InteractionContent;
import qmul.gvgai.engine.core.game.Game;
import qmul.gvgai.engine.core.vgdl.VGDLRegistry;
import qmul.gvgai.engine.core.vgdl.VGDLSprite;
import qmul.gvgai.engine.ontology.effects.Effect;

@Slf4j
public class KillIfOtherHasMore extends Effect {
    public String resource;
    public int resourceId;
    public int limit;
    public boolean subtract;

    public KillIfOtherHasMore(InteractionContent cnt) {
        is_kill_effect = true;
        resourceId = -1;
        subtract = false;
        this.parseParameters(cnt);
        resourceId = VGDLRegistry.GetInstance().getRegisteredSpriteValue(resource);
    }

    @Override
    public void execute(VGDLSprite sprite1, VGDLSprite sprite2, Game game) {
        if (sprite1 == null || sprite2 == null) {
            log.warn("Neither the 1st nor 2nd sprite can be EOS with KillIfOtherHasMore interaction.");
            return;
        }

        applyScore = false;
        //If 'sprite2' has more than a limit of the resource type given, sprite dies.
        if (sprite2.getAmountResource(resourceId) >= limit) {
            applyScore = true;
            //boolean variable set to false to indicate the sprite was not transformed
            game.killSprite(sprite1, false);
            if (subtract)
                sprite2.subtractResource(resourceId, limit);

        }
    }
}
