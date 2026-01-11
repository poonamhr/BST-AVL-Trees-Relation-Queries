package edu.cn5005.relations;

import edu.cn5005.persons.Person;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// This class implements the code for Part D and E

public class RelationEngine {
    private final Map<Integer, Person> personsById; // id → Person mapping
    private final Map<String, Integer> idByName; // name → id mapping for quick lookup

    public RelationEngine(Map<Integer, Person> personsById,
                          Map<String, Integer> idByName) {
        this.personsById = personsById;
        this.idByName = idByName;
    }

    // Helper Methods

    // Retrieving a Person object by id
    private Person getPersonById(int id) {
        return personsById.get(id);
    }

    // Retrieving a Person object by name using name -> id mapping
    private Person getPersonByName(String name) {
        Integer id = idByName.get(name);
        if (id == null) {
            System.err.println("Unknown name: " + name);
            return null;
        }
        return getPersonById(id);
    }

    // Getting all parent IDs (father and mother) of a person
    private Set<Integer> getParentIds(Person p) {
        Set<Integer> parents = new HashSet<>();
        if (p.getFatherId() != null) {
            parents.add(p.getFatherId());
        }
        if (p.getMotherId() != null) {
            parents.add(p.getMotherId());
        }
        return parents;
    }

    // Getting all children IDs of a person
    private Set<Integer> getChildrenIds(Person p) {
        Set<Integer> children = new HashSet<>();
        int parentId = p.getId();

        for (Person candidate : personsById.values()) {
            Integer fatherId = candidate.getFatherId();
            Integer motherId = candidate.getMotherId();

            if ((fatherId != null && fatherId == parentId) ||
                    (motherId != null && motherId == parentId)) {
                children.add(candidate.getId());
            }
        }
        return children;
    }

    // Getting all grandparent IDs of a person
    private Set<Integer> getGrandparentIds(Person p) {
        Set<Integer> grandparents = new HashSet<>();
        for (Integer parentId : getParentIds(p)) {
            Person parent = getPersonById(parentId);
            if (parent != null) {
                grandparents.addAll(getParentIds(parent)); // Add parents of the parent
            }
        }
        return grandparents;
    }

    // Getting all grandchild IDs of a person
    private Set<Integer> getGrandchildIds(Person p) {
        Set<Integer> grandChildren = new HashSet<>();
        for (Integer childId : getChildrenIds(p)) {
            Person child = getPersonById(childId);
            if (child != null) {
                grandChildren.addAll(getChildrenIds(child)); // Add children of the child
            }
        }
        return grandChildren;
    }

    // D.1 – Father / Mother

    public boolean isFather(int idA, int idB) {
        Person a = getPersonById(idA);
        Person b = getPersonById(idB);

        // If either person is missing, return false
        if (a == null || b == null) {
            System.err.println("isFather: unknown id " + idA + " or " + idB);
            return false;
        }

        // Check gender before checking parent link
        String gender = a.getGender();
        if (gender == null || !gender.equalsIgnoreCase("male")) {
            return false;
        }

        Integer fatherIdOfB = b.getFatherId();
        return fatherIdOfB != null && fatherIdOfB == a.getId();
    }

    public boolean isMother(int idA, int idB) {
        Person a = getPersonById(idA);
        Person b = getPersonById(idB);

        if (a == null || b == null) {
            System.err.println("isMother: unknown id " + idA + " or " + idB);
            return false;
        }

        String gender = a.getGender();
        if (gender == null || !gender.equalsIgnoreCase("female")) {
            return false;
        }

        Integer motherIdOfB = b.getMotherId();
        return motherIdOfB != null && motherIdOfB == a.getId();
    }

    // D.2 – Child / Sibling

    public boolean isChild(int idA, int idB) {
        // A is child of B if B is father or mother of A
        return isFather(idB, idA) || isMother(idB, idA);
    }

    public boolean isSibling(int idA, int idB) {
        if (idA == idB) {
            return false; // A person is not their own sibling
        }

        Person a = getPersonById(idA);
        Person b = getPersonById(idB);

        if (a == null || b == null) {
            System.err.println("isSibling: unknown id " + idA + " or " + idB);
            return false;
        }

        Integer aFather = a.getFatherId();
        Integer aMother = a.getMotherId();
        Integer bFather = b.getFatherId();
        Integer bMother = b.getMotherId();

        // Checking if they share at least one parent
        boolean shareFather = (aFather != null && aFather.equals(bFather));
        boolean shareMother = (aMother != null && aMother.equals(bMother));

        return shareFather || shareMother;
    }


    // D.3 – Grandparent / Grandchild

    public boolean isGrandparent(int idA, int idB) {
        Person a = getPersonById(idA);
        Person b = getPersonById(idB);

        if (a == null || b == null) {
            System.err.println("isGrandparent: unknown id " + idA + " or " + idB);
            return false;
        }

        Set<Integer> grandparentsOfB = getGrandparentIds(b);
        // Checking if A is in B's grandparents
        return grandparentsOfB.contains(a.getId());
    }

    public boolean isGrandchild(int idA, int idB) {
        return isGrandparent(idB, idA);
    }


    // D.4 – First Cousins

    public boolean isFirstCousin(int idA, int idB) {
        if (idA == idB) {
            return false;
        }
        if (isSibling(idA, idB)) {
            return false;
        }

        Person a = getPersonById(idA);
        Person b = getPersonById(idB);

        if (a == null || b == null) {
            System.err.println("isFirstCousin: unknown id " + idA + " or " + idB);
            return false;
        }

        // They are first cousins if they share at least one grandparent
        Set<Integer> grandparentsA = getGrandparentIds(a);
        Set<Integer> grandparentsB = getGrandparentIds(b);

        for (Integer ga : grandparentsA) {
            if (grandparentsB.contains(ga)) {
                return true;
            }
        }
        return false;
    }

    // D.5 – relation(nameA, nameB)

    public String relation(String nameA, String nameB) {
        Person a = getPersonByName(nameA);
        Person b = getPersonByName(nameB);

        if (a == null || b == null) {
            return "Κάποιο από τα ονόματα δεν βρέθηκε στο οικογενειακό δέντρο.";
        }

        int idA = a.getId();
        int idB = b.getId();

        if (idA == idB) {
            return nameA + " και " + nameB + " είναι το ίδιο άτομο.";
        }

        if (isFather(idA, idB)) {
            return nameA + " είναι πατέρας του/της " + nameB + ".";
        }
        if (isMother(idA, idB)) {
            return nameA + " είναι μητέρα του/της " + nameB + ".";
        }
        if (isChild(idA, idB)) {
            return nameA + " είναι παιδί του/της " + nameB + ".";
        }
        if (isSibling(idA, idB)) {
            return nameA + " και " + nameB + " είναι αδέλφια.";
        }
        if (isGrandparent(idA, idB)) {
            return nameA + " είναι παππούς/γιαγιά του/της " + nameB + ".";
        }
        if (isGrandchild(idA, idB)) {
            return nameA + " είναι εγγόνι του/της " + nameB + ".";
        }
        if (isFirstCousin(idA, idB)) {
            return nameA + " και " + nameB + " είναι πρώτα ξαδέλφια.";
        }
        if (isHalfSibling(idA, idB)) {
            return nameA + " και " + nameB + " είναι ετεροθαλή αδέλφια.";
        }
        if (isSpouse(idA, idB)) {
            return nameA + " και " + nameB + " είναι σύζυγοι.";
        }

        return "Δεν υπάρχει άμεση σχέση μεταξύ " + nameA + " και " + nameB + " στο οικογενειακό αυτό δέντρο.";
    }


    // Part E

    // Half-siblings: share exactly one parent
    public boolean isHalfSibling(int idA, int idB) {
        if (idA == idB) return false;
        Person a = getPersonById(idA);
        Person b = getPersonById(idB);
        if (a == null || b == null) return false;
        boolean shareFather = a.fatherId != null && a.fatherId.equals(b.fatherId);
        boolean shareMother = a.motherId != null && a.motherId.equals(b.motherId);

        // True only if they share exactly one parent (XOR)
        return (shareFather ^ shareMother);
    }

    // Spouse checking based on spouse_id field
    public boolean isSpouse(int idA, int idB) {
        Person a = getPersonById(idA);
        if (a == null || a.getSpouseId() == null) return false;
        return a.getSpouseId() == idB;
    }
}

