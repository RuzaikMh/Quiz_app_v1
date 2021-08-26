package ruzaik.mh.quiz_app_v1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class result : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val username = intent.getStringExtra(Constants.USER_NAME)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val totalAnswers = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)

        val tv_username = findViewById<TextView>(R.id.userName)
        tv_username.text = username

        val tv_score = findViewById<TextView>(R.id.score)
        tv_score.text = "Your Score is $correctAnswers out of $totalAnswers"

        val finish = findViewById<Button>(R.id.btn_finish)
        finish.setOnClickListener {
            startActivity(Intent(this,MainActivity :: class.java))
            finish()
        }

    }
}