package com.example.dicegame

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.dicegame.databinding.FragmentGamePlayFragementBinding
import kotlin.random.Random

class gamePlayFragement : Fragment() {
    lateinit var dice1: ImageView;
    lateinit var dice2: ImageView;
    lateinit var dice3: ImageView;
    lateinit var dice4: ImageView;
    lateinit var dice5: ImageView;
    lateinit var playerDiceList : MutableList<ImageView>
    lateinit var computerDiceList: MutableList<ImageView>
    lateinit var binding  : FragmentGamePlayFragementBinding

//    gameBeginner
    var winScore = 101

//    score holders
    var yourScore : Int = 0
    var computerScore: Int = 0

//    temporary Score array
     var yourDicesArr : MutableList<Int> = mutableListOf<Int>( 1 , 2 , 3 , 4 , 3);
     var computerDicesArr :MutableList<Int> = mutableListOf<Int>( 1 , 2 , 3 , 4 , 3);

     var yourDicesRollability : MutableList<Boolean> = mutableListOf(true,true,true,true,true)
    var computerDicesRollability : MutableList<Boolean> = mutableListOf(true,true,true,true,true)

//    shuffle counter
    var shuffleCounter = 0
    var computerShuffleCounter = 0

//    previouslyClicked Dice


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_play_fragement, container,false)
//        bind all the dices
        dice1 = binding.pd1
        dice2 = binding.pd2
        dice3 = binding.pd3
        dice4 = binding.pd4
        dice5 = binding.pd5

        var yourTempScore =0
        var computerTempScore =0
        computerShuffleCounter = (Random.nextInt(3)+1)
        playerDiceList = mutableListOf(dice1,dice2,dice3,dice4,dice5)
        computerDiceList = mutableListOf(binding.cd1,binding.cd2,binding.cd3,binding.cd4,binding.cd5)
//        set winning scores
        binding.setwinScore.setOnClickListener {
            changeWinScore()
        }
//        update all the wins
        binding.totalWins.setText("H:"+GlobalData.playerWons.toString() + " /C:" + GlobalData.computerWons.toString())

//        make the shuffle
        binding.shuffle.setOnClickListener {
            if (shuffleCounter<2){
                yourTempScore =shuffleAlltheDices(playerDiceList,true)

            }
            else{
                yourTempScore = shuffleAlltheDices(playerDiceList,true)
                yourScore= updateScore(binding.yourScroreValue,yourTempScore)
                computerShuffleCounter=RandomStratergyForRole(computerTempScore)
                colourTheClickedDice()
                winnerCheck(it)
                shuffleCounter =0

            }
//            make the computers 1st shuffle
            if (shuffleCounter == 1){
                computerTempScore = shuffleAlltheDices(computerDiceList,false)
            }
        }

//        click the score button
        binding.Score.setOnClickListener {
            if (shuffleCounter !=0){
                yourScore= updateScore(binding.yourScroreValue,yourTempScore)
//                computerTempScore = shuffleAlltheDices(computerDiceList,false)
//                computerScore= updateScore(binding.computerScoreValue, computerTempScore)
                computerShuffleCounter=RandomStratergyForRole(computerTempScore)
                colourTheClickedDice()
                winnerCheck(it)
                shuffleCounter =0

            }
            else{
                Toast.makeText(activity,"Shuffle First",Toast.LENGTH_SHORT).show();
            }

        }
        attachEventListenersForDices()

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun changeWinScore() {
        winScore= binding.editWinningScore.text.toString().toInt()
        binding.setwinScore.visibility = View.GONE
        binding.topScore.setText("Winning Score: " + binding.editWinningScore.text.toString())
        binding.editWinningScore.visibility = View.GONE
        binding.topScore.visibility =View.VISIBLE
    }

    private fun rollDice(imgR : ImageView): Int {
        val imageNumber = (Random.nextInt(6)+1);
        val imageResource = when(imageNumber){
            1 -> R.drawable.dice_1
            2-> R.drawable.dice_2
            3-> R.drawable.dice_3
            4-> R.drawable.dice_4
            5-> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        imgR.setImageResource(imageResource)
        return imageNumber

    }

    private fun shuffleAlltheDices(diceList : MutableList<ImageView>,player:Boolean): Int{

        var tempTotal = 0
        var count =0
        if(player){
            shuffleCounter += 1
        }
        for (imgSelectors in diceList){

            if (player){


                if (yourDicesRollability[count]){
                    val score = rollDice(imgSelectors)
                    yourDicesArr[count] = score
                    tempTotal += score
                }
                else{
                    tempTotal += yourDicesArr[count]
                    yourDicesRollability[count] = true
                }
            }
            else{
                if (computerDicesRollability[count]) {
                    val score = rollDice(imgSelectors)
                    computerDicesArr[count] = score
                    tempTotal += score
                }

                else{
                    tempTotal += computerDicesArr[count]
                    computerDicesRollability[count] = true
                }

            }
            count++

        }
       return tempTotal
    }

    private fun updateScore(scoreHolder: TextView, tempTotal: Int):Int{
        var currentScore = scoreHolder.text.toString().toInt()
        currentScore += tempTotal
        scoreHolder.setText(currentScore.toString())

        return currentScore

    }
    private fun winnerCheck(view: View){
        Log.d("Your Score", yourScore.toString())
        Log.d("Computer Score", computerScore.toString())
        if (yourScore>= winScore || computerScore>= winScore){
            if (yourScore<computerScore){
//                you Lost

                val popUp = YouLostDialogBox.newInstance("You Lost",false)
                popUp.show(childFragmentManager,"dialog")
                binding.shuffle.visibility = View.GONE
                binding.Score.visibility = View.GONE
                binding.setwinScore?.visibility = View.GONE
                binding.editWinningScore?.visibility = View.GONE
                GlobalData.computerWons += 1



            }
            else if (computerScore< yourScore){

//                you wins
                val popUp = YouLostDialogBox.newInstance("You Won",true)
                popUp.show(childFragmentManager,"dialog")
                binding.shuffle.visibility = View.GONE
                binding.Score.visibility = View.GONE
                binding.setwinScore?.visibility = View.GONE
                binding.editWinningScore?.visibility = View.GONE
                GlobalData.playerWons += 1


            }
            else if(computerScore.equals(yourScore)){
                val pScore = shuffleAlltheDices(playerDiceList,true)
                yourScore= updateScore(binding.yourScroreValue,pScore)
                val cScore = shuffleAlltheDices(computerDiceList,false)
                computerScore =updateScore(binding.computerScoreValue,cScore)
                shuffleCounter=0
                winnerCheck(view)

            }
        }
    }
    private fun colourTheClickedDice(){
        var counter = 0
        for (bool in yourDicesRollability){
            if(!bool){
                playerDiceList[counter].alpha = 0.4f
                Log.d("visit", bool.toString() )
            }
            else{
                playerDiceList[counter].alpha = 1.0f
            }
            counter++
        }
    }

    private fun attachEventListenersForDices(){
        for (dices in playerDiceList){

            dices.setOnClickListener {
                if (shuffleCounter != 0){
                    Log.d("reach",dices.id.toString())
                    val clikedElementId = when(dices.id){
                        2131231236 -> 0
                        2131231237 -> 1
                        2131231238 -> 2
                        2131231239 -> 3
                        2131231240 -> 4
                        else -> 0
                    }
                    Log.d("clicked",clikedElementId.toString())
                    yourDicesRollability[clikedElementId] = false
//                color the clicked one
                    colourTheClickedDice()
                    Log.d("after clicked array", yourDicesRollability.toString())
                }
            }

        }

    }
    private fun RandomStratergyForRole(firstAttemptScore: Int) : Int{

        Log.d("Role NUmber", computerShuffleCounter.toString())
        if (computerShuffleCounter==1){
            computerScore=updateScore(binding.computerScoreValue,firstAttemptScore)
        }
        if(computerShuffleCounter==2){

            computerDicesRollability = utilities.makeHighestValuesFalse(computerDicesArr)
            Log.d("Changed List ", computerDicesRollability.toString())
            val tempCompScore = shuffleAlltheDices(computerDiceList,false)
            computerScore=updateScore(binding.computerScoreValue,tempCompScore)
        }
        if (computerShuffleCounter==3){

            computerDicesRollability = utilities.makeHighestValuesFalse(computerDicesArr)
            Log.d("Changed List ", computerDicesRollability.toString())
            var tempCompScore = shuffleAlltheDices(computerDiceList,false)

            computerDicesRollability = utilities.makeHighestValuesFalse(computerDicesArr)
            Log.d("Changed List ", computerDicesRollability.toString())
            tempCompScore = shuffleAlltheDices(computerDiceList,false)
            computerScore=updateScore(binding.computerScoreValue,tempCompScore)

        }

        return (Random.nextInt(3)+1)

    }

}