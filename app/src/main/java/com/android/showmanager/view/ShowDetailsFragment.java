package com.android.showmanager.view;

import com.android.showmanager.R;
import com.android.showmanager.contract.IShowDetailsContract;
import com.android.showmanager.pojo.ShowDetails;
import com.android.showmanager.pojo.ShowSearchDetails;
import com.android.showmanager.presenter.ShowDetailsPresenter;
import com.android.showmanager.rest.GetShowResultIntractor;
import com.android.showmanager.utils.Constants;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ShowDetailsFragment extends Fragment implements IShowDetailsContract.IShowDetailsView
{
    IShowDetailsContract.IShowDetailsPresenter presenter;
    String imdbId;
    TextView director;
    TextView genre;
    TextView actors;
    TextView imdbRating;
    TextView name;
    TextView year;
    ImageView imageView;
    ImageButton button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.frag_show_details, container, false);
        initView(view);
        presenter = new ShowDetailsPresenter<ShowDetails>(this, new GetShowResultIntractor());
        imdbId = getArguments().getString(Constants.IMDB_ID);
        presenter.loadShowDetails(imdbId);
        return view;
    }

    private void initView(View view)
    {

        button = view.findViewById(R.id.bookmarkButton);
        button.setVisibility(View.GONE);
        director = view.findViewById(R.id.director);
        actors = view.findViewById(R.id.actors);
        imdbRating = view.findViewById(R.id.imdbRating);
        genre = view.findViewById(R.id.genre);
        name = view.findViewById(R.id.showName);
        year = view.findViewById(R.id.showYear);
        imageView = view.findViewById(R.id.imageView);

    }

    @Override
    public void showProgress()
    {
        //TODO Show Progress
    }

    @Override
    public void hideProgress()
    {
        //TODO Hide Progress
    }

    @Override
    public void loadSearchResult(ShowDetails showDetails)
    {
        initUI(showDetails);
    }

    private void initUI(final ShowDetails showDetails)
    {
        name.setText(showDetails.getTitle());
        year.setText(showDetails.getYear());
        //TODO Remove hardcoded Strings
        director.setText("Director: "+showDetails.getDirector());
        actors.setText("Actors: "+showDetails.getActors());
        imdbRating.setText("IMDB Rating: "+showDetails.getImdbRating());
        genre.setText("Genre: "+showDetails.getGenre());

        Picasso.with(getContext())
            .load(showDetails.getPoster())
            .placeholder(R.drawable.placeholder_background)
            .error(R.drawable.placeholder_background)
            .fit()
            .noFade()
            .into(imageView);
    }

    @Override
    public void showResponseFailure()
    {
        Toast.makeText(getContext(), "Failed to load details", Toast.LENGTH_SHORT).show();
    }
}
