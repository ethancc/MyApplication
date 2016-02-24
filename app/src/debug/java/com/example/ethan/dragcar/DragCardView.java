package com.example.ethan.dragcar;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

/**
 * Created by ethamhuang on 16/1/11.
 */
public class DragCardView extends CardView {


    protected GameCard gameCard;
    public DragCardView(Context context) {
        this(context, null);
    }

    public DragCardView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }


    public DragCardView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }


    public static class GameCard {
        private int position;

        public GameCard(int position){
            this.position = position;
        }

        @Override
        public String toString() {
            return String.format("第 %1$s 个", position);
        }
    }


    public void setGameCard(GameCard gameCard) {
        this.gameCard = gameCard;
    }

    public GameCard getGameCard() { return  gameCard;}

}
