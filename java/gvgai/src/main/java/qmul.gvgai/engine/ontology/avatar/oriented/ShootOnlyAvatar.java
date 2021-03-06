package qmul.gvgai.engine.ontology.avatar.oriented;

import qmul.gvgai.engine.core.vgdl.VGDLSprite;
import qmul.gvgai.engine.core.content.SpriteContent;
import qmul.gvgai.engine.core.game.Game;
import qmul.gvgai.engine.ontology.Types;
import qmul.gvgai.engine.tools.Direction;
import qmul.gvgai.engine.tools.Utils;
import qmul.gvgai.engine.tools.Vector2d;

import java.awt.*;
import java.util.ArrayList;


public class ShootOnlyAvatar extends ShootAvatar
{
    public ShootOnlyAvatar(){}

    public ShootOnlyAvatar(Vector2d position, Dimension size, SpriteContent cnt)
    {
        MAX_WEAPONS = 5;

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
    }


    public void postProcess()
    {
        super.postProcess();

        actions.clear();

        //Define actions here first.
        if(actions.size()==0)
        {
            actions.add(Types.ACTIONS.ACTION_LEFT);
            actions.add(Types.ACTIONS.ACTION_UP);
            actions.add(Types.ACTIONS.ACTION_RIGHT);
            actions.add(Types.ACTIONS.ACTION_DOWN);
            actions.add(Types.ACTIONS.ACTION_USE);
        }
    }

    public void applyMovement(Game game, Direction action)
    {
        //No movement

        //this.physics.passiveMovement(this);
        if (physicstype != 0)
            super.updatePassive();
    }

    public void updateUse(Game game)
    {
        int itypeToShoot;
        if (Utils.processUseKey(getKeyHandler().getMask(), getPlayerID()))
            itypeToShoot = actions.indexOf(Types.ACTIONS.ACTION_USE);
        else {
            Direction action = Utils.processMovementActionKeys(getKeyHandler().getMask(), getPlayerID());
            itypeToShoot = actions.indexOf(Types.ACTIONS.fromVector(action));
        }
        if (itypeToShoot != -1 && itypeToShoot < itype.length) {
            if (hasAmmo(itypeToShoot)) {
                shoot(game, itypeToShoot);
            }
        }
    }

    public VGDLSprite copy()
    {
        ShootOnlyAvatar newSprite = new ShootOnlyAvatar();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(VGDLSprite target)
    {
        ShootOnlyAvatar targetSprite = (ShootOnlyAvatar) target;
        super.copyTo(targetSprite);
    }
    
    @Override
    public ArrayList<String> getDependentSprites(){
    	ArrayList<String> result = new ArrayList<String>();
    	
    	return result;
    }
}
