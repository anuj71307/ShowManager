package com.android.showmanager.view;

import com.android.showmanager.R;
import com.android.showmanager.contract.IShowDetailsContract;
import com.android.showmanager.pojo.ShowDetails;
import com.android.showmanager.presenter.ShowDetailsPresenter;
import com.android.showmanager.rest.GetShowResultIntractor;
import com.android.showmanager.utils.Constants;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShowDetailsActivity extends AppCompatActivity implements IShowDetailsContract.IShowDetailsView
{
    private static final String TAG = ShowDetailsActivity.class.getSimpleName();
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
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_details_layout);
        initView();
        initProgressBar();
        presenter = new ShowDetailsPresenter<ShowDetails>(this, new GetShowResultIntractor());
        imdbId = getIntent().getExtras().getString(Constants.IMDB_ID);
        presenter.loadShowDetails(imdbId);

    }

    private void initView()
    {

        button = findViewById(R.id.bookmarkButton);
        button.setVisibility(View.GONE);
        director = findViewById(R.id.director);
        actors = findViewById(R.id.actors);
        imdbRating = findViewById(R.id.imdbRating);
        genre =findViewById(R.id.genre);
        name = findViewById(R.id.showName);
        year = findViewById(R.id.showYear);
        imageView = findViewById(R.id.imageView);

    }

    //showing progress bar programatically
    private void initProgressBar()
    {
        //TODO Anuj Show progress bar
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new
            RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);
        addContentView(relativeLayout, params);
    }



    @Override
    public void showProgress()
    {
        Log.i(TAG, "Showing progress");
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress()
    {
        Log.i(TAG, "hiding progress");
        progressBar.setVisibility(View.INVISIBLE);
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

        Picasso.with(this)
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
        Toast.makeText(this, "Failed to load details", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy()
    {
        presenter.onDestroy();
        super.onDestroy();
    }

}
