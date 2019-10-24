package com.craiovadata.android.sunshine.ui.main;

import com.craiovadata.android.sunshine.data.RepositoryWeather;

import org.jetbrains.annotations.NotNull;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final RepositoryWeather mRepository;

    public ViewModelFactory(RepositoryWeather repository) {
        this.mRepository = repository;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainViewModel(mRepository);
    }

}
