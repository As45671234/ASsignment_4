public abstract class Animal {
    private String name;
    private String species;
    private int age;

    public Animal(){}

    public Animal(String name, String species, int age) {
        this.name = name;
        this.species = species;
        this.age = age;
    }

    public String getName() { return name; }
    public String getSpecies() { return species; }
    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }
}
