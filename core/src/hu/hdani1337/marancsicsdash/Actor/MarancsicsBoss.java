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

import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.SoundManager.crashSound;
import static hu.hdani1337.marancsicsdash.SoundManager.glassSound;
import static hu.hdani1337.marancsicsdash.SoundManager.hee;
import static hu.hdani1337.marancsicsdash.Stage.OptionsStage.difficulty;

public class MarancsicsBoss extends OneSpriteAnimatedActor {
    //region AssetList
    public static final String MARANCSICSBOSS_ATLAS = "atlas/marancsics/marancsicsBoss.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(MARANCSICSBOSS_ATLAS);
    }
    //endregion
    //region Változók
    private float speed;
    public static short hp;
    private boolean playing;
    //endregion
    //region Konstruktor
    public MarancsicsBoss(MyGame game, World world, WorldBodyEditorLoader loader) {
        super(game, MARANCSICSBOSS_ATLAS);
        setFps(36);
        setSize(getWidth()*0.018f, getHeight()*0.018f);
        setActorWorldHelper(new Box2DWorldHelper(world, this, loader, "MarancsicsBoss", new MyFixtureDef(), BodyDef.BodyType.DynamicBody));
        speed = 0.18f;
        hp = 250;
        playing = false;

        if(getActorWorldHelper() != null && getActorWorldHelper() instanceof Box2DWorldHelper) {
            ((Box2DWorldHelper) getActorWorldHelper()).addContactListener(new MyContactListener() {
                @Override
                public void beginContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {
                    collisionZsolti(myHelper,otherHelper);
                }

                @Override
                public void endContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {
                    endCollisionZsolti(otherHelper);
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
    //endregion
    //region Ütközésfigyelések
    private void collisionZsolti(Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper){
        if(otherHelper.getActor() instanceof Zsolti){
            if(otherHelper.getActor().getY() < myHelper.getActor().getY()+myHelper.getActor().getHeight()/2) zsoltiDead();
            else zsoltiNotDead(otherHelper);
        }
    }

    private void endCollisionZsolti(Box2DWorldHelper otherHelper){
        if(otherHelper.getActor() instanceof Zsolti){
            addTimer(new TickTimer(0.3f, false, new TickTimerListener() {
                @Override
                public void onTick(Timer sender, float correction) {
                    super.onTick(sender, correction);
                    if (getStage() != null && getStage() instanceof BossStage)
                        ((BossStage) getStage()).isShakeScreen = false;
                }
            }));
        }
    }
    //endregion
    //region Ütközésfigyelések metódusai
    /**
     * Marancsics elütötte Zsoltit
     * **/
    private void zsoltiDead(){
        if(hp!=0) {
            if (!muted && !playing && !Zsolti.isDead) {
                crashSound.play();
                playing = true;
                addTimer(new TickTimer(0.15f, false, new TickTimerListener() {
                    @Override
                    public void onStop(Timer sender) {
                        super.onStop(sender);
                        playing = false;
                    }
                }));
            }
            Zsolti.isDead = true;
            BossStage.isAct = false;
        }
    }

    /**
     * Zsolti ráugrik Marancsics kocsijára
     * **/
    private void zsoltiNotDead(Box2DWorldHelper otherHelper){
        if(!muted && !playing && !Zsolti.isDead && hp != 0) {
            glassSound.play();
            hee.play();
            playing = true;
            addTimer(new TickTimer(0.15f,false,new TickTimerListener(){
                @Override
                public void onStop(Timer sender) {
                    super.onStop(sender);
                    playing = false;
                }
            }));
        }
        if(hp != 0)otherHelper.getBody().applyForceToCenter(new Vector2(0,2000),true);
        if(!Zsolti.isDead) hp -= Math.random()*15+5;
        if(hp <= 0 || hp > 250) hp = 0;
        if(getStage() != null && getStage() instanceof BossStage) ((BossStage)getStage()).isShakeScreen = true;
    }
    //endregion
    //region Act metódusai
    @Override
    public void act(float delta) {
        super.act(delta);
        /**
         * Csak akkor ellenőrizzünk mindent, ha a BossStagen vagyunk
         * **/
        if(getStage() != null && getStage() instanceof BossStage) {
            if(BossStage.isAct) {
                /**
                 * Megy a játék
                 * **/
                move();
                checkIsDead();
            }else if(Zsolti.isDead) {
                /**
                 * Nem megy a játék és Zsolti meghalt
                 * **/
                moveWhenZsoltiIsDead();
            }
        }
    }

    /**
     * Menjen az autó
     * **/
    private void move(){
        setX(getX() - speed);
        if (getX() < -getWidth() && !Zsolti.isDead) newPosition();
    }

    /**
     * Ellenőrizzük, hogy halott e
     * **/
    private void checkIsDead(){
        if (hp <= 0) {
            hp = 0;
            BossStage.isAct = false;
        }
    }

    /**
     * Menjen ki a képből, ha Zsolti meghalt
     * **/
    private void moveWhenZsoltiIsDead(){
        if (getX() > -getWidth()) setX(getX() - speed);
    }

    private void newPosition() {
        if(getStage() != null && getStage() instanceof BossStage) {
            setPosition((float) (Math.random() * 16 + getStage().getViewport().getWorldWidth()), Background.ground*2);
            speed = (float) (Math.random()*(difficulty*0.15))+(difficulty*0.08f)+0.05f;
            setFps(speed*150);
            setRotation(0);
        }
    }
    //endregion
}
