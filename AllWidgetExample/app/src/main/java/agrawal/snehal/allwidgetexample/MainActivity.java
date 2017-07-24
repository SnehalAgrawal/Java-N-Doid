package agrawal.snehal.allwidgetexample;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import agrawal.snehal.allwidgetexample.imagezoom.ImageZoomMirroringActivity;
import agrawal.snehal.allwidgetexample.imagezoom.ImageZoomSingleTouchActivity;
import agrawal.snehal.allwidgetexample.imagezoom.ImageZoomViewPagerActivity;
import agrawal.snehal.allwidgetexample.listexample.AlphabeticIndex;
import agrawal.snehal.allwidgetexample.listexample.AlphabeticIndexWithGroupHeader;
import agrawal.snehal.allwidgetexample.listexample.ListViewArrayAdapter;
import agrawal.snehal.allwidgetexample.listexample.ListViewBasedapter;
import agrawal.snehal.allwidgetexample.listexample.ListViewExpandable;
import agrawal.snehal.allwidgetexample.listexample.ListViewInfinite;
import agrawal.snehal.allwidgetexample.listexample.ListViewListActivity;
import agrawal.snehal.allwidgetexample.listexample.ListViewSimpleAdapter;
import agrawal.snehal.allwidgetexample.listexample.ListViewSimpleCursorAdapter;
import agrawal.snehal.allwidgetexample.listexample.SectionListView;
import agrawal.snehal.allwidgetexample.tabhost.TabHostFragmentActivity;
import agrawal.snehal.allwidgetexample.tabhost.TabHostIntentActivity;
import agrawal.snehal.allwidgetexample.tabhost.TabHostSimpleActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clicktogo(View view) {
        switch (view.getId()) {
            case R.id.btextclock:
                startActivity(new Intent(MainActivity.this, TextClockActivity.class));
                break;
            case R.id.bchronometer:
                startActivity(new Intent(MainActivity.this, ChronometerActivity.class));
                break;
            case R.id.bcalenderview:
                startActivity(new Intent(MainActivity.this, CalenderViewActivity.class));
                break;
            case R.id.bdatepicker:
                startActivity(new Intent(MainActivity.this, DatePickerActivity.class));
                break;
            case R.id.btimepicker:
                startActivity(new Intent(MainActivity.this, TimePickerActivity.class));
                break;
            case R.id.btoast:
                Toast.makeText(MainActivity.this, "Toast generated", Toast.LENGTH_LONG).show();
                break;
            case R.id.bedittext:
                startActivity(new Intent(MainActivity.this, EdittextTextviewExample.class));
                break;
            case R.id.bimagebutton:
                startActivity(new Intent(MainActivity.this, ImageButtonImageExample.class));
                break;
            case R.id.bcheckbox:
                startActivity(new Intent(MainActivity.this, CheckBoxExample.class));
                break;
            case R.id.bradiobutton:
                startActivity(new Intent(MainActivity.this, RadioButtonExample.class));
                break;
            case R.id.bspinner:
                startActivity(new Intent(MainActivity.this, SpinnerExample.class));
                break;
            case R.id.bautocomplete:
                startActivity(new Intent(MainActivity.this, AutocompleteTextViewExample.class));
                break;
            case R.id.bmislenious:
                startActivity(new Intent(MainActivity.this, ToggleProgressSeekSwitchRate.class));
                break;
            case R.id.bsnackebar:
                startActivity(new Intent(MainActivity.this, SnackBarExample.class));
                break;
            case R.id.bquickcontactbatch:
                startActivity(new Intent(MainActivity.this, QuickContactBadgeActivity.class));
                break;
            case R.id.bgridview:
                startActivity(new Intent(MainActivity.this, GridViewActivity.class));
                break;
            case R.id.bwebview:
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
                break;
            case R.id.bsearchview:
                startActivity(new Intent(MainActivity.this, SearchViewActivity.class));
                break;
            case R.id.btabhost:
                final CharSequence[] tabhostoption = {"Static TabHost", "TabHost with intent(Deprecated)", "TabHost fragment"};
                AlertDialog.Builder tabhost_builder = new AlertDialog.Builder(MainActivity.this);
                tabhost_builder.setItems(tabhostoption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                startActivity(new Intent(MainActivity.this, TabHostSimpleActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(MainActivity.this, TabHostIntentActivity.class));
                                break;
                            case 2:
                                startActivity(new Intent(MainActivity.this, TabHostFragmentActivity.class));
                                break;
                            default:
                                dialog.dismiss();
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.blistview:
                final CharSequence[] listoption = {"Simple Cursor Adapter", "Array Adapter", "Simple Adapter", "Base Adapter", "List Activity", "Expandable ListView", "Infinite ListView", "Alphabetic index", "Group Headers", "Alphabetic with group headers"};
                AlertDialog.Builder list_builder = new AlertDialog.Builder(MainActivity.this);
                list_builder.setItems(listoption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                startActivity(new Intent(MainActivity.this, ListViewSimpleCursorAdapter.class));
                                dialog.dismiss();
                                break;
                            case 1:
                                startActivity(new Intent(MainActivity.this, ListViewArrayAdapter.class));
                                dialog.dismiss();
                                break;
                            case 2:
                                startActivity(new Intent(MainActivity.this, ListViewSimpleAdapter.class));
                                dialog.dismiss();
                                break;
                            case 3:
                                startActivity(new Intent(MainActivity.this, ListViewBasedapter.class));
                                dialog.dismiss();
                                break;
                            case 4:
                                startActivity(new Intent(MainActivity.this, ListViewListActivity.class));
                                dialog.dismiss();
                                break;
                            case 5:
                                startActivity(new Intent(MainActivity.this, ListViewExpandable.class));
                                dialog.dismiss();
                                break;
                            case 6:
                                startActivity(new Intent(MainActivity.this, ListViewInfinite.class));
                                dialog.dismiss();
                                break;
                            case 7:
                                startActivity(new Intent(MainActivity.this, AlphabeticIndex.class));
                                dialog.dismiss();
                                break;
                            case 8:
                                startActivity(new Intent(MainActivity.this, SectionListView.class));
                                dialog.dismiss();
                                break;
                            case 9:
                                startActivity(new Intent(MainActivity.this, AlphabeticIndexWithGroupHeader.class));
                                dialog.dismiss();
                                break;
                            default:
                                dialog.dismiss();
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.bimageswitcher:
                startActivity(new Intent(MainActivity.this, ImageSwitcherActivity.class));
                break;
            case R.id.badapterviewflipper:
                startActivity(new Intent(MainActivity.this, AdapterViewFlipperActivity.class));
                break;
            case R.id.bviewflipper:
                startActivity(new Intent(MainActivity.this, ViewFlipperActivity.class));
                break;
            case R.id.bstackview:
                startActivity(new Intent(MainActivity.this, StackViewActivity.class));
                break;
            case R.id.btextswitcher:
                startActivity(new Intent(MainActivity.this, TextSwitcherActivity.class));
                break;
            case R.id.bviewanimator:
                startActivity(new Intent(MainActivity.this, ViewAnimatorActivity.class));
                break;
            case R.id.bviewswitcher:
                startActivity(new Intent(MainActivity.this, ViewSwitcherActivity.class));
                break;
            case R.id.bnumberpicker:
                startActivity(new Intent(MainActivity.this, NumberPickerActivity.class));
                break;
            case R.id.bviewstub:
                startActivity(new Intent(MainActivity.this, ViewStubActivity.class));
                break;
            case R.id.btaxtureview:
                startActivity(new Intent(MainActivity.this, TaxtureViewActivity.class));
                break;
            case R.id.bsurfaceview:
                startActivity(new Intent(MainActivity.this, SurfaceViewActivity.class));
                break;
            case R.id.brecycler:
                startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
                break;
            case R.id.bnotification:
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                break;
            case R.id.bexternalfont:
                startActivity(new Intent(MainActivity.this, ExternalFont.class));
                break;
            case R.id.btexttospeech:
                startActivity(new Intent(MainActivity.this, TextToSpeechActivty.class));
                break;
            case R.id.bswipetorefresh:
                startActivity(new Intent(MainActivity.this, SwipeToRefresh.class));
                break;
            case R.id.bconnectioncheck:
                startActivity(new Intent(MainActivity.this, ConnectionCheck.class));
                break;
            case R.id.bsqlitedatabase:
                startActivity(new Intent(MainActivity.this, SqliteDatabaseExample.class));
                break;
            case R.id.banimation:
                startActivity(new Intent(MainActivity.this, AnimationActivity.class));
                break;
            case R.id.bpermission:
                startActivity(new Intent(MainActivity.this, PermissionActivity.class));
                break;
            case R.id.bcameraapi:
                startActivity(new Intent(MainActivity.this, CameraAPI.class));
                break;
            case R.id.bexportcsv:
                startActivity(new Intent(MainActivity.this, ExportCSV.class));
                break;
            case R.id.bdownload:
                startActivity(new Intent(MainActivity.this, DownloadDoc.class));
                break;
            case R.id.bfilepicker:
                startActivity(new Intent(MainActivity.this, FilePickerActivity.class));
                break;
            case R.id.baudiostreaming:
                startActivity(new Intent(MainActivity.this, AudioStreamingActivity.class));
                break;
            case R.id.bvideostreaming:
                startActivity(new Intent(MainActivity.this, VideoStreamingActivity.class));
                break;
            case R.id.bcallmaillink:
                startActivity(new Intent(MainActivity.this, CallMailLink.class));
                break;
            case R.id.bxmlparsing:
                startActivity(new Intent(MainActivity.this, XMLParsing.class));
                break;
            case R.id.bsensorexample:
                startActivity(new Intent(MainActivity.this, SensorActivity.class));
                break;
            case R.id.bimagezoom:
                final CharSequence[] imagezoomoption = {"Single TouchImageView", "ViewPager Example", "Mirroring Example"};
                AlertDialog.Builder image_builder = new AlertDialog.Builder(MainActivity.this);
                image_builder.setItems(imagezoomoption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                startActivity(new Intent(MainActivity.this, ImageZoomSingleTouchActivity.class));
                                dialog.dismiss();
                                break;
                            case 1:
                                startActivity(new Intent(MainActivity.this, ImageZoomViewPagerActivity.class));
                                dialog.dismiss();
                                break;
                            case 2:
                                startActivity(new Intent(MainActivity.this, ImageZoomMirroringActivity.class));
                                dialog.dismiss();
                                break;
                            default:
                                dialog.dismiss();
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.bmenuexample:
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
                break;
            case R.id.bservice:
                startActivity(new Intent(MainActivity.this, ServiceActivity.class));
                break;
            case R.id.balarm:
                startActivity(new Intent(MainActivity.this, AlarmActivity.class));
                break;
            case R.id.brecordmedia:
                startActivity(new Intent(MainActivity.this, RecordMedia.class));
                break;
            case R.id.bbluetooth:
                startActivity(new Intent(MainActivity.this, BluetoothActivity.class));
                break;
            default:
                Toast.makeText(this, "Not Defined", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
