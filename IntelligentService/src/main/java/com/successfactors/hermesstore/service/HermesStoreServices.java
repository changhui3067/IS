package com.successfactors.hermesstore.service;

/**
 * Created by Xc on 2016/4/19.
 */
public class HermesStoreServices {
    private static HermesStoreServices instance;
    public static HermesStoreServices getInstance(){
        if(instance==null){
            instance = new HermesStoreServices();
        }
        return instance;
    }


}
