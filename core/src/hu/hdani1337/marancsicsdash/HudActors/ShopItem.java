package hu.hdani1337.marancsicsdash.HudActors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.IPrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyGroup;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteActor;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;
import hu.hdani1337.marancsicsdash.Actor.Background;
import hu.hdani1337.marancsicsdash.Actor.Coin;
import hu.hdani1337.marancsicsdash.Actor.Marancsics;
import hu.hdani1337.marancsicsdash.Actor.SuperCoin;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;
import hu.hdani1337.marancsicsdash.SoundManager;
import hu.hdani1337.marancsicsdash.Stage.ShopStage;

import static hu.hdani1337.marancsicsdash.HudActors.TextBox.RETRO_FONT;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.preferences;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtBox;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtCoin;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtConstructor;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtCorona;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtDesert;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtDouble;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtInstantBoss;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtOcean;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtSiberia;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtWarrior;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtZala;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtZsolti;

public class ShopItem extends MyGroup implements IPrettyStage {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(TextBox.class,assetList);
        assetList.collectAssetDescriptor(Zsolti.class,assetList);
        assetList.collectAssetDescriptor(ShopBackgroundPreview.class,assetList);
        assetList.addFont(RETRO_FONT, RETRO_FONT, 32, Color.WHITE, AssetList.CHARS);
        SoundManager.load(assetList);
    }

    private TextBox background;
    private OneSpriteActor item;
    private Coin coinIcon;
    private MyLabel priceLabel;
    private MyLabel nameLabel;
    private ShopItemType type;
    private Background.BackgroundType backgroundType;
    private Marancsics.MarancsicsType marancsicsType;
    private Zsolti.ZsoltiType zsoltiType;

    private boolean setBackground;
    private boolean boughtAlready;
    private int price;
    private String name;

    private boolean touch;

    /**
     * ITEM KONSTRUKTOR
     * **/
    public ShopItem(MyGame game, boolean setBackground, ShopItemType type, String name) {
        super(game);
        this.setBackground = setBackground;
        this.type = type;
        this.backgroundType = null;
        this.zsoltiType = null;
        this.marancsicsType = null;
        this.name = name;
        assignment();
        setSizes();
        setPositions();
        addListeners();
        addActors();
    }

    /**
     * BACKGROUND KONSTRUKTOR
     * **/
    public ShopItem(MyGame game, Background.BackgroundType backgroundType) {
        super(game);
        this.setBackground = true;
        this.type = null;
        this.zsoltiType = null;
        this.marancsicsType = null;
        this.backgroundType = backgroundType;
        this.name = "";
        assignment();
        setSizes();
        setPositions();
        addListeners();
        addActors();
    }

    /**
     * MARANCSICS SKIN KONSTRUKTOR
     * **/
    public ShopItem(MyGame game, Marancsics.MarancsicsType marancsicsType, String name) {
        super(game);
        this.setBackground = false;
        this.type = null;
        this.zsoltiType = null;
        this.marancsicsType = marancsicsType;
        this.backgroundType = null;
        this.name = name;
        assignment();
        setSizes();
        setPositions();
        addListeners();
        addActors();
    }

    /**
     * ZSOLTI SKIN KONSTRUKTOR
     * **/
    public ShopItem(MyGame game, Zsolti.ZsoltiType zsoltiType, String name) {
        super(game);
        this.setBackground = false;
        this.type = null;
        this.zsoltiType = zsoltiType;
        this.marancsicsType = null;
        this.backgroundType = null;
        this.name = name;
        assignment();
        setSizes();
        setPositions();
        addListeners();
        addActors();
    }

    @Override
    public void assignment() {
        makeItem();
        if(setBackground) background = new TextBox(game," ");
        coinIcon = new Coin(game, true);
        priceLabel = new MyLabel(game, price + "", new Label.LabelStyle(game.getMyAssetManager().getFont(RETRO_FONT), Color.WHITE)) {
            @Override
            public void init() {

            }
        };
        nameLabel = new MyLabel(game, name, new Label.LabelStyle(game.getMyAssetManager().getFont(RETRO_FONT), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
            }
        };
        if(!boughtAlready) item.setColor(Color.GRAY);
    }

    @Override
    public void setSizes() {
        if(background!=null) {
            background.setSize(item.getWidth() + 16, item.getHeight() + 18);
            coinIcon.setSize(96, 96);
        }else{
            coinIcon.setSize(64,64);
        }
    }

    @Override
    public void setPositions() {
        if(background != null) {
            background.setPosition(item.getX() - 8, item.getY() - 9);
            coinIcon.setPosition(item.getX()+item.getWidth()/2-coinIcon.getWidth()-15, item.getY() +item.getHeight()/2- coinIcon.getHeight()/2);
            priceLabel.setPosition(coinIcon.getX() + coinIcon.getWidth()+15, coinIcon.getY() + coinIcon.getHeight() / 2 - priceLabel.getHeight() / 2);
        }
        else {
            nameLabel.setPosition(item.getX()+item.getWidth()/2-nameLabel.getWidth()/2,item.getY()+item.getHeight()+15);
            coinIcon.setPosition(item.getX() + item.getWidth()/2 - coinIcon.getWidth()-16, item.getY() - coinIcon.getHeight());
            priceLabel.setPosition(coinIcon.getX() + coinIcon.getWidth()+8, coinIcon.getY() + coinIcon.getHeight() / 2 - priceLabel.getHeight() / 2);
            if(marancsicsType != null) nameLabel.setY(nameLabel.getY()-25);
        }
    }

    @Override
    public void addListeners() {
        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(touch){
                    if(!boughtAlready) {
                        if (Coin.coin >= price) {
                            if (!muted) SoundManager.paySound.play();
                            Coin.coin -= price;
                            coinIcon.remove();
                            priceLabel.remove();
                            if (type != null) {
                                switch (type) {
                                    case SUPERCOIN: {
                                        boughtCoin = true;
                                        preferences.putLong("coin", Coin.coin);
                                        preferences.putBoolean("boughtCoin", boughtCoin);
                                        preferences.flush();
                                        break;
                                    }
                                    case DOUBLEJUMP: {
                                        boughtDouble = true;
                                        preferences.putLong("coin", Coin.coin);
                                        preferences.putBoolean("boughtDouble", boughtDouble);
                                        preferences.flush();
                                        break;
                                    }
                                    case INSTANTBOSS: {
                                        boughtInstantBoss = true;
                                        preferences.putLong("coin", Coin.coin);
                                        preferences.putBoolean("boughtInstantBoss", boughtInstantBoss);
                                        preferences.flush();
                                        break;
                                    }
                                    case SUPERZSOLTI: {
                                        boughtZsolti = true;
                                        preferences.putLong("coin", Coin.coin);
                                        preferences.putBoolean("boughtZsolti", boughtZsolti);
                                        preferences.flush();
                                        break;
                                    }
                                }
                            } else if (backgroundType != null) {
                                switch (backgroundType) {
                                    case ZALA: {
                                        boughtZala = true;
                                        preferences.putLong("coin", Coin.coin);
                                        preferences.putBoolean("boughtZala", boughtZala);
                                        preferences.flush();
                                        break;
                                    }
                                    case OCEAN: {
                                        boughtOcean = true;
                                        preferences.putLong("coin", Coin.coin);
                                        preferences.putBoolean("boughtOcean", boughtOcean);
                                        preferences.flush();
                                        break;
                                    }
                                    case SZAHARA: {
                                        boughtDesert = true;
                                        preferences.putLong("coin", Coin.coin);
                                        preferences.putBoolean("boughtDesert", boughtDesert);
                                        preferences.flush();
                                        break;
                                    }
                                    case SZIBERIA: {
                                        boughtSiberia = true;
                                        preferences.putLong("coin", Coin.coin);
                                        preferences.putBoolean("boughtSiberia", boughtSiberia);
                                        preferences.flush();
                                        break;
                                    }
                                }
                            } else if (marancsicsType != null){
                                switch (marancsicsType){
                                    case BOX:{
                                        boughtBox = true;
                                        preferences.putLong("coin", Coin.coin);
                                        preferences.putBoolean("boughtBox", boughtBox);
                                        preferences.flush();
                                        break;
                                    }
                                    case CORONA:{
                                        boughtCorona = true;
                                        preferences.putLong("coin", Coin.coin);
                                        preferences.putBoolean("boughtCorona", boughtCorona);
                                        preferences.flush();
                                        break;
                                    }
                                    case CONSTRUCTOR:{
                                        boughtConstructor = true;
                                        preferences.putLong("coin", Coin.coin);
                                        preferences.putBoolean("boughtConstructor", boughtConstructor);
                                        preferences.flush();
                                        break;
                                    }
                                }
                            } else if (zsoltiType != null){
                                switch (zsoltiType){
                                    case WARRIOR:{
                                        boughtWarrior = true;
                                        preferences.putLong("coin", Coin.coin);
                                        preferences.putBoolean("boughtWarrior", boughtWarrior);
                                        preferences.flush();
                                        break;
                                    }
                                }
                            }
                            boughtAlready = true;
                            item.setColor(1, 1, 1, 1);
                            if (getStage() != null && getStage() instanceof ShopStage)
                                ((ShopStage) getStage()).coinText.setText(Coin.coin + "");
                        } else if (!muted) SoundManager.errorSound.play();
                    }
                }
            }
        });
    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        if(background != null) addActor(background);
        if(name != "") addActor(nameLabel);
        if(!boughtAlready){
            addActor(coinIcon);
            addActor(priceLabel);
        }
        addActor(item);
    }

    private void makeItem(){
        if(type != null) {
            switch (type) {
                case SUPERCOIN: {
                    item = new SuperCoin(game, false);
                    item.setSize(item.getWidth()*1.5f,item.getHeight()*1.5f);
                    boughtAlready = boughtCoin;
                    price = 1500;
                    break;
                }
                case DOUBLEJUMP: {
                    item = new Zsolti(game, Zsolti.ZsoltiType.ZSOLTI) {
                        float time = -0.5f;

                        @Override
                        public void act(float delta) {
                            super.act(delta);
                            time += delta;
                            if (time >= 0) {
                                if (time < 0.5) {
                                    setY(getY() + 8);
                                    setRotation(getRotation() + 5);
                                } else if (time < 0.7) {
                                    setY(getY() - 8);
                                    setRotation(getRotation() - 5);
                                } else if (time < 0.85) {
                                    setY(getY() + 8);
                                    setRotation(getRotation() + 5);
                                } else if (time < 1.3) {
                                    setY(getY() - 8);
                                    setRotation(getRotation() - 5);
                                } else time = -1.5f;
                            }
                        }
                    };
                    boughtAlready = boughtDouble;
                    price = 250;
                    break;
                }
                case INSTANTBOSS: {
                    boughtAlready = boughtInstantBoss;
                    price = 100;
                    item = new InstantBoss(game);
                    break;
                }
                case SUPERZSOLTI: {
                    boughtAlready = boughtZsolti;
                    price = 250;
                    item = new Zsolti(game, Zsolti.ZsoltiType.ZSOLTI);
                    ((Zsolti)item).superTime = 3;
                    addTimer(new TickTimer(5,true,new TickTimerListener(){
                        @Override
                        public void onTick(Timer sender, float correction) {
                            super.onTick(sender, correction);
                            ((Zsolti)item).superTime = 3;
                        }
                    }));
                    break;
                }
            }
        } else if(backgroundType != null){
            item = new ShopBackgroundPreview(game);
            ((ShopBackgroundPreview)item).step(backgroundType);
            price = 200;
            switch (backgroundType){
                case ZALA:{
                    boughtAlready = boughtZala;
                    break;
                }
                case OCEAN:{
                    boughtAlready = boughtOcean;
                    break;
                }
                case SZAHARA: {
                    boughtAlready = boughtDesert;
                    break;
                }
                case SZIBERIA:{
                    boughtAlready = boughtSiberia;
                    break;
                }
            }
        }else if (marancsicsType != null){
            switch (marancsicsType){
                case BOX:{
                    boughtAlready = boughtBox;
                    item = new Marancsics(game, Marancsics.MarancsicsType.BOX);
                    price = 250;
                    break;
                }
                case CORONA:{
                    boughtAlready = boughtCorona;
                    item = new Marancsics(game, Marancsics.MarancsicsType.CORONA);
                    price = 300;
                    break;
                }
                case CONSTRUCTOR:{
                    boughtAlready = boughtConstructor;
                    item = new Marancsics(game, Marancsics.MarancsicsType.CONSTRUCTOR);
                    price = 250;
                    break;
                }
            }
        } else if (zsoltiType != null){
            switch (zsoltiType){
                case WARRIOR:{
                    boughtAlready = boughtWarrior;
                    item = new Zsolti(game, Zsolti.ZsoltiType.WARRIOR);
                    price = 500;
                    break;
                }
            }
        }
    }

    public void setAlpha(float alpha){
        if(background!=null) background.setAlpha(alpha);
        item.setAlpha(alpha);
        coinIcon.setAlpha(alpha);
        priceLabel.setColor(priceLabel.getColor().r,priceLabel.getColor().g,priceLabel.getColor().b,alpha);
        nameLabel.setColor(priceLabel.getColor().r,priceLabel.getColor().g,priceLabel.getColor().b,alpha);

        if(alpha == 0) {
            setZIndex(0);
            touch = false;
        }
        else{
            setZIndex(100);
            touch = true;
        }
    }

    @Override
    public float getWidth() {
        if(background != null) return background.getWidth();
        else return item.getWidth();
    }

    @Override
    public float getHeight() {
        if(background != null) return background.getHeight();
        else return item.getHeight();
    }
}
