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
    public void move(int xPosition, int yPosition, boolean isWhite){
        String message = "";
        if(isWhite){
            message += "Белый ";
        } else{
            message += "Черный ";
        }
        message += " игрок поставил камень на (" + xPosition + ", " + yPosition + ")";
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
    public void wrongMove(boolean isWhite){
        String message = "";
        if(isWhite){
            message += "Белый игрок ";
        } else {
            message += "Черный игрок ";
        }
        message += "сделал неправильный ход";
        logger.warn(message);
    }
    public void deleteStones(int stoneNumber, boolean isWhite){
        String message = "";
        if (isWhite){
            message += "Белый ";
        } else {
            message += "Черный ";
        }
        message += "Игрок закрыл " + stoneNumber + " камней";
        logger.info(message);
    }

}
