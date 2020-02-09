package hu.hdani1337.marancsicsdash.HudActors;

import com.badlogic.gdx.Gdx;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Logo extends OneSpriteStaticActor {

    public static final String LOGO_TEXTURE = "pic/logo.png";
    public static final String MARANCSHOP_TEXTURE = "pic/marancshop.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(LOGO_TEXTURE);
        assetList.addTexture(MARANCSHOP_TEXTURE);
    }

    public enum LogoType{
        MENU, SHOP
    }

    public Logo(MyGame game, LogoType logotype) {
        super(game, LOGO_TEXTURE);
        switch (logotype){
            case MENU:{
                sprite.setTexture(game.getMyAssetManager().getTexture(LOGO_TEXTURE));
                break;
            }
            case SHOP:{
                sprite.setTexture(game.getMyAssetManager().getTexture(MARANCSHOP_TEXTURE));
                setSize(game.getMyAssetManager().getTexture(MARANCSHOP_TEXTURE).getWidth(),game.getMyAssetManager().getTexture(MARANCSHOP_TEXTURE).getHeight());
                break;
            }
            default:{
                Gdx.app.log("Logo", "Paraméterként megadott logotípus érvénytelen, alapértelmezettként a MenuLogo kerül beállításra!");
            }
        }
    }

    private int speed = 2;

    @Override
    public void act(float delta) {
        super.act(delta);
        setRotation(getRotation() + delta * speed);

        if(getRotation() >= 12 || getRotation() <= -12){
            speed *= -1;
        }

    }
}
