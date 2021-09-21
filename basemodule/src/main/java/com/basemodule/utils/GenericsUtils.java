package com.basemodule.utils;

import com.basemodule.viewmodel.BaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import timber.log.Timber;

/**
 * Created by Michael on 12/27/17.
 */

public abstract class GenericsUtils {
    @SuppressWarnings("unchecked")
    public static<T> Class<T> getViewModelClass(Class<?> genericClass) {

        Class<T> viewModelClass = null;

        Type[] genericTypes = ((ParameterizedType) Objects.requireNonNull(genericClass.getGenericSuperclass())).getActualTypeArguments();

        for(Type type : genericTypes) {
            try {
                if (type instanceof Class && BaseViewModel.class.isAssignableFrom((Class<?>) type)) {
                    viewModelClass = (Class<T>) type;
                }
            }catch (Throwable t){
                Timber.w(t);
            }
        }
        return viewModelClass;
    }
}

