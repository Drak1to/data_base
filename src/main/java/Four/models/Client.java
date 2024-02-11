package Four.models;

public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int visitsCount;
   private String dayVisits;
   private String comments;
   private int awardCount;



    public Client(int id, String firstName, String lastName, String phoneNumber, int visitsCount, String dayVisits, String comments, int awardCount) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.visitsCount = visitsCount;
        this.dayVisits = dayVisits;
        this.comments = comments;
        this.awardCount = awardCount;

    }

    public Client(String firstName, String lastName, String phoneNumber, int visitorsCount, String dayVisits, String comments, int awardCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.visitsCount = visitorsCount;
        this.dayVisits = dayVisits;
        this.comments = comments;
        this.awardCount = awardCount;

    }

    public Client() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getVisitsCount() {
        return visitsCount;
    }

    public void setVisitsCount(int visitsCount) {
        this.visitsCount = visitsCount;
    }

    public String getDayVisits() {
        return dayVisits;
    }

    public void setDayVisits(String dayVisits) {
        this.dayVisits = dayVisits;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getAwardCount() {
        return awardCount;
    }

    public void setAwardCount(int awardCount) {
        this.awardCount = awardCount;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", visitsCount=" + visitsCount +
                ", dayVisits='" + dayVisits + '\'' +
                ", comments='" + comments + '\'' +
                ", awardCount='" + awardCount + '\'' +
                '}';
    }
}
