package edu.salleurl.ls30394.crowdie.model;

import android.location.Location;

/**
 * Created by avoge on 04/03/2017.
 */

public class User {

    private String userName;

    private Location userLocation;

    private boolean isLifeGuard;

    private boolean firstAidSkills;

    private boolean canPerformCPR;

    private boolean hasMentalHealthIssues;

    private boolean hasCardioVascularIssues;

    private boolean isPhysicallyDisabled;

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Location getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(Location userLocation) {
        this.userLocation = userLocation;
    }

    public boolean isLifeGuard() {
        return isLifeGuard;
    }

    public void setLifeGuard(boolean lifeGuard) {
        isLifeGuard = lifeGuard;
    }

    public boolean isFirstAidSkills() {
        return firstAidSkills;
    }

    public void setFirstAidSkills(boolean firstAidSkills) {
        this.firstAidSkills = firstAidSkills;
    }

    public boolean isHasMentalHealthIssues() {
        return hasMentalHealthIssues;
    }

    public void setHasMentalHealthIssues(boolean hasMentalHealthIssues) {
        this.hasMentalHealthIssues = hasMentalHealthIssues;
    }

    public boolean isHasCardioVascularIssues() {
        return hasCardioVascularIssues;
    }

    public void setHasCardioVascularIssues(boolean hasCardioVascularIssues) {
        this.hasCardioVascularIssues = hasCardioVascularIssues;
    }

    public boolean isPhysicallyDisabled() {
        return isPhysicallyDisabled;
    }

    public void setPhysicallyDisabled(boolean physicallyDisabled) {
        isPhysicallyDisabled = physicallyDisabled;
    }

    public boolean isCanPerformCPR() {
        return canPerformCPR;
    }

    public void setCanPerformCPR(boolean canPerformCPR) {
        this.canPerformCPR = canPerformCPR;
    }
}
