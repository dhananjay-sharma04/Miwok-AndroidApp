package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    //    Handle Audio focus when playing a sound
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
//                        resume playback
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                }
            };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
//      Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        create an array of words
        final ArrayList <Word> words = new ArrayList<Word>();
//        adding some words
        words.add(new Word("Where are you going?", "તમે ક્યાં જાવ છો?", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "તમારું નામ શું છે?", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is Dhananjay", "મારું નામ ધનંજય છે", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "તમને કેવું લાગે છે?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "મને સારુ લાગી રહ્યુ છે.", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "તમે આવી રહ્યા છો?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "હા, હું આવું છું.", R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.", "હું આવું છુ.", R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.", "ચાલો જઇએ.", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "અહી આવો.", R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);
        ListView listView = (ListView) findViewById(R.id.word_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
//                request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
//                        use the music stream
                        AudioManager.STREAM_MUSIC,
//                        request permanent focus
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
//                    start playback
                    releaseMediaPlayer();
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceID());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            // Abandon audio focus when playback complete
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}