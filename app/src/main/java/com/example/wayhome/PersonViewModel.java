package com.example.wayhome;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wayhome.data.repository.PersonRepository;
import com.example.wayhome.data.room.Person;

import java.util.List;

public class PersonViewModel extends AndroidViewModel {
    PersonRepository repository;
    LiveData<List<Person>> personList;


    public PersonViewModel(@NonNull Application application) {
        super(application);
        repository = new PersonRepository(application);
        personList = repository.getPerson();

    }


    public LiveData<List<Person>> getAllPersons(){
        return personList;
    }


    public void insertPerson(Person person){
        repository.insert(person);
    }


    
}
