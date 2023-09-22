package com.example.plando.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.plando.PlanDoApplication

abstract class BaseFragment<VB : ViewDataBinding>(@LayoutRes val layoutRes: Int) : Fragment() {
    private var _binding: VB? = null
    protected val binding
        get() = _binding!!

    protected val appComponent
        get() = (activity?.application as PlanDoApplication).appComponent

    open fun setBindingData() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        setBindingData()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun <Type> getNavigationResultOnceAndExec(key: String, exec: (Type) -> Unit) =
        findNavController().currentBackStackEntry?.savedStateHandle?.apply {
            getLiveData<Type>(key).observe(viewLifecycleOwner) {
                exec.invoke(it)
                remove<Type>(key)
            }
        }

    fun <Type> setNavigationResult(key: String, result: Type) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
    }

    fun @receiver:DrawableRes Int.getDrawable() =
        ContextCompat.getDrawable(requireContext(), this)?.mutate()

    fun @receiver:ColorRes Int.getColor() = ContextCompat.getColor(requireContext(), this)

    fun View.setViewVisibility(isVisible: Boolean) {
        this.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}