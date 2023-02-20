package com.example.reading.presentation.view.base


import android.content.Context
import android.view.View
import android.view.ViewParent
import androidx.viewbinding.ViewBinding
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.util.concurrent.ConcurrentHashMap

abstract class ViewBindingEpoxyModelWithHolder<T : ViewBinding> :
    EpoxyModelWithHolder<ViewBindingHolder>() {
    private var _binding: T? = null
    protected val binding: T
        get() = _binding!!

    protected val context: Context by lazy { binding.root.context }

    @Suppress("UNCHECKED_CAST")
    override fun bind(holder: ViewBindingHolder) {
        _binding = (holder.viewBinding as T)
        bind()
    }

    private fun bind() {
        initializeView()
        initializeComponent()
        initializeEvents()
        initializeData()
        bindView()
    }

    open fun initializeView() {}
    open fun initializeComponent() {}
    open fun initializeEvents() {}
    open fun initializeData() {}
    open fun bindView() {}


    @Suppress("UNCHECKED_CAST")
    override fun unbind(holder: ViewBindingHolder) {
        (holder.viewBinding as T).unbind()
        _binding = null
    }

    open fun T.unbind() {}

    override fun createNewHolder(parent: ViewParent): ViewBindingHolder {
        return ViewBindingHolder(this::class.java)
    }
}

// Static cache of a method pointer for each type of item used.
private val sBindingMethodByClass = ConcurrentHashMap<Class<*>, Method>()

@Suppress("UNCHECKED_CAST")
@Synchronized
private fun getBindMethodFrom(javaClass: Class<*>): Method =
    sBindingMethodByClass.getOrPut(javaClass) {
        val actualTypeOfThis = getSuperclassParameterizedType(javaClass)
        val viewBindingClass = actualTypeOfThis.actualTypeArguments[0] as Class<ViewBinding>
        viewBindingClass.getDeclaredMethod("bind", View::class.java)
            ?: error("The binder class ${javaClass.canonicalName} should have a method bind(View)")
    }

private fun getSuperclassParameterizedType(klass: Class<*>): ParameterizedType {
    val genericSuperclass = klass.genericSuperclass
    return (genericSuperclass as? ParameterizedType)
        ?: getSuperclassParameterizedType(genericSuperclass as Class<*>)
}

class ViewBindingHolder(private val epoxyModelClass: Class<*>) : EpoxyHolder() {
    // Using reflection to get the static binding method.
    // Lazy so it's computed only once by instance, when the 1st ViewHolder is actually created.
    private val bindingMethod by lazy { getBindMethodFrom(epoxyModelClass) }

    internal lateinit var viewBinding: ViewBinding
    override fun bindView(itemView: View) {
        // The 1st param is null because the binding method is static.
        viewBinding = bindingMethod.invoke(null, itemView) as ViewBinding
    }
}