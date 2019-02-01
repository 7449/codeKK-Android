package com.codekk.mvp.presenter


import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.codekk.R
import com.codekk.mvp.view.ViewManager
import com.codekk.ui.base.BaseModel
import com.codekk.ui.base.BasePresenterImpl
import com.codekk.ui.fragment.*

@Suppress("UNCHECKED_CAST")
fun <T : Fragment> AppCompatActivity.findFragmentByTag(tag: String, ifNone: (String) -> T): T =
        supportFragmentManager.findFragmentByTag(tag) as T? ?: ifNone(tag)


class MainPresenterImpl(view: ViewManager.MainView) : BasePresenterImpl<ViewManager.MainView, BaseModel<*>>(view), PresenterManager.MainPresenter {

    companion object {
        const val FIRST_FRAGMENT = -1
        private const val TYPE_OP_FRAGMENT = 0
        private const val TYPE_OP_TAG = "op"
        private const val TYPE_OPA_FRAGMENT = 1
        private const val TYPE_OPA_TAG = "opa"
        private const val TYPE_JOB_FRAGMENT = 2
        private const val TYPE_JOB_TAG = "job"
        private const val TYPE_BLOG_FRAGMENT = 3
        private const val TYPE_BLOG_TAG = "blog"
        private const val TYPE_RECOMMEND_FRAGMENT = 4
        private const val TYPE_RECOMMEND_TAG = "recommend"
    }

    private lateinit var opFragment: Fragment
    private lateinit var opaFragment: Fragment
    private lateinit var jobFragment: Fragment
    private lateinit var blogFragment: Fragment
    private lateinit var recommendFragment: Fragment

    override fun switchId(@MenuRes menuRes: Int) {
        when (menuRes) {
            R.id.op, FIRST_FRAGMENT -> setSelectFragment(TYPE_OP_FRAGMENT)
            R.id.opa -> setSelectFragment(TYPE_OPA_FRAGMENT)
            R.id.job -> setSelectFragment(TYPE_JOB_FRAGMENT)
            R.id.blog -> setSelectFragment(TYPE_BLOG_FRAGMENT)
            R.id.recommend -> setSelectFragment(TYPE_RECOMMEND_FRAGMENT)
            R.id.setting -> view?.switchSetting()
            R.id.pact -> view?.switchPact()
        }
    }

    override fun onBackPressed() {
        val fragment = view?.mainActivity?.supportFragmentManager?.findFragmentByTag(TYPE_OP_TAG)
        if (fragment != null && fragment.isHidden) {
            setSelectFragment(TYPE_OP_FRAGMENT)
            view?.selectMenuFirst()
        } else {
            view?.onBack()
        }
    }

    override fun onMainDestroy() {

    }


    private fun setSelectFragment(type: Int) {
        val activity = view?.mainActivity ?: return
        val manager = activity.supportFragmentManager
        val transaction = manager.beginTransaction()
        hideFragment(transaction)
        when (type) {
            TYPE_OP_FRAGMENT -> {
                opFragment = activity.findFragmentByTag(TYPE_OP_TAG) { OpListFragment.newInstance() }
                if (opFragment.isAdded) {
                    transaction.show(opFragment)
                } else {
                    transaction.add(R.id.fragment, opFragment, TYPE_OP_TAG)
                }
            }
            TYPE_OPA_FRAGMENT -> {
                opaFragment = activity.findFragmentByTag(TYPE_OPA_TAG) { OpaListFragment.newInstance() }
                if (opaFragment.isAdded) {
                    transaction.show(opaFragment)
                } else {
                    transaction.add(R.id.fragment, opaFragment, TYPE_OPA_TAG)
                }
            }
            TYPE_JOB_FRAGMENT -> {
                jobFragment = activity.findFragmentByTag(TYPE_JOB_TAG) { JobListFragment.newInstance() }
                if (jobFragment.isAdded) {
                    transaction.show(jobFragment)
                } else {
                    transaction.add(R.id.fragment, jobFragment, TYPE_JOB_TAG)
                }
            }
            TYPE_BLOG_FRAGMENT -> {
                blogFragment = activity.findFragmentByTag(TYPE_BLOG_TAG) { BlogListFragment.newInstance() }
                if (blogFragment.isAdded) {
                    transaction.show(blogFragment)
                } else {
                    transaction.add(R.id.fragment, blogFragment, TYPE_BLOG_TAG)
                }
            }
            TYPE_RECOMMEND_FRAGMENT -> {
                recommendFragment = activity.findFragmentByTag(TYPE_RECOMMEND_TAG) { RecommendListFragment.newInstance() }
                if (recommendFragment.isAdded) {
                    transaction.show(recommendFragment)
                } else {
                    transaction.add(R.id.fragment, recommendFragment, TYPE_RECOMMEND_TAG)
                }
            }
        }
        transaction.commit()
    }


    private fun hideFragment(transaction: FragmentTransaction) {
        if (::opFragment.isInitialized) {
            transaction.hide(opFragment)
        }
        if (::opaFragment.isInitialized) {
            transaction.hide(opaFragment)
        }
        if (::jobFragment.isInitialized) {
            transaction.hide(jobFragment)
        }
        if (::blogFragment.isInitialized) {
            transaction.hide(blogFragment)
        }
        if (::recommendFragment.isInitialized) {
            transaction.hide(recommendFragment)
        }
    }
}
