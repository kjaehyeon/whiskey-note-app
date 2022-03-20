package org.jhkim.whiskey_note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.jhkim.whiskey_note.fragment.BoardFragment
import org.jhkim.whiskey_note.fragment.SearchFragment
import org.jhkim.whiskey_note.fragment.ShelfFragment

class MainActivity : AppCompatActivity(){
    private val PAGE_CNT = 3
    private val pager : ViewPager2 by lazy{
        findViewById(R.id.pager)
    }
    private val bnv_main : BottomNavigationView by lazy {
        findViewById(R.id.bnv_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager.apply {
            adapter = ViewPagerAdapter(this@MainActivity)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    bnv_main.selectedItemId = when (position) {
                        0 -> R.id.nav_search
                        1 -> R.id.nav_board
                        2 -> R.id.nav_shelf
                        else -> throw RuntimeException("Runtime Error")
                    }
                }
            })
            isUserInputEnabled = false //스와이프로 페이지 넘기기 제한
        }
        bnv_main.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_search -> pager.currentItem = 0
                R.id.nav_board -> pager.currentItem = 1
                R.id.nav_shelf -> pager.currentItem = 2
                else -> throw RuntimeException("Runtime Error")
            }
            true
        }
    }
    private inner class ViewPagerAdapter(fragment : FragmentActivity): FragmentStateAdapter(fragment){
        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> SearchFragment()
                1 -> BoardFragment()
                2 -> ShelfFragment()
                else -> throw RuntimeException("Runtime Error")
            }
        }
        override fun getItemCount():Int = PAGE_CNT
    }
}