package qmul.gvgai.engine.ontology.avatar.oriented;

import java.awt.Dimension;

import qmul.gvgai.engine.core.content.SpriteContent;
import qmul.gvgai.engine.core.game.Game;
import qmul.gvgai.engine.core.vgdl.VGDLSprite;
import qmul.gvgai.engine.ontology.Types;
import qmul.gvgai.engine.tools.Direction;
import qmul.gvgai.engine.tools.Utils;
import qmul.gvgai.engine.tools.Vector2d;


public class CarAvatar extends OrientedAvatar
{

    public double angle_diff = 0.15;
    public double facing = 0;

    public CarAvatar(){}

    public CarAvatar(Vector2d position, Dimension size, SpriteContent cnt)
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
        speed=0;
    }

    /**
     * This update call is for the game tick() loop.
     * @param game current state of the game.
     */
    public void updateAvatar(Game game, boolean requestInput, boolean[] actionMask)
    {
        super.updateAvatar(game, requestInput, actionMask);
        updateUse(game);
        aim();
        move();
    }
    
    public void applyMovement(Game game, Direction action)
    {
    	//this.physics.passiveMovement(this);
    	if (physicstype != 0)
    		super.updatePassive();
    }

    
    public void aim()
    {
    	double angle = this.rotation;

    	if(Utils.processMovementActionKeys(getKeyHandler().getMask(), getPlayerID()) == Types.DLEFT) 
    	{
    		angle -= angle_diff;
    	}
    	else if (Utils.processMovementActionKeys(getKeyHandler().getMask(), getPlayerID()) == Types.DRIGHT) 
    	{
    		angle += angle_diff;
    	}
    	this._updateRotation(angle);
    }
    
    public void move()
    {
    	if (Utils.processMovementActionKeys(getKeyHandler().getMask(), getPlayerID()) == Types.DUP) 
    	{
    		//this.orientation = new Direction(0,0);
    		facing = 0;
    	}
    	else if (Utils.processMovementActionKeys(getKeyHandler().getMask(), getPlayerID()) == Types.DDOWN) 
    	{
    		//this.orientation = new Direction(0,0);
    		facing = 1;
    	}
    	Direction direx =  new Direction(Math.cos(this.rotation+(facing*Math.toRadians(180))), Math.sin(this.rotation+(facing*Math.toRadians(180))));
    	this.physics.activeMovement(this, direx, 5);
    }

    public VGDLSprite copy()
    {
        CarAvatar newSprite = new CarAvatar();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(VGDLSprite target)
    {
        CarAvatar targetSprite = (CarAvatar) target;
        targetSprite.facing = this.facing;
        targetSprite.angle_diff = this.angle_diff;
        super.copyTo(targetSprite);
    }

}
