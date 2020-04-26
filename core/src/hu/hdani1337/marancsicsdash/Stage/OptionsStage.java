package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;
import hu.hdani1337.marancsicsdash.Actor.Marancsics;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;
import hu.hdani1337.marancsicsdash.HudActors.Logo;
import hu.hdani1337.marancsicsdash.HudActors.ShopBackgroundPreview;
import hu.hdani1337.marancsicsdash.HudActors.TextBox;
import hu.hdani1337.marancsicsdash.MarancsicsDash;
import hu.hdani1337.marancsicsdash.SoundManager;

import static hu.hdani1337.marancsicsdash.MarancsicsDash.UpdatePresence;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.preferences;
import static hu.hdani1337.marancsicsdash.Stage.GameStage.selectedMarancsics;
import static hu.hdani1337.marancsicsdash.Stage.MenuBackgroundStage.MENU_BG_TEXTURE;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtBox;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtConstructor;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtCorona;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtDesert;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtOcean;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtSiberia;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtWarrior;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtZala;

public class OptionsStage extends PrettyStage {
    //region AssetList
    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(MENU_BG_TEXTURE);
        assetList.collectAssetDescriptor(TextBox.class,assetList);
        assetList.collectAssetDescriptor(Logo.class,assetList);
        assetList.collectAssetDescriptor(ShopBackgroundPreview.class,assetList);
        assetList.collectAssetDescriptor(Marancsics.class,assetList);
        assetList.collectAssetDescriptor(Zsolti.class,assetList);
        SoundManager.load(assetList);
    }
    //endregion
    //region Változók
    public static int difficulty = preferences.getInteger("difficulty");
    public static int gamemode = preferences.getInteger("gamemode");
    public static int selectedBackground = preferences.getInteger("selectedBackground");
    public static int windowWidth = preferences.getInteger("windowWidth");
    public static int windowHeight = preferences.getInteger("windowHeight");
    public static boolean fullscreen = preferences.getBoolean("fullscreen");

    private OneSpriteStaticActor MenuBackground;

    private Logo optionsLogo;

    private TextBox backButton;
    private TextBox muteButton;
    private TextBox gameModeButton;
    private TextBox difficultyButton;
    private TextBox resolutionButton;
    private TextBox fullscreenButton;
    private TextBox warningBox;

    private boolean setBack;

    private TextBox backgroundPreviewBackground;
    private ShopBackgroundPreview backgroundPreview;
    private TextBox backgroundPreviewText;
    private TextBox disclaimer;
    private TextBox disclaimer2;
    private Zsolti zsoltiPreview;
    private TextBox zsoltiPreviewText;
    private Marancsics marancsicsPreview;
    private TextBox marancsicsPreviewText;

    private Vector2 Resolution;
    //endregion
    //region Konstruktor
    public OptionsStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }
    //endregion
    //region Absztrakt metódusok
    @Override
    public void assignment() {
        Resolution = new Vector2(windowWidth,windowHeight);
        if(windowHeight == 0 && windowWidth == 0) Resolution = new Vector2(1280,720);
        SoundManager.assign();
        MarancsicsDash.presenceDetail = "Tweaking the settings";
        UpdatePresence();
        if(!muted)
            SoundManager.menuMusic.play();
        setBack = false;
        MenuBackground = new OneSpriteStaticActor(game,MENU_BG_TEXTURE);
        backButton = new TextBox(game,"Vissza a menübe");
        muteButton = new TextBox(game, "Némítás: -NULL-");
        gameModeButton = new TextBox(game, "Játékmód: -NULL-");
        difficultyButton = new TextBox(game, "Nehézség: -NULL-");
        resolutionButton = new TextBox(game, "Felbontás: 720p");
        fullscreenButton = new TextBox(game, "Teljes képernyö: -NULL-");
        warningBox = new TextBox(game, "A módosítások a menübe\nlépéskor lépnek érvénybe!");
        optionsLogo = new Logo(game, Logo.LogoType.OPTIONS);
        backgroundPreview = new ShopBackgroundPreview(game);
        backgroundPreviewBackground = new TextBox(game, " ");
        backgroundPreview.step(selectedBackground);
        disclaimer = new TextBox(game,"A háttér vagy kinézet változtatásához\nkattints a képére\n(Ha nem változik meg,\nakkor még nem vásároltál egyet sem)",0.5f);
        disclaimer2 = new TextBox(game,"A kívánt beállítás megváltoztatásához\nkattints a szövegére",0.5f);
        backgroundPreviewText = new TextBox(game,"Csernobil,Szahara,Szibéria,Zala,Atlanti-óceán".split(",")[selectedBackground]);
        marancsicsPreviewText = new TextBox(game,"Programozó,Boxoló,Építész,Beteg".split(",")[preferences.getInteger("selectedMarancsics")]);
        zsoltiPreviewText = new TextBox(game,"Kalandor,Harcos".split(",")[preferences.getInteger("selectedZsolti")]);
        marancsicsPreview = new Marancsics(game, selectedMarancsics);
        zsoltiPreview = new Zsolti(game, GameStage.selectedZsolti);
        setTexts();
    }

    @Override
    public void setSizes() {
        if(getViewport().getWorldWidth() > MenuBackground.getWidth()) MenuBackground.setWidth(getViewport().getWorldWidth());
        backgroundPreviewBackground.setSize(backgroundPreview.getWidth()+16,backgroundPreview.getHeight()+18);
    }

    @Override
    public void setPositions() {
        if(getViewport().getWorldWidth() < MenuBackground.getWidth()) MenuBackground.setX((getViewport().getWorldWidth()-MenuBackground.getWidth())/2);
        backButton.setPosition(getViewport().getWorldWidth() - (backButton.getWidth() + 45),50);
        optionsLogo.setPosition(getViewport().getWorldWidth()/2 - optionsLogo.getWidth()/2, getViewport().getWorldHeight() - optionsLogo.getHeight()*1.15f);
        warningBox.setPosition(getViewport().getWorldWidth()/2-warningBox.getWidth()/2,-warningBox.getHeight());
        backgroundPreview.setPosition(getViewport().getWorldWidth()/2-backgroundPreview.getWidth()/2,getViewport().getWorldHeight()/2-backgroundPreview.getHeight()/2+72);
        backgroundPreviewBackground.setPosition(backgroundPreview.getX()-8,backgroundPreview.getY()-9);
        backgroundPreviewText.setPosition(backgroundPreviewBackground.getX()+backgroundPreviewBackground.getWidth()/2-backgroundPreviewText.getWidth()/2,backgroundPreviewBackground.getY()-backgroundPreviewText.getHeight()-18);
        disclaimer.setPosition(getViewport().getWorldWidth()-disclaimer.getWidth()-16,getViewport().getWorldHeight()-disclaimer.getHeight()-16);
        fullscreenButton.setPosition(getViewport().getWorldWidth()/2-fullscreenButton.getWidth()/2,backgroundPreviewText.getY()-fullscreenButton.getHeight()-64);
        resolutionButton.setPosition(getViewport().getWorldWidth()/2-resolutionButton.getWidth()/2,fullscreenButton.getY()-resolutionButton.getHeight()-16);
        marancsicsPreview.setPosition(getViewport().getWorldWidth()*0.7f,backgroundPreviewBackground.getY()+backgroundPreviewBackground.getHeight()/2-marancsicsPreview.getHeight()/2+16);
        zsoltiPreview.setPosition(marancsicsPreview.getX()+marancsicsPreview.getWidth()+100,backgroundPreviewBackground.getY()+backgroundPreviewBackground.getHeight()/2-zsoltiPreview.getHeight()/2);
        zsoltiPreviewText.setPosition(zsoltiPreview.getX()+zsoltiPreview.getWidth()/2-zsoltiPreviewText.getWidth()/2,zsoltiPreview.getY()-zsoltiPreviewText.getHeight()-16);
        marancsicsPreviewText.setPosition(marancsicsPreview.getX()+marancsicsPreview.getWidth()/2-marancsicsPreviewText.getWidth()/2-8,zsoltiPreviewText.getY());
        muteButton.setPosition(backgroundPreviewBackground.getX()/2-muteButton.getWidth()/2,getViewport().getWorldHeight()-535);
        gameModeButton.setPosition(backgroundPreviewBackground.getX()/2-gameModeButton.getWidth()/2,getViewport().getWorldHeight()-410);
        difficultyButton.setPosition(backgroundPreviewBackground.getX()/2-difficultyButton.getWidth()/2,getViewport().getWorldHeight()-285);
        disclaimer2.setPosition(16,getViewport().getWorldHeight()-disclaimer2.getHeight()-16);
    }

    int bgID = selectedBackground;
    int marancsID = preferences.getInteger("selectedMarancsics");
    int zsoltID = preferences.getInteger("selectedZsolti");

    @Override
    public void addListeners() {
        backButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                preferences.putInteger("difficulty",difficulty);
                preferences.putInteger("gamemode",gamemode);
                preferences.putInteger("selectedBackground",selectedBackground);
                preferences.putInteger("windowWidth", (int) Resolution.x);
                preferences.putInteger("windowHeight", (int) Resolution.y);
                preferences.putBoolean("fullscreen", fullscreen);
                preferences.putBoolean("muted",muted);
                preferences.flush();
                setBack = true;
                windowHeight = (int) Resolution.y;
                windowWidth = (int) Resolution.x;
                if(fullscreen && !Gdx.graphics.isFullscreen()) Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                else if(!fullscreen) Gdx.graphics.setWindowedMode((int)Resolution.x,(int)Resolution.y);
            }
        });

        muteButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(!muted){
                    muted = true;
                    SoundManager.menuMusic.pause();
                }
                else{
                    muted = false;
                    SoundManager.menuMusic.play();
                }
                setTexts();
            }
        });

        gameModeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(gamemode == 1){
                    gamemode = 2;
                }
                else{
                    gamemode = 1;
                }
                setTexts();
            }
        });

        difficultyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(difficulty == 3){
                    difficulty = 1;
                }
                else{
                    difficulty++;
                }
                setTexts();
            }
        });

        resolutionButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Switch nem működik, nemtudom miért
                if(Resolution.x == 1920){
                    Resolution.x = 640;
                    Resolution.y = 360;
                }else if(Resolution.x == 1600){
                    Resolution.x = 1920;
                    Resolution.y = 1080;
                }else if(Resolution.x == 1280){
                    Resolution.x = 1600;
                    Resolution.y = 900;
                }else if(Resolution.x == 854){
                    Resolution.x = 1280;
                    Resolution.y = 720;
                }else if(Resolution.x == 640){
                    Resolution.x = 854;
                    Resolution.y = 480;
                }
                setTexts();
            }
        });

        fullscreenButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                fullscreen = !fullscreen;
                setTexts();
            }
        });

        backgroundPreview.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                bgID++;
                if(bgID > 4) bgID = 0;
                switch (bgID){
                    case 1:{
                        if(boughtDesert) {
                            backgroundPreview.sprite.setTexture(game.getMyAssetManager().getTexture(ShopBackgroundPreview.SZAHARA_PREVIEW_TEXTURE));
                            backgroundPreviewText.setText("Szahara");
                            break;
                        }
                        else bgID++;
                    }
                    case 2:{
                        if(boughtSiberia) {
                            backgroundPreview.sprite.setTexture(game.getMyAssetManager().getTexture(ShopBackgroundPreview.SZIBERIA_PREVIEW_TEXTURE));
                            backgroundPreviewText.setText("Szibéria");
                            break;
                        }
                        else bgID++;
                    }
                    case 3:{
                        if(boughtZala) {
                            backgroundPreview.sprite.setTexture(game.getMyAssetManager().getTexture(ShopBackgroundPreview.ZALA_PREVIEW_TEXTURE));
                            backgroundPreviewText.setText("Zala");
                            break;
                        }
                        else bgID++;
                    }
                    case 4:{
                        if(boughtOcean) {
                            backgroundPreview.sprite.setTexture(game.getMyAssetManager().getTexture(ShopBackgroundPreview.OCEAN_PREVIEW_TEXTURE));
                            backgroundPreviewText.setText("Atlanti-óceán");
                            break;
                        }
                        else bgID = 0;
                    }
                    default:{
                        backgroundPreview.sprite.setTexture(game.getMyAssetManager().getTexture(ShopBackgroundPreview.CSERNOBIL_PREVIEW_TEXTURE));
                        backgroundPreviewText.setText("Csernobil");
                        break;
                    }
                }
                backgroundPreviewText.setPosition(backgroundPreviewBackground.getX()+backgroundPreviewBackground.getWidth()/2-backgroundPreviewText.getWidth()/2,backgroundPreviewBackground.getY()-backgroundPreviewText.getHeight()-18);
                selectedBackground = bgID;
            }
        });

        marancsicsPreview.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                marancsID++;
                if(marancsID > 3) marancsID = 0;
                switch (marancsID){
                    case 1:{
                        if(boughtBox) {
                            marancsicsPreview.setTextureAtlas(game.getMyAssetManager().getTextureAtlas(Marancsics.MARANCSICS_BOX));
                            selectedMarancsics = Marancsics.MarancsicsType.BOX;
                            marancsicsPreviewText.setText("Boxoló");
                            break;
                        }
                        else marancsID++;
                    }
                    case 2:{
                        if(boughtConstructor) {
                            marancsicsPreview.setTextureAtlas(game.getMyAssetManager().getTextureAtlas(Marancsics.MARANCSICS_CONSTRUCTOR));
                            selectedMarancsics = Marancsics.MarancsicsType.CONSTRUCTOR;
                            marancsicsPreviewText.setText("Építész");
                            break;
                        }
                        else marancsID++;
                    }
                    case 3:{
                        if(boughtCorona) {
                            marancsicsPreview.setTextureAtlas(game.getMyAssetManager().getTextureAtlas(Marancsics.MARANCSICS_CORONA));
                            selectedMarancsics = Marancsics.MarancsicsType.CORONA;
                            marancsicsPreviewText.setText("Beteg");
                            break;
                        }
                        else marancsID = 0;
                    }
                    default:{
                        marancsicsPreview.setTextureAtlas(game.getMyAssetManager().getTextureAtlas(Marancsics.MARANCSICS_ATLAS));
                        selectedMarancsics = Marancsics.MarancsicsType.MARANCSICS;
                        marancsicsPreviewText.setText("Programozó");
                        break;
                    }
                }
                marancsicsPreviewText.setPosition(marancsicsPreview.getX()+marancsicsPreview.getWidth()/2-marancsicsPreviewText.getWidth()/2-8,zsoltiPreviewText.getY());
                preferences.putInteger("selectedMarancsics",marancsID);
                preferences.flush();
            }
        });

        zsoltiPreview.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                zsoltID++;
                if(zsoltID > 1) zsoltID = 0;
                switch (zsoltID){
                    case 1:{
                        if(boughtWarrior) {
                            zsoltiPreview.setTextureAtlas(game.getMyAssetManager().getTextureAtlas(Zsolti.ZSOLTI_WARRIOR));
                            GameStage.selectedZsolti = Zsolti.ZsoltiType.WARRIOR;
                            zsoltiPreviewText.setText("Harcos");
                            break;
                        }
                        else zsoltID = 0;
                    }
                    default:{
                        zsoltiPreview.setTextureAtlas(game.getMyAssetManager().getTextureAtlas(Zsolti.ZSOLTI_ATLAS));
                        GameStage.selectedZsolti = Zsolti.ZsoltiType.ZSOLTI;
                        zsoltiPreviewText.setText("Kalandor");
                        break;
                    }
                }
                zsoltiPreviewText.setPosition(zsoltiPreview.getX()+zsoltiPreview.getWidth()/2-zsoltiPreviewText.getWidth()/2,zsoltiPreview.getY()-zsoltiPreviewText.getHeight()-16);
                preferences.putInteger("selectedZsolti",zsoltID);
                preferences.flush();
            }
        });
    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(MenuBackground);
        addActor(optionsLogo);
        addActor(difficultyButton);
        addActor(backButton);
        addActor(muteButton);
        addActor(gameModeButton);
        addActor(warningBox);
        addActor(backgroundPreviewBackground);
        addActor(backgroundPreview);
        addActor(backgroundPreviewText);
        addActor(disclaimer);
        addActor(disclaimer2);
        addActor(marancsicsPreview);
        addActor(zsoltiPreview);
        addActor(zsoltiPreviewText);
        addActor(marancsicsPreviewText);
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            addActor(resolutionButton);
            addActor(fullscreenButton);
        }
    }
    //endregion
    //region Gombok szövegeinek átállító metódusa
    private void setTexts(){
        //Némítás
        if(muted){
            muteButton.setText("Némítás: Némítva");
            //music.stop();
        }
        else{
            muteButton.setText("Némítás: Nincs némítva");
        }

        //Nehézségek
        switch (difficulty){
            case 1:{
                difficultyButton.setText("Nehézség: Könnyü");
                break;
            }
            case 2:{
                difficultyButton.setText("Nehézség: Normál");
                break;
            }
            case 3:{
                difficultyButton.setText("Nehézség: Nehéz");
                break;
            }
            default:{
                difficultyButton.setText("Nehézség: Normál");
                preferences.putInteger("difficulty",2);
                preferences.flush();
                break;
            }
        }

        //Játékmód
        switch (gamemode){
            case 1:{
                gameModeButton.setText("Játékmód: Story");
                break;
            }
            default:{
                gameModeButton.setText("Játékmód: Endless");
                break;
            }
        }

        muteButton.setPosition(backgroundPreviewBackground.getX()/2-muteButton.getWidth()/2,getViewport().getWorldHeight()-535);
        gameModeButton.setPosition(backgroundPreviewBackground.getX()/2-gameModeButton.getWidth()/2,getViewport().getWorldHeight()-410);
        difficultyButton.setPosition(backgroundPreviewBackground.getX()/2-difficultyButton.getWidth()/2,getViewport().getWorldHeight()-285);

        if(!fullscreen) {
            resolutionButton.setText("Felbontás: " + (int) Resolution.y + "p");
            resolutionButton.setColor(Color.WHITE);
        }
        else {
            resolutionButton.setText("Felbontás: " + Gdx.graphics.getHeight() + "p");
            resolutionButton.setColor(Color.GRAY);
        }
        fullscreenButton.setText("Teljes képernyö: " + ((fullscreen) ? "Be" : "Ki"));

        if(Gdx.app.getType() == Application.ApplicationType.Desktop){
            if(fullscreen == Gdx.graphics.isFullscreen() && Resolution.y == Gdx.graphics.getHeight()) change = false;
            else change = true;
        }
    }
    //endregion
    //region Act metódusai
    boolean change = false;
    float alpha = 0;
    float bgAlpha = 1;
    @Override
    public void act(float delta) {
        super.act(delta);
        if(!setBack) fadeIn();
        else fadeOut();
        checkChangedSettingsOnPc();
        setBackgroundAlpha();
    }

    /**
     * Áttűnéssel jönnek be az actorok
     * **/
    private void fadeIn(){
        if (alpha < 0.95) alpha += 0.025;
        else alpha = 1;
        setAlpha();
    }

    /**
     * Áttűnéssel mennek ki az actorok
     * **/
    private void fadeOut(){
        if (alpha > 0.05) {
            setAlpha();
            alpha -= 0.05;
            if(bgAlpha<0.95) bgAlpha+= 0.05;
            MenuBackground.setAlpha(bgAlpha);
        } else {
            //Ha már nem látszanak akkor megyünk vissza a menübe
            alpha = 0;
            setAlpha();
            game.setScreenBackByStackPopWithPreloadAssets(new LoadingStage(game));
            addTimer(new TickTimer(1,false,new TickTimerListener(){
                @Override
                public void onTick(Timer sender, float correction) {
                    super.onTick(sender, correction);
                    setBack = false;
                }
            }));
        }
    }

    /**
     * Ez a figyelmeztető doboz csak akkor jön elő, ha PC-n játszuk a játékot
     * **/
    private void checkChangedSettingsOnPc(){
        if(change){
            if(warningBox.getY()<15) {
                warningBox.setY(warningBox.getY() + 5);
            }
        }else{
            if(warningBox.getY()>-warningBox.getHeight()) {
                warningBox.setY(warningBox.getY() - 5);
            }
        }
    }

    /**
     * Háttér átlátszóságát állítja be
     * **/
    private void setBackgroundAlpha(){
        if(bgAlpha>0.25 && !setBack){
            bgAlpha-=0.025;
            MenuBackground.setAlpha(bgAlpha);
        }
    }

    /**
     * Actorok átlátszóságának egyidejű beállítása
     * **/
    private void setAlpha(){
        optionsLogo.setAlpha(alpha);
        backButton.setAlpha(alpha);
        muteButton.setAlpha(alpha);
        gameModeButton.setAlpha(alpha);
        difficultyButton.setAlpha(alpha);
        resolutionButton.setAlpha(alpha);
        fullscreenButton.setAlpha(alpha);
        zsoltiPreview.setAlpha(alpha);
        marancsicsPreview.setAlpha(alpha);
        backgroundPreviewBackground.setAlpha(alpha);
        backgroundPreview.setAlpha(alpha);
        backgroundPreviewText.setAlpha(alpha);
        disclaimer.setAlpha(alpha);
        disclaimer2.setAlpha(alpha);
        zsoltiPreviewText.setAlpha(alpha);
        marancsicsPreviewText.setAlpha(alpha);
    }
    //endregion
}
