package agrawal.snehal.allwidgetexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private AlbumsAdapter adapter;
    private List<Album> albumList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new AlbumsAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
            //Glide.with(this).load("http://www.hashtroop.com/img/icon.png").into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1)
                    scrollRange = appBarLayout.getTotalScrollRange();
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void prepareAlbums() {
        int[] covers = new int[]{R.drawable.album1, R.drawable.album2, R.drawable.album3, R.drawable.album4, R.drawable.album5,
                R.drawable.album6, R.drawable.album7, R.drawable.album8, R.drawable.album9, R.drawable.album10, R.drawable.album11};
        Album a = new Album("True Romance", 13, covers[0]);
        albumList.add(a);
        a = new Album("Xscpae", 8, covers[1]);
        albumList.add(a);
        a = new Album("Maroon 5", 11, covers[2]);
        albumList.add(a);
        a = new Album("Born to Die", 12, covers[3]);
        albumList.add(a);
        a = new Album("Honeymoon", 14, covers[4]);
        albumList.add(a);
        a = new Album("I Need a Doctor", 1, covers[5]);
        albumList.add(a);
        a = new Album("Loud", 11, covers[6]);
        albumList.add(a);
        a = new Album("Legend", 14, covers[7]);
        albumList.add(a);
        a = new Album("Hello", 11, covers[8]);
        albumList.add(a);
        a = new Album("Greatest Hits", 17, covers[9]);
        albumList.add(a);
        adapter.notifyDataSetChanged();
    }

    private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private class Album {
        private String name;
        private int numOfSongs;
        private int thumbnail;

        Album(String name, int numOfSongs, int thumbnail) {
            this.name = name;
            this.numOfSongs = numOfSongs;
            this.thumbnail = thumbnail;
        }

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        int getNumOfSongs() {
            return numOfSongs;
        }

        void setNumOfSongs(int numOfSongs) {
            this.numOfSongs = numOfSongs;
        }

        int getThumbnail() {
            return thumbnail;
        }

        void setThumbnail(int thumbnail) {
            this.thumbnail = thumbnail;
        }
    }

    class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {
        private Context mContext;
        private List<Album> albumList;

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title, count;
            ImageView thumbnail, overflow;

            MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                count = (TextView) view.findViewById(R.id.count);
                thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                overflow = (ImageView) view.findViewById(R.id.overflow);
            }
        }

        AlbumsAdapter(Context mContext, List<Album> albumList) {
            this.mContext = mContext;
            this.albumList = albumList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card, parent, false);
            return new MyViewHolder(itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            Album album = albumList.get(position);
            holder.title.setText(album.getName());
            holder.count.setText(album.getNumOfSongs() + " songs");
            Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);
            holder.overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(holder.overflow);
                }
            });
        }

        private void showPopupMenu(View view) {
            PopupMenu popup = new PopupMenu(mContext, view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_album, popup.getMenu());
            popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
            popup.show();
        }

        class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
            MyMenuItemClickListener() {
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_add_favourite:
                        Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_play_next:
                        Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                }
                return false;
            }
        }

        @Override
        public int getItemCount() {
            return albumList.size();
        }
    }
}

