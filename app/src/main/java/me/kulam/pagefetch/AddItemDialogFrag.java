package me.kulam.pagefetch;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddItemDialogFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddItemDialogFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemDialogFrag extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private EditText categoryInput;
    private EditText pageTitle;
    private EditText pageDesc;
    private EditText pageCategory;
    private EditText pageUrl;

    private String title;

    private OnFragmentInteractionListener mListener;

    public AddItemDialogFrag() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @return A new instance of fragment AddItemDialogFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static AddItemDialogFrag newInstance(String title) {
        AddItemDialogFrag fragment = new AddItemDialogFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button btn;
        View view;
        TextView addItemTitle;

        if(title.equalsIgnoreCase("New Category")){
            view = inflater.inflate(R.layout.add_item_frag, container);
            btn = (Button) view.findViewById(R.id.add_item_btn);
            categoryInput = (EditText) view.findViewById(R.id.add_item_input); //Global scope for onClick purpose
            addItemTitle = (TextView) view.findViewById(R.id.add_item_title);

        } else{
            view = inflater.inflate(R.layout.add_page_frag, container);
            btn = (Button) view.findViewById(R.id.add_page_btn);
            addItemTitle = (TextView) view.findViewById(R.id.add_page_title);

            pageTitle = (EditText) view.findViewById(R.id.add_page_input_title); //Global scope for onClick purpose;
            pageCategory = (EditText) view.findViewById(R.id.add_page_input_category); //Global scope for onClick purpose;
            pageUrl = (EditText) view.findViewById(R.id.add_page_input_url); //Global scope for onClick purpose;
            pageDesc = (EditText) view.findViewById(R.id.add_page_input_desc); //Global scope for onClick purpose;


        }

        addItemTitle.setText(title); //Move out later
        btn.setText("Add"); //Move out later

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int input = categoryInput.getId();
                if(title.equalsIgnoreCase("New Category")){
                    int checkCategoryInput = mListener.handleUserInput(categoryInput.getText().toString(),null,null,null); //Callback
                    if (checkCategoryInput == -1){
                        Toast.makeText(getDialog().getContext(), "Category already exists", Toast.LENGTH_SHORT).show();
                    } else if (checkCategoryInput == 0){
                        Toast.makeText(getDialog().getContext(), "Field is empty", Toast.LENGTH_SHORT).show();
                    } else{
                        getDialog().dismiss();
                    }
                }else{

                    String inputTitle =      pageTitle.getText().toString();
                    String inputCategory =   pageCategory.getText().toString();
                    String inputUrl =        pageUrl.getText().toString();
                    String inputDesc =       pageDesc.getText().toString();

                    String validUrlHTTP = "http://www."+inputUrl.toLowerCase().trim();
                    String validUrlHTTPS = "https://www."+inputUrl.toLowerCase().trim();

                    //TODO: Get image
                    //TODO: Check edit text input wrong input
                    if(inputTitle.length() == 0 || inputCategory.length() == 0  || inputUrl.length() == 0 ){
                        Toast.makeText(getDialog().getContext(),"Some fields are empty", Toast.LENGTH_SHORT).show();
                    }else{
                        if(URLUtil.isValidUrl(validUrlHTTP)){
                            mListener.handleUserInput(inputTitle,inputCategory,validUrlHTTP,inputDesc); //Callback
                            getDialog().dismiss();
                            //Toast.makeText(getDialog().getContext(),"Valid HTTP", Toast.LENGTH_SHORT).show();
                        }
                        else if(URLUtil.isValidUrl(validUrlHTTPS)){
                            mListener.handleUserInput(inputTitle,inputCategory,validUrlHTTPS,inputDesc); //Callback
                            getDialog().dismiss();
                            //Toast.makeText(getDialog().getContext(),"Valid HTTPS", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getDialog().getContext(),"Web page/URL is invalid", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setWindowAnimations(R.style.searchDialogSlide);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onStart() {
        super.onStart();
        try{
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch(ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        int handleUserInput(String inputTitle, String inputCategory, String inputUrl, String inputDesc);
    }
}
