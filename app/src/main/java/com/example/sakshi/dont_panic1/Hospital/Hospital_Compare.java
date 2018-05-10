package com.example.sakshi.dont_panic1.Hospital;

import android.util.Log;

import java.util.Comparator;

/**
 * Created by Payal on 07-05-2018.
 */

public class Hospital_Compare implements Comparator<NearbyHospitalsDetail>{
    @Override
    public int compare(NearbyHospitalsDetail o1, NearbyHospitalsDetail o2) {
        int answer=0;
        if(o1.getavailaibility()>o2.getavailaibility())
            answer=-1;
        else if(o1.getavailaibility()<o2.getavailaibility())
            answer=1;
        else
            answer=0;
        if(answer==0)
            return o2.getRating().compareTo(o1.getRating());
        return answer;
    }
}

