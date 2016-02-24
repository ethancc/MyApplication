package com.example.ethan;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ethan.dragcar.DragCardViewContainer;
import com.example.ethan.dragcar.DragCardView;
import com.example.ethan.myapplication.R;

import java.util.ArrayList;

public class DragCardActivity extends Activity {

    private DragCardViewContainer container;
    private ArrayList<DragCardView.GameCard> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_car);

        container = (DragCardViewContainer) findViewById(R.id.drag_card_gallery);
        initCards();

        container.addGameCardList(cards);
    }

    private void initCards(){
        cards = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            cards.add(new DragCardView.GameCard(i));
        }

        container.setListener(new DragCardViewContainer.OnRefreshGameCardViewListener() {
            @Override
            public DragCardView getDragCardView(DragCardView gameCardView, DragCardView.GameCard gameCard, int positon) {
                if (gameCardView == null) {
                    gameCardView = new DragCardView(DragCardActivity.this);
                    gameCardView.setCardBackgroundColor(Color.YELLOW);
                    gameCardView.setRadius(15);
                    gameCardView.setCardElevation(1);
                }
                TextView view = new TextView(gameCardView.getContext());
                view.setText(gameCard.toString());
                view.setGravity(Gravity.CENTER_HORIZONTAL);
                gameCardView.removeAllViews();

                ImageView image = new ImageView(gameCardView.getContext());
                image.setImageResource(R.mipmap.ic_launcher);
                gameCardView.addView(image,
                        new DragCardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                gameCardView.addView(view,
                        new DragCardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                return gameCardView;
            }
        });
    }

    public void launchLikeClickActivity(View v){
        LikeClickActivity.show(this);
    }

}
