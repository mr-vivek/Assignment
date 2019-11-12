package com.vivek.assignment.application.di;

import com.vivek.assignment.application.AssignmentApp;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, AppModule.class})
public interface ApiComponents {
    void inject(AssignmentApp application);
}
