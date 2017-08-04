package com.example.svgtester;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.caverock.androidsvg.SVGImageView;

import java.util.Random;

import svgandroid.SVG;
import svgandroid.SVGParser;

public class MainActivity extends AppCompatActivity {
    private com.caverock.androidsvg.SVG svg;
    private int angle =0;
    private SVGImageView svgImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = new LinearLayout(this);
         svgImageView = new SVGImageView(this);
        svgImageView.setSVGGetter(new SVGImageView.SVGGetter() {
            @Override
            public void onSVGCreated(com.caverock.androidsvg.SVG svg) {
                MainActivity.this.svg = svg;
            }
        });
        svgImageView.setImageAsset("icon_pause.svg");
        layout.addView(svgImageView, new LinearLayout.LayoutParams(500,500/*LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT*/));
        setContentView(layout);
        handler.postDelayed(runnable,200);

        // SVG svg = SVGParser.getSVGFromResource(getResources(), R.raw.filename);
        //Picture picture = svg.getPicture(); Drawable drawable = svg.createPictureDrawable();

        // Create a new
        try {
            ImageView imageView = new ImageView(this);
            // Set the background color to white
            imageView.setBackgroundColor(Color.WHITE); // Parse the SVG file from the resource
            SVGParser svgParser = new SVGParser();
            // svgParser.
            SVG svg = svgParser.getSVGFromResource(getResources(), R.raw.icon_pause);
            // Get a drawable from the parsed SVG and set it as the drawable for the ImageView
            imageView.setImageDrawable(svg.createPictureDrawable()); // Set the ImageView as the content view for the Activity
            setContentView(imageView);
            Matrix mMatrix = new Matrix();
            RectF bounds = new RectF();
            Path path = svgParser.getPathArrayList().get(0);
            Path pathSecond = svgParser.getPathArrayList().get(1);

            path.computeBounds(bounds, true);
            mMatrix.postRotate(new Random().nextInt(100), bounds.centerX(), bounds.centerY());
            path.transform(mMatrix);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.GREEN);
            Canvas canvas = svgParser.getSVGHandler().getCanvas();
            //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
            canvas.drawPath(path, paint);
            canvas.drawPath(pathSecond, paint);

            //   canvas.restore();
        } catch (Exception e) {

        }

    }
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(angle>=360){
                handler.removeCallbacks(runnable);
            }else{
                angle = angle+6;
                svgImageView.setSVG(svg, angle);
                handler.postDelayed(runnable,200);
            }
        }
    };
}
