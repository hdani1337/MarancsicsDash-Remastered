package hu.hdani1337.marancsicsdash.HudActors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;
import hu.hdani1337.marancsicsdash.Stage.BossStage;
import hu.hdani1337.marancsicsdash.Stage.GameStage;

import static hu.hdani1337.marancsicsdash.Stage.GameStage.isAct;

public class Jump extends OneSpriteStaticActor {
    //region AssetList
    public static final String JUMP_TEXTURE = "pic/ui/jump.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(JUMP_TEXTURE);
    }
    //endregion
    //region Változók
    private boolean touchDown;
    private boolean touchUp;
    private int force;
    private Zsolti zsolti;
    //endregion
    //region Konstruktor
    /**
     * @param stage Átadom neki a GameStaget/BossStaget, mert abból gond nélkül eltudom érni Zsoltit
     * **/
    public Jump(MyGame game, MyStage stage) {
        super(game, JUMP_TEXTURE);
        touchDown = false;
        force = 0;
        if(stage != null){
            if(stage instanceof GameStage){
                this.zsolti = ((GameStage)stage).zsolti;
            }else if(stage instanceof BossStage){
                this.zsolti = ((BossStage)stage).zsolti;
            }else this.zsolti = null;
        }

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
                touchUp = true;
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }
    //endregion
    //region Act metódusai
    @Override
    public void act(float delta) {
        super.act(delta);
        if(touchDown){
            //Amíg nyomva tartjuk, addig növeljük az erőt (maximum 7500-ig, minimum 4000-ig)
            if(force < 7500) force+=250;
        } else{
            /**
             * Ugrás feltételei:
             *  - Menjen a játékmenet
             *  - Zsoltinak mindenképp kell lennie WorldHelperje
             *  - Legyen hátralévő ugrása (alapesetben egyet ugorhat, ha pedig a játékos megveszi a doubleJumpot, akkor kettőt)
             * **/
            if(isAct) {
                if(zsolti != null) {
                    if (zsolti.getActorWorldHelper() != null) {
                        if (zsolti.getActorWorldHelper() instanceof Box2DWorldHelper) {
                            if (touchUp) {
                                if(zsolti.remainingJumps > 0) {
                                    ((Box2DWorldHelper) zsolti.getActorWorldHelper()).getBody().applyForceToCenter(new Vector2(0, force), true);
                                }
                                zsolti.remainingJumps--;
                                touchUp = false;
                            }
                        }
                    }
                }
            }
            force = 0;
        }
    }
    //endregion
}
