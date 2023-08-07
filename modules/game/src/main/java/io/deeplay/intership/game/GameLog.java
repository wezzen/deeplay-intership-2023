package io.deeplay.intership.game;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
public class GameLog {
    private static final Logger logger = Logger.getLogger(GameLog.class);

    public void init(){
        PropertyConfigurator.configure("log4j.properties");
    }
    public void startSession(){
        logger.info("Сессия началась");
    }
    public void startGame(int id){
        String message = "Началась игра под номером - " + id;
        logger.info(message);
    }
    public void introducePlayers(String playerBlack, boolean isHuman1, String playerWhite, boolean isHuman2){
        String message = "";
        message += "Подключились 2 игрока: " + "[";
        message += "Черными играет ";
        if(!isHuman1){
            message += "ИИ ";
        }
        message += playerBlack + ", ";
        message += "белыми играет ";
        if(!isHuman2){
            message += "ИИ ";
        }
        message += playerWhite + "]";
        logger.info(message);
    }
    public void move(Stone move, Color playerColor){
        String message = String.format("%s игрок поставил камень на (%d, %d)", chooseColor(playerColor), move.getRowNumber(), move.getColumnNumber());
        logger.info(message);
    }

    public void endGame(double totalScore){
        String message = "";
        if(totalScore > 0){
            message += "Белый игрок победил со счётом: ";
            message += totalScore;
        } else {
            message += "Черный игрок победил со счётом: ";
            totalScore = -1 * totalScore;
            message += totalScore;
        }
        logger.info(message);
    }
    public void endSession(){
        logger.info("Сессия закончилась");
    }
    public void wrongMove(Color playerColor){
        String message = String.format("%s игрок сделал неправильный ход", chooseColor(playerColor));
        logger.warn(message);
    }
    public void deleteStones(int stoneNumber, Color playerColor){
        String message = String.format("%s игрок закрыл %d камней",chooseColor(playerColor),stoneNumber);
        logger.info(message);
    }
    public void skipMove(Color playerColor){
        String message = String.format("%s игрок пропустил ход",chooseColor(playerColor));
        logger.info(message);
    }
    private String chooseColor(Color color){
        String playersColor = "";
        if (color == Color.WHITE){
            playersColor += "Белый";
        } else {
            playersColor += "Черный";
        }
        return playersColor;
    }
}
