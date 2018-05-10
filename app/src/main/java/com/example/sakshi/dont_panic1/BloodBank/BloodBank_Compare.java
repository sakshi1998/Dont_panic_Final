package com.example.sakshi.dont_panic1.BloodBank;

import com.example.sakshi.dont_panic1.Hospital.NearbyHospitalsDetail;

import java.util.Comparator;

public class BloodBank_Compare implements Comparator<BloodDetail> {
    @Override
    public int compare(BloodDetail o1, BloodDetail o2) {
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
