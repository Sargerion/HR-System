package edu.epam.project.model.entity;

public class Application extends Entity {

    private Vacancy applicationVacancy;
    private Finder applicationFinder;

    public Application(Integer entityId, Vacancy applicationVacancy, Finder applicationFinder) {
        super(entityId);
        this.applicationVacancy = applicationVacancy;
        this.applicationFinder = applicationFinder;
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
        return applicationVacancy.equals(that.applicationVacancy) && applicationFinder.equals(that.applicationFinder);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31
                + (applicationVacancy != null ? applicationVacancy.hashCode() : 0)
                + (applicationFinder != null ? applicationFinder.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Application{");
        sb.append(super.toString());
        sb.append("applicationVacancy=").append(applicationVacancy);
        sb.append(", applicationFinder=").append(applicationFinder);
        sb.append('}');
        return sb.toString();
    }
}