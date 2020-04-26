package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.WorldBodyEditorLoader;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;
import hu.hdani1337.marancsicsdash.Stage.GameStage;

import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.SoundManager.crashSound;
import static hu.hdani1337.marancsicsdash.Stage.GameStage.isAct;
import static hu.hdani1337.marancsicsdash.Stage.OptionsStage.difficulty;

public class Tank extends OneSpriteAnimatedActor {
    //region AssetList
    public static final String TANK_ATLAS = "atlas/tank.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(TANK_ATLAS);
    }
    //endregion
    //region Változók
    private boolean contacted;//Hozzáért e Marancsicshoz
    //endregion
    //region Konstruktor
    public Tank(MyGame game, World world, WorldBodyEditorLoader loader) {
        super(game, TANK_ATLAS);
        this.contacted = false;
        setFps(15);
        setSize(getWidth()*0.007f, getHeight()*0.007f);
        setActorWorldHelper(new Box2DWorldHelper(world, this, loader, "Tank", new MyFixtureDef(), BodyDef.BodyType.DynamicBody));
        setX((float) (Math.random()*15+20));
        setY(Background.ground);
    }
    //endregion
    //region Act metódusai
    @Override
    public void act(float delta) {
        super.act(delta);
        if(isAct) {
            setX(getX() - (difficulty*0.1f));
            if(getStage() != null)
                if(getX() > getStage().getViewport().getWorldWidth() && contacted)
                    remove();
        }
        else if (getFps() != 0) setFps(0);
    }

    @Override
    public boolean remove() {
        if(!muted) crashSound.play();
        if(getStage() != null && getStage() instanceof GameStage) {
            ((GameStage) getStage()).tanks.remove(this);
            ((GameStage) getStage()).score++;
        }
        return super.remove();
    }

    public void kicked(){
        this.contacted = true;
    }
    //endregion
}
