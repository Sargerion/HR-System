package edu.epam.project.command;

public class CommandResult {

    private String page;
    private TransitionType transitionType;

    public CommandResult(String page, TransitionType transitionType) {
        this.page = page;
        this.transitionType = transitionType;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public TransitionType getTransitionType() {
        return transitionType;
    }

    public void setTransitionType(TransitionType transitionType) {
        this.transitionType = transitionType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommandResult{");
        sb.append("page='").append(page).append('\'');
        sb.append(", transitionType=").append(transitionType);
        sb.append('}');
        return sb.toString();
    }
}
