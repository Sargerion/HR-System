package edu.epam.project.model.entity;

public class Application extends Entity {

    private Vacancy applicationVacancy;
    private Finder applicationFinder;
    private boolean isConfirmApplication;

    public Application(Integer entityId, Vacancy applicationVacancy, Finder applicationFinder, boolean isConfirmApplication) {
        super(entityId);
        this.applicationVacancy = applicationVacancy;
        this.applicationFinder = applicationFinder;
        this.isConfirmApplication = isConfirmApplication;
    }

    public Vacancy getApplicationVacancy() {
        return applicationVacancy;
    }

    public void setApplicationVacancy(Vacancy applicationVacancy) {
        this.applicationVacancy = applicationVacancy;
    }

    public Finder getApplicationFinder() {
        return applicationFinder;
    }

    public void setApplicationFinder(Finder applicationFinder) {
        this.applicationFinder = applicationFinder;
    }

    public boolean isConfirmApplication() {
        return isConfirmApplication;
    }

    public void setConfirmApplication(boolean confirmApplication) {
        isConfirmApplication = confirmApplication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Application that = (Application) o;
        return isConfirmApplication == that.isConfirmApplication && applicationVacancy.equals(that.applicationVacancy) && applicationFinder.equals(that.applicationFinder);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31
                + (applicationVacancy != null ? applicationVacancy.hashCode() : 0)
                + (applicationFinder != null ? applicationFinder.hashCode() : 0)
                + (Boolean.hashCode(isConfirmApplication));
        return result;
    }
}