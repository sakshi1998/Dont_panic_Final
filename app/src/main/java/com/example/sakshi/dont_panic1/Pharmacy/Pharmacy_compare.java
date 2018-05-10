package com.example.sakshi.dont_panic1.Pharmacy;

import com.example.sakshi.dont_panic1.Hospital.NearbyHospitalsDetail;

import java.util.Comparator;

public class Pharmacy_compare  implements Comparator<PharmacyDetail> {
    @Override
    public int compare(PharmacyDetail o1, PharmacyDetail o2) {
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
