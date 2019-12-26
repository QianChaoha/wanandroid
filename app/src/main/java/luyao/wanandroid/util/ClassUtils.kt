package luyao.wanandroid.util

import androidx.lifecycle.ViewModel


import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by jingbin on 2018/12/26.
 */

object ClassUtils {

    /**
     * 获取泛型ViewModel的class对象
     */
    fun <T> getViewModel(obj: Any): Class<T>? {
        val currentClass = obj.javaClass
        val tClass = getGenericClass<T>(currentClass, ViewModel::class.java)
        return if (tClass == null || tClass == ViewModel::class.java) {
            null
        } else tClass
    }

    private fun <T> getGenericClass(klass: Class<*>, filterClass: Class<*>): Class<T>? {
        val type = klass.genericSuperclass
        if (type == null || type !is ParameterizedType) return null
        val types = type.actualTypeArguments
        for (t in types) {
            val tClass = t as Class<T>
            if (filterClass.isAssignableFrom(tClass)) {
                return tClass
            }
        }
        return null
    }
}
