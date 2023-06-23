/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helpers;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 *
 * @author Emerson
 */
public class GenericCreateTableButton {

    public static <T> TableColumn<T, Void> createButtonColumn(Callback<T, Void> callback) {
        TableColumn<T, Void> buttonColumn = new TableColumn<>("Ações");

        buttonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("Excluir");

            {
                button.setOnAction(event -> {
                    T item = getTableRow().getItem();

                    if (item != null) {
                        try {
                            callback.call(item);
                        } catch (Exception e) {
                            System.out.print(e.getMessage());
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });

        return buttonColumn;
    }
}
