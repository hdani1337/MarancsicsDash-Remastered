package hu.hdani1337.marancsicsdash.HudActors;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.hdani1337.marancsicsdash.Stage.ShopStage;

public class ShopCategory extends OneSpriteStaticActor {

    public static final String CATEGORY_HATTEREK = "pic/ui/hatterek.png";
    public static final String CATEGORY_KEPESSEGEK = "pic/ui/kepessegek.png";
    public static final String CATEGORY_SKINEK = "pic/ui/skinek.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(CATEGORY_HATTEREK);
        assetList.addTexture(CATEGORY_KEPESSEGEK);
        assetList.addTexture(CATEGORY_SKINEK);
    }

    private ShopCategoryType type;

    public ShopCategory(MyGame game, ShopCategoryType type) {
        super(game, CATEGORY_HATTEREK);
        this.type = type;
        setTexture();
        addListeners();
    }

    private void setTexture(){
        switch (type){
            case BACKGROUND:{
                sprite.setTexture(game.getMyAssetManager().getTexture(CATEGORY_HATTEREK));
                break;
            }
            case ABILITIES:{
                sprite.setTexture(game.getMyAssetManager().getTexture(CATEGORY_KEPESSEGEK));
                setSize(game.getMyAssetManager().getTexture(CATEGORY_KEPESSEGEK).getWidth(),game.getMyAssetManager().getTexture(CATEGORY_KEPESSEGEK).getHeight());
                break;
            }
            case SKINS:{
                sprite.setTexture(game.getMyAssetManager().getTexture(CATEGORY_SKINEK));
                setSize(game.getMyAssetManager().getTexture(CATEGORY_SKINEK).getWidth(),game.getMyAssetManager().getTexture(CATEGORY_SKINEK).getHeight());
                break;
            }
            case NULL:{
                setVisible(false);
                break;
            }
        }
    }

    private void addListeners(){
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(getStage() != null && getStage() instanceof ShopStage) {
                    ((ShopStage) getStage()).selectedCategory = type;
                }
            }
        });
    }

    public void setType(ShopCategoryType type){
        this.type = type;
        setTexture();
    }
}
