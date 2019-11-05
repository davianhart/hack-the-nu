package Objects;

import java.util.ArrayList;

public class VolCards {

    // Field Variables
    private String title;
    private String companyName;
    private String imageLocation;
    private ArrayList<String> oppurtunityTags;
    private String oppurtunityLocation;
    private String description;
    private Company company;


    // Constructor
    public VolCards(String title, String companyName, String imageLocation, ArrayList<String> oppurtunityTags, String oppurtunityLocation, String description, Company company) {
        this.title = title;
        this.companyName = companyName;
        this.imageLocation = imageLocation;
        this.oppurtunityTags = oppurtunityTags;
        this.oppurtunityLocation = oppurtunityLocation;
        this.description = description;
        this.company = company;
    }

    public VolCards(String title, String companyName, String imageLocation, ArrayList<String> oppurtunityTags) {
        this.title = title;
        this.companyName = companyName;
        this.imageLocation = imageLocation;
        this.oppurtunityTags = oppurtunityTags;
    }

    public VolCards() {
        //Default Constructor
    }


    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public ArrayList<String> getOppurtunityTags() {
        return oppurtunityTags;
    }

    public void setOppurtunityTags(ArrayList<String> oppurtunityTags) {
        this.oppurtunityTags = oppurtunityTags;
    }

    public String getOppurtunityLocation() {
        return oppurtunityLocation;
    }

    public void setOppurtunityLocation(String oppurtunityLocation) {
        this.oppurtunityLocation = oppurtunityLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    // Methods


    @Override
    public String toString() {
        return "VolCards{" +
                "title='" + title + '\'' +
                ", companyName='" + companyName + '\'' +
                ", imageLocation='" + imageLocation + '\'' +
                ", oppurtunityTags=" + oppurtunityTags +
                ", oppurtunityLocation='" + oppurtunityLocation + '\'' +
                ", description='" + description + '\'' +
                ", location='" + "N/A" + '\'' +
                ", company=" + company +
                '}';
    }
}
