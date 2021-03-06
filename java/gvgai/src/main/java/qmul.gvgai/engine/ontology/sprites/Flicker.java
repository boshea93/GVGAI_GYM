package qmul.gvgai.engine.ontology.sprites;

import java.awt.Dimension;

import com.badlogic.gdx.graphics.Color;
import qmul.gvgai.engine.core.vgdl.VGDLSprite;
import qmul.gvgai.engine.core.content.SpriteContent;
import qmul.gvgai.engine.core.game.Game;
import qmul.gvgai.engine.ontology.Types;
import qmul.gvgai.engine.tools.Vector2d;


public class Flicker extends VGDLSprite
{
    public int limit;

    public int age;

    public Flicker(){}

    public Flicker(Vector2d position, Dimension size, SpriteContent cnt)
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
        limit = 1;
        age = 0;
        color =  Color.RED;
    }

    public void update(Game game)
    {
        super.update(game);

        if(age > limit)
            //boolean variable set to false to indicate the sprite was not transformed
            game.killSprite(this, false);
        age++;

    }

    public VGDLSprite copy()
    {
        Flicker newSprite = new Flicker();
        this.copyTo(newSprite);
        return newSprite;
    }

    public void copyTo(VGDLSprite target)
    {
        Flicker targetSprite = (Flicker) target;
        targetSprite.limit = this.limit;
        targetSprite.age = this.age;
        super.copyTo(targetSprite);
    }
}
