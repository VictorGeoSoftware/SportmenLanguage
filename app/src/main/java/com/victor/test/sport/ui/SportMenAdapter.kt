package com.victor.test.sport.ui

import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.victor.test.sport.R
import com.victor.test.sport.data.PlayerDto
import com.victor.test.sport.data.SportMenItem
import com.victor.test.sport.data.TypeDto
import com.victor.test.sport.utils.inflate
import com.victor.test.sport.utils.loadPhotoUrl
import com.victor.test.sport.utils.trace
import kotlinx.android.synthetic.main.adapter_sport_men.view.*
import kotlinx.android.synthetic.main.adapter_title.view.*

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */
class SportMenAdapter(sportMenList: ArrayList<TypeDto>): RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private lateinit var nameFilter: NameFilter
    private val completeSportMenList = ArrayList<SportMenItem>()

    companion object {
        const val TYPE_TITLE = 0
        const val TYPE_SPORT_MEN = 1
    }


    init {
        updateList(sportMenList)
    }

    override fun getItemCount(): Int {
        return completeSportMenList.size
    }

    override fun getItemViewType(position: Int): Int {
        var type = TYPE_TITLE

        if (completeSportMenList[position].sportMen != null) {
            type = TYPE_SPORT_MEN
        }

        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_TITLE) {
            TitleViewHolder(parent.inflate(R.layout.adapter_title))
        } else {
            SportMenViewHolder(parent.inflate(R.layout.adapter_sport_men))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SportMenViewHolder) {
            holder.bind(completeSportMenList[position].sportMen)
        } else if (holder is TitleViewHolder) {
            holder.bind(completeSportMenList[position].title)
        }
    }

    override fun getFilter(): Filter {
        return nameFilter
    }

    fun updateList(sportMenList: ArrayList<TypeDto>) {
        for (sportMenType in sportMenList) {
            completeSportMenList.add(SportMenItem(sportMenType.title, sportMenType.title, null))

            trace("SportMenAdapter - init - sportMan :: ${sportMenType.players.size}")
            for (sportMen in sportMenType.players) {
                completeSportMenList.add(SportMenItem(null, null, sportMen))
            }
        }
        trace("SportMenAdapter - init :: ${completeSportMenList.size}")

        nameFilter = NameFilter(this, completeSportMenList)
        notifyDataSetChanged()
    }



    class TitleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(title: String?) = with(itemView) {
            txtTitle.text = title
        }
    }

    class SportMenViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(player: PlayerDto?) = with(itemView) {

            val completeName = player?.name + " " + player?.surname
            txtCompleteName.text = completeName

            imgPhoto.loadPhotoUrl(player?.image!!)
        }
    }

    /**
     * This custom filter manage sport man name and surname.
     */
    class NameFilter(private val sportMenAdapter: SportMenAdapter, private val sportMenList: ArrayList<SportMenItem>): Filter() {
        private val originalSportMenList = ArrayList<SportMenItem>()
        private val filteredSportMen = ArrayList<SportMenItem>()

        init {
            originalSportMenList.addAll(sportMenList)
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {

            filteredSportMen.clear()
            val filterResults = FilterResults()

            if (constraint?.length == 0) {
                filteredSportMen.addAll(originalSportMenList)
            } else {
                val filterPattern = constraint?.toString()?.toLowerCase()?.trim()

                for (sportMen in originalSportMenList) {

                    if (sportMen.sportMen != null) {
                        val matchName = sportMen.sportMen.name.toLowerCase()!!.startsWith(filterPattern!!, true)
                        val matchSurname = sportMen.sportMen.surname.toLowerCase()!!.startsWith(filterPattern!!, true)
//                        trace("NameFilter - filterPattern :: $filterPattern  $matchName   $matchSurname")

                        if (matchName || matchSurname) {
                            filteredSportMen.add(sportMen)
                        }
                    }
                }
            }

            filterResults.values = filteredSportMen
            filterResults.count = filteredSportMen.size
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            sportMenAdapter.completeSportMenList.clear()
            sportMenAdapter.completeSportMenList.addAll(filteredSportMen)
            sportMenAdapter.notifyDataSetChanged()
        }
    }

}
