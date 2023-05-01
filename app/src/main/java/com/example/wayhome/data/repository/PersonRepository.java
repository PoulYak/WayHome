package com.example.wayhome.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.wayhome.data.room.Person;
import com.example.wayhome.data.room.PersonDao;
import com.example.wayhome.data.room.PersonDatabase;

import java.util.List;

public class PersonRepository {

    PersonDao personDao;

    public PersonRepository(Application application) {

        PersonDatabase db = PersonDatabase.getDatabase(application);
        personDao = db.personDao();
    }

    public LiveData<List<Person>> getPerson(){
        return personDao.getAllPersons();
    }

    public void insert(Person person){
        new insertAsyncTask(personDao).execute(person);
    }




    private static class insertAsyncTask extends AsyncTask<Person, Void, Void>{
        private PersonDao taskDao;

        insertAsyncTask(PersonDao personDao){
            taskDao = personDao;
        }



        @Override
        protected Void doInBackground(Person... people) {
            taskDao.insertAll(people[0]);
            return null;
        }
    }
}
