package qmul.gvgai.engine.ontology.sprites.producer;

import com.badlogic.gdx.graphics.Color;
import qmul.gvgai.engine.core.vgdl.VGDLRegistry;
import qmul.gvgai.engine.core.vgdl.VGDLSprite;
import qmul.gvgai.engine.core.content.SpriteContent;
import qmul.gvgai.engine.core.game.Game;
import qmul.gvgai.engine.ontology.Types;
import qmul.gvgai.engine.tools.Vector2d;

import java.awt.*;
import java.util.ArrayList;

public class BomberRandomMissile extends SpawnPoint
{
    public String stypeMissile;
    private ArrayList<Integer> itypesMissile;

    public BomberRandomMissile(){}

    public BomberRandomMissile(Vector2d position, Dimension size, SpriteContent cnt)
    {
        //Init the sprite
        this.init(position, size);

        //Specific class default parameter values.
        loadDefaults();

        //Parse the arguments.
        this.parseParameters(cnt);

        int notItypesArray[] = VGDLRegistry.GetInstance().explode(stypeMissile);
        itypesMissile = new ArrayList<>();
        for(Integer it : notItypesArray)
            itypesMissile.add(it);
    }

    protected void loadDefaults()
    {
        super.loadDefaults();
        color = Color.ORANGE;
        is_static = false;
        is_oriented = true;
        orientation = Types.DRIGHT.copy();
        is_npc = true;
    }

    public void update(Game game)
    {
        int type = game.getRandomGenerator().nextInt(itypesMissile.size());
        itype = itypesMissile.get(type);

        super.update(game);
    }

    /**
     * Updates missile itype with newitype
     * @param itype - current type of missile
     * @param newitype - new type of missile to replace the first
     */
    public void updateItype(int itype, int newitype) {
        int idx = itypesMissile.indexOf(itype);
        try {
            itypesMissile.set(idx, newitype);
        } catch (ArrayIndexOutOfBoundsException e) {
            //System.out.println("Type of missile unrecognized.");
        }
    }

    public VGDLSprite copy()
    {
        BomberRandomMissile newSprite = new BomberRandomMissile();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(VGDLSprite target)
    {
        BomberRandomMissile targetSprite = (BomberRandomMissile) target;

        targetSprite.itypesMissile = new ArrayList<>();
        for(Integer it : this.itypesMissile)
            targetSprite.itypesMissile.add(it);
        
        super.copyTo(targetSprite);
    }
}
