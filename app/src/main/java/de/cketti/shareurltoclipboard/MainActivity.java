package de.cketti.shareurltoclipboard;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static final String URL = "https://example.org/cool-link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spanned introText = Html.fromHtml(getString(R.string.app_intro_text));
        TextView introTextView = (TextView) findViewById(R.id.introText);
        introTextView.setMovementMethod(LinkMovementMethod.getInstance());
        introTextView.setText(introText);

        TextView shareTextView = (TextView) findViewById(R.id.shareText);
        shareTextView.setText(getShareText());

        findViewById(R.id.shareButton).setOnClickListener(v -> share());
    }

    private void share() {
        // Create Share Intent
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, getShareText());

        // Create "Copy link to clipboard" Intent
        Intent clipboardIntent = CopyToClipboardActivity.createCopyToClipboardIntent(this, URL);

        // Create Chooser Intent with "Copy link to clipboard" option
        Intent chooserIntent = Intent.createChooser(shareIntent, null/* default title */);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { clipboardIntent });

        startActivity(chooserIntent);
    }

    private String getShareText() {
        return getString(R.string.share_intro_text) + "\n" + URL;
    }
}
