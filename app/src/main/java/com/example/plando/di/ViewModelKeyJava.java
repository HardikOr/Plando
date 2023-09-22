package com.example.plando.di;

import androidx.lifecycle.ViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

@MapKey
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewModelKeyJava {
    Class<? extends ViewModel> value();
}