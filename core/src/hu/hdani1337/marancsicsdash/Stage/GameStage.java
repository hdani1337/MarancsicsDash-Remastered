package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2dStage;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.WorldBodyEditorLoader;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.IPrettyStage;
import hu.hdani1337.marancsicsdash.Actor.Background;
import hu.hdani1337.marancsicsdash.Actor.Blood;
import hu.hdani1337.marancsicsdash.Actor.Coin;
import hu.hdani1337.marancsicsdash.Actor.Ground;
import hu.hdani1337.marancsicsdash.Actor.Marancsics;
import hu.hdani1337.marancsicsdash.Actor.MarancsicsBoss;
import hu.hdani1337.marancsicsdash.Actor.Mushroom;
import hu.hdani1337.marancsicsdash.Actor.SuperCoin;
import hu.hdani1337.marancsicsdash.Actor.Tank;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;
import hu.hdani1337.marancsicsdash.MarancsicsDash;
import hu.hdani1337.marancsicsdash.SoundManager;

import static hu.hdani1337.marancsicsdash.MarancsicsDash.UpdatePresence;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.preferences;
import static hu.hdani1337.marancsicsdash.SoundManager.gameMusic;
import static hu.hdani1337.marancsicsdash.SoundManager.menuMusic;
import static hu.hdani1337.marancsicsdash.Stage.OptionsStage.difficulty;

public class GameStage extends Box2dStage implements IPrettyStage {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(Zsolti.class,assetList);
        assetList.collectAssetDescriptor(Marancsics.class,assetList);
        assetList.collectAssetDescriptor(Tank.class,assetList);
        assetList.collectAssetDescriptor(Mushroom.class,assetList);
        assetList.collectAssetDescriptor(Coin.class,assetList);
        assetList.collectAssetDescriptor(MarancsicsBoss.class,assetList);
        assetList.collectAssetDescriptor(Blood.class,assetList);
        assetList.collectAssetDescriptor(Background.class,assetList);
        assetList.collectAssetDescriptor(SuperCoin.class,assetList);
        SoundManager.load(assetList);
    }

    public boolean isShakeScreen;//KÉPERNYŐ MEGRÁZÁSA
    public WorldBodyEditorLoader loader;

    /**
     * OBJEKTUMOK
     * **/
    public Zsolti zsolti;//ZSÁÚTTI
    public Marancsics marancsics;//TOMI BÁ'
    public ArrayList<Coin> coins;//ÉRME LISTA
    public Mushroom mushroom;//GOMBA
    public SuperCoin superCoin;//SUPER COIN
    public ArrayList<Background> backgrounds;//HÁTTÉR LISTA
    public ArrayList<Tank> tanks;//TANK LISTA
    public ArrayList<Blood> blood;//VÉR LISTA
    public ArrayList<Ground> grounds;//TALAJ LISTA

    public static boolean isAct;
    public static Background.BackgroundType backgroundType;
    public static long score;

    public static Marancsics.MarancsicsType selectedMarancsics = Marancsics.MarancsicsType.values()[preferences.getInteger("selectedMarancsics")];
    public static Zsolti.ZsoltiType selectedZsolti = Zsolti.ZsoltiType.values()[preferences.getInteger("selectedZsolti")];

    public GameStage(MyGame game) {
        super(new ResponseViewport(9), game);
        MarancsicsDash.presenceDetail = "In Game";
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
        if(difficulty == 0) difficulty = 2;
        if(selectedMarancsics == null) selectedMarancsics = Marancsics.MarancsicsType.MARANCSICS;
        if(selectedZsolti == null) selectedZsolti = Zsolti.ZsoltiType.ZSOLTI;
        SoundManager.assign();
        score = 0;
        switch (preferences.getInteger("selectedBackground")){
            case 0: {
                backgroundType = Background.BackgroundType.CSERNOBIL;
                MarancsicsDash.presenceDetail = "In Game - Chernobyl";
                UpdatePresence();
                break;
            }
            case 2: {
                backgroundType = Background.BackgroundType.SZIBERIA;
                MarancsicsDash.presenceDetail = "In Game - Siberia";
                UpdatePresence();
                break;
            }
            case 3: {
                backgroundType = Background.BackgroundType.ZALA;
                MarancsicsDash.presenceDetail = "In Game - Zala County";
                UpdatePresence();
                break;
            }
            case 1: {
                backgroundType = Background.BackgroundType.SZAHARA;
                MarancsicsDash.presenceDetail = "In Game - Sahara";
                UpdatePresence();
                break;
            }
            case 4: {
                backgroundType = Background.BackgroundType.OCEAN;
                MarancsicsDash.presenceDetail = "In Game - Atlantic Ocean";
                UpdatePresence();
                break;
            }
        }
        loader = new WorldBodyEditorLoader("bodies.json");
        isAct = true;
        isShakeScreen = false;
        zsolti = new Zsolti(game, world, loader);
        marancsics = new Marancsics(game, world, loader);
        tanks = new ArrayList<>();
        coins = new ArrayList<>();
        blood = new ArrayList<>();
        mushroom = new Mushroom(game,this);
        superCoin = new SuperCoin(game,this);
        backgrounds = new ArrayList<>();
        grounds = new ArrayList<>();
        generateBaseBackgrounds();
        generateBaseGrounds();
    }

    @Override
    public void setSizes() {

    }

    @Override
    public void setPositions() {
        zsolti.setPosition(2.5f,1);
        mushroom.newPosition();
        superCoin.newPosition();
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
        if(ShopStage.boughtZsolti) addActor(mushroom);
        else mushroom = null;
        if(ShopStage.boughtCoin) addActor(superCoin);
        else superCoin = null;
    }

    public void afterInit() {
        if(!muted) {
            menuMusic.stop();
            gameMusic.setLooping(true);
            gameMusic.setVolume(0.7f);
            gameMusic.play();
        }
        zsolti.superTime = 3;
    }

    public void addCoins(){
        for (int i = 0; i < 250; i++) {
            coins.add(new Coin(game, this));
            addActor(coins.get(coins.size() - 1));
        }
    }

    private int offset = 1;

    private void shakeScreen(){
        /**
         * SZINUSZ-KOSZINUSZ FÜGGVÉNYEK SEGÍTSÉGÉVEL MOZGATJUK A VIEVPORTOT
         * **/
        getViewport().setScreenX((int) (Math.sin(offset)*3));
        getViewport().setScreenY((int) (Math.cos(offset)*3));
        offset++;
        Gdx.input.vibrate(100);
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

    private float tankElapsed = elapsedTime;
    private float coinElapsed = elapsedTime;

    @Override
    public void act(float delta) {
        super.act(delta);
        /**
         * AMÍG NINCS VÉGE A JÁTÉKNAK ADDIG FUT AZ ACT
         * **/
        if(isAct) {
            /**
             * HA MARANCSICS VAGY SUPERZSOLTI BELEÉRT A TANKBA, AKKOR RÁZZUK MEG A KÉPERNYŐT
             * AZ ÉRINTKEZÉS BEFEJEZTÉVEL ÁLLÍTSUK VISSZA A VIEWPORTOT ÉS AZ OFFSETET
             *
             * U.I: A KAMERA MOZGATÁSÁHOZ EGYSZERŰEN AUTISTA VAGYOK, EZÉRT A VIEWPORTOT MOZGATOM
             * **/
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
             * RANDOM IDŐKÖZÖNKÉNT ÚJ TANK HOZZÁADÁSA
             * */
            if (elapsedTime > tankElapsed) {
                tankElapsed = (float) (elapsedTime + Math.random() * 5);
                tanks.add(new Tank(game, world, loader));
                addActor(tanks.get(tanks.size() - 1));
            }

            /**
             * RANDOM IDŐKÖZÖNKÉNT ÚJ PÉNZ HOZZÁADÁSA
             * */
            if (elapsedTime > coinElapsed) {
                coinElapsed = (float) (elapsedTime + Math.random() * 3);
                coins.add(new Coin(game, this));
                addActor(coins.get(coins.size() - 1));
            }

            /**
             * HA A TANK MÁR BŐVEN NEM LÁTSZIK, AKKOR TÁVOLÍTSUK EL
             * **/
            for (Tank tank : tanks) {
                if (tank.getX() < -tank.getWidth() * 2) {
                    tank.remove();
                    tanks.remove(tank);
                    break;//LISTA HOSSZÁNAK VÁLTOZÁSA MIATTI EXCEPTION ELKERÜLÉSE EGY BREAKKEL
                }
            }

            /**
             * HA A PÉNZ MÁR BŐVEN NEM LÁTSZIK, AKKOR TÁVOLÍTSUK EL
             * **/
            for (Coin coin : coins) {
                if (coin.getX() < -coin.getWidth() * 2) {
                    coin.remove();
                    coins.remove(coin);
                    break;//LISTA HOSSZÁNAK VÁLTOZÁSA MIATTI EXCEPTION ELKERÜLÉSE EGY BREAKKEL
                }
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

            //TANKOK ÚJRAINDÍTÁSA
            for (Tank t : tanks)
                if(t.getFps() == 0) {
                    ((Box2DWorldHelper) t.getActorWorldHelper()).getBody().setActive(true);
                    t.setFps(15);
                }
        }
        /**
         * HA VÉGE A JÁTÉKNAK AKKOR ZSOLTI VÉREZZEN
         * **/
        else if(zsolti.isDead){
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

            //TANKOK MEGÁLLÍTÁSA
            for (Tank t : tanks)
                if(t.getFps() != 0) {
                    ((Box2DWorldHelper) t.getActorWorldHelper()).getBody().setActive(false);
                    t.setFps(0);
                }

            //HA RÁZKÓDIK A KÉPERNYŐ AKKOR ÁLLÍTSUK VISSZA
            offset = 1;
            getViewport().setScreenX(0);
            getViewport().setScreenY(0);
        }
    }
}
