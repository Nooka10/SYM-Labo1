package ch.heigvd.sym.template;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;

public class SuccessLogin extends AppCompatActivity {
	
	// GUI elements
	private ImageView imageView = null;
	private TextView emailTextView = null;
	private TextView imeiTextView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_success_login);
		
		// On récupère l'Intent qui a démarré cette activité
		Intent intent = getIntent();
		// On extrait les extras qui ont été transférés à cette activité
		String emailEntered = intent.getStringExtra("emailEntered");
		String pswdEntered = intent.getStringExtra("passwordGiven");
		
		// On récupère les éléments du layout
		imageView = findViewById(R.id.imageView);
		emailTextView = findViewById(R.id.emailTextView);
		imeiTextView = findViewById(R.id.imeiTextView);
		
		// on demande la permission à l'utilisateur
		Dexter.withActivity(this)
				.withPermissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE)
				.withListener(new MultiplePermissionsListener() {
					@SuppressLint("MissingPermission")
					@Override
					public void onPermissionsChecked(MultiplePermissionsReport report) {
						List<PermissionGrantedResponse> permissionsGranted = report.getGrantedPermissionResponses();
						for (PermissionGrantedResponse permission : permissionsGranted) {
							if (permission.getPermissionName().equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
								// autorisation de l'accès au stockage accordée -> on modifie l'image
								File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/perso.jpg");
								if (file.exists()) {
									Bitmap profileBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
									imageView.setImageBitmap(profileBitmap);
								}
							}
							if (permission.getPermissionName().equals(Manifest.permission.READ_PHONE_STATE)) {
								// autorisation de l'accès aux infos du téléphone accordée -> on modifie l'imei
								TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
								imeiTextView.setText(telephonyManager.getDeviceId());
							}
						}
					}
					
					@Override
					public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
						token.continuePermissionRequest();
					}
					
				}).onSameThread().check();
		
		emailTextView.setText(emailEntered);
	}
}
