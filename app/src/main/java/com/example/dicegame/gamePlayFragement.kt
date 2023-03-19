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

    lateinit var playerDicesGeneratedIds : MutableList<Int>

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
//        hold scores for each hand
        var yourTempScore =0
        var computerTempScore =0
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_play_fragement, container,false)
//        selecting  all player the dices
        dice1 = binding.pd1
        dice2 = binding.pd2
        dice3 = binding.pd3
        dice4 = binding.pd4
        dice5 = binding.pd5

//       get Ids of player dices
        playerDicesGeneratedIds = mutableListOf(dice1.id,dice2.id,dice3.id,dice4.id,dice5.id)

//        update scores when rotate
        yourScore = GlobalData.currentPlayerScore
        computerScore = GlobalData.currentComputerScore
        winScore = GlobalData.gameOverScore
        updateScore(binding.yourScroreValue,yourScore)
        updateScore(binding.computerScoreValue,computerScore)

//        decides how many times computer should roll for the first hand
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
//                update the shuffle counter for the next hand
                computerShuffleCounter=RandomStratergyForRole(computerTempScore)
//                colour everything white back
                colourTheClickedDice()
//                check winner
                winnerCheck(it)
//                reset counter
                shuffleCounter =0

            }
            else{
                Toast.makeText(activity,"Shuffle First",Toast.LENGTH_SHORT).show();
            }

        }
        attachEventListenersForDices()

        return binding.root
    }

//    change the game winning score by updating the win score
    @SuppressLint("SetTextI18n")
    private fun changeWinScore() {
        winScore= binding.editWinningScore.text.toString().toInt()
        binding.setwinScore.visibility = View.GONE
        binding.topScore.setText("Winning Score: " + binding.editWinningScore.text.toString())
        binding.editWinningScore.visibility = View.GONE
        binding.topScore.visibility =View.VISIBLE
    }

//    roll a single dice by using the image resource
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

//    shuffle all the dices when a image view array of dices is passed
    private fun shuffleAlltheDices(diceList : MutableList<ImageView>,player:Boolean): Int{

        var tempTotal = 0
        var count =0
        if(player){
            shuffleCounter += 1
        }
        for (imgSelectors in diceList){
//            if shuffling is done for player
            if (player){
//                if dice rollability index value is true

                if (yourDicesRollability[count]){
                    val score = rollDice(imgSelectors)
                    yourDicesArr[count] = score
                    tempTotal += score
                }
//                if value is false
                else{
                    tempTotal += yourDicesArr[count]
                    yourDicesRollability[count] = true
                }
            }
//          if the shuffling is done for computer
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

//    update the image views
    private fun updateScore(scoreHolder: TextView, tempTotal: Int):Int{
        var currentScore = scoreHolder.text.toString().toInt()
        currentScore += tempTotal
        scoreHolder.setText(currentScore.toString())

        return currentScore

    }
//    check winner after each hand
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

//                global data updtae
                yourScore=0
                computerScore=0
                winScore =101


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

                // global data updtae
                computerScore = 0
                yourScore = 0
                winScore=101


            }
//            if the scores are tied, check win at each shuffle
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
                        playerDicesGeneratedIds[0]-> 0
                        playerDicesGeneratedIds[1] -> 1
                        playerDicesGeneratedIds[2] -> 2
                        playerDicesGeneratedIds[3]-> 3
                        playerDicesGeneratedIds[4] -> 4
                        else -> 0
                    }
                    Log.d("clicked",clikedElementId.toString())
                    yourDicesRollability[clikedElementId] = false
//                color the clicked one
                    colourTheClickedDice()
                    Log.d("after clicked array", yourDicesRollability.toString())
                }
                else{
                    val toast = Toast.makeText(activity, "Shuffle 1st", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }

        }

    }

//    random stratergy for the rolling
    private fun RandomStratergyForRole(firstAttemptScore: Int) : Int{

//    computer decides to roll once
        if (computerShuffleCounter==1){
            computerScore=updateScore(binding.computerScoreValue,firstAttemptScore)
        }
//    computer decides to roll twice
        if(computerShuffleCounter==2){

            computerDicesRollability = utilities.makeHighestValuesFalse(computerDicesArr)
            val tempCompScore = shuffleAlltheDices(computerDiceList,false)
            computerScore=updateScore(binding.computerScoreValue,tempCompScore)
        }
//    computer decides to roll thrice
        if (computerShuffleCounter==3){

            computerDicesRollability = utilities.makeHighestValuesFalse(computerDicesArr)
            var tempCompScore = shuffleAlltheDices(computerDiceList,false)

            computerDicesRollability = utilities.makeHighestValuesFalse(computerDicesArr)
            tempCompScore = shuffleAlltheDices(computerDiceList,false)
            computerScore=updateScore(binding.computerScoreValue,tempCompScore)

        }
//      return the number of roll count for the next hand
        return (Random.nextInt(3)+1)

    }

//    on destroy view store the important data in a static attribute in a class
    override fun onDestroyView() {
        super.onDestroyView()
        GlobalData.currentPlayerScore = yourScore
        GlobalData.currentComputerScore = computerScore
        GlobalData.gameOverScore = winScore

    }

}