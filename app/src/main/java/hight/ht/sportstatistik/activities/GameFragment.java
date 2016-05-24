package hight.ht.sportstatistik.activities;

import android.app.Activity;

import hight.ht.sportstatistik.R;
import hight.ht.sportstatistik.datahandling.DatabaseHelper;
import hight.ht.sportstatistik.datahandling.Game;
import hight.ht.sportstatistik.helper.GameAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment implements GameAdapter.SpielAdapterCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    DatabaseHelper dbh;
    GameAdapter spiele;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public void onStart() {
        this.spiele = new GameAdapter(getActivity().getApplicationContext(), hight.ht.sportstatistik.R.id.label, (List<Game>) dbh.getAllGames());
        spiele.notifyDataSetChanged();
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(hight.ht.sportstatistik.R.layout.fragment_game, container, false);

        dbh = DatabaseHelper.getInstance(getActivity().getApplicationContext());
        ListView lv = (ListView) rootView.findViewById(hight.ht.sportstatistik.R.id.listViewSpiele);
        TextView noGames = (TextView) rootView.findViewById(R.id.gameFragmentNoGames);
        this.spiele = new GameAdapter(getActivity().getApplicationContext(), hight.ht.sportstatistik.R.id.label, (List<Game>) dbh.getAllGames());
        spiele.setCallback(this);
        lv.setAdapter(spiele);
        if(spiele.getCount() == 0){
            lv.setVisibility(View.INVISIBLE);
            noGames.setVisibility(View.VISIBLE);
        }else{
            lv.setVisibility(View.VISIBLE);
            noGames.setVisibility(View.INVISIBLE);
        }

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.onFragmentInteraction(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void deleteGame(int spielId, int position) {
        //spiele.remove(spiele.getItem(position));
        //spiele.notifyDataSetChanged();
        mListener.deleteGame(spielId, position);
    }

    @Override
    public void setGameActive(int spielId, int position) {
        mListener.setGameActive(spielId, position);
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
        public void onFragmentInteraction(int position);
        public void deleteGame(int gameId, int position);
        public void setGameActive(int spielId, int position);
    }



}
