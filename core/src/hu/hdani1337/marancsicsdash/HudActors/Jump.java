package hu.hdani1337.marancsicsdash.HudActors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.hdani1337.marancsicsdash.Stage.GameStage;

import static hu.hdani1337.marancsicsdash.Stage.GameStage.isAct;

public class Jump extends OneSpriteStaticActor {

    public static final String JUMP_TEXTURE = "pic/jump.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(JUMP_TEXTURE);
    }

    private boolean touchDown;
    private int force;
    private GameStage stage;

    /**
     * @param stage Átadom neki a GameStaget, mert abból gond nélkül eltudom érni Zsoltit
     * **/
    public Jump(MyGame game, GameStage stage) {
        super(game, JUMP_TEXTURE);
        touchDown = false;
        force = 0;
        this.stage = stage;

        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                force = 4000;
                touchDown = true;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                touchDown = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(touchDown){
            if(force < 7500) force+=250;
            System.out.println(force);
        } else{
            if(isAct) {
                if (stage.zsolti.getActorWorldHelper() != null) {
                    if(stage.zsolti.getActorWorldHelper() instanceof Box2DWorldHelper) {
                        if(!stage.zsolti.inAir) {
                            ((Box2DWorldHelper) stage.zsolti.getActorWorldHelper()).getBody().applyForceToCenter(new Vector2(0, force), true);
                        }
                    }
                }
            }
            force = 0;
        }
    }
}
