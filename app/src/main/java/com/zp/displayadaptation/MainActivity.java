package com.zp.displayadaptation;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.RandomAccessFile;
import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        createDefaultDimens(750);
    }

    private void createDefaultDimens(float targetWidth) {
        String file_path = Environment.getExternalStorageDirectory().getPath() + "/";
        String file_name = "dimens_360dp_targetWidth_" + targetWidth + ".txt";
        File file = new File(file_path + file_name);
        if (file.exists())
            file.delete();
        file = new File(file_path + file_name);

        float ratio = 360 / targetWidth * 2;
        int end_sp = 50;
        int end_dp = (int) (720 / ratio) + 1;

        String strContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "\n" + "<resources>";
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());

            // sp
            for (int k = 6; k <= end_sp; k++) {
                BigDecimal shift = BigDecimal.valueOf(k * ratio);
                shift = shift.setScale(2, BigDecimal.ROUND_HALF_UP);
                String str = "\n" + "    <dimen name=" + "\"sp_" + k + "\">" + shift + "sp</dimen>";
                if (k == end_sp)
                    str += "\n";
                raf.seek(file.length());
                raf.write(str.getBytes());
            }

            // dp
            for (int i = 0; i <= end_dp; i++) {
                BigDecimal shift = BigDecimal.valueOf(i == 0 ? 0.5 * ratio : i * ratio);
                shift = shift.setScale(2, BigDecimal.ROUND_HALF_UP);
                String str = "\n" + "    <dimen name=" + "\"dp_" + (i == 0 ? 0.5 : i) + "\">" + shift + "dp</dimen>";
                if (i == end_dp)
                    str = str + "\n" + "</resources>";
                raf.seek(file.length());
                raf.write(str.getBytes());
            }
            raf.close();
        } catch (Exception e) {
        }
    }
}
