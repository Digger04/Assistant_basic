package com.example.assistant_basic;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    ImageView btn_Nghe;
    EditText edt_View;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    Context context = MainActivity.this;

    //Chuyển văn bản thàn giọng nói
    TextToSpeech textToSpeech;
    Button btn_NOI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Nghe = findViewById(R.id.img_btn_nnghe);
        edt_View = findViewById(R.id.edtView);
        btn_NOI = findViewById(R.id.btn_noi);

        btn_Nghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speech_TO_TEXT();
            }
        });

        btn_NOI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( edt_View.getText().toString().isEmpty() ){
                    edt_View.setText("Tôi là trợ lý ảo  !");
                    tts("Tôi là trợ lý ảo  !");
                } else {
                    tts( edt_View.getText().toString() );
                }
            }
        });
    }

    public void speech_TO_TEXT(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault() );

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Đang nghe...");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Thiết bị không hỗ t" ,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void  tts(String text){
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {
                if ( status != TextToSpeech.ERROR ){
                    textToSpeech.setLanguage( new Locale("vi_VN") );
                    textToSpeech.setSpeechRate((float) 1.2);
                    textToSpeech.speak(text , TextToSpeech.QUEUE_FLUSH , null);

                }
            }
        });
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && data != null) {

                    List<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    edt_View.setText(text);
                    if (text.equals("Mở thư viện")) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent,123);
                        text = "Đã mở thư viện";
                        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onInit(int status) {
                                if ( status != TextToSpeech.ERROR ){
                                    textToSpeech.setLanguage( new Locale("vi_VN") );
                                    textToSpeech.setSpeechRate((float) 1.2);
                                    textToSpeech.speak("Đã mở thư viện" , TextToSpeech.QUEUE_FLUSH , null);

                                }
                            }
                        });
                    }else if (text.equals("Mở camera")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 222);
                        text = "Đã mở camera";
                        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onInit(int status) {
                                if ( status != TextToSpeech.ERROR ){
                                    textToSpeech.setLanguage( new Locale("vi_VN") );
                                    textToSpeech.setSpeechRate((float) 1.2);
                                    textToSpeech.speak("Đã mở camera" , TextToSpeech.QUEUE_FLUSH , null);

                                }
                            }
                        });
                    }else if (text.equals("Mở Facebook")) {
                        Intent fb = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
                        startActivity(fb);
                        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onInit(int status) {
                                if ( status != TextToSpeech.ERROR ){
                                    textToSpeech.setLanguage( new Locale("vi_VN") );
                                    textToSpeech.setSpeechRate((float) 1.2);
                                    textToSpeech.speak("Đã mở facebook" , TextToSpeech.QUEUE_FLUSH , null);

                                }
                            }
                        });
                    }else if (text.equals("Mở Messenger")) {
                        Intent mess = getPackageManager().getLaunchIntentForPackage("com.facebook.orca");
                        startActivity(mess);
                        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onInit(int status) {
                                if ( status != TextToSpeech.ERROR ){
                                    textToSpeech.setLanguage( new Locale("vi_VN") );
                                    textToSpeech.setSpeechRate((float) 1.2);
                                    textToSpeech.speak("Đã mở messenger" , TextToSpeech.QUEUE_FLUSH , null);
                                }
                            }
                        });
                    }else if (text.equals("Mở Lazada") || text.equals("Mở lazada") ||
                            text.equals("mở Lazada") || text.equals("mở lazada")) {

                        Intent lazada = getPackageManager().getLaunchIntentForPackage("com.lazada.android");
                        startActivity(lazada);
                        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onInit(int status) {
                                if ( status != TextToSpeech.ERROR ){
                                    textToSpeech.setLanguage( new Locale("vi_VN") );
                                    textToSpeech.setSpeechRate((float) 1.2);
                                    textToSpeech.speak("Đã mở lazada" , TextToSpeech.QUEUE_FLUSH , null);
                                }
                            }
                        });
                    }else if (text.equals("Mở Shopee") || text.equals("mở shopee") ||
                    text.equals("Mở shopee") || text.equals("mở Shopee")) {

                        Intent shopee = getPackageManager().getLaunchIntentForPackage("com.shopee.id");
                        startActivity(shopee);
                        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onInit(int status) {
                                if ( status != TextToSpeech.ERROR ){
                                    textToSpeech.setLanguage( new Locale("vi_VN") );
                                    textToSpeech.setSpeechRate((float) 1.2);
                                    textToSpeech.speak("Đã mở shopee" , TextToSpeech.QUEUE_FLUSH , null);
                                }
                            }
                        });
                    }else if (text.equals("Mở Zing MP3") || text.equals("mở zing mp3") ||
                            text.equals("Mở zing mp3") || text.equals("mở Zing mp3")) {

                        Intent zing = getPackageManager().getLaunchIntentForPackage("com.zing.mp3");
                        startActivity(zing);
                        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onInit(int status) {
                                if ( status != TextToSpeech.ERROR ){
                                    textToSpeech.setLanguage( new Locale("vi_VN") );
                                    textToSpeech.setSpeechRate((float) 1.2);
                                    textToSpeech.speak("Đã mở zing mp3" , TextToSpeech.QUEUE_FLUSH , null);
                                }
                            }
                        });
                    }else if (text.equals("Mở Google Maps") || text.equals("mở google maps") ||
                            text.equals("Mở google maps") || text.equals("mở Google Maps")) {

                        Intent map = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
                        startActivity(map);
                        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onInit(int status) {
                                if ( status != TextToSpeech.ERROR ){
                                    textToSpeech.setLanguage( new Locale("vi_VN") );
                                    textToSpeech.setSpeechRate((float) 1.2);
                                    textToSpeech.speak("Đã mở google maps" , TextToSpeech.QUEUE_FLUSH , null);
                                }
                            }
                        });
                    }else if (text.equals("Mở Google") || text.equals("mở google") ||
                            text.equals("Mở google") || text.equals("mở Google")) {

                        Intent google = getPackageManager().getLaunchIntentForPackage("com.google.android.googlequicksearchbox");
                        startActivity(google);
                        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onInit(int status) {
                                if ( status != TextToSpeech.ERROR ){
                                    textToSpeech.setLanguage( new Locale("vi_VN") );
                                    textToSpeech.setSpeechRate((float) 1.2);
                                    textToSpeech.speak("Đã mở google" , TextToSpeech.QUEUE_FLUSH , null);
                                }
                            }
                        });
                    }else if (text.equals("Mở Zalo") || text.equals("mở zalo") ||
                            text.equals("Mở zalo") || text.equals("mở Zalo")) {

                        Intent zalo = getPackageManager().getLaunchIntentForPackage("com.zing.zalo");
                        startActivity(zalo);
                        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onInit(int status) {
                                if (status != TextToSpeech.ERROR) {
                                    textToSpeech.setLanguage(new Locale("vi_VN"));
                                    textToSpeech.setSpeechRate((float) 1.2);
                                    textToSpeech.speak("Đã mở zalo", TextToSpeech.QUEUE_FLUSH, null);
                                }
                            }
                        });
                    }else if (text.equals("Mở Lịch") || text.equals("mở lịch") ||
                            text.equals("Mở lịch") || text.equals("mở Lịch")) {

                        Intent lich = getPackageManager().getLaunchIntentForPackage("com.google.android.calendar");
                        startActivity(lich);
                        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onInit(int status) {
                                if (status != TextToSpeech.ERROR) {
                                    textToSpeech.setLanguage(new Locale("vi_VN"));
                                    textToSpeech.setSpeechRate((float) 1.2);
                                    textToSpeech.speak("Đã mở zalo", TextToSpeech.QUEUE_FLUSH, null);
                                }
                            }
                        });
                    }

                }
                break;
            }

        }
    }

}