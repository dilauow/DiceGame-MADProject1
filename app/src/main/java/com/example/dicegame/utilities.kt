package com.example.dicegame

class utilities {
    companion object{
        public fun makeHighestValuesFalse(list: MutableList<Int>): MutableList<Boolean>{
            val rollaBility = mutableListOf<Boolean>(true,true,true,true,true,)
            val maxScore = list.max()
            for(i in 0..4){
                if(list[i] == maxScore){
                    rollaBility[i] = false
                }
            }
            return rollaBility
        }
    }


}