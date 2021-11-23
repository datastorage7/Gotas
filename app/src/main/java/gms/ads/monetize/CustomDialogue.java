package gms.ads.monetize;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class CustomDialogue extends Dialog {
    public CustomDialogue(Context context, int layoutnumber, String title, String description) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        if (layoutnumber == 2) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            WindowManager.LayoutParams wlmp = getWindow().getAttributes();

            wlmp.gravity = Gravity.CENTER_HORIZONTAL;
            getWindow().setAttributes(wlmp);
        }


        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);


        if (layoutnumber == 1) {

            View view = LayoutInflater.from(context).inflate(
                    R.layout.simple_progress, null);
            setContentView(view);
        } else {

            View view = LayoutInflater.from(context).inflate(
                    R.layout.customdialog_fc, null);

            setContentView(view);


            ((TextView) view.findViewById(R.id.title)).setText("" + title);
            ((TextView) view.findViewById(R.id.message)).setText("" + description);
        }


    }
}
