public class Epic extends Task {
    protected String[] subtasks;

    public Epic(int id, String[] subtasks) {
        super(id);
        this.subtasks = subtasks;
    }

    public String[] getSubtasks() {
        return subtasks;
    }

//    @Override
//    public boolean matches(String query) {
//        boolean result = false;
//        if (subtasks != null && query != null) {
//            for (String subtask : subtasks) {
//                if (subtask != null && subtask.toLowerCase().contains(query.toLowerCase())) {
//                    result = true;
//                    break;
//                }
//            }
//        }
//        return result;
//    }
@Override
public boolean matches(String query) {
    if (subtasks == null || query == null) return false;
    for (String subtask : subtasks) {
        if (subtask != null && subtask.contains(query)) {
            return true;
        }
    }
    return false;
}


}
