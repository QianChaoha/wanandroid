package luyao.wanandroid.ui.login

import android.app.ProgressDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.title_layout.*
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.databinding.ActivityLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.ThreadPoolExecutor

/**
 * Created by Lu
 * on 2018/4/5 07:56
 */
class LoginActivity : luayo.mvvm.core.base.BaseVMActivity<LoginViewModel, ActivityLoginBinding>() {

    val mViewModel: LoginViewModel by viewModel()

    override fun getLayoutResId() = R.layout.activity_login

    override fun initView() {
        //调用setLifecycleOwner之后，绑定了LiveData数据源的xml控件才会随着数据变化而改变
        mBinding.lifecycleOwner = this
        //给DataBinding设置数据源ViewModel
        mBinding.viewModel = mViewModel
        mToolbar.setTitle(R.string.login)
        mToolbar.setNavigationIcon(R.drawable.arrow_back)
    }

    override fun initData() {
        mToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun startObserve() {
        mViewModel.apply {
            // new Observer(){}  lambda表达式  -->   Observer{}
            mRegisterUser.observe(this@LoginActivity, Observer {
                it?.run {
                    mViewModel.login()
                }
            })

            uiState.observe(this@LoginActivity, Observer {
                if (it.showProgress) showProgressDialog()

                it.showSuccess?.let {
                    dismissProgressDialog()
//                    startKtxActivity<MainFragment>()
                    finish()
                }

                it.showError?.let { err ->
                    dismissProgressDialog()
                    toast(err)
                }
            })
        }
    }

    private var progressDialog: ProgressDialog? = null
    private fun showProgressDialog() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

}