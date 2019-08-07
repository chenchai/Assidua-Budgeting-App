package ai.chench.assidua

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager

import android.os.Bundle
import android.util.Log

import com.google.android.material.tabs.TabLayout

import java.util.ArrayList
import java.util.UUID

import ai.chench.assidua.data.Budget
import ai.chench.assidua.data.ExpenditureViewModel
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val viewModel = ViewModelProviders.of(this).get(ExpenditureViewModel::class.java)

        val adapter = BudgetPagerAdapter(
                supportFragmentManager,
                //FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
                // causes a crash when replacing fragments due to lifecycle issues
                FragmentStatePagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT,
                ArrayList())

        viewModel.budgets.observe(this, Observer { budgetMap: Map<UUID, Budget> ->
            Log.d(TAG, "new budget size: " + budgetMap.size + " Notifying adapter!")

            val budgetList = ArrayList(budgetMap.values)

            adapter.setBudgets(budgetList)
            adapter.notifyDataSetChanged()
        })

        viewPager.setAdapter(adapter)
        tabLayout.setupWithViewPager(viewPager)






    }

    companion object {
        private val TAG = "MainActivity"
    }
}
