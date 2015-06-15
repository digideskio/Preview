
package forest.rice.field.k.preview.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class LyricSelectDialogFragment extends DialogFragment {

    public DialogInterface.OnClickListener mOnClickListener;

    public static LyricSelectDialogFragment newInstance(String[] items) {
        LyricSelectDialogFragment dialogFragment = new LyricSelectDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putStringArray("items", items);
        dialogFragment.setArguments(bundle);

        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] items = (savedInstanceState == null) ? getArguments().getStringArray("items")
                : savedInstanceState.getStringArray("items");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, mOnClickListener);

        return builder.create();
    }

}
