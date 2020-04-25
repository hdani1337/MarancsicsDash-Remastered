package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2dStage;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.WorldBodyEditorLoader;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.IPrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.hdani1337.marancsicsdash.Actor.Background;
import hu.hdani1337.marancsicsdash.Actor.Blood;
import hu.hdani1337.marancsicsdash.Actor.Fire;
import hu.hdani1337.marancsicsdash.Actor.Ground;
import hu.hdani1337.marancsicsdash.Actor.MarancsicsBoss;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;
import hu.hdani1337.marancsicsdash.MarancsicsDash;
import hu.hdani1337.marancsicsdash.SoundManager;

import static hu.hdani1337.marancsicsdash.MarancsicsDash.UpdatePresence;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.SoundManager.bossMusic;
import static hu.hdani1337.marancsicsdash.SoundManager.gameMusic;
import static hu.hdani1337.marancsicsdash.SoundManager.menuMusic;
import static hu.hdani1337.marancsicsdash.Stage.GameStage.backgroundType;
import static hu.hdani1337.marancsicsdash.Stage.GameStage.selectedZsolti;

public class BossStage extends Box2dStage implements IPrettyStage {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(Zsolti.class,assetList);
        assetList.collectAssetDescriptor(MarancsicsBoss.class,assetList);
        assetList.collectAssetDescriptor(Blood.class,assetList);
        assetList.collectAssetDescriptor(Background.class,assetList);
        SoundManager.load(assetList);
    }

    public WorldBodyEditorLoader loader;
    public static boolean isAct;

    /**
     * OBJEKTUMOK
     * **/
    public Zsolti zsolti;//ZSÁÚTTI
    public MarancsicsBoss marancsics;//TOMI BÁ'
    public ArrayList<Background> backgrounds;//HÁTTÉR LISTA
    public ArrayList<Blood> blood;//VÉR LISTA
    public ArrayList<Fire> flames;//LÁNG LISTA
    public ArrayList<Ground> grounds;//TALAJ LISTA

    public BossStage(MyGame game) {
        super(new ResponseViewport(9), game);
        gameMusic.stop();
        MarancsicsDash.presenceDetail = "Fighting with the boss";
        UpdatePresence();
        assignment();
        setSizes();
        setPositions();
        addListeners();
        setZIndexes();
        addActors();
        afterInit();
    }

    @Override
    public void assignment() {
        addedFlames = false;
        loader = new WorldBodyEditorLoader("bodies.json");
        zsolti = new Zsolti(game, world, loader);
        marancsics = new MarancsicsBoss(game,world,loader);
        backgrounds = new ArrayList<>();
        blood = new ArrayList<>();
        flames = new ArrayList<>();
        grounds = new ArrayList<>();
        isAct = true;
        for (int i = 0; i < 18; i++) flames.add(new Fire(game));
        generateBaseBackgrounds();
        generateBaseGrounds();
    }

    @Override
    public void setSizes() {

    }

    @Override
    public void setPositions() {
        marancsics.setX(getViewport().getWorldWidth()+6);
        zsolti.setPosition(1,Background.ground*2);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(zsolti);
        addActor(marancsics);
    }

    private void generateBaseBackgrounds(){
        /**
         * KEZDETNEK 3 HÁTTÉR ELÉG
         * **/
        for (int i = 0; i < 3; i++){
            backgrounds.add(new Background(game, backgroundType, world, getViewport()));
            backgrounds.get(i).setX(backgrounds.get(i).getWidth()*i);
            addActor(backgrounds.get(i));
            backgrounds.get(i).setZIndex(0);
        }
    }

    private void generateBaseGrounds(){
        /**
         * KEZDETNEK 3 HÁTTÉR ELÉG
         * **/
        for (int i = 0; i < 3; i++){
            grounds.add(new Ground(game, backgroundType, getViewport()));
            grounds.get(i).setX(grounds.get(i).getWidth()*i);
            addActor(grounds.get(i));
            grounds.get(i).setZIndex(1);
        }
    }

    public void afterInit() {
        if(!muted) {
            gameMusic.stop();
            bossMusic.setLooping(true);
            bossMusic.setVolume(0.7f);
            bossMusic.play();
        }
    }

    private int offset = 1;
    public boolean isShakeScreen;

    private void shakeScreen(){
        /**
         * SZINUSZ-KOSZINUSZ FÜGGVÉNYEK SEGÍTSÉGÉVEL MOZGATJUK A VIEVPORTOT
         * **/
        getViewport().setScreenX((int) (Math.sin(offset)*3));
        getViewport().setScreenY((int) (Math.cos(offset)*3));
        offset++;
        Gdx.input.vibrate(100);
    }

    private boolean addedFlames;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(isAct) {
            if (isShakeScreen) {
                shakeScreen();
            } else {
                offset = 1;
                getViewport().setScreenX(0);
                getViewport().setScreenY(0);
            }

            /**
             * HA AZ UTOLSÓ ELŐTTI HÁTTÉR MÁR ÉPPENHOGY BEÉR A KÉPERNYŐRE, AKKOR RAKJUNK BE ÚJ HÁTTERET
             * */
            if (backgrounds.get(backgrounds.size() - 2).getX() < getViewport().getWorldWidth()) {
                backgrounds.add(new Background(game, backgroundType, world, getViewport()));
                backgrounds.get(backgrounds.size() - 1).setX(backgrounds.get(backgrounds.size() - 2).getX() + backgrounds.get(backgrounds.size() - 2).getWidth());
                addActor(backgrounds.get(backgrounds.size() - 1));
                backgrounds.get(backgrounds.size() - 1).setZIndex(0);
            }

            /**
             * HA AZ UTOLSÓ ELŐTTI TALAJ MÁR ÉPPENHOGY BEÉR A KÉPERNYŐRE, AKKOR RAKJUNK BE ÚJ TALAJT
             * */
            if (grounds.get(grounds.size() - 2).getX() < getViewport().getWorldWidth()) {
                grounds.add(new Ground(game, backgroundType, getViewport()));
                grounds.get(grounds.size() - 1).setX(grounds.get(grounds.size() - 2).getX() + grounds.get(grounds.size() - 2).getWidth());
                addActor(grounds.get(grounds.size() - 1));
                grounds.get(grounds.size() - 1).setZIndex(0);
            }

            /**
             * HA A HÁTTÉR MÁR BŐVEN NEM LÁTSZIK, AKKOR TÁVOLÍTSUK EL
             * **/
            for (Background background : backgrounds) {
                if (background.getX() < -background.getWidth() * 2) {
                    background.remove();
                    backgrounds.remove(background);
                    break;//LISTA HOSSZÁNAK VÁLTOZÁSA MIATTI EXCEPTION ELKERÜLÉSE EGY BREAKKEL
                }
            }

            /**
             * HA A TALAJ MÁR BŐVEN NEM LÁTSZIK, AKKOR TÁVOLÍTSUK EL
             * **/
            for (Ground g : grounds) {
                if (g.getX() < -g.getWidth() * 2) {
                    g.remove();
                    grounds.remove(g);
                    break;//LISTA HOSSZÁNAK VÁLTOZÁSA MIATTI EXCEPTION ELKERÜLÉSE EGY BREAKKEL
                }
            }

            /**
             * MEGÁLLÍTÁS UTÁN HA MÉG NEM MOZOGNAK AZ ANIMATED ACTOROK AKKOR MOZOGJANAK
             * **/
            //ZSOLTI ÚJRAINDÍTÁSA
            if(zsolti.getFps() == 0) {
                zsolti.setFps(12);
                ((Box2DWorldHelper) zsolti.getActorWorldHelper()).getBody().setActive(true);
            }

            //MARANCSICS ÚJRAINDÍTÁSA
            if(marancsics.getFps() == 0) {
                ((Box2DWorldHelper) marancsics.getActorWorldHelper()).getBody().setActive(true);
                marancsics.setFps(12);
            }
        }
        /**
         * HA VÉGE A JÁTÉKNAK AKKOR ZSOLTI VÉREZZEN
         * **/
        else if(Zsolti.isDead){
            if(blood.size() < 128) {
                for (int i = 0; i < 4; i++){
                    blood.add(new Blood(game, world, zsolti));
                    addActor(blood.get(blood.size() - 1));
                }
            }
            switch (selectedZsolti){
                case ZSOLTI:{
                    zsolti.setTextureAtlas(game.getMyAssetManager().getTextureAtlas(Zsolti.DEAD_ZSOLTI));
                    break;
                }
                case WARRIOR:{
                    zsolti.setTextureAtlas(game.getMyAssetManager().getTextureAtlas(Zsolti.DEAD_ZSOLTI_WARRIOR));
                    break;
                }
            }
            offset = 1;
            getViewport().setScreenX(0);
            getViewport().setScreenY(0);
        }
        else if(marancsics.hp <= 0){
            if(!addedFlames){
                for (Fire flame : flames){
                    flame.setPosition((float)(marancsics.getX() + Math.random()*marancsics.getWidth()*0.7f), (float)(marancsics.getY()+Math.random()*(marancsics.getHeight()*0.7f)));
                    addActor(flame);
                }
                marancsics.setFps(0);
                zsolti.setFps(0);
                addedFlames = true;
                getViewport().setScreenX(0);
                getViewport().setScreenY(0);
            }
        }
        /**
         * HA MEGÁLLÍTJUK A JÁTÉKOT AKKOR AZ ANIMATED ACTOROK NE MOZOGJANAK
         * **/
        else{
            //ZSOLTI MEGÁLLÍTÁSA
            if(zsolti.getFps() != 0) {
                ((Box2DWorldHelper) zsolti.getActorWorldHelper()).getBody().setActive(false);
                zsolti.setFps(0);
            }

            //MARANCSICS MEGÁLLÍTÁSA
            if(marancsics.getFps() != 0) {
                ((Box2DWorldHelper) marancsics.getActorWorldHelper()).getBody().setActive(false);
                marancsics.setFps(0);
            }

            //HA RÁZKÓDIK A KÉPERNYŐ AKKOR ÁLLÍTSUK VISSZA
            offset = 1;
            getViewport().setScreenX(0);
            getViewport().setScreenY(0);
        }
    }
}
