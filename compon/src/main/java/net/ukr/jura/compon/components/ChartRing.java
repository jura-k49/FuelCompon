package net.ukr.jura.compon.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import net.ukr.jura.compon.R;
import net.ukr.jura.compon.interfaces_classes.IComponent;

import java.util.ArrayList;
import java.util.List;

public class ChartRing extends View implements View.OnTouchListener, IComponent {
    private float halfW, widthLine, radiusRounding;
    private float canvasW;
    private float DEFAULT_widthLine = 10f;
    private float radius, gapBetweenArc;
    private float PI = 3.1416f;
    private RectF rectf;
    private float downAngle, rotateSave = 0f, rotateAngle = 0f, rotateAngleSave = 0f;
    private float maxWidth, radiusItemMax;
    private boolean rotate;

    private List<PointValue> data = null;

    public ChartRing(Context context) {
        this(context, null);
    }

    public ChartRing(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartRing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
        rotate = false;
        setAttributes(context, attrs);
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChartRing);
        widthLine = a.getDimension(R.styleable.ChartRing_widthLine, DEFAULT_widthLine);
        radiusRounding = widthLine / 2;
        a.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasW = w;
        halfW = canvasW / 2f;
        float wN = canvasW - radiusRounding;
        rectf = new RectF(radiusRounding, radiusRounding, wN, wN);
        radius = halfW - radiusRounding;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data != null) {
            canvas.rotate(rotateAngle + rotateSave - 90f, halfW, halfW);
            float part = 0;
            float angle, angleBegin = 0;
            Path path = new Path();
            float wN = canvasW - radiusItemMax;
            rectf = new RectF(radiusItemMax, radiusItemMax, wN, wN);
            radius = halfW - radiusItemMax;
            float gapGraduse = 360f *gapBetweenArc;
            for (PointValue pv: data) {
                angle = 360f * pv.value;
                float aBegin, aEnd;
                path.reset();
                float radiusItem;
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setStyle(Paint.Style.STROKE);
                if (pv.widthLine > 0) {
                    radiusItem = pv.widthLine / 2;
                    paint.setStrokeWidth(pv.widthLine);
                } else {
                    radiusItem = radiusRounding;
                    paint.setStrokeWidth(widthLine);
                }
                if (pv.isRounding) {
                    float deltaForRounding = 180f * radiusItem / radius / PI;
                    float deltaTwo = 2f * deltaForRounding;
                    aBegin = angleBegin + deltaForRounding;
                    aEnd = angle - deltaTwo;
                    double radian = aBegin * PI / 180f;
                    float y = halfW + radius * (float) Math.sin(radian);
                    float x = halfW + radius * (float) Math.cos(radian);
                    Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
                    p.setColor(pv.colorBegin);
                    canvas.drawCircle(x, y, radiusItem, p);
                    radian = (aEnd + angleBegin + deltaForRounding) * PI / 180f;
                    y = halfW + radius * (float) Math.sin(radian);
                    x = halfW + radius * (float) Math.cos(radian);
                    Paint p1 = new Paint(Paint.ANTI_ALIAS_FLAG);
                    p1.setColor(pv.colorEnd);
                    canvas.drawCircle(x, y, radiusItem, p1);
                } else {
                    aBegin = angleBegin;
                    aEnd = angle;
                }
                path.arcTo(rectf, aBegin, aEnd);
                angleBegin += (angle + gapGraduse);
                if (pv.colorEnd == 0) {
                    paint.setColor(pv.colorBegin);
                } else {
                    int[] colors = new int[] {pv.colorBegin, pv.colorBegin, pv.colorEnd, pv.colorEnd};
                    float[] positions = new float[]{0, part, part + pv.value, 1};
                    paint.setShader(new SweepGradient(halfW, halfW, colors, positions));
                }
                canvas.drawPath(path, paint);
                part += (pv.value + gapBetweenArc);
            }
        }
    }

//    public void setData(List<PointValue> data, float gap) {
//    public void setData(Double valueD) {
    @Override
    public void setData(Object dataD) {
        float gap = 0f;
        double dd = 9d;
        if (dataD instanceof String) {
            dd = Double.valueOf((String) dataD);
        } else {
            dd = (Double) dataD;
        }
        float value = (float) dd;
value = 0.35f;
        float v1 = 1f - value;
        List<PointValue> data = new ArrayList<>();
        data.add(new PointValue("", value, Color.argb(255, 255, 150, 0), Color.argb(255, 181, 0, 154), true));
        data.add(new PointValue("", v1, Color.argb(255, 160,160,160), 0, false, 4));

        this.data = new ArrayList<>();
        PointValue pv;
        int ik = data.size();
        float sum = gap;
        maxWidth = 0f;
        for (int i = 0; i < ik; i++) {
            sum += data.get(i).value;
        }
        for (int i = 0; i < ik; i++) {
            pv = data.get(i);
            if (maxWidth < pv.widthLine) {
                maxWidth = pv.widthLine;
            }
            if (ik == 1) {
                this.data.add(new PointValue(pv.name, 0.99f, pv.colorBegin, pv.colorEnd, pv.isRounding, pv.widthLine));
            } else {
                this.data.add(new PointValue(pv.name, pv.value / sum, pv.colorBegin, pv.colorEnd, pv.isRounding, pv.widthLine));
            }
        }
        gapBetweenArc = gap / sum / ik;
        if (maxWidth < widthLine) {
            maxWidth = widthLine;
        }
        radiusItemMax = maxWidth / 2f;
        if (canvasW > 0) {
            invalidate();
        }
    }

    public void setRotate(boolean rotate) {
        this.rotate = rotate;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if ( ! rotate) return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downAngle = calculateAngle(event.getX(), event.getY());
                rotateAngle = 0f;
                rotateAngleSave = 0f;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                rotateAngle = calculateAngle(event.getX(), event.getY()) - downAngle;
                if (rotateAngle != rotateAngleSave) {
                    rotateAngleSave = rotateAngle;
//                    rotateAngle = rotateSave + rotateAngle1;
                    invalidate();
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                rotateSave = (rotateAngle + rotateSave) % 360f;
                rotateAngle = 0f;
                break;
            }
        }
        return true;
    }

    private float calculateAngle(float x, float y) {
        float x1 = x - halfW;
        float y1 = y - halfW;
        double hypotenuse = Math.sqrt(x1 * x1 + y1 * y1);
        if (hypotenuse == 0d) {
            hypotenuse = 0.000001d;
        }
        double angle1 = Math.acos(x1 / hypotenuse);
        if (y1 < 0) angle1 = PI * 2f - angle1;
        return (float) angle1 * 180f / PI;
    }
//
//    @Override
//    public void setData(Object data) {
//
//    }
}

