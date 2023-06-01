package com.cde.cowdataprocessor.deeplearning4j.classifier;

import java.io.IOException;

import com.cde.cowdataprocessor.deeplearning4j.model.SNNMotionModel;
import com.cde.utils.enums.activity.ActivityDescription;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SNNMotionClassifier {
    
    private static Logger LOGGER = LoggerFactory.getLogger(SNNMotionClassifier.class);

    @Autowired
    SNNMotionModel model;

    public ActivityDescription classify(Double x, Double y, Double z, Double alpha, Double beta, Double gamma) {

        INDArray input = Nd4j.create(new double[] {5.0,3.5,1.6,0.6});
        INDArray output = null;

        try {
            output = model.classify(input);
        } catch (IOException e) {
            LOGGER.error("error classifying input values using motion model", e);
            e.printStackTrace();
        }

        if (null != output) LOGGER.info(output.toString());

        if (beta<0) {
            return ActivityDescription.EATING;
        }
        
        if (beta<25) {
            return ActivityDescription.LAYING;
        }
        
        if (beta >= 25){
            return ActivityDescription.STANDING;
        }
        
        return ActivityDescription.STANDING;
    }

}