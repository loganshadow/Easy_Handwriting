package com.example.easyhandwriting;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.itextpdf.text.pdf.BaseFont.IDENTITY_H;

public class ConvertedText extends AppCompatActivity {
    private static final int STORAGE_CODE = 1000;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converted_text_pdf);
        textView = findViewById(R.id.textView);
        Bundle arguments = getIntent().getExtras();
        try {
            String text = arguments.get("text").toString();
            textView.setText(text);
        } catch(NullPointerException e){
            e.printStackTrace();
        }
    }
    public void createMyPDF(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, STORAGE_CODE);
            } else {
                savePdf();
            }
        } else {
            savePdf();
        }
    }

    public class MyFont implements FontProvider {
        private static final String FONT_PATH = "res/font/hand.ttf";
        private static final String FONT_ALIAS = "my_font";
        public MyFont(){
            FontFactory.register(FONT_PATH, FONT_ALIAS);
        }
        @Override
        public Font getFont(String fontname, String encoding, boolean embedded, float size, int style, BaseColor color) {
            return FontFactory.getFont(FONT_ALIAS, IDENTITY_H, BaseFont.EMBEDDED, size, style, color);
        }
        @Override
        public boolean isRegistered(String name) {
            return name.equals( FONT_ALIAS );
        }
    }

    private void savePdf(){
        Document mDoc = new Document();
        String myFile = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String mFilePath = getApplicationContext().getExternalFilesDir(null).toString() + "/" + myFile + ".pdf";

        try {
            PdfWriter.getInstance(mDoc, new FileOutputStream(mFilePath));
            mDoc.open();
            String myString = textView.getText().toString();
            BaseColor color = new BaseColor(0,0,0);
            Font font1 = new MyFont().getFont("my_font", IDENTITY_H, false, 12, 1, color);
            Paragraph paragraph = new Paragraph(myString, font1);
            mDoc.add(paragraph);
            mDoc.close();
            Toast.makeText(getApplicationContext(), getApplicationContext().getExternalFilesDir(null).toString(), Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}

