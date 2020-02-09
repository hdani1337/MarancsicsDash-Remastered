package hu.hdani1337.marancsicsdash.HudActors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.hdani1337.marancsicsdash.Stage.GameStage;

public class Jump extends OneSpriteStaticActor {

    public static final String JUMP_TEXTURE = "pic/jump.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(JUMP_TEXTURE);
    }

    public Jump(MyGame game, final GameStage stage) {
        super(game, JUMP_TEXTURE);
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ((Box2DWorldHelper)stage.zsolti.getActorWorldHelper()).getBody().applyForceToCenter(new Vector2(0,5000),true);
            }
        });
    }
}
