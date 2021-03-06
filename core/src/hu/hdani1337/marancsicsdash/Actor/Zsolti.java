package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyContactListener;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.WorldBodyEditorLoader;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;
import hu.hdani1337.marancsicsdash.Stage.BossStage;
import hu.hdani1337.marancsicsdash.Stage.GameStage;
import hu.hdani1337.marancsicsdash.Stage.MenuBackgroundStage;

import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.SoundManager.crashSound;
import static hu.hdani1337.marancsicsdash.SoundManager.kickSound;
import static hu.hdani1337.marancsicsdash.SoundManager.powerUpSound;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtDouble;

public class Zsolti extends OneSpriteAnimatedActor {
    //region Zsolti típus Enum
    public enum ZsoltiType{
        ZSOLTI, WARRIOR
    }
    //endregion
    //region AssetList
    public static final String ZSOLTI_ATLAS = "atlas/zsolti/zsolti.atlas";
    public static final String DEAD_ZSOLTI = "atlas/zsolti/zsoltiDead.atlas";
    public static final String ZSOLTI_WARRIOR = "atlas/zsolti/zsoltiKard.atlas";
    public static final String DEAD_ZSOLTI_WARRIOR = "atlas/zsolti/zsoltiKardDead.atlas";
    public static final String SUPER_ZSOLTI_ATLAS = "atlas/zsolti/superZsolti.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(ZSOLTI_ATLAS);
        assetList.addTextureAtlas(DEAD_ZSOLTI);
        assetList.addTextureAtlas(ZSOLTI_WARRIOR);
        assetList.addTextureAtlas(DEAD_ZSOLTI_WARRIOR);
        assetList.addTextureAtlas(SUPER_ZSOLTI_ATLAS);
    }
    //endregion
    //region Változók
    public static boolean isDead;//Zsolti halott e
    public static boolean inAir;//Zsolti a levegőben van e
    public int remainingJumps;//Megmaradt ugrások száma (alapból egy, doubleJump-nál 2)
    private boolean playing;//Játszódik le éppen valamilyen hang (azért kell, hogy többször ne menjen ugyanaz a hang)
    //endregion
    //region Konstruktorok
    /**
     * Box2D konstruktor ContactListenerrel
     * **/
    public Zsolti(MyGame game, World world, WorldBodyEditorLoader loader) {
        super(game, ZSOLTI_ATLAS);
        setTextureAtlas(game.getMyAssetManager().getTextureAtlas(atlasByType()));
        setFps(12);
        setSize(getWidth()*0.011f, getHeight()*0.011f);
        setActorWorldHelper(new Box2DWorldHelper(world, this, loader, "Zsolti", new MyFixtureDef(), BodyDef.BodyType.DynamicBody));
        isDead = false;
        inAir = true;
        playing = false;
        if(boughtDouble) remainingJumps = 2;
        else remainingJumps = 1;

        /**
         * ÜTKÖZÉSFIGYELÉSEK HOZZÁRENDELÉSE A WORLDHELPERHEZ
         * **/
        if(getActorWorldHelper() != null && getActorWorldHelper() instanceof Box2DWorldHelper) {
            ((Box2DWorldHelper) getActorWorldHelper()).addContactListener(new MyContactListener() {
                @Override
                public void beginContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {
                    collisionTank(otherHelper);
                    collisionMushroom(otherHelper);
                    collisionMarancsics(otherHelper);
                }

                @Override
                public void endContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {
                    endCollisionTank(otherHelper);
                }

                @Override
                public void preSolve(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {

                }

                @Override
                public void postSolve(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {

                }
            });
        }
    }

    /**
     * Scene2D konstruktor
     * **/
    public Zsolti(MyGame game, ZsoltiType type){
        super(game, ZSOLTI_ATLAS);
        switch (type){
            case WARRIOR:{
                textureAtlas = game.getMyAssetManager().getTextureAtlas(ZSOLTI_WARRIOR);
                break;
            }
            case ZSOLTI:{
                textureAtlas = game.getMyAssetManager().getTextureAtlas(ZSOLTI_ATLAS);
                break;
            }
        }
        setFps(12);
    }
    //endregion
    //region Ütközésfigyelések
    //region Ütközések kezdete
    private void collisionTank(Box2DWorldHelper otherHelper){
        if (otherHelper.getActor() instanceof Tank) {
            if (superTime > 0) {
                if (getStage() != null && getStage() instanceof GameStage)
                    ((GameStage) getStage()).isShakeScreen = true;
                otherHelper.getBody().applyForceToCenter(new Vector2(5000, 1000), true);
                ((Tank)otherHelper.actor).kicked();
            }else {
                setFps(0);
                isDead = true;
                GameStage.isAct = false;
            }
            if(!muted && !playing) {
                crashSound.play();
                playing = true;
                addTimer(new TickTimer(0.15f,false,new TickTimerListener(){
                    @Override
                    public void onStop(Timer sender) {
                        super.onStop(sender);
                        playing = false;
                    }
                }));
            }

        }
    }

    private void collisionMushroom(Box2DWorldHelper otherHelper){
        if (otherHelper.getActor() instanceof Mushroom) {
            otherHelper.actor.remove();
            superTime = 8;
            if (!muted && !playing) {
                powerUpSound.play();
                playing = true;
                addTimer(new TickTimer(0.15f, false, new TickTimerListener() {
                    @Override
                    public void onStop(Timer sender) {
                        super.onStop(sender);
                        playing = false;
                    }
                }));
            }
        }
    }

    private void collisionMarancsics(Box2DWorldHelper otherHelper){
        if (otherHelper.getActor() instanceof Marancsics) {
            ((Marancsics) otherHelper.getActor()).setFps(24);
            otherHelper.getBody().applyForceToCenter(new Vector2(-900,0),true);
            ((Box2DWorldHelper)getActorWorldHelper()).getBody().applyForceToCenter(new Vector2(700,0),true);
            if(getStage() != null) {
                if (!muted && getX() < getStage().getViewport().getWorldWidth() + 2 && !playing) {
                    kickSound.play();
                    playing = true;
                    addTimer(new TickTimer(0.15f,false,new TickTimerListener(){
                        @Override
                        public void onStop(Timer sender) {
                            super.onStop(sender);
                            playing = false;
                        }
                    }));
                }
            }
        }
    }
    //endregion
    //region Ütközések vége
    private void endCollisionTank(Box2DWorldHelper otherHelper){
        if (otherHelper.getActor() instanceof Tank) {
            addTimer(new TickTimer(0.3f, false, new TickTimerListener() {
                @Override
                public void onTick(Timer sender, float correction) {
                    super.onTick(sender, correction);
                    if (getStage() != null && getStage() instanceof GameStage)
                        ((GameStage) getStage()).isShakeScreen = false;
                }
            }));
        }
    }
    //endregion
    //endregion
    //region Act metódusai
    public float superTime = 0;
    private void checkSuperTime(float delta){
        if(superTime > 0){
            /**
             * SUPER ZSOLTI
             * **/
            if(getStage() != null && getStage() instanceof GameStage && GameStage.isAct)
                superTime -= delta;
            if(getTextureAtlas() != game.getMyAssetManager().getTextureAtlas(SUPER_ZSOLTI_ATLAS))
                setTextureAtlas(game.getMyAssetManager().getTextureAtlas(SUPER_ZSOLTI_ATLAS));
        }
        else{
            /**
             * ÁTLAGOS ZSOLTI
             * **/
            superTime = 0;
            if(getStage() != null) {
                if(getStage() instanceof GameStage || getStage() instanceof BossStage) {
                    if (getTextureAtlas() != game.getMyAssetManager().getTextureAtlas(atlasByType()))
                        setTextureAtlas(game.getMyAssetManager().getTextureAtlas(atlasByType()));
                }
            }
        }
    }

    private void checkZsolti(float delta){
        checkSuperTime(delta);
        /**
         * Ha a menüben futkozik, ott ne ellenőrizzük le, hogy a talajon van e vagy forog e
         * **/
        if(getStage() != null && !(getStage() instanceof MenuBackgroundStage)) {
            checkZsoltiIsRotated();
            checkZsoltiIsOnGround();
        }
    }

    private void checkZsoltiIsRotated(){
        /**
         * ZSOLTI NE BORULJON FEL AMÍG ÉL
         * **/
        if (getRotation() != 0 && !isDead)
            setRotation(0);
    }

    private void checkZsoltiIsOnGround(){
        if (getY() > Background.ground * 2 + 0.05) {
            /**
             * ZSOLTI ELHAGYJA A TALAJT
             * **/
            inAir = true;
        } else {
            /**
             * ZSOLTI VISSZAESIK A TALAJRA VAGY NAGYON A KÖZELÉBE
             * **/
            if (boughtDouble) remainingJumps = 2;
            else remainingJumps = 1;
            inAir = false;
        }

        /**
         * FIGYELJÜK HOGY ZSOLTI A TALAJON VAN E VAGY SEM
         * **/
        if (getX() != 2.5f && !isDead && getStage() instanceof GameStage) {
            setX(2.5f);
            setOrigin(0, 0);
            getActorWorldHelper().setBodyPosition(getX() + 0.3f, getY());
        }

        if (getX() != 1 && !isDead && getStage() instanceof BossStage) {
            setX(1);
            setOrigin(0, 0);
            getActorWorldHelper().setBodyPosition(getX() + 0.3f, getY());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        checkZsolti(delta);
    }
    //endregion
    //region Egyéb metódusok
    /**
     * Visszaadja a játékos által kiválasztott skin hash-ét
     * **/
    private String atlasByType(){
        if(GameStage.selectedZsolti != null){
            switch (GameStage.selectedZsolti){
                case WARRIOR:{
                    return ZSOLTI_WARRIOR;
                }
                default:{
                    return ZSOLTI_ATLAS;
                }
            }
        }else return ZSOLTI_ATLAS;
    }
    //endregion
}
