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
import hu.hdani1337.marancsicsdash.HudActors.Logo;
import hu.hdani1337.marancsicsdash.HudActors.TextBox;
import hu.hdani1337.marancsicsdash.MarancsicsDash;
import hu.hdani1337.marancsicsdash.SoundManager;

import static hu.hdani1337.marancsicsdash.MarancsicsDash.UpdatePresence;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.preferences;
import static hu.hdani1337.marancsicsdash.Stage.MenuBackgroundStage.MENU_BG_TEXTURE;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtDesert;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtOcean;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtSiberia;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtZala;

public class OptionsStage extends PrettyStage {

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(MENU_BG_TEXTURE);
        SoundManager.load(assetList);
    }

    public static int difficulty = preferences.getInteger("difficulty");;
    public static int gamemode = preferences.getInteger("gamemode");
    public static int selectedBackground = preferences.getInteger("selectedBackground");
    public static int windowWidth = preferences.getInteger("windowWidth");
    public static int windowHeight = preferences.getInteger("windowHeight");
    public static boolean fullscreen = preferences.getBoolean("fullscreen");

    public OptionsStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }

    private OneSpriteStaticActor MenuBackground;

    private Logo optionsLogo;

    private TextBox backButton;
    private TextBox muteButton;
    private TextBox gameModeButton;
    private TextBox backgroundTypeButton;
    private TextBox difficultyButton;
    private TextBox resolutionButton;
    private TextBox fullscreenButton;
    private TextBox warningBox;

    private boolean setBack;

    private Vector2 Resolution;

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
        backgroundTypeButton = new TextBox(game, "Háttér: -NULL-");
        difficultyButton = new TextBox(game, "Nehézség: -NULL-");
        resolutionButton = new TextBox(game, "Felbontás: 720p");
        fullscreenButton = new TextBox(game, "Teljes képernyö: -NULL-");
        warningBox = new TextBox(game, "A módosítások a menübe\nlépéskor lépnek érvénybe!");
        optionsLogo = new Logo(game, Logo.LogoType.OPTIONS);
        setTexts();
    }

    @Override
    public void setSizes() {
        if(getViewport().getWorldWidth() > MenuBackground.getWidth()) MenuBackground.setWidth(getViewport().getWorldWidth());
    }

    @Override
    public void setPositions() {
        if(getViewport().getWorldWidth() < MenuBackground.getWidth()) MenuBackground.setX((getViewport().getWorldWidth()-MenuBackground.getWidth())/2);
        muteButton.setPosition(75,100);
        gameModeButton.setPosition(75,225);
        difficultyButton.setPosition(75,350);
        backgroundTypeButton.setPosition(75,475);
        resolutionButton.setPosition(75,600);
        fullscreenButton.setPosition(75,725);
        backButton.setPosition(getViewport().getWorldWidth() - (backButton.getWidth() + 45),50);
        optionsLogo.setPosition(getViewport().getWorldWidth()/2 - optionsLogo.getWidth()/2, getViewport().getWorldHeight() - optionsLogo.getHeight()*1.15f);
        warningBox.setPosition(getViewport().getWorldWidth()/2-warningBox.getWidth()/2,-warningBox.getHeight());
    }

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

        backgroundTypeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if(selectedBackground < 4) selectedBackground++;
                else selectedBackground = 0;

                if(selectedBackground == 1 && !boughtSiberia) selectedBackground = 2;
                if(selectedBackground == 2 && !boughtZala) selectedBackground = 3;
                if(selectedBackground == 3 && !boughtDesert) selectedBackground = 4;
                if(selectedBackground == 4 && !boughtOcean) selectedBackground = 0;
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
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            addActor(resolutionButton);
            addActor(fullscreenButton);
        }
        if(boughtSiberia || boughtZala)
        {
            addActor(backgroundTypeButton);
        }
    }

    /**
     * Tudom hogy Switchel szebb és hatékonyabb, kuss
     * **/
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
                System.out.println("A fejlesztő egy buzi.");
                difficultyButton.setText("Nehézség: Hiba");
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

        //Háttér
        switch (selectedBackground){
            case 0:{
                backgroundTypeButton.setText("Háttér: Csernobil");
                break;
            }
            case 1:{
                backgroundTypeButton.setText("Háttér: Szibéria");
                break;
            }
            case 2:{
                backgroundTypeButton.setText("Háttér: Zala");
                break;
            }
            case 3:{
                backgroundTypeButton.setText("Háttér: Szahara");
                break;
            }
            case 4:{
                backgroundTypeButton.setText("Háttér: Atlanti-óceán");
                break;
            }
        }

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

    boolean change = false;
    float alpha = 0;
    float bgAlpha = 1;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!setBack) {
            //Áttűnéssel jönnek be az actorok
            if (alpha < 0.95) alpha += 0.025;
            else alpha = 1;
            setAlpha();
        }
        else
        {
            //Áttűnéssel mennek ki az actorok
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
        if(change){
            if(warningBox.getY()<15) {
                warningBox.setY(warningBox.getY() + 5);
            }
        }else{
            if(warningBox.getY()>-warningBox.getHeight()) {
                warningBox.setY(warningBox.getY() - 5);
            }
        }

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
        backgroundTypeButton.setAlpha(alpha);
        difficultyButton.setAlpha(alpha);
        resolutionButton.setAlpha(alpha);
        fullscreenButton.setAlpha(alpha);
    }
}
