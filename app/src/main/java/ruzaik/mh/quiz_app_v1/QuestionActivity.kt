  package ruzaik.mh.quiz_app_v1

  import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

  class QuestionActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var submit : Button
    lateinit var quz : TextView
    lateinit var flag : ImageView
    lateinit var progressBar : ProgressBar
    lateinit var progressText : TextView
    lateinit var optionOne : TextView
    lateinit var optionTwo : TextView
    lateinit var optionThree : TextView
    lateinit var optionFour : TextView
    private var mCurrentPostion : Int = 1
    private var mQuestionList : ArrayList<Question>? = null
    private var mSelectedOptionPosition : Int = 0
    lateinit var question : Question
    lateinit var correctTv : TextView
    private var correctAnswers : Int = 0
      private var mUserName : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        submit = findViewById(R.id.btn_submit)
        quz = findViewById(R.id.tv_quz)
        flag = findViewById(R.id.iv_image)
        progressBar = findViewById(R.id.progrssBar)
        progressText = findViewById(R.id.tv_progress)
        optionOne = findViewById(R.id.tv_option_one)
        optionTwo = findViewById(R.id.tv_option_two)
        optionThree = findViewById(R.id.tv_option_three)
        optionFour = findViewById(R.id.tv_option_four)

        startQuestion()

        optionOne.setOnClickListener(this)
        optionTwo.setOnClickListener(this)
        optionThree.setOnClickListener(this)
        optionFour.setOnClickListener(this)
        submit.setOnClickListener(this)

    }

     private fun startQuestion(){
         mQuestionList = Constants.getQuestion()

         question = mQuestionList!![mCurrentPostion - 1]

         defaultOptionsView()

         if(mCurrentPostion == mQuestionList!!.size){
             submit.text = "FINISH"
         }else{
             submit.text = "SUBMIT"
         }

         quz.text = question!!.question
         flag.setImageResource(question.image)
         optionOne.text = question.optionOne
         optionTwo.text = question.optionTwo
         optionThree.text = question.optionThree
         optionFour.text = question.optionFour
         progressBar.progress = mCurrentPostion
         progressText.text = "$mCurrentPostion/${progressBar.max}"
      }

      private fun defaultOptionsView(){
          val options = ArrayList<TextView>()
          options.add(0,optionOne)
          options.add(0,optionTwo)
          options.add(0,optionThree)
          options.add(0,optionFour)

          for (option in options){
              option.setTextColor(Color.parseColor("#7A8089"))
              option.typeface = Typeface.DEFAULT
              option.background = ContextCompat.getDrawable(
                  this,
                  R.drawable.default_option_border_bg
              )
          }
      }

      override fun onClick(v: View?) {
          when(v?.id){
              R.id.tv_option_one -> selectedOptionView(optionOne,1)
              R.id.tv_option_two -> selectedOptionView(optionTwo,2)
              R.id.tv_option_three -> selectedOptionView(optionThree,3)
              R.id.tv_option_four -> selectedOptionView(optionFour,4)
              R.id.btn_submit -> {
                  if(mSelectedOptionPosition == 0){
                      mCurrentPostion++

                      when{
                          mCurrentPostion <= mQuestionList!!.size -> {
                              startQuestion()
                          }else -> {
                               val intent = Intent(this, result::class.java)
                                intent.putExtra(Constants.USER_NAME, mUserName)
                                intent.putExtra(Constants.CORRECT_ANSWERS, correctAnswers)
                                intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList!!.size)
                                startActivity(intent)
                                finish()
                          }
                      }
                  }else{
                      val currentQuestion = mQuestionList?.get(mCurrentPostion - 1)
                      if(currentQuestion!!.correctAnswer != mSelectedOptionPosition){
                          answerView(mSelectedOptionPosition,R.drawable.selected_option_border_wrong_bg)
                      }else{
                          correctAnswers++
                      }

                      answerView(currentQuestion.correctAnswer,R.drawable.selected_option_border_correct_bg)

                      if(mCurrentPostion == mQuestionList!!.size){
                          submit.text = "FINISH"
                      }else{
                          submit.text = "GO TO NEXT QUESTION"
                      }

                      mSelectedOptionPosition = 0
                  }
              }
          }
      }

      private fun answerView(answer : Int, drawable : Int) {
          when(answer){
              1 -> optionOne.background = ContextCompat.getDrawable(this,drawable)
              2 -> optionTwo.background = ContextCompat.getDrawable(this,drawable)
              3 -> optionThree.background = ContextCompat.getDrawable(this,drawable)
              4 -> optionFour.background = ContextCompat.getDrawable(this,drawable)
          }
      }

      private fun selectedOptionView(tv : TextView, selectedOptionNum : Int){
          defaultOptionsView()
          mSelectedOptionPosition = selectedOptionNum

          when(question.correctAnswer){
              1 -> correctTv = optionOne
              2 -> correctTv = optionTwo
              3 -> correctTv = optionThree
              4 -> correctTv = optionFour
          }

          tv.setTextColor(Color.parseColor("#363A43"))
          tv.setTypeface(tv.typeface, Typeface.BOLD)
          tv.background = ContextCompat.getDrawable(
              this,
              R.drawable.selected_option_border_bg
          )
      }
  }