package application.modle.decorate;

import java.util.Map;

public abstract class DecoratorProvide implements DealData{
    private DealData dealData;

    DecoratorProvide(DealData dealData){
        this.dealData = dealData;
    }


    @Override
    public String processData(Map params){
        return dealData.processData(params);
    }
}
