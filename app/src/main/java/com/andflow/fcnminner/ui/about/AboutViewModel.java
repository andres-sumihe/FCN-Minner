package com.andflow.fcnminner.ui.about;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutViewModel extends ViewModel {

    private MutableLiveData<String> mDescText;
    private MutableLiveData<String> mContactText;

    public AboutViewModel() {
        mDescText = new MutableLiveData<>();
        mDescText.setValue("9 Fruit Creative Team is a community engaged in Cryptocurrency since April 2021, we are focused on developing a new currency called FruitCoins, FruitCoins has the same function as a virtual machine that can run peer-to-peer smart contracts. Fruit Coins was developed to fulfill the final semester assignment of the mobile application development course" +
                "\n\n" +
                "The headquarter location is on Jalan Kemiri Barat, No.825c, Salatiga, Central Java, 50711"
        );

        mContactText = new MutableLiveData<>();
        mContactText.setValue("Telp : +62-851-5600-5454" +
                "\n" +
                "Email : contactnineteam@gmail.com"
        );

    }

    public LiveData<String> getDescText() {
        return mDescText;
    }
    public LiveData<String> getContactText() {
        return mContactText;
    }
}