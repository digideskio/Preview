
package forest.rice.field.k.preview.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class TrackSelectDialogFragment extends DialogFragment {

    public DialogInterface.OnClickListener mOnClickListener;

    public static TrackSelectDialogFragment newInstance(int itemsId) {
        TrackSelectDialogFragment dialog = new TrackSelectDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ItemsId", itemsId);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int itemsId = (savedInstanceState == null) ? getArguments().getInt("ItemsId")
                : savedInstanceState.getInt("ItemsId");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(itemsId, mOnClickListener);
        return builder.create();
    }
}
