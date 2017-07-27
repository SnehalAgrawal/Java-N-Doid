package agrawal.snehal.searchview.interfaces;

public interface onSearchListener {
    void onSearch(String query);
    void searchViewOpened();
    void searchViewClosed();
    void onCancelSearch();
}
