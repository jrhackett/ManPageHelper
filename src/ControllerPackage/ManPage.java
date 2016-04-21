/**
 * Created by jacobhackett on 4/21/16.
 */
public class ManPage {

    String name;
    String synopsis;
    String description;
    String type;

    public ManPage(String name, String synopsis, String description, String type) {
        this.name = name;
        this.synopsis = synopsis;
        this.description = description;
        this.type = type;
    }

    public String toString() {
        return "Name: " + this.name + "\nUsage: " + this.synopsis + "\nDescription: " + this.description;
    }

    public String getName() {
        return this.name;
    }

    public boolean compareType(String type) {
        return this.type.equals(type);
    }
}
