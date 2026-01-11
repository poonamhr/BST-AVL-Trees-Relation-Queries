package edu.cn5005.persons;

// This code represents one person from persons.csv.

public class Person {

    public final int id;
    public final String name;
    public final String gender;
    public final Integer fatherId;
    public final Integer motherId;
    public final Integer spouseId;

    // Constructs a Person object using parsed CSV values.
    public Person(int id, String name, String gender, Integer fatherId, Integer motherId, Integer spouseId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.fatherId = fatherId;
        this.motherId = motherId;
        this.spouseId = spouseId;
    }

    // Getter methods used by parser, tests, and genealogy logic
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public Integer getMotherId() {
        return motherId;
    }

    public Integer getSpouseId() {
        return spouseId;
    }
}


