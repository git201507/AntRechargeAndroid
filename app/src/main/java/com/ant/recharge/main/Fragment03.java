package com.ant.recharge.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;

/**
 *
 */
public class Fragment03 extends Fragment implements View.OnClickListener{
    private OnFragmentInteractionListener mListener;

    private View uploadButton;
    private ImageView piaoyangImageView;
    private View okButton;
    private View cancelButton;

    public Fragment03() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        FontHelper.applyFont(getActivity(), view);

        uploadButton = view.findViewById(R.id.upload);
        okButton = view.findViewById(R.id.action_submit);
        cancelButton = view.findViewById(R.id.action_cancel);
        piaoyangImageView = (ImageView) view.findViewById(R.id.piaoyang);

        uploadButton.setOnClickListener(this);
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        SharedPreferences preferences = getContext().getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, Context.MODE_PRIVATE);
        String userStr = preferences.getString("additional", null);
        try {
            User user = JsonUtil.decode(userStr, User.class);

            TextView nameTV = (TextView)view.findViewById(R.id.p_name);
            TextView phoneTV = (TextView)view.findViewById(R.id.p_phone);

            nameTV.setText(user.getName());
            phoneTV.setText(user.getTelephone());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.upload:
                break;
            case R.id.action_submit:
                break;
            case R.id.action_cancel:
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
