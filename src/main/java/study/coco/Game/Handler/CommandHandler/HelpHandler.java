package study.coco.Game.Handler.CommandHandler;

import study.coco.Game.Game;

/**
 * This Handler reacts to ____ commands.
 */
public class HelpHandler extends CommandHandler {

    private static final String[] commands = {"h", "help", "?"};
    private static final String message = """
            •••••••••
            H I L F E
            •••••••••
            In diesem Spiel interagierst du lediglich über Kommandos, die du in das Terminal eingibst 
            und mit ENTER bestätigst.
            Auch die Rückmeldungen vom Spiel erhältst du in Textform.
            
            KOMMANDO-ÜBERSICHT:
            |========================|=================================================================|
            | KOMMANDO               | BESCHREIBUNG                                                    |
            |========================|=================================================================|
            | n / north              | Bewege dich auf dem Spielfeld nach NORDEN.                      |
            | e / east               | Bewege dich auf dem Spielfeld nach OSTEN.                       |
            | s / south              | Bewege dich auf dem Spielfeld nach SÜDEN.                       |
            | w / west               | Bewege dich auf dem Spielfeld nach WESTEN.                      |
            |------------------------|-----------------------------------------------------------------|
            | l / look               | Überblick über deinen aktuellen Ort                             |
            | l / look + <ITEM>      | Beschreibung über <ITEM> (in Inventar oder an einem Ort)        |
            | l / look + <RICHTUNG>  | Beschreibung des DURCHGANGS in der Himmelsrichtung (z.B. Türen) |
            |------------------------|-----------------------------------------------------------------|
            | t / take + <ITEM>      | Nimm das <ITEM> in dein INVENTAR auf.                           |
            | d / drop + <ITEM>      | Lege das <ITEM> im aktuellen Ort ab.                            |
            | u / use  + <ITEM>      | Benutze ein <ITEM> zum Öffnen eines DURCHGANGS.                 |
            | u / use  + <PASSWORT>  | Benutze ein <PASSWORT> zum Öffnen eines DURCHGANGS.             |
            |                        | (Du musst dich vorher in eine Himmelsrichtung bewegt haben.)    |
            |------------------------|-----------------------------------------------------------------|
            | i / inventory          | Überblick über den Inhalt deines INVENTARS                      |
            |------------------------|-----------------------------------------------------------------|
            | x / exit               | Verlasse das Spiel.                                             |
            | h / help               | Überblick über alle Kommandos (diese Tabelle)                   |
            |========================|=================================================================|
            
            Durch die Bewegungskommandos bewegst du dich auf dem Spielfeld hin und her.
            Einige DURCHGÄNGE zwischen zwei Orten sind verschlossen und müssen erst durch Benutzen
            eines ITEMS oder PASSWORTES geöffnet werden. PASSWÖRTER sind Zeichenketten aus Buchstaben
            und Zahlen.
            
            Schaue dich mit dem 'look'-Kommando oft um und erhalte nützliche Hinweise zum Lösen der Rätsel.
            """;
    private static final String type = "message";

    public HelpHandler(Game game) {
        super(game, type, commands, message);
    }

    
}
