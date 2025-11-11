package com.example.ejemplorecyclerview

data class Article(
    val title: String,
    val description: String,
    val imageRes: Int
){
    companion object {
        val data
            get() = arrayListOf(
                Article(
                    "Omori",
                    "Ps Omori w",
                    R.drawable.converted_51e4qik4mal__uf894_1000_ql80_
                ),
                Article(
                    "Hiro",
                    "Ps Hiro w",
                    R.drawable.converted_b8902fa0529998a80b5f32854af96771
                ),
                Article(
                    "Keitaro",
                    "Ps Keitaro w",
                    R.drawable.converted_d2a136fb392b340cfc0ae74f951d6a30
                ),
                Article(
                    "Nose",
                    "Ps Nose w",
                    R.drawable.converted_gt2i_nsweaag8jb__1_
                )
            )
    }
}