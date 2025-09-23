public class Meeting extends Task {

    protected String topic;
    protected String project;
    protected String start;

    public Meeting(int id, String topic, String project, String start) {
        super(id);
        this.topic = topic;
        this.project = project;
        this.start = start;
    }

    public String getTopic() {
        return topic;
    }

    public String getProject() {
        return project;
    }

    public String getStart() {
        return start;
    }

    //
//    @Override
//    public boolean matches(String query) {
//        boolean result = true;
//        if (query == null) {
//            result = false;
//        } else {
//            String lowerQuery = query.toLowerCase();
//            if (topic == null || !topic.toLowerCase().contains(lowerQuery)) {
//                if (project == null || !project.toLowerCase().contains(lowerQuery)) {
//                    result = false;
//                }
//            }
//        }
//        return result;
//    }
    @Override
    public boolean matches(String query) {
        if (query == null) return false;
        if (topic != null && topic.contains(query)) {
            return true;
        }
        if (project != null && project.contains(query)) {
            return true;
        }
        return false;
    }
}
