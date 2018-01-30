package values;

public class ExtractionValue implements Value<String> {
    private String value;

    public ExtractionValue(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public void setValue(String temps){
        this.value = temps;
    }

    @Override
    public String toString(){
        return value;
    }
}
