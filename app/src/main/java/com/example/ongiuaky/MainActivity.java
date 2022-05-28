package com.example.ongiuaky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ongiuaky.ViewModel.DogAdapter;
import com.example.ongiuaky.ViewModel.DogApiService;
import com.example.ongiuaky.databinding.ActivityMainBinding;
import com.example.ongiuaky.model.AppDatabase;
import com.example.ongiuaky.model.DogBreed;
import com.example.ongiuaky.model.DogDAO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements DogAdapter.OnDogListener {
    private DogApiService apiService;
    private DogAdapter dogsAdapter;
    private List<DogBreed> dogList;
    private ActivityMainBinding binding;
    private DogDAO DogDAO;
    private AppDatabase appDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        binding.rvDogApp.setLayoutManager(new GridLayoutManager(this,2));
        dogList=new ArrayList<>();
        dogsAdapter=new DogAdapter(dogList,this);
        binding.rvDogApp.setAdapter(dogsAdapter);
        apiService=new DogApiService();
        apiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@NonNull List<DogBreed> dogBreeds) {
                        Log.d("DEBUG","success");
                        for(DogBreed dog: dogBreeds){
                            DogBreed i=new DogBreed(dog.getId(),dog.getName(),dog.getLifeSpan(),dog.getOrigin(),dog.getUrl(),dog.getBreed_for(),dog.getBreed_group(),dog.getTemperament());
//                            DogBreed.height j=dog.getHeight();
//                            DogBreed.height k=dog.getWeight();
//                            i.setHeight(j);
//                            i.setWeight(k);
                            dogList.add(i);
                            Log.d("DEBUG",i.getName());
                            dogsAdapter.notifyDataSetChanged();
                        }
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                appDatabase = AppDatabase.getInstance(getApplicationContext());
                                DogDAO = appDatabase.contactDAO();
                                for(DogBreed dog:dogList){
                                    DogBreed i=new DogBreed(dog.getId(),dog.getName(),
                                            dog.getLifeSpan(),dog.getOrigin(),dog.getUrl(),dog.getBreed_for(),dog.getBreed_group(),dog.getTemperament());
                                    DogDAO.insert(i);
                                }
                            }
                        });
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("DEBUG","Fail"+e.getMessage());
                    }
                });
    }

    @Override
    public void onDogClick(int position) {

    }
}