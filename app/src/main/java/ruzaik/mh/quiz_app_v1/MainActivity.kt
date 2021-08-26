package ruzaik.mh.quiz_app_v1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var btnStart : Button
    lateinit var nameTxt : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        btnStart = findViewById(R.id.btn_start)
        nameTxt = findViewById(R.id.et_name)

        btnStart.setOnClickListener {
            if(nameTxt.text.toString().isEmpty()){
                Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this,QuestionActivity :: class.java)
                intent.putExtra(Constants.USER_NAME, nameTxt.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}