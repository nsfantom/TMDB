package tm.fantom.tmdb.ui.base;

import android.app.Dialog;
import android.view.Gravity;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

import tm.fantom.tmdb.R;
import tm.fantom.tmdb.databinding.ToastCustomViewBinding;

public abstract class BaseActivity extends AppCompatActivity implements StandardDialogs {

    private WeakReference<Dialog> progressDialog;

    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new WeakReference<>(new Dialog(this));
            Window decor = progressDialog.get().getWindow();
            if (decor != null) decor.getDecorView().setBackground(null);
            progressDialog.get().setContentView(R.layout.custom_progressbar);
            progressDialog.get().setCancelable(false);
            progressDialog.get().setCanceledOnTouchOutside(false);
        }

        progressDialog.get().show();
    }

    public void hideProgress() {
        if (this.isDestroyed() || this.isFinishing()) return;
        if (progressDialog != null && progressDialog.get() != null && progressDialog.get().isShowing()) {
            progressDialog.get().dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void showToast(String message, final int length) {
        if (!this.isFinishing()) {
            ToastCustomViewBinding toastLayout = ToastCustomViewBinding.inflate(getLayoutInflater());
            toastLayout.customText.setText(message);
            Toast toast = new Toast(this);
            toast.setView(toastLayout.getRoot());
            toast.setDuration(length);
            toast.show();
        }
    }

    @Override
    public void showToast(String message, final int yOffset, final int length) {
        if (!this.isFinishing()) {
            ToastCustomViewBinding toastLayout = ToastCustomViewBinding.inflate(getLayoutInflater());
            toastLayout.customText.setText(message);
            Toast toast = new Toast(this);
            toast.setView(toastLayout.getRoot());
            toast.setDuration(length);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, yOffset);
            toast.show();
        }
    }
}
