package com.example.dicegame

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.dicegame.databinding.FragmentGamePlayFragementBinding
import kotlin.math.log
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

//    previouslyClicked Dice


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
        playerDiceList = mutableListOf(dice1,dice2,dice3,dice4,dice5)
        computerDiceList = mutableListOf(binding.cd1,binding.cd2,binding.cd3,binding.cd4,binding.cd5)
        binding.shuffle.setOnClickListener {
            if (shuffleCounter<2){
                yourTempScore =shuffleAlltheDices(playerDiceList,true)

            }
            else{
                yourTempScore = shuffleAlltheDices(playerDiceList,true)
                yourScore= updateScore(binding.yourScroreValue,yourTempScore,it)
                computerTempScore = shuffleAlltheDices(computerDiceList,false)
                computerScore= updateScore(binding.computerScoreValue, computerTempScore, it)
                colourTheClickedDice()
                winnerCheck(it)
                shuffleCounter =0

            }


        }
        binding.Score.setOnClickListener {
            if (shuffleCounter !=0){
                yourScore= updateScore(binding.yourScroreValue,yourTempScore,it)
                computerTempScore = shuffleAlltheDices(computerDiceList,false)
                computerScore= updateScore(binding.computerScoreValue, computerTempScore, it)
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
        shuffleCounter += 1
        var tempTotal = 0
        var count =0
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

    private fun updateScore(scoreHolder: TextView, tempTotal: Int, view: View):Int{
        var currentScore = scoreHolder.text.toString().toInt()
        currentScore += tempTotal
        scoreHolder.setText(currentScore.toString())

        return currentScore

    }
    private fun winnerCheck(view: View){
        if (yourScore>= 100 || computerScore>= 100){
            if (yourScore<computerScore){
//                you won
                Navigation.findNavController(view).navigate(R.id.action_gamePlayFragement5_to_youLost)

            }
            else if (computerScore< yourScore){

//                computer wins
                Navigation.findNavController(view).navigate(R.id.action_gamePlayFragement5_to_youwon)

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
}