package com.penglai.haima.dialog;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.utils.PhoneUtil;

import java.util.Hashtable;

/**
 * Created by  on 2019/11/5.
 * 文件说明：
 */
public class TwoCodeShowDialog extends BaseDialogView {
    ImageView img_close;
    ImageView img_code;
    LinearLayout ll_code;
    String content;


    public TwoCodeShowDialog(BaseActivity activity, String content) {
        super(activity);
        this.content = content;
        init(R.layout.dialog_two_code);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        img_close = findViewById(R.id.img_close);
        img_code = findViewById(R.id.img_code);
        ll_code = findViewById(R.id.ll_code);
        ViewGroup.LayoutParams params = ll_code.getLayoutParams();
        //获取当前控件的布局对象
        params.height = (int) (PhoneUtil.getScreenWidth(getActivity()) * 0.9);//设置当前控件布局的高度
        ll_code.setLayoutParams(params);
    }

    @Override
    public void initViewData() {
        img_code.setImageBitmap(createQRImage(content, (int) (PhoneUtil.getScreenWidth(getActivity()) * 0.8), (int) (PhoneUtil.getScreenWidth(getActivity()) * 0.8)));

    }

    @Override
    public void initViewListener() {
        img_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 创建二维码
     */
    public Bitmap createQRImage(String resetpwInfo, int QR_WIDTH, int QR_HEIGHT) {
        Bitmap bitmap;
        try {
            if (TextUtils.isEmpty(resetpwInfo)) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(resetpwInfo, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
