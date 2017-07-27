package agrawal.snehal.searchview.interfaces;

public interface onSimpleSearchActionsListener {
    void onItemClicked(String item);
    void onScroll();
    void error(String localizedMessage);
}
