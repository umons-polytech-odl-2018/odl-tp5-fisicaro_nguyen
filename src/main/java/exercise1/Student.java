package exercise1;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a student.
 * A Student is identified by its registration number.
 * A student gets scored in different courses.
 * These scores are expressed as integers on a scale from 0 to 20.
 */
public class Student {
    /**
     * Creates a new Student.
     *
     * @throws NullPointerException if one of the parameter is null.
     */

    private String name;
    private String registrationNumber;
    //private HashMap<String, Integer> scoreBycourse;
    Map<String, Integer> scoreBycourse=new HashMap<String, Integer>();

    public Student(String name, String registrationNumber) {
        this.name=name;
        this.registrationNumber=registrationNumber;

        if (name==null)
            throw new NullPointerException();
        else if (registrationNumber== null)
            throw new NullPointerException();
    }

    /**
     * Sets the score of this student for the given course.
     * If the score is set twice for the same course, the new score replaces the previous one.
     *
     * @throws NullPointerException if the course name is null.
     * @throws IllegalArgumentException if the score is less than 0 or greater than 20.
     */
    public void setScore(String course, int score) {
        scoreBycourse.put(course,score);
        if (course == null)
            throw new NullPointerException();
        else if (score <0 || score > 20)
            throw new IllegalArgumentException();

    }

    /**
     * Returns the score for the given course.
     *
     * @return the score if found, <code>OptionalInt#empty()</code> otherwise.
     */
    public OptionalInt getScore(String course) {
        Integer nullableScore =scoreBycourse.get(course);
        return nullableScore!= null ? OptionalInt.of(nullableScore) : OptionalInt.empty();
    }

    /**
     * Returns the average score.
     *
     * @return the average score or 0 if there is none.
     */
    public double averageScore() {

        /*int count =0;
        double totalscore = 0.0;
        for (Integer score : scoreBycourse.values()){
            count++;
            totalscore+= score;
        }
        return totalscore /count;*/

   return scoreBycourse.values().stream()
    .mapToInt(Integer::intValue)
    .average()
    .orElse(0.0);
         //récupère les valeurs dans un flux stream en séquentielle ou en parallèle ;
        //On prend les integers, transforme en int c"est des intValue
    }

    /**
     * Returns the course with the highest score.
     *
     * @return the best scored course or <code>Optional#empty()</code> if there is none.
     */
    public Optional<String> bestCourse() {
        return scoreBycourse.entrySet().stream() //entryset donne la paire de valeur cours + note
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) //Map entry comme make pair
            .map(Map.Entry::getKey)
            .findFirst();
    }

    /**
     * Returns the highest score.
     *
     * @return the highest score or 0 if there is none.
     */
    public int bestScore() {
        return scoreBycourse.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .mapToInt(Map.Entry::getValue)
            .findFirst()
            .orElse(0);
    }

    /**
     * Returns the set of failed courses sorted by decreasing score.
     * A course is considered as passed if its score is higher than 12.
     */
    public Set<String> failedCourses() {
        return scoreBycourse.entrySet().stream()
            .filter(entry -> entry.getValue()<12)
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toCollection(LinkedHashSet::new));

        /*
        List<Map.Entry<String,Integer>> filterdEntries = new ArrayList<>();
        for (Map.Entry<String, Integer>entry : scoreBycourse.entrySet()){
            if (entry.getValue() < 12) {
                filterdEntries.add(entry);
            }
         }
         Collections.sort .................................................

            return failedCourses;
        }*/

    }

    /**
     * Returns <code>true</code> if the student has an average score greater than or equal to 12.0 and has less than 3 failed courses.
     */
    public boolean isSuccessful() {
        //return averageScore() >=12 && failedCourses().size < 3;
    return false;
    }

    /**
     * Returns the set of courses for which the student has received a score, sorted by course name.
     */
    public Set<String> attendedCourses() { return scoreBycourse.keySet().stream()
    .sorted()
    .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public String getName() {
        return name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public Map<String,Integer>getScoreBycourse(){return scoreBycourse;}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getName());
        sb.append(" (").append(getRegistrationNumber()).append(")");
        return sb.toString();
    }
}
