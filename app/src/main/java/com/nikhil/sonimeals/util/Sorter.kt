package com.nikhil.sonimeals.util

import com.nikhil.sonimeals.model.Restaurants

class Sorter {
    companion object {
        var costComparator = Comparator<Restaurants> { res1, res2 ->
            val cost1 = res1.costForTwo
            val cost2 = res2.costForTwo
            if (cost1.compareTo(cost2) == 0) {
                ratingCompartor.compare(res1, res2)
            } else {
                cost1.compareTo(cost2)
            }
        }

        var ratingCompartor = Comparator<Restaurants> { res1, res2 ->
            val rating1 = res1.rating
            val rating2 = res2.rating
            if (rating1.compareTo(rating2) == 0) {
                val costOne = res1.costForTwo
                val costTwo = res2.costForTwo
                costOne.compareTo(costTwo)
            } else {
                rating1.compareTo(rating2)
            }
        }
    }
}