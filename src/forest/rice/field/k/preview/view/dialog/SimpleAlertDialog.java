
package forest.rice.field.k.preview.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class SimpleAlertDialog extends DialogFragment {

    public DialogInterface.OnClickListener mOnClickListener;

    public static SimpleAlertDialog newInstance(String message) {
        SimpleAlertDialog dialog = new SimpleAlertDialog();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = (savedInstanceState == null) ? getArguments().getString("message")
                : savedInstanceState.getString("message");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.ok, mOnClickListener);

        return builder.create();
    }
}
