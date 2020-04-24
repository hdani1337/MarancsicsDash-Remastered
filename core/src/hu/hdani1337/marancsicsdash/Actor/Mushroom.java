package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.WorldBodyEditorLoader;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.hdani1337.marancsicsdash.Stage.GameStage;

public class Mushroom extends OneSpriteStaticActor implements CollectableItem {

    public static final String MUSHROOM_TEXTURE = "pic/ui/mushroom.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(MUSHROOM_TEXTURE);
    }

    private boolean isAct;
    private Zsolti zsolti;

    public Mushroom(MyGame game, GameStage stage) {
        super(game, MUSHROOM_TEXTURE);
        setSize(getWidth()*0.003f, getHeight()*0.003f);
        this.zsolti = stage.zsolti;
        this.isAct = true;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(this.isAct && GameStage.isAct) {
            isCollected(zsolti);
        }
    }

    @Override
    public void collected() {
        zsolti.superTime = 8;
        newPosition();
    }

    public void newPosition(){
        setPosition((float)(72+Math.random()*168),(float)(Math.random()*6+Background.ground*2));
    }
}
