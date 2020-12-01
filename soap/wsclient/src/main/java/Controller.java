import javafx.fxml.FXML;
import javafx.scene.control.*;
import ws.service.Library;
import ws.service.LibraryService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

enum METHODS { TAKE, RETURN, ADD_NEW, ADD_COPY }


public class Controller {
    @FXML private Button getAllBTM, takeBTM, returnBTM, addNewBTM, addCopyBTM, sendBTM;
    @FXML private TextField titleTF, isbnTF, pubYearTF, countAddTF;
    @FXML private TextArea resultTA, authorsTA, pubOfficeTA;
    @FXML private ToolBar tb;

    private METHODS method;
    private final Library library;

    public Controller() throws MalformedURLException {
        LibraryService service = new LibraryService(new URL("http://localhost:8080/Library?wsdl"));
        library = service.getLibraryPort();
    }

    public void sendBtmAct() {
        resultTA.clear();
        switch (method) {
            case TAKE: {
                boolean res = library.takeBook(new Integer(isbnTF.getText()));
                if (res)
                    resultTA.setText("Taking the book was successful");
                else
                    resultTA.setText("This book is out of stock");
                break;
            }
            case RETURN: {
                library.returnBook(new Integer(isbnTF.getText()));
                break;
            }
            case ADD_NEW: {
                library.addNewBook(
                        new Integer(isbnTF.getText()),
                        Arrays.asList(authorsTA.getText().split("\n")),
                        new Integer(pubYearTF.getText()),
                        pubOfficeTA.getText(),
                        titleTF.getText());
                break;
            }
            case ADD_COPY: {
                library.addCopyBook(new Integer(isbnTF.getText()), new Integer(countAddTF.getText()));
                break;
            }
        }
        setDis();
    }

    public void clientRbAct() {
        tb.setDisable(false);
        getAllBTM.setDisable(false);
        takeBTM.setDisable(false);
        returnBTM.setDisable(false);
        addNewBTM.setDisable(true);
        addCopyBTM.setDisable(true);
    }

    public void adminRbAct() {
        tb.setDisable(false);
        getAllBTM.setDisable(false);
        takeBTM.setDisable(true);
        returnBTM.setDisable(true);
        addNewBTM.setDisable(false);
        addCopyBTM.setDisable(false);
    }

    public void takeBtmAct() {
        method = METHODS.TAKE;
        setDis();
        isbnTF.setDisable(false);
        sendBTM.setDisable(false);
    }

    public void getAllBtmAct() {
        setDis();
        List<String> books = library.getAllBooks();
        StringBuilder stringBuilder = new StringBuilder();
        books.forEach(b -> stringBuilder.append(b).append("\n"));
        resultTA.setText(stringBuilder.toString());
    }

    public void returnBtmAct() {
        method = METHODS.RETURN;
        setDis();
        isbnTF.setDisable(false);
        sendBTM.setDisable(false);
    }

    public void addNewBtmAct() {
        method = METHODS.ADD_NEW;
        setDis();
        titleTF.setDisable(false);
        isbnTF.setDisable(false);
        authorsTA.setDisable(false);
        pubYearTF.setDisable(false);
        pubOfficeTA.setDisable(false);
        sendBTM.setDisable(false);
    }

    public void addCopyBtmAct() {
        method = METHODS.ADD_COPY;
        setDis();
        isbnTF.setDisable(false);
        countAddTF.setDisable(false);
        sendBTM.setDisable(false);
    }

    private void setDis() {
        titleTF.setDisable(true);
        isbnTF.setDisable(true);
        authorsTA.setDisable(true);
        pubYearTF.setDisable(true);
        pubOfficeTA.setDisable(true);
        countAddTF.setDisable(true);
        sendBTM.setDisable(true);
    }
}
