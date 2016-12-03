package edu.eezo.data;

/**
 * Interface for model objects than can be viewed at JTable.
 * Created by Eezo on 11.11.2016.
 */
public interface ITableViewable {

    /**
     * Returns an array of objects that represent a single data row for JTable.<br><br>
     * <p>
     * <b>IMPORTANT:</b> Implementing class also must have the "getTableColumnsIdentifiers()" method to indicate column titles for JTable.
     * See example:<br>
     * <code><pre>
     * public static String[] getTableColumnsIdentifiers() {
     *      return new String[]{"ID", "Timing", "Client", "Origin", "Destination"};
     * }</pre></code><br>
     * <b>NOTE:</b> <code>String[].length == Object[].length</code>
     *
     * @return an array of objects
     */
    Object[] getTableRowData();
}