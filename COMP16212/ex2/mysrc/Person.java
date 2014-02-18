import java.awt.*;

public abstract class Person
{
    private final String personName;
    private String latestSaying;
    private final PersonImage image;
    
    public Person(final String personName) {
        super();
        this.personName = personName;
        this.latestSaying = "I am " + this.personName;
        this.image = this.makeImage();
    }
    
    public PersonImage makeImage() {
        return new PersonImage(this);
    }
    
    public String getPersonName() {
        return this.personName;
    }
    
    public String getLatestSaying() {
        return this.latestSaying;
    }
    
    public PersonImage getImage() {
        return this.image;
    }
    
    public abstract String getPersonType();
    
    public String getClassHierarchy() {
        return "Person";
    }
    
    public abstract Color getColour();
    
    public boolean isHappy() {
        return true;
    }
    
    public abstract String getCurrentSaying();
    
    public void speak() {
        this.latestSaying = this.getCurrentSaying();
        this.image.update();
        this.image.flash(2, 20);
    }
    
    public String toString() {
        return this.getPersonType() + " " + this.getPersonName() + " " + this.isHappy() + " " + this.getLatestSaying();
    }
}
