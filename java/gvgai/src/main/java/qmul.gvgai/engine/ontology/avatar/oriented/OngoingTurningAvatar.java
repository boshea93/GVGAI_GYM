package qmul.gvgai.engine.ontology.avatar.oriented;

import qmul.gvgai.engine.core.vgdl.VGDLRegistry;
import qmul.gvgai.engine.core.vgdl.VGDLSprite;
import qmul.gvgai.engine.core.content.SpriteContent;
import qmul.gvgai.engine.core.game.Game;
import qmul.gvgai.engine.ontology.Types;
import qmul.gvgai.engine.tools.Direction;
import qmul.gvgai.engine.tools.Utils;
import qmul.gvgai.engine.tools.Vector2d;

import java.awt.*;


public class OngoingTurningAvatar extends OrientedAvatar
{

    public String spawnBehind;
    private int spawnId;

    public OngoingTurningAvatar(){}

    public OngoingTurningAvatar(Vector2d position, Dimension size, SpriteContent cnt)
    {
        //Init the sprite
        this.init(position, size);

        //Specific class default parameter values.
        loadDefaults();

        //Parse the arguments.
        this.parseParameters(cnt);
    }

    protected void loadDefaults()
    {
        super.loadDefaults();
        speed = 1;
        is_oriented = true;
    }


    public void postProcess()
    {
        super.postProcess();
        if(spawnBehind != null)
            spawnId = VGDLRegistry.GetInstance().getRegisteredSpriteValue(spawnBehind);
    }


    /**
     * This update call is for the game tick() loop.
     * @param game current state of the game.
     */
    public void updateAvatar(Game game, boolean requestInput, boolean[] actionMask)
    {
        lastMovementType = Types.MOVEMENT.MOVE;

        Direction action;

        if (requestInput || actionMask == null) {
            //Get the input from the player.
            requestPlayerInput(game);
            //Map from the action mask to a Vector2D action.
            action = Utils.processMovementActionKeys(getKeyHandler().getMask(), getPlayerID());
        } else {
            action = Utils.processMovementActionKeys(actionMask, getPlayerID());
        }

        //Update the orientation for this cycle's movement,
        // but only if the movement is perpendicular to the current orientation.
        if((action != Types.DNONE) && (Direction.orthogonal(action, this.orientation)))
        {
            this._updateOrientation(action);
        }

        //Update movement.
        super.updatePassive();

        //Spawn behind:
        if(!this.rect.intersects(this.lastrect))
            game.addSprite(spawnId, this.getLastPosition());
    }

    public VGDLSprite copy()
    {
        OngoingTurningAvatar newSprite = new OngoingTurningAvatar();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(VGDLSprite target)
    {
        OngoingTurningAvatar targetSprite = (OngoingTurningAvatar) target;
        super.copyTo(targetSprite);
    }
}
