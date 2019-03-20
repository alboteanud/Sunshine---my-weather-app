package com.example.android.sunshine.ui.main;

import com.example.android.sunshine.data.RepositoryWeather;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final RepositoryWeather mRepository;

    public MainViewModelFactory(RepositoryWeather repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(mRepository);
    }

}
