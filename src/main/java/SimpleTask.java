public class SimpleTask extends Task {
    protected String title;

    public SimpleTask(int id, String title) {
        super(id);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

//    @Override
//    public boolean matches(String query) {
//        boolean result = false;
//        if (title != null && query != null) {
//            result = title.toLowerCase().contains(query.toLowerCase());
//        }
//        return result;
//    }
@Override
public boolean matches(String query) {
    if (title == null || query == null) return false;
    return title.contains(query);
}


}
