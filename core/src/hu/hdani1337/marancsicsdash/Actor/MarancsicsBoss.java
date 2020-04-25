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

import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.SoundManager.crashSound;
import static hu.hdani1337.marancsicsdash.SoundManager.glassSound;
import static hu.hdani1337.marancsicsdash.SoundManager.hee;
import static hu.hdani1337.marancsicsdash.SoundManager.kickSound;

public class MarancsicsBoss extends OneSpriteAnimatedActor {

    public static final String MARANCSICSBOSS_ATLAS = "atlas/marancsics/marancsicsBoss.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(MARANCSICSBOSS_ATLAS);
    }

    private float speed;
    public static short hp;
    private boolean playing;

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
                    if(otherHelper.getActor() instanceof Zsolti){
                        if(otherHelper.getActor().getY() < myHelper.getActor().getY()+myHelper.getActor().getHeight()/2){
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
                        }else{
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
                            if(getStage() != null && getStage() instanceof BossStage){
                                ((BossStage)getStage()).isShakeScreen = true;
                            }
                        }
                    }
                }

                @Override
                public void endContact(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {
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

                @Override
                public void preSolve(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {

                }

                @Override
                public void postSolve(Contact contact, Box2DWorldHelper myHelper, Box2DWorldHelper otherHelper) {

                }
            });
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(getStage() != null && getStage() instanceof BossStage) {
            if(BossStage.isAct) {
                setX(getX() - speed);
                if (getX() < -getWidth() && !Zsolti.isDead) newPosition();
                if (hp <= 0) {
                    hp = 0;
                    BossStage.isAct = false;
                }
            }else if(Zsolti.isDead) {
                if (getX() > -getWidth()) setX(getX() - speed);
            }
        }
    }

    public void newPosition() {
        if(getStage() != null && getStage() instanceof BossStage) {
            setPosition((float) (Math.random() * 16 + getStage().getViewport().getWorldWidth()), Background.ground*2);
            speed = (float) (Math.random()/4)+0.15f;
            setFps(speed*150);
            setRotation(0);
        }
    }
}
