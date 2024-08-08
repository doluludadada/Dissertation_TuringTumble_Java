package com.gu.turingtumble.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.kotcrab.vis.ui.widget.VisDialog;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.gu.turingtumble.utils.GameManager;

public class GameHints {
    private static GameHints instance;
    private int currentHintIndex = 0;
    private final String[] hintImages = {
        "hint1.png",
        "hint2.png",
        "hint3.png",
        "hint4.png",
        "hint5.png",
        "hint6.png",
        "hint7.png"
    };
    private final String[] hintTexts = {
        "When the launcher color is red or blue, it means it can launch a ball.",
        "Press here to select components.",
        "Place the components on the board.",
        "After placing the parts on the field, you can change their direction.\nEach part has a special function.",
        "Clicking the mouse again can change the direction of the parts.",
        "The challenge requires outputting 8 blue balls in this level! \nPlace the parts in appropriate positions and then launch the balls!",
        "When the ball touches the blue area, it will automatically trigger the launch of a blue ball.\n When it touches the red area, a red ball will be automatically launched."
    };
    private Stage uiStage;

    private GameHints() {
    }

    public static GameHints getInstance() {
        if (instance == null) {
            instance = new GameHints();
        }
        return instance;
    }

    public void initialise(Stage stage) {
        this.uiStage = stage;
    }

    public void showNextHint() {
        if (currentHintIndex < hintImages.length) {
            GameManager.pauseGame();
            showHintDialog(hintImages[currentHintIndex], hintTexts[currentHintIndex]);
            currentHintIndex++;
        } else {
            GameManager.resumeGame();
        }
    }

    private void showHintDialog(String imagePath, String text) {
        VisDialog hintDialog = new VisDialog("Hint") {
            @Override
            protected void result(Object object) {
                showNextHint();
            }
        };

        Image hintImage = new Image(new Texture(Gdx.files.internal(imagePath)));
        hintImage.setSize(800, 800); //

        VisLabel hintLabel = new VisLabel(text);
        VisTextButton okButton = new VisTextButton("Okay");

        hintDialog.text(hintLabel);
        hintDialog.getContentTable().row();
        hintDialog.getContentTable().add(hintImage).size(1000, 800).center();
        hintDialog.getContentTable().row();
        hintDialog.button(okButton, true);

        hintDialog.setModal(true);
        hintDialog.setSize(1000, 800);
        hintDialog.setPosition((Gdx.graphics.getWidth() - hintDialog.getWidth()) / 2, (Gdx.graphics.getHeight() - hintDialog.getHeight()) / 2); // 居中顯示

        hintDialog.show(uiStage);
    }
}
