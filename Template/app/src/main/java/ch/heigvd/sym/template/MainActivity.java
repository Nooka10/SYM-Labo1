/*
 * File     : MainActivity.java
 * Project  : TemplateActivity
 * Author   : Markus Jaton 2 juillet 2014
 * 			  Fabien Dutoit 28 août 2018
 *            IICT / HEIG-VD
 *
 * mailto:fabien.dutoit@heig-vd.ch
 *
 * This piece of code reads a [email_account / password ] combination.
 * It is used as a template project for the SYM module element given at HEIG-VD
 * Target audience : students IL, TS, IE [generally semester 1, third bachelor year]
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE REGENTS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package ch.heigvd.sym.template;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // For logging purposes
    private static final String TAG = MainActivity.class.getSimpleName();

    // Just for test purposes : please destroy !
    private static final String[] validEmails = {"a@a.aa", "toto@tutu.com", "benoit@schopfer.ch", "antoine@rochat.ch", "jeremie@chatillon.ch"};
    private static final String[] validPasswords = {"a", "tata", "benben", "toinetoine", "jeje"};

    // GUI elements
    private EditText email = null;
    private EditText password = null;
    private Button signIn = null;
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the welcome screen / login authentication dialog
        setContentView(R.layout.authent);

        // Link to GUI elements
        this.email = findViewById(R.id.email);
        this.password = findViewById(R.id.password);
        this.signIn = findViewById(R.id.buttOk);

        // Then program action associated to "Ok" button
        signIn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                /*
                 * There you have to check out if the email/password
                 * combination given is valid or not
                 */
                String mail = email.getText().toString();
                String passwd = password.getText().toString();
                if (isValid(mail, passwd)) {
                    Intent intent = new Intent(MainActivity.this, SuccessLogin.class);
                    intent.putExtra("emailEntered", mail);
                    intent.putExtra("passwordGiven", passwd);
                    startActivity(intent);

                    Toast.makeText(MainActivity.this, getResources().getString(R.string.good), Toast.LENGTH_LONG).show();

                    //finish();
                }
            }

        });
    }

    private boolean isValid(String mail, String passwd) {
        if (mail == null || passwd == null || mail.isEmpty() || passwd.isEmpty()) {
            Log.w(TAG, getResources().getString(R.string.err_mailOrPasswdIsNullOrEmpty));
            showErrorDialog(mail, passwd);
            //Toast.makeText(MainActivity.this, getResources().getString(R.string.err_mailOrPasswdIsNullOrEmpty), Toast.LENGTH_LONG).show();
            return false;
        }
        if (!mail.matches("^([\\w\\-.]+)@((\\[([0-9]{1,3}\\.){3}[0-9]{1,3}])|(([\\w\\-]+\\.)+)([a-zA-Z]{2,4}))$")) {
            Log.w(TAG, getResources().getString(R.string.err_invalidMail));
            Toast.makeText(MainActivity.this, getResources().getString(R.string.err_invalidMail), Toast.LENGTH_LONG).show();
            return false;
        }
        // Return true if combination valid, false otherwise
        for (int i = 0; i < validEmails.length; i++) {
            if (mail.equals(validEmails[i])) { // mail valide
                if (passwd.equals(validPasswords[i])) { // password valide
                    return true; // login ok
                }
                break; // mail valide mais password invalide
            }
        }
        // Si on arrive ici -> mail et/ou passwd invalide(s)
        Log.w(TAG, getResources().getString(R.string.wrong));
        showErrorDialog(mail, passwd);
        return false;

    }

    protected void showErrorDialog(String mail, String passwd) {
        /*
         * Pop-up dialog to show error
         */
        AlertDialog.Builder alertbd = new AlertDialog.Builder(this);
        alertbd.setIcon(R.drawable.ic_warning_black_24dp);
        alertbd.setTitle(R.string.wronglogin);
        alertbd.setMessage(R.string.wrong);
        alertbd.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                email.getText().clear();
                password.getText().clear();
                // dialog close automatically
            }
        });
        alertbd.create().show();
    }

}
