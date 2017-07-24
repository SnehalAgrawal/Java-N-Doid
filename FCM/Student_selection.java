public class Student_selection extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_student_selection);
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
                switch (key) {
                    case "type":
                        temp_type = value;
                        break;
                }
            }
            Log.d(TAG, "type " + temp_type);
        } else {
            Log.d(TAG, "intent: " + getIntent().getExtras());
            // Toast.makeText(Student_selection.this, "here intent null student_selection", Toast.LENGTH_SHORT).show();
        }
    }
}
